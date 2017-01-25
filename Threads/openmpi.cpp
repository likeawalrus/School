//Cole Hannel
//This program takes a problem size and then creates an array
//of that size. The array is sorted through a combination of 
//MPI and sorting.

#include <iostream>  
#include <stdlib.h>
#include <mpi.h> 	   
#include <algorithm> 

using namespace std;

int compare(const void *ap, const void *bp);
int *merge_array(int *A, int *B, int size);

int main(int argc, char *argv[]) {

	int n = atoi(argv[1]); // n is the size of the problem
	int communication_size; //mpi communication size 
	int my_rank;
	int *my_array;

	MPI_Init(NULL, NULL); 
	MPI_Comm_size(MPI_COMM_WORLD, &communication_size); 
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank); 

	if (my_rank == 0){ //The main thread
		for(int i = 1; i < communication_size; i++){
			MPI_Send(&n, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
			int size = n/communication_size;
			int *theArray = new int[size];
			srand(my_rank + 1);
			for(int i = 0; i < size; i++)
				theArray[i] = rand() % 100;
			my_array = theArray;
			qsort(my_array, n/communication_size, sizeof(int), compare);
		}
	} else{ 
		MPI_Recv(&n, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);//receiving 
		int size = n/communication_size;
		int *theArray = new int[size];
		srand(my_rank + 1);
		for(int i = 0; i < size; i++)
			theArray[i] = rand() % 100;
		my_array = theArray;
		qsort(my_array, n/communication_size, sizeof(int), compare); //built in sorting
	}
	
	cout << "From process " << my_rank << endl;
	for(int i = 0; i < n/communication_size; i++){
		cout << my_array[i] << " ";
	}
	cout << endl;
	int partner;
	int div = 2;
	int diff = 1;
	bool fin = false;
	int *temp_array;
	int sizeOfArray = n/communication_size;
	while(div <= communication_size && !fin){
		if (my_rank % div == 0){
			partner = my_rank + diff;
			temp_array = new int[sizeOfArray];  
			MPI_Recv(temp_array, sizeofArray, MPI_INT, partner, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			my_array = merge_array(my_array, temp_array, sizeOfArray);
			sizeOfArray *= 2;			
			free(temp_array); //prevent memory leak
		} else {
			partner = my_rank - diff; //otherwise send it
			MPI_Send(my_array, sizeOfArray, MPI_INT, partner, 0, MPI_COMM_WORLD); 
			fin = true;
		}
		div *= 2;
		diff *= 2;
	}


	if (my_rank == 0){
		cout << "sorted list: " << endl;
		for(int i = 0; i < sizeOfArray; i++){
			cout << my_array[i] << " ";
		}
		cout << endl;
	}

	free(my_array);
	MPI_Finalize(); 
   	return 0;
}


//takes two arrays and then uses the merge function
int *merge_array(int *A, int *B, int size){
	int *temp = new int[size * 2];
	merge(A, A + size, B, B + size, temp);
	return temp;
}

//comparison for things
int compare(const void *a, const void *b){
	return (*(int*)a - *(int*)b);
}
