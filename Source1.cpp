//Cole Hannel
//Prog 4
//Program reads Key value pairs from a string, output returned is
//the same list of key/value pairs but with identical keys listed together
//Identical keys being ones with the same value. Uses an unordered map and
//vectors to sort through key keys and the values.
#include  <string>
#include <iostream>
#include <fstream>
#include <unordered_map>
#include <vector>
using namespace std;

int main(){
	fstream tread("Prog4-data");
	string key;
	string val;
	string test;
	//helper vector for storing keys
	vector<string> keylist;
	//The value is actually the key, and the list of keys is the value
	unordered_map < string, vector<string>> mappy;
	while (!tread.eof()){
		tread >> key;
		tread >> val;
		//If key already found, append it to vector
		if (mappy.count(val) > 0){
			mappy.at(val).push_back(key);
		}
		//inserts a new key/value pair if key is not found
		else{
			vector<string> x;
			x.push_back(key);
			mappy.emplace(val, x);
			//adds key to key list
			keylist.push_back(val);
		}
	}
	//while key list isn't empty get another key
	while (!keylist.empty()){
		test = keylist.back();
			cout << mappy.at(test).back();
			mappy.at(test).pop_back();
			//loopos through vector of gotten key and returns 
			//the actual keys until out of keys
		while (!mappy.at(test).empty()){
			cout << ", " << mappy.at(test).back();
			mappy.at(test).pop_back();
		}
		cout << endl;
		//then the value
		cout << test << endl;
		keylist.pop_back();
	}
}