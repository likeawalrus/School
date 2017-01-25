//Cole Hannel
//Stoplight program to simulate a 'smart' stoplight system
//Each lane is a separate thread as is the light itself
//Will run forever

#include <iostream>
#include <chrono>
#include <mutex>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <thread>

std::mutex mtx;

//Defines the car. Car defined by id, direction, and time of arrival.
struct car {
int id;
int dir;
float time;
};
//globals
car carque[100];
int next = 0;
int last = 0;
int carid = 0;
bool dolphins_in_ocean = true;
bool notendtimes = true;
//const float startclock = (float)clock())/CLOCKS_PER_SEC;

void lane(int direction){
    while(dolphins_in_ocean){
        mtx.lock();
        if(next != (last+1)){
            car x;
            x.id = carid;
            carid++;
            x.dir = direction;
            clock_t now = clock();
            x.time = ((float)now)/CLOCKS_PER_SEC;
            carque[next] = x;
            next = (next+1)%100;
            std::cout<<"Car "<<x.id<< " added going ";
            if(direction == 0)
                std::cout<<"north"<<std::endl;
            else if(direction == 1)
                std::cout<<"east"<<std::endl;
            else if(direction == 2)
                std::cout<<"south"<<std::endl;
            else
                std::cout<<"west"<<std::endl;
        }
        mtx.unlock();
        int randtime = (rand()%3000);
        std::this_thread::sleep_for(std::chrono::milliseconds(randtime));
    }
}

void light(){
    while(dolphins_in_ocean){
        mtx.lock();
        clock_t now = clock();
        float current = ((float)now)/CLOCKS_PER_SEC;
        int waittime;
        if(next == last){
            //Do Nothing
        }
        else if(current-carque[last].time < 1){//if car just arrived, or arrived withhin one second
            waittime = (3000+((50000 - (int)(current-carque[last].time)*25000)/25000));//For our purposes full speed is 25 m/s
            std::cout<<"Car "<<carque[last].id<< " going ";
            if(carque[last].dir == 0)
                std::cout<<"north sped through"<<std::endl;
            else if(carque[last].dir == 1)
                std::cout<<"east sped through"<<std::endl;
            else if(carque[last].dir == 2)
                std::cout<<"south sped through"<<std::endl;
            else
                std::cout<<"west sped through"<<std::endl;
            last = (last+1)%100;

        }
        else {
            waittime = 3000;
            std::cout<<"Car "<<carque[last].id<< " going ";
            if(carque[last].dir == 0)
                std::cout<<"north was stopped and went"<<std::endl;
            else if(carque[last].dir == 1)
                std::cout<<"east was stopped and went"<<std::endl;
            else if(carque[last].dir == 2)
                std::cout<<"south was stopped and went"<<std::endl;
            else
                std::cout<<"west was stopped and went"<<std::endl;
            last = (last+1)%100;

        }
        if(carid > 150){
        notendtimes = false;
        }
        mtx.unlock();
        std::this_thread::sleep_for(std::chrono::milliseconds(waittime));
    }
}

int main() {
    srand(time(NULL));
    std::thread north (lane, 0);
    std::thread east (lane, 1);
    std::thread south (lane, 2);
    std::thread west (lane, 3);
    std::thread lit (light);
    //String x;
    //x<<cin;
    while(notendtimes){
        //wait around
    }
    dolphins_in_ocean = false;
    north.join();
    east.join();
    south.join();
    west.join();
    lit.join();
    return 0;
}
