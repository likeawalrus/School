#include <objectloader.h>

using namespace std;

int objectloader::filereader(char *fname){

	char line[1024];
	char *tok;
	int Lcount = 0;

	file fopen(fname, "r");
	if(file == NULL)
		{
			printf("Error: can't open file");
			return 1;
		}
	else
		{              
		while(fgets(line,sizeof(line), file) != NULL )
			{
			int i=0;
			tok = NULL;   // initialize token
			tok = strtok(line," "); 
			// separate by space
			if(strcmp(tok,"d")==0) // if d
				{
				while(tok!= NULL)
					{
					temp[i]=atoi(tok);  
					// get value
					i++;       
					tok = strtok(NULL, "\n"); 
					//until end of line 
					}

				Numberofvertices=temp[1]; 
				// get number of vertices
				NumberofTex=temp[2];     
				// get number of text 
				Numberofnormals=temp[3];  
				// get number of edges   
				Numberofaces=temp[4];     
				// get number of triangles

				VertexData=(vec*)malloc(Numberofvertices*sizeof(*VertexData)); //allocate memory
				Normals=(vec*)malloc(Numberofnormals*sizeof(*Normals));    //allocate
				memoryTex=(vecTex*)malloc(NumberofTex*sizeof(*Tex));     	// allocate memory
				Indices= (faces*)malloc(Numberofaces*4*sizeof(*Indices));    //allocate memory
				}
			}			
			else if(strcmp(tok,"v")==0)
				{
				while (tok!= NULL)
					{
					tmp[i]=atof(tok);                  // get value
					i++;
					tok=strtok(NULL, "\n");
					//till end of line
					}/* end while */
				tmp[1] /=factor;
				tmp[2] /=factor;
				tmp[3] /=factor;
				VertexData[Lcount].x=tmp[1];
				/* 
				stor
				vertex coordinates */
				VertexData[Lcount].y=tmp[2];
				VertexData[Lcount].z=tmp[3];
				VertexData[Lcount].w= Lcount;
				Lcount++;
				}
			else if(strcmp(tok,"vn")==0)
				{
				while (tok!= NULL)
					{
					tmp[i]=atof(tok);                 // get value
					i++;
					tok=strtok(NULL, "\n");
					//till end of line
					}/* end while */
				Normals[Ncount].x=tmp[1];
				// stornormal coordinates
				Normals[Ncount].y=tmp[2];
				//stornormal coordinates
				Normals[Ncount].z= tmp[3];
				// stornormal coordinates
				Ncount++;
				} // end if







		}
}
