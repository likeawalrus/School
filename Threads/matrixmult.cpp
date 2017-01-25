/*////////////////////////////
Cole Hannel
This code takes two matrices of appropriate sizes(L*M and M*N)
and multiplies them together. The first two matrices are filled
with the values i+j and i+j+1 respectively where i and j are the
coordinates in the matrices. The expected input for this program
is L, M, N, and the number of threads
Output is the first and last parts of the resulting matrix
/////////////////////////////*/

#include <iostream>
#include <cstdlib> //for atoi()
#include <pthread.h> 
#include <sys/time.h> 
using namespace std;

//globals --accessible to all threads
int thread_count;       //for command line arg
pthread_mutex_t mymutex;
double** matrixA;
double** matrixB;
double** matrixC;
int L, M, N;
void *matrix_mult(void* rank); //prototype for a Thread function

///////////////////
int main(int argc, char* argv[]) 
{
  clock_t t;
  t = clock();
  long thread_id; //long for type conversion [long<-->void*] for 64 bit system
  pthread_mutex_init(&mymutex, NULL);
  L = atoi(argv[1]);//Expected input is L, M, N, threads
  M = atoi(argv[2]);
  N = atoi(argv[3]);
  cout<<L<<","<<M<<","<<N<<endl;
  double** columnA = new double*[L];
  matrixA = columnA;
  double** columnB = new double*[M];
  matrixB = columnB;
  double** columnC = new double*[L];
  matrixC = columnC;
  thread_count = atoi(argv[4]); //Number of threads
  cout<<thread_count<<" thread"<<endl;
//main is the one responsible for filling in the matrixes
  pthread_t myThreads[thread_count]; //define threads 
  for(int i = 0; i < L; i++){ //Main initiates the values for matrix A
    double* row = new double[M];
    matrixA[i] = row;
      for(int j = 0; j < M; j++){
        matrixA[i][j] = i+j;
      }
  }

  for(int i = 0; i < M; i++){ //Main initiates the values for matrix B
    double* row = new double[N];
    matrixB[i] = row;
      for(int j = 0; j < N; j++){
        matrixB[i][j] = i+j+1;
      }
  }
  for(int i = 0; i < L; i++){ //Initiate C to but set everything to 0
    double* row = new double[N];
    matrixC[i] = row;
      for(int j = 0; j < N; j++){
        matrixC[i][j] = 0.0;
      }
  }
  //creates a certain number of threads
  for(thread_id = 0; thread_id < thread_count; thread_id++){  
     pthread_create(&myThreads[thread_id], NULL, matrix_mult, (void*)thread_id);  
  }
  //pthread_mutex_lock(&mymutex);
  //cout<<"Hello from the main thread"<<endl;
  //pthread_mutex_unlock(&mymutex);
  //wait until all threads finish
  for(thread_id = 0; thread_id < thread_count; thread_id++) 
     pthread_join(myThreads[thread_id], NULL); 
  //Now to view the output
  for(int i = 0; i<L && i<20;i++){
    for(int j = 0; j<N && j<10;j++){
        cout<<matrixC[i][j]<<" ";
      }
    cout<<endl;
  }
  //Last part printed out
  cout<<"End of the matrix"<<endl;
  if(L>20&& N>10){
    for(int i = std::max(0,L-20); i<L;i++){
      for(int j = std::max(0,(N-10)); j<N;j++){
        cout<<matrixC[i][j]<<" ";
      }
      cout<<endl;
    }
  }
  t = clock() - t;
  cout<<"Total time taken is "<<((float)t)/CLOCKS_PER_SEC<<" seconds"<<endl;

  return 0;
}//main

//////////////////slave function
void *matrix_mult(void* rank) 
{

  int my_rank = (long)rank; //rank is void* type, so can cast to (long) type only; 
  int Q = L/thread_count;
  int R = L%thread_count;
  int my_first_row;
  int my_last_row;
  if(my_rank<R){
    my_first_row = my_rank*(Q+1);
    my_last_row = my_first_row+Q;
  }
  else{
    my_first_row = my_rank * Q+R;
    my_last_row = my_first_row+(Q-1);
  }
  pthread_mutex_lock(&mymutex);
  //This is where the math will happen
  for(int i = my_first_row; i<=my_last_row;i++){
    for(int j = 0; j<N; j++){
      for(int k = 0; k < M; k++){
        matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
      }
    }
  }
  pthread_mutex_unlock(&mymutex); 
  return NULL;
}
