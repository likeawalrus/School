//Header_Name.h
#include <malloc.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
typedef struct
{
float x;  // x coordinate
float y;  // y coordinate
float z;  // z coordinate
float w;  // optional component
}vec;
typedef struct
{
float u; // x component of texture
float v; // y component of texture
}vecTex;
typedef struct
{
int v;  // store vertex index
int n;  // store normal index
int t;  // store texture index
}faces;

class objectloader {
public:
	objectloader();
	virtual ~objectloader();
	int Numberofvertices;
	// get number of vertices
	int NumberofTex;            
	// get number of text 
	int Numberofnormals;
	// get number of edges 
	int Numberofaces;
	// get number of triangles 
	vec* VertexData;
	// For Vertex Data
	vec* Normals;
	// For Normals
	vecTex* Tex;
	// For Tex Data
	faces *Indices;
	// For indices
	int Temp[4];  
	// store temporary int values
	float Tmp[4]; 
	// store temporary flat values

	File* theFile;
	int filereader(char *fname);
	int temp(4);
	float tmp(4);
	int Indcount;
};

