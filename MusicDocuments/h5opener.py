#Cole Hannel
#CSCI 191
#This started by taking some of the tutorial code for using the million song data set and modifying it for my purposes
#the goal of the program is to take some subset of the million song dataset(it's too big) and build a tree to determine
#which songs are metal and which songs are not.

# usual imports
import os
import sys
import time
import glob
import datetime
import sqlite3
import numpy as np # get it at: http://numpy.scipy.org/
# path to the Million Song Dataset subset (uncompressed)
# CHANGE IT TO YOUR LOCAL CONFIGURATION
msd_subset_path='/home/xolejh/MusicDocuments/MillionSongSubset'
msd_subset_data_path=os.path.join(msd_subset_path,'data')
msd_subset_addf_path=os.path.join(msd_subset_path,'AdditionalFiles')
assert os.path.isdir(msd_subset_path),'wrong path' # sanity check
# path to the Million Song Dataset code
# CHANGE IT TO YOUR LOCAL CONFIGURATION
msd_code_path='/home/xolejh/MusicDocuments/MSongsDBmaster'
assert os.path.isdir(msd_code_path),'wrong path' # sanity check
# we add some paths to python so we can import MSD code
# Ubuntu: you can change the environment variable PYTHONPATH
# in your .bashrc file so you do not have to type these lines
sys.path.append( os.path.join(msd_code_path,'PythonSrc') )

# imports specific to the MSD
import hdf5_getters as GETTERS

# the following function simply gives us a nice string for
# a time lag in seconds
def strtimedelta(starttime,stoptime):
    return str(datetime.timedelta(seconds=stoptime-starttime))

# we define this very useful function to iterate the files
def apply_to_all_files(basedir,func=lambda x: x,ext='.h5'):
    """
    From a base directory, go through all subdirectories,
    find all files with the given extension, apply the
    given function 'func' to all of them.
    If no 'func' is passed, we do nothing except counting.
    INPUT
       basedir  - base directory of the dataset
       func     - function to apply to all filenames
       ext      - extension, .h5 by default
    RETURN
       number of files
    """
    cnt = 0
    # iterate over all files in all subdirectories
    for root, dirs, files in os.walk(basedir):
        files = glob.glob(os.path.join(root,'*'+ext))
        # count files
        cnt += len(files)
        # apply function to all files
        for f in files :
            func(f)       
    return cnt

# we can now easily count the number of files in the dataset
#print 'number of song files:',apply_to_all_files(msd_subset_data_path)


genre_list = []


#year of release, duration, loudness, timbre, pitch, tempo, hotness, familiarity,
measures_taken = [False, False, False, False, False, False, False, False]

number_of_metal_songs = 0.0
metal_loudness = 0.0
metal_length = 0.0
metal_energy = 0.0
metal_hotness = 0.0
metal_loudness_max_average = 0.0
metal_timbre_average = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]

other_loudness = 0.0
other_length = 0.0
other_energy = 0.0
other_hotness = 0.0
other_loudness_max_average = 0.0
other_timbre_average = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]

def entropy_measure_2d(theArray, low, high, point):
  positive = 0.0
  negative = 0.0
  pcount = 0.0 
  ncount = 0.0
  ent = 0.0
  for row in theArray: #row contains something with the structure [[0.0,0.0,...0.0], 1 or 0]
    if row[0][point] > high or row[0][point] < low:
      negative += row[1]
      ncount += 1.0
    else:
      positive += row[1]
      pcount += 1.0
  from math import log
  log2=lambda x: log(x)/log(2) if x != 0.0 else 0.0
  if pcount != 0.0 and ncount != 0.0 and positive != 0.0 and negative != 0.0:
    ent = abs(((positive/pcount)*log2((positive/pcount)) + ((pcount-positive)/pcount)*log2(((pcount-positive)/pcount)))*(pcount/(pcount+ncount)) + ((negative/ncount)*log2((negative/ncount)) + ((ncount-negative)/ncount)*log2(((ncount-negative)/ncount)))*(ncount/(pcount+ncount)))
  elif pcount != 0.0 and positive != 0.0:
    ent = abs((positive/pcount)*log2((positive/pcount)) + ((pcount-positive)/pcount)*log2(((pcount-positive)/pcount)))
  elif ncount != 0.0 and negative != 0.0:  
    ent = abs((negative/ncount)*log2((negative/ncount)) + ((ncount-negative)/ncount)*log2(((ncount-negative)/ncount)))
  return ent

def entropy_measure_1d(theArray, low, high):
  positive = 0.0
  negative = 0.0
  pcount = 0.0
  ncount = 0.0
  ent = 0.0
  for row in theArray:
    if row[0] > high or row[0] < low:
      negative += row[1]
      ncount += 1.0
    else:
      positive += row[1]
      pcount += 1.0
  from math import log
  log2=lambda x: log(x)/log(2) if x != 0.0 else 0.0
  if pcount != 0.0 and ncount != 0.0 and positive != 0.0 and negative != 0.0:
    ent = abs(((positive/pcount)*log2((positive/pcount)) + ((pcount-positive)/pcount)*log2(((pcount-positive)/pcount)))*(pcount/(pcount+ncount)) + ((negative/ncount)*log2((negative/ncount)) + ((ncount-negative)/ncount)*log2(((ncount-negative)/ncount)))*(ncount/(pcount+ncount)))
  elif pcount != 0.0 and positive != 0.0:
    ent = abs((positive/pcount)*log2((positive/pcount)) + ((pcount-positive)/pcount)*log2(((pcount-positive)/pcount)))
  elif ncount != 0.0 and negative != 0.0:  
    ent = abs((negative/ncount)*log2((negative/ncount)) + ((ncount-negative)/ncount)*log2(((ncount-negative)/ncount)))
  return ent

def entropy_generator_1d(theArray):
  lowest_value = theArray[0][0]
  highest_value = theArray[-1][0]
  curr_entropy = 1.0
  gap = 0.0
  l_measure = 0
  r_measure = 0
  #find array extremes
  for row in theArray:
    if lowest_value > row[0]:
      lowest_value = row[0]
    if highest_value < row[0]:
      highest_value = row[0]
  gap = min(1.0, (highest_value-lowest_value)/30.0)
  #measure down
  curr_entropy = entropy_measure_1d(theArray, lowest_value, highest_value)
  while (l_measure == 0 or r_measure == 0):
    if l_measure >= 0 and curr_entropy >= entropy_measure_1d(theArray, lowest_value+gap, highest_value):
      curr_entropy = entropy_measure_1d(theArray, lowest_value+gap, highest_value)
      lowest_value += gap
      r_measure = 0
    elif l_measure >= 0 and curr_entropy > entropy_measure_1d(theArray, lowest_value-gap, highest_value):
      curr_entropy = entropy_measure_1d(theArray, lowest_value-gap, highest_value)
      lowest_value -= gap
      r_measure = 0
    else:
      l_measure = -1
    if r_measure >= 0 and curr_entropy > entropy_measure_1d(theArray, lowest_value, highest_value+gap):
      curr_entropy = entropy_measure_1d(theArray, lowest_value, highest_value+gap)
      highest_value += gap
      l_measure = 0
    elif r_measure >= 0 and curr_entropy >= entropy_measure_1d(theArray, lowest_value, highest_value-gap):
      curr_entropy = entropy_measure_1d(theArray, lowest_value, highest_value-gap)
      highest_value -= gap
      l_measure = 0
    else:
      r_measure = -1
    if lowest_value >= highest_value:
      temp = highest_value
      highest_value = lowest_value
      lowest_value = temp
      l_measure = -1
      r_measure = -1
  return [lowest_value, highest_value, curr_entropy]

#for this one, lowest value and highest value are actually going to be arrays
#I shudder at the thought of how time consuming this will be
def entropy_generator_2d(theArray):
  averaged_Arrays = []
  for row in theArray:
    count = 0.0
    item_Ave = [[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0], [0.0]]
    for x in row[0]:
      for i in range(12):
        item_Ave[i] += x[i]
      count +=1.0
    for i in range(12):
      item_Ave[i] = item_Ave[i]/count
    averaged_Arrays.append([item_Ave, row[1]])
  #first need to average out the array
  lowest_value = theArray[0][0][0]
  highest_value = theArray[-1][0][0]
  curr_entropy = [1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0, 1.0]
  gap = 0.0
  l_measure = 0
  r_measure = 0
  #find array extremes
  for row in averaged_Arrays:
    for x in range(12):
      if lowest_value[x] > row[0][x]:
        lowest_value[x] = row[0][x]
      if highest_value[x] < row[0][x]:
        highest_value[x] = row[0][x]
  gap = (highest_value[0]-lowest_value[0])/30.0
  #measure down
  for x in range(12):
    curr_entropy[x] = entropy_measure_2d(averaged_Arrays, lowest_value[x], highest_value[x], x)
    l_measure = 0
    r_measure = 0
    while l_measure >= 0 or r_measure >= 0:
      if l_measure >= 0 and curr_entropy[x] >= entropy_measure_2d(averaged_Arrays, lowest_value[x]+gap, highest_value[x], x):
        curr_entropy[x] = entropy_measure_2d(averaged_Arrays, lowest_value[x]+gap, highest_value[x], x)
        lowest_value[x] += gap
        r_measure = 0
      elif l_measure >= 0 and curr_entropy[x] > entropy_measure_2d(averaged_Arrays, lowest_value[x]-gap, highest_value[x], x):
        curr_entropy[x] = entropy_measure_2d(averaged_Arrays, lowest_value[x]-gap, highest_value[x], x)
        lowest_value[x] -= gap
        r_measure = 0
      else:
        l_measure = -1
      if r_measure >= 0 and curr_entropy[x] > entropy_measure_2d(averaged_Arrays, lowest_value[x], highest_value[x]+gap, x):
        curr_entropy[x] = entropy_measure_2d(averaged_Arrays, lowest_value[x], highest_value[x]+gap, x)
        highest_value[x] += gap
        l_measure = 0
      elif r_measure >= 0 and curr_entropy[x] >= entropy_measure_2d(averaged_Arrays, lowest_value[x], highest_value[x]-gap, x):
        curr_entropy[x] = entropy_measure_2d(averaged_Arrays, lowest_value[x], highest_value[x]-gap, x)
        highest_value[x] -= gap
        l_measure = 0
      else:
        r_measure = -1
      if lowest_value[x] >= highest_value[x]:
        temp = highest_value[x]
        highest_value[x] = lowest_value[x]
        lowest_value[x] = temp
        l_measure = -1
        r_measure = -1
  return [lowest_value, highest_value, curr_entropy]

#year of release, duration, pitch, tempo, hotness, familiarity,loudness, timbre,
def tree_builder(availableData, limit, tested):
  metal_count = 0
  song_total = 0
  new_tested = []
  for i in tested:
    new_tested.append(i)
  entropy_comparison = [[],[],[],[],[],[]]
  entropy_comparison_2d = [[],[]]
  year_of_release, duration, loudness, timbre, pitch, tempo, hotness, familiarity = [], [], [], [], [], [], [], []
  #get all data for this leaf's available data
  for filename in availableData:
    song_total += 1
    isMetal = 0
    h5 = GETTERS.open_h5_file_read(filename)
    some_item = GETTERS.get_artist_terms(h5)
    for term in some_item:
      if term.find('black metal') != -1:
        isMetal = 1
    if isMetal == 1:
      metal_count += 1
    year_of_release.append([GETTERS.get_year(h5),isMetal])
    duration.append([GETTERS.get_duration(h5),isMetal])
    loudness.append([GETTERS.get_loudness(h5),isMetal])
    timbre.append([GETTERS.get_segments_timbre(h5),isMetal])
    pitch.append([GETTERS.get_segments_pitches(h5),isMetal])
    tempo.append([GETTERS.get_tempo(h5),isMetal])
    hotness.append([GETTERS.get_artist_hotttnesss(h5),isMetal])
    familiarity.append([GETTERS.get_artist_familiarity(h5),isMetal])
    h5.close()
  #compare entropies
  if not tested[0]:
    entropy_comparison[0] = entropy_generator_1d(year_of_release)
  else:#guarantees won't be best choice
    entropy_comparison[0] = [0.0, 0.0, 1.1]  
  if not tested[1]:
    entropy_comparison[1] = entropy_generator_1d(duration)
  else:
    entropy_comparison[1] = [0.0, 0.0, 1.1]  
  if not tested[2]:
    entropy_comparison[2] = entropy_generator_1d(loudness)
  else:
    entropy_comparison[2] = [0.0, 0.0, 1.1]  
  if not tested[3]:
    entropy_comparison[3] = entropy_generator_1d(tempo)
  else:
    entropy_comparison[3] = [0.0, 0.0, 1.1]  
  if not tested[4]:
    entropy_comparison[4] = entropy_generator_1d(hotness)
  else:
    entropy_comparison[4] = [0.0, 0.0, 1.1]  
  if not tested[5]:
    entropy_comparison[5] = entropy_generator_1d(familiarity)
  else:
    entropy_comparison[5] = [0.0, 0.0, 1.1]  
  if not tested[6]:
    entropy_comparison_2d[0] = entropy_generator_2d(timbre)
  else:
    entropy_comparison_2d[0] = [[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],[1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1]]  
  if not tested[7]:
    entropy_comparison_2d[1] = entropy_generator_2d(pitch)
  else:
    entropy_comparison_2d[1] = [[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],[1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1]]  
  count, biggest_entropy = 1.0, entropy_comparison[0][2]
  for i in range(6):
    if entropy_comparison[i][2] < biggest_entropy:
      biggest_entropy = entropy_comparison[i][2]
      count = i
  for i in range(2):
    for j in range(12):
      if entropy_comparison_2d[i][2][j] < biggest_entropy:
        biggest_entropy = entropy_comparison_2d[i][2][j]
        count = (i*11)+j+6  
  #after comaprisons, it is time to divide the array and continue the recursion
  if count == 0:
    print "year", " ", entropy_comparison[0][0]," ", entropy_comparison[0][1], " ", biggest_entropy
    new_tested[0] = True
  elif count == 1:
    print "duration", " ", entropy_comparison[1][0]," ", entropy_comparison[1][1], " ", biggest_entropy
    new_tested[1] = True
  elif count == 2:
    print "loudness", " ", entropy_comparison[2][0]," ", entropy_comparison[2][1], " ", biggest_entropy
    new_tested[2] = True
  elif count == 3:
    print "tempo", " ", entropy_comparison[3][0]," ", entropy_comparison[3][1], " ", biggest_entropy
    new_tested[3] = True
  elif count == 4:
    print "hotness", " ", entropy_comparison[4][0]," ", entropy_comparison[4][1], " ", biggest_entropy
    new_tested[4] = True
  elif count == 5:
    print "familiarity", " ", entropy_comparison[5][0]," ", entropy_comparison[5][1], " ", biggest_entropy
    new_tested[5] = True
  elif count >= 6 and count < 18:
    print "timbre ", count-6, " ", entropy_comparison_2d[0][0][count-6]," ", entropy_comparison_2d[0][1][count-6], " ", biggest_entropy
    new_tested[6] = True
  else:
    print "tempo", count-18, " ", entropy_comparison_2d[1][0][count-18]," ", entropy_comparison_2d[1][1][count-18], " ", biggest_entropy
    new_tested[7] = True
  left_split, right_split = [], [] 
  for filename in availableData:
    h5 = GETTERS.open_h5_file_read(filename)
    if count == 0:
      b = GETTERS.get_year(h5)
      if b < entropy_comparison[0][0] or b > entropy_comparison[0][1]:
        left_split.append(filename)
      else:
        right_split.append(filename)
    elif count == 1:
      b = GETTERS.get_duration(h5)
      if b < entropy_comparison[1][0] or b > entropy_comparison[1][1]:
        left_split.append(filename)
      else:
        right_split.append(filename)
    elif count == 2:
      b = GETTERS.get_loudness(h5)
      if b < entropy_comparison[2][0] or b > entropy_comparison[2][1]:
        left_split.append(filename)
      else:
        right_split.append(filename)
    elif count == 3:
      b = GETTERS.get_tempo(h5)
      if b < entropy_comparison[3][0] or b > entropy_comparison[3][1]:
        left_split.append(filename)
      else:
        right_split.append(filename)
    elif count == 4:
      b = GETTERS.get_artist_hotttnesss(h5)
      if b < entropy_comparison[4][0] or b > entropy_comparison[4][1]:
        left_split.append(filename)
      else:
        right_split.append(filename)
    elif count == 5:
      b = GETTERS.get_artist_familiarity(h5)
      if b < entropy_comparison[5][0] or b > entropy_comparison[5][1]:
        left_split.append(filename)
      else:
        right_split.append(filename)
    elif count > 5 and count <= 17:
      b = GETTERS.get_segments_timbre(h5)#b is the problem. It contains something that looks like [[x,...x],[x,...x]]
      item_Ave = [[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0], [0.0]]
      bcount = 0
      for row in b:
        for i in range(12):
          item_Ave[i] += row[i]
        bcount +=1
      for i in range(12):
        item_Ave[i] = item_Ave[i]/bcount
      if item_Ave[count-6] < entropy_comparison_2d[0][0][count-6] or item_Ave[count-6] > entropy_comparison_2d[0][1][count-6]:
        left_split.append(filename)
      else:
        right_split.append(filename)
    else:
      b = GETTERS.get_segments_pitches(h5)
      item_Ave = [[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0],[0.0], [0.0]]
      bcount = 0
      for row in b:
        for i in range(12):
          item_Ave[i] += row[i]
        bcount +=1
      for i in range(12):
        item_Ave[i] = item_Ave[i]/bcount
      if item_Ave[count-18] < entropy_comparison_2d[1][0][count-18] or item_Ave[count-18] > entropy_comparison_2d[1][1][count-18]:
        left_split.append(filename)
      else:
        right_split.append(filename)
    h5.close()
  del year_of_release
  del duration
  del loudness
  del timbre
  del pitch
  del tempo
  del hotness
  del familiarity
  del entropy_comparison
  del entropy_comparison_2d
  if(limit<4) and left_split != [] and right_split != []:
    print new_tested
    print "left"
    tree_builder(left_split, limit+1, new_tested)
    print "right"
    tree_builder(right_split, limit+1, new_tested)
  else:
    print "end node"
    print metal_count, " metal count"
    print song_total, " song count"

def begin_hunt(basedir,ext='.h5'):
  bigArray = []
  for root, dirs, files in os.walk(basedir):
    files = glob.glob(os.path.join(root,'*'+ext))
    for f in files :
      bigArray.append(f)
  tree_builder(bigArray, 0, [False, False, False, False, False, False, False, False])             

begin_hunt(msd_subset_data_path)
#apply_to_all_files(msd_subset_data_path,func=func_to_get_loudness_average)
#print number_of_metal_songs
#print metal_loudness/number_of_metal_songs
#print other_loudness/(10000-number_of_metal_songs)
#print metal_length/number_of_metal_songs
#print other_length/(10000-number_of_metal_songs)
#print metal_energy/number_of_metal_songs
#print other_energy/(10000-number_of_metal_songs)
#print metal_hotness/number_of_metal_songs
#print other_hotness/(10000-number_of_metal_songs)
#for x in range(11):
#  metal_timbre_average[x] = metal_timbre_average[x]/number_of_metal_songs
#for x in range(11):
#  other_timbre_average[x] = other_timbre_average[x]/(10000-number_of_metal_songs)
#print metal_timbre_average
#print other_timbre_average
#print metal_loudness_max_average/number_of_metal_songs
#print other_loudness_max_average/(10000-number_of_metal_songs)


#need to 
