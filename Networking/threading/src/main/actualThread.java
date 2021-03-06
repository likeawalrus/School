/*////////////////////////////
Cole Hannel
This code takes two matrices of appropriate sizes(L*M and M*N)
and multiplies them together. The first two matrices are filled
with the values i+j and i+j+1 respectively where i and j are the
coordinates in the matrices. The expected input for this program
is L, M, N, and the number of threads
Output is the first and last parts of the resulting matrix
/////////////////////////////*/

package main;

public class actualThread extends Thread{

	static int L, M, N, threadCount;
	private long myRank;
	/**
	 * @param args
	 */
	actualThread(long rank){
		myRank = rank;
	}
	
	public void run() 
	{
		  int my_rank = (int)myRank; //rank is void* type, so can cast to (long) type only; 
		  int Q = threading.L/threading.thread_count;
		  int R = threading.L%threading.thread_count;
		  int my_first_row;
		  int my_last_row;
		  Double temp = 0.0;
		  if(my_rank<R){
		    my_first_row = my_rank*(Q+1);
		    my_last_row = my_first_row+Q;
		  }
		  else{
		    my_first_row = my_rank * Q+R;
		    my_last_row = my_first_row+(Q-1);
		  }		  
		  //This is where the math will happen
		  for(int i = my_first_row; i<=my_last_row;i++){
		    for(int j = 0; j<threading.N; j++){
		      for(int k = 0; k < threading.M; k++){
		        temp += threading.matrixA[i][k] * threading.matrixB[k][j];
		      }
		      threading.matrixC[i][j] = temp;
		      temp = 0.0;
		    }
		  }
		}

}
