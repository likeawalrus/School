//Cole Hannel
//CSCI 117 Lab 3
//Program takes a line from a file p1_data 
//Interprets a file and performs operations based on what the interpretter understands
//Takes variables and does basic input/output commands
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <math.h>
#include <map>
#include <string>
using namespace std;

//functions used
int Exp(), Term(), Exp2(int), Term2(int), Fact(), Numb(char);
void Declarations(), Declaration(), Statements(), Statement(string);
void Input(), Output(), Assignment(string);
//The steam and file name, grabs the data file
ifstream fin("p1_data.txt");
//Where all of our variables will be stored
map<char, int> symtab;

int main()
{
	string word;
	fin >> word;
	if (word == "program"){
		Declarations();
		Statements();
	}
	else{
		cout << "Syntactical or lexical error at program start";
	}
	fin.close();
	//cout << "result= " << Exp() << endl;
}

void Declarations(){
	string word;
	fin >> word;
	if (word == "begin"){
		//Do nothing
	}
	else if (word == "var"){
		Declaration();
		Declarations();
	}
	else{
		cout << "Syntactical or Lexical Error in Declarations";
	}

}
//Declaration takes a letter(or series of letters) and turns them 
//into one character variables
//Either upper or lower case letters are allowed, line ends with ;
void Declaration(){
	char letter;
	fin >> letter;
	if (letter >= 'A' && letter <= 'Z' || letter >= 'a' && letter <= 'z'){
		symtab[letter];
		Declaration();
	}
	else if (letter == ','){
		Declaration();
	}
	else if (letter == ';'){
		//Do nothing
	}
	else{
		cout << "Lexical or Syntactical error in variable";
	}
}
//Statements simply calls statement,
//and then recursively loops through
//until the end of the file is reached
void Statements(){
	string word;
	fin >> word;
	if (word == "end"){
		//Do nothing
	}
	else{
		Statement(word);
		Statements();
	}
}
//Statement, calls either input, output, or assignment based
//on first word, and returns an error if line does not end with semicolon
void Statement(string word){
	char letter;
	if (word == "input"){
		Input();
	}
	else if (word == "output"){
		Output();
	}
	else{
		Assignment(word);
	}
	fin >> letter;
	if (letter != ';'){
		cout << "Semantic error, line does not end with ;";
	}
}
//Takes an input from command line and stores that value in one of the defined variables
//if the variable was not declared then an error is returned
void Input(){
	char letter;
	fin >> letter;
	if (letter >= 'a' && letter <= 'z' || letter >= 'A' && letter <= 'Z'){
		if (symtab.find(letter) == symtab.end()){
			cout << "Variable not declared";
		}
		else{
			cin >> symtab[letter];
		}
	}
	else {
		cout << "Lexical or Syntactical error";
	}
}
//Outputs a number <output> = <var>|<exp>
//Can output either the stored value of a variable or
//the result of an equation
void Output(){
	char letter;
	fin >> letter;
	if (letter >= 'a' && letter <= 'z' || letter >= 'A' && letter <= 'Z'){
		if (symtab.find(letter) == symtab.end()){
			cout << "Variable not declared";
		}
		else{
			cout << symtab[letter] << endl;
		}
	}
	else {
		fin.putback(letter);
		cout << Exp();
	}
}
//Assigns a variable a value based on <var> = <var>|<exp>;
//If <var> then assigns that, oherwise calls exp or returns an error
void Assignment(string word){
	if (word.length() == 1){
		char letter;
		letter = word.at(0);
		if (letter >= 'a' && letter <= 'z' || letter >= 'A' && letter <= 'Z'){
			if (symtab.find(letter) == symtab.end()){
				cout << "Variable not declared";
			}
			else{
				char tester;
				fin >> tester;
				if (tester == '='){
					char other;
					fin >> other;
					if (other >= 'a' && other <= 'z' || other >= 'A' && other <= 'Z'){
						if (symtab.find(other) == symtab.end()){
							cout << "Variable not declared";
						}
						else{
							symtab[letter] = symtab[other];
						}
					}
					else {
						fin.putback(other);
						symtab[letter] = Exp();
					}

				}
				else
					cout << "Equals sign not used in assignment";
			}
		}
	}
	else
		cout << "Lexical or syntactical error";

}
//Returns The result of addition and subtraction on a term
int Exp()
{
	//Grabs the first term to be added/subtracted
	return Exp2(Term());
}

//Returns the results of multiplication and division on factors
int Term()
{		
	//Grabbing Fact() grabs the first number in the term
	return Term2(Fact());
}

//Runs looking for + or -, calculates a result when it finds it
//If none is found, simply returns input. Adds and subtracts whole
//terms
int Exp2(int inp)
{
	int result = inp;
	char a;
	if (!fin.eof())
	{
		fin >> a;
		//Checks for + and performs addition on terms
		if (a == '+')
			result = Exp2(result + Term());
		//Checks for - and performs addition on terms
		else if (a == '-')
			result = Exp2(result - Term());
		//Checks for ) and if so returns
		else if (a == ')' || a == ';')
			fin.putback(a);
		else
			cout << "Error in computation";
	}
	return result;
}

//Does division and multiplication. Looks for * and /
//and if it finds it, does the operation (on a factor and another factor),
//otherwise, just returns input
int Term2(int inp)
{
	int result = inp;
	char a;
	if (!fin.eof())
	{
		fin >> a;
		//Checks for multiplication
		if (a == '*')
			result = Term2(result * Fact());
		//Checks for division
		else if (a == '/')
			result = Term2(result / Fact());
		//If + or -, returns
		else if (a == '+' || a == '-' || a == ')' || a == ';')
			fin.putback(a);
		else
			cout << "Error in computation";

	}
	return result;
}

//Takes a character and converts it to an int by sendinf it to Numb
//Checks if next char is '^', if so performs pow() until there are no more exponents
//Returns a number
int Fact()
{
	int result = 0;
	char a;
	fin >> a;
	result = Numb(a);
	fin >> a;
	if (a == '^')
		result = pow(result, Fact());
	else
		fin.putback(a);
	return result;
}
//Checks if character is actually a (, and if so performs the expression
//operation on it until ) is reached. Otherwise returns an int
int Numb(char a){
	int result;
	if (a == '('){
		result = Exp();
		char x;
		fin >> x;
	}
	else
		result = atoi(&a);
	return result;
}