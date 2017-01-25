//Cole Hannel
//CSCI 176 Program 1
//Program takes a command line input that is a number n and returns the time it
//takes to iteratively and recursive reach the nth Fibonacci number
//Program uses four processes, one control process and one process for each of
//the methods of running Fibo. It uses pipes to communicate between the processes

#include <unistd.h>
#include <cstdlib>
#include <iostream>
#include <string>
#include <sstream>
#include <ctime>
#include <wait.h>


using namespace std;

long iterativeFibo(int);
long recursiveFibo(int);

int main(int argc, char* argv[]){
    int pid[4];
    int satus, i;
    int p1[2],p2[2], p3[2], p4[2];
    istringstream ss(argv[1]);
    int x, y;
    int res1, res2;
    double timeiterative, timerecursive, timecontit = 0.0, timecontrec = 0.0;
    ss >> x;
    y = x;
    //I dislike having lots of spare processes that do nothing
    //So I came up with this as a method to get three children
    //and only three children using forks
    pid[0]=0;pid[1]=0;pid[2]=0;pid[3]=0;

    pipe(p1); pipe(p2);pipe(p3);pipe(p4);
    pid[0] = fork();
    for(i = 1; i <= 2; i ++){
	if(pid[i-1] != 0)
    	    pid[i] = fork();
    }
	//cout <<pid[0]<<" "<<pid[1]<<" "<<pid[2]<<endl;
	//control process
        if(pid[0] == 0 && pid[1] == 0 && pid[2] == 0){
            write(p1[1], &x, sizeof(int));
            write(p2[1], &y, sizeof(int));
            //close(p1[0]);close(p2[0]);
            while(read(p4[0], &timecontit, sizeof(double)) <= 0);
            while(read(p3[0], &timecontrec, sizeof(double)) <= 0);
            cout<<"Recursive took "<<timecontrec<<" seconds"<<endl;
            //cout<<"Recursive Number "<<a<<endl;
            cout<<"Iterative took "<<timecontit<<" seconds"<<endl;
            //cout<<"Iterative number "<<a<<endl;

        }//recursive process
        else if(pid[1] == 0 && pid[2] == 0){
            read(p1[0], &res1, sizeof(int));
            clock_t begin1 = clock();
            long answer = recursiveFibo(res1);
            clock_t end1 = clock();
            timerecursive = double(end1 - begin1) / CLOCKS_PER_SEC;
            cout<<"Recursive Fib number "<<answer<<endl;
            write(p3[1], &timerecursive, sizeof(double));
        }//iterative process
        else if(pid[2] == 0){
            read(p2[0], &res2, sizeof(int));
            clock_t begin1 = clock();
            long answer = iterativeFibo(res2);
            clock_t end1 = clock();
            timeiterative = double(end1 - begin1) / CLOCKS_PER_SEC;
            cout<<"Iterative Fib number "<<answer<<endl;
            write(p4[1], &timeiterative, sizeof(double));
        }
	else {
	//parent waits here for children to die
        while (wait(NULL) > 0) {
        }
	    //waitpid(-1, NULL, 0);
	    cout<<"fin"<<endl;
	}
}
//Iterative version of Fibonacci function
long iterativeFibo(int length){
    long a = 1, b = 1, c = 1;

    for(int i = 2; i < length; i++){
        c = a + b;
        a = b;
        b = c;
    }
    return c;
}
//Recursive version of Fibonacci function
long recursiveFibo(int length){
    if(length == 0){
        return 0;
    }
    else if(length < 3){
        return 1;
    }
    else{
        return recursiveFibo(length-1) + recursiveFibo(length-2);
    }
}
