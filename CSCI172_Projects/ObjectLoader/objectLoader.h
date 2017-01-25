#ifndef OBJECTLOADER_H
#define OBJECTLOADER_H

#include <malloc.h>     // memory allocation
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct{
float x;  // x coordinate
float y;  // y coordinate
float z;  // z coordinate
float w;  // optional component
}vec;

typedef struct{
float u; // x component of texture
float v; // y component of texture
}vecTex;

typedef struct{
int v;  // store vertex index
int n;  // store normal index
int t;  // store texture index
}faces;

class objectLoader
{
    public:
        objectLoader();
        virtual ~objectLoader();
        int readOBJfile(char *fname);

        int Numberofvertices;			 /* get number of vertoces */
        int NumberofTex;                 /* get number of text */
        int Numberofnormals;		     /* get number of edges  */
        int Numberofaces;			     /* get number of triangles */

        vec *VertexData;
        vec *Normals;
        vecTex *Tex;
        faces *Indices;

        FILE *file;			// declare a FILE pointer

        int temp[4];  // store temporary int values
        float tmp[4]; // store temporary flat values
        int Indcount; // number of faces

    protected:

    private:
};

#endif // OBJECTLOADER_H

