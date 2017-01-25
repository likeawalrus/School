#include <iostream>
#include <omp.h>
#include <sys/time.h>
#include <stdlib.h>
using namespace std;

int thread_count;
int N;

void serial_count_sort(int theArray[], int n)
{
	int i, j, count;
	int *temp = new int[n];
	for(i = 0; i < n; i++)
	{
		count = 0;
		for(j = 0; j < n; j++)
		{
			if(theArray[j] < theArray[i])
				count++;
			else if(theArray[j] == theArray[i] && j < i)
				count++;
		}
		temp[count] = theArray[i];
	}
	for(i = 0; i < n; i++){
		theArray[i] = temp[i];
	}
	free(temp);
}

void parallel_count_sort1(int theArray[], int n)
{
	int i, j, k, count;
	int *temp = new int[n];
	# pragma omp parallel for num_threads(thread_count) \
			default(none) shared(theArray, n, temp) private(i, j, count)
	for(i = 0; i < n; i++)
	{
		count = 0;
		for(j = 0; j < n; j++)
		{
			if(theArray[j] < theArray[i])
				count++;
			else if(theArray[j] == theArray[i] && j < i)
				count++;
		}
		temp[count] = theArray[i];
	}
	for(k = 0; k < n; k++)
		theArray[k] = temp[k];
	free(temp);
}

void parallel_count_sort2(int theArray[], int n)
{
	int i, j, k, count;
	int *temp = new int[n];
	# pragma omp parallel for num_threads(thread_count) \
			default(none) shared(theArray, n, temp) private(i, j, count)
	for(i = 0; i < n; i++)
	{
		count = 0;
		for(j = 0; j < n; j++)
		{
			if(theArray[j] < theArray[i])
				count++;
			else if(theArray[j] == theArray[i] && j < i)
				count++;
		}
		temp[count] = theArray[i];
	}
	# pragma omp parallel for num_threads(thread_count) \
			default(none) shared(theArray, n, temp) private(k)
	for(k = 0; k < n; k++)
		theArray[k] = temp[k];
	free(temp);
}

// Checks if the arrays were sorted correctly
bool sortedCheck(int theArray[])
{
	for(int i = 0; i < N-1; i++)
	{
		if(theArray[i] > theArray[i+1] )
			return false;
	}
	return true;
}

// Prints the given array
void printArray(int theArray[])
{
	cout << "first 20"<<endl;
	for(int i = 0; i < 20; i++)
	{
		cout << theArray[i] << ", ";
	}
	cout << endl;
	cout << "last 20"<<endl;
	for(int i = N - 20; i < N; i++)
	{
		cout << theArray[i] << ", ";
	}
}

int main(int argc, char* argv[])
{
	thread_count = atoi(argv[1]);
	N = atoi(argv[2]);
  clock_t start, stop;
	int *A = new int[N];
	int *B = new int[N];
	int *C = new int[N];
  int my_rand=0;
	for(int i = 0; i < N; i++)
	{
    my_rand = rand() % N;
		A[i] = my_rand;
		B[i] = my_rand;
		C[i] = my_rand;
	}
	cout<<"Original A"<<endl;
	printArray(A);
  start = clock();
	serial_count_sort(A, N);
	stop = clock();
	cout << "Final A"<<endl;
	printArray(A);
	cout<<"It is ";
  if(sortedCheck(A)){
		cout<<"Sorted"<<endl;
	}
	else{
	  cout<<"Not Sorted"<<endl;
  }
	cout<<"And took "<<((float)stop -(float)start)/CLOCKS_PER_SEC<<"seconds"<<endl;

	cout<<"Original B"<<endl;
	printArray(B);
  start = clock();
	parallel_count_sort1(B, N);
	stop = clock();
	cout << "Final B"<<endl;
	printArray(B);
	cout<<"It is ";
  if(sortedCheck(B)){
		cout<<"Sorted"<<endl;
	}
	else{
	  cout<<"Not Sorted"<<endl;
  }
	cout<<"And took "<<((float)stop -(float)start)/CLOCKS_PER_SEC<<"seconds"<<endl;

	cout<<"Original C"<<endl;
	printArray(C);
  start = clock();
	parallel_count_sort2(C, N);
	stop = clock();
	cout << "Final C"<<endl;
	printArray(C);
	cout<<"It is ";
  if(sortedCheck(C)){
		cout<<"Sorted"<<endl;
	}
	else{
	  cout<<"Not Sorted"<<endl;
  }
	cout<<"And took "<<((float)stop -(float)start)/CLOCKS_PER_SEC<<"seconds"<<endl;

	return 0;
}
