#include "objectLoader.h"
#include <iostream>

using namespace std;

objectLoader::objectLoader()
{
    Indcount =0;//ctor
}

objectLoader::~objectLoader()
{
       free(VertexData);
       free(Normals);
       free(Tex);
       free(Indices);
       fclose(file);

    //dtor
}

int objectLoader::readOBJfile(char *fname)
{
     char line[1024];	    // line reader
     char *tok;             // string tokenizer
     int Lcount=0;          // Vertex Line count
     int Ncount =0;         // Normal Count
     int Tcount = 0;         // Tex line count
     int factor=300;        // factor out the model
     int ed[12];

     file = fopen(fname,"r");	//open a text file for reading


     if(file==NULL)// if there is no file
        {
            printf("Error: can't open file %s.\n",fname);
            return 1;
        }
    else{
          while( fgets(line, sizeof(line), file) != NULL )
         {
						//cout<<line<<endl;
            int i =0;
            tok=NULL;                   // initialize token
            tok = strtok (line," ");    // separate by space

            if(strcmp(tok,"d")==0)      // if d
            {

            while (tok != NULL)
            {
            temp[i]=atoi(tok);  // get value
            i++;
            tok = strtok (NULL, " \n"); //until end of line
            }// end while

            Numberofvertices=temp[1];			 // get number of vertoces
            NumberofTex =temp[2];                // get number of text
            Numberofnormals=temp[3];		     // get number of edges
            Numberofaces=temp[4];			     // get number of triangles
						//cout<<temp[1]<<" "<<temp[2]<<" "<<temp[3]<<" "<<temp[4]<<endl;
            VertexData=(vec*)malloc((Numberofvertices+2)*sizeof(*VertexData));// allocate memory
            Normals=(vec*)malloc((Numberofnormals+2)*sizeof(*Normals));		 // allocate memory
            Tex=(vecTex*)malloc((NumberofTex+2)*sizeof(*Tex));                   // allocate memory
            Indices= (faces*)malloc((Numberofaces+2)*4*sizeof(*Indices));		 // allocate memory
            } // end if

             else if(strcmp(tok,"v")==0)
            {
                while (tok != NULL)
                {
                    tmp[i]=atof(tok);                  // get value
                        i++;
                    tok = strtok (NULL, " \n");		   //till end of line
                }/* end while */

                 tmp[1] /=factor;
                 tmp[2] /=factor;
                 tmp[3] /=factor;

                VertexData[Lcount].x= tmp[1];		/* stor vertex coordinates */
                VertexData[Lcount].y= tmp[2];
                VertexData[Lcount].z= tmp[3];
                VertexData[Lcount].w= Lcount;
								//cout<<VertexData[Lcount].x<<" "<<VertexData[Lcount].y<<" "<<VertexData[Lcount].z<<" "<<Lcount<<endl;
                Lcount++;
            }

             else if(strcmp(tok,"vn")==0)
            {
                while (tok != NULL)
                {
                    tmp[i]=atof(tok);                 // get value
                        i++;
                    tok = strtok (NULL, " \n");		  //till end of line
                }/* end while */

            Normals[Ncount].x= tmp[1];		  // stor normal coordinates
            Normals[Ncount].y= tmp[2];		  // stor normal coordinates
            Normals[Ncount].z= tmp[3];		  // stor normal coordinates
						//cout<<tmp[1]<<" "<<tmp[2]<<" "<<tmp[3]<<endl;
            Ncount++;
            } // end if

            else if(strcmp(tok,"vt")==0)
            {

            while (tok != NULL)
            {
                tmp[i]=atof(tok);                  // get value
                    i++;
                tok = strtok (NULL, " \n");		   // till end of line
            }/* end while */

            Tex[Tcount].u= tmp[1];				/* stor vertex coordinates */
            Tex[Tcount].v= tmp[2];

            Tcount++;
            } // end if

        else if(strcmp(tok,"f")==0)
        {

            while (tok != NULL)
            {
                ed[i]=atoi(tok);                  // get value
                i++;
                tok = strtok (NULL, " /");			 //till end of line
            }/* end while */

        	//cout << ed[1]-1 <<"  "<<ed[4]-1<<"  "<< ed[7]-1<<endl;

					Indices[Indcount].v   = ed[1]-1;
					Indices[Indcount+1].v	= ed[4]-1;
					Indices[Indcount+2].v	= ed[7]-1;

					Indices[Indcount].t   = ed[2]-1;
					Indices[Indcount+1].t	= ed[5]-1;
					Indices[Indcount+2].t	= ed[8]-1;

					Indices[Indcount].n   = ed[3]-1;
					Indices[Indcount+1].n	= ed[6]-1;
					Indices[Indcount+2].n	= ed[9]-1;

					Indcount+=3;

				} // end if f

    else tok=NULL;

    }// EOF while end
		//cout<<"end of file reached"<<endl;
   }fclose(file);
}

