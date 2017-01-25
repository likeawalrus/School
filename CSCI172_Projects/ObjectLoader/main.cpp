//Cole Hannel
//CSCI 172 Project 4: 3d model loading
//For this program we built a model in a .obj file and importing it using the supplied objectLoader file
//Then we colored it based on vertex location relative to origin of object

#include <string.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif
#include <GL/gl.h> //segmentation fault error in linux if this not in program
#include <stdlib.h>
#include <iostream>

#include <math.h>
#include "objectLoader.h"

using namespace std;

bool WireFrame= false;
int v; // to assign face value

float RotateX =0;
float RotateY =0;
float RotateZ =0;
float translate_z =-30;

const GLfloat light_ambient[]  = { 0.0f, 0.0f, 0.0f, 1.0f };
const GLfloat light_diffuse[]  = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light_position[] = { 2.0f, 5.0f, 5.0f, 0.0f };

const GLfloat mat_ambient[]    = { 0.7f, 0.7f, 0.7f, 1.0f };
const GLfloat mat_diffuse[]    = { 0.8f, 0.8f, 0.8f, 1.0f };
const GLfloat mat_specular[]   = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat high_shininess[] = { 100.0f };

/* GLUT callback Handlers */

objectLoader Loader;

static void resize(int width, int height)
{
     double Ratio;

   if(width<=height)
            glViewport(0,(GLsizei) (height-width)/2,(GLsizei) width,(GLsizei) width);
    else
          glViewport((GLsizei) (width-height)/2 ,0 ,(GLsizei) height,(GLsizei) height);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
	gluPerspective (50.0f,1,0.1f, 100.0f);
 }

static void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    glColor3f(1.0,0.8,0.2);//just a default color, never used never seen
    gluLookAt(0,0,10,0.0,0.0,0.0,0.0,1.0,0.0);
    glTranslated(-0.5,-0.5,translate_z);//moves the model over slightly fromt he point of origin

    glRotated(RotateX,1,0,0);//uses rotation value from keys in order to rotate the model
    glRotated(RotateY,0,1,0);
    glRotated(RotateZ,0,0,1);
    if(WireFrame)
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);		//Draw Our Mesh In Wireframe Mesh
	else
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);		//Toggle WIRE FRAME

        int i=0;
        glBegin(GL_TRIANGLES);//start drawing triangles
		while(i<Loader.Indcount)
		{
		//Constructs a face. The edge of each face is colored the same as the location of the vertex, so that when
		//shown on screen it apears as if one edge is red, one is blue, etc.
		//X Y and Z values of each vertex are gotten from the Loader which got them from testmodel.obj

		    glColor3f(Loader.VertexData[Loader.Indices[i].v].x,Loader.VertexData[Loader.Indices[i].v].y,Loader.VertexData[Loader.Indices[i].v].z);
        if(Loader.Numberofnormals>0)
				glNormal3f(Loader.Normals[Loader.Indices[i].n].x,Loader.Normals[Loader.Indices[i].n].y,Loader.Normals[Loader.Indices[i].n].z);//triangle one first vertex
				glVertex3f(Loader.VertexData[Loader.Indices[i].v].x,Loader.VertexData[Loader.Indices[i].v].y,Loader.VertexData[Loader.Indices[i].v].z);//triangle one first vertex

		    glColor3f(Loader.VertexData[Loader.Indices[i+1].v].x,Loader.VertexData[Loader.Indices[i+1].v].y,Loader.VertexData[Loader.Indices[i+1].v].z);
        if(Loader.Numberofnormals>0)
        glNormal3f(Loader.Normals[Loader.Indices[i+1].n].x,Loader.Normals[Loader.Indices[i+1].n].y,Loader.Normals[Loader.Indices[i+1].n].z);//triangle one first vertex
        glVertex3f(Loader.VertexData[Loader.Indices[i+1].v].x,Loader.VertexData[Loader.Indices[i+1].v].y,Loader.VertexData[Loader.Indices[i+1].v].z);//triangle one first vertex

		    glColor3f(Loader.VertexData[Loader.Indices[i+2].v].x,Loader.VertexData[Loader.Indices[i+2].v].y,Loader.VertexData[Loader.Indices[i+2].v].z);
        if(Loader.Numberofnormals>0)
        glNormal3f(Loader.Normals[Loader.Indices[i+2].n].x,Loader.Normals[Loader.Indices[i+2].n].y,Loader.Normals[Loader.Indices[i+2].n].z);//triangle one first vertex
        glVertex3f(Loader.VertexData[Loader.Indices[i+2].v].x,Loader.VertexData[Loader.Indices[i+2].v].y,Loader.VertexData[Loader.Indices[i+2].v].z);//triangle one first vertex
				//cout<<Loader.VertexData[Loader.Indices[i].v].x<<Loader.VertexData[Loader.Indices[i].v].y<<Loader.VertexData[Loader.Indices[i].v].z<<endl;
				//cout<<Loader.VertexData[Loader.Indices[i+1].v].x<<Loader.VertexData[Loader.Indices[i+1].v].y<<Loader.VertexData[Loader.Indices[i+1].v].z<<endl;
				//cout<<Loader.VertexData[Loader.Indices[i+2].v].x<<Loader.VertexData[Loader.Indices[i+2].v].y<<Loader.VertexData[Loader.Indices[i+2].v].z<<endl;
		i+=3;
		} // end while
		glEnd();//end drawing of triangles
    glutSwapBuffers();
}

static void key(unsigned char key, int x, int y)
{
    switch (key)
    {
        case 27 :
        case 'q':
            Loader.~objectLoader();
            exit(0);
            break;

        case 'w':
            WireFrame =!WireFrame;
            break;
    }
}

void Specialkeys(int key, int x, int y)
{
    switch(key)
    {
    case GLUT_KEY_END:
	  translate_z += (float) 1.0f;
		break;
	case GLUT_KEY_HOME:
	  translate_z -= (float) 1.0f;
		break;
    case GLUT_KEY_UP:					/* Clockwise rotation over X */
	 	RotateX = ((int)RotateX +2)%360;
		break;
	case GLUT_KEY_DOWN:					/* Counter Clockwise rotation over X */
		RotateX = ((int)RotateX -2)%360;
		  break;
	case GLUT_KEY_LEFT:					/* Clockwise rotation over Y */
    	RotateY =((int)RotateY +2)%360;
		break;
	case GLUT_KEY_RIGHT:
		RotateY = ((int)RotateY -2)%360;/* Counter Clockwise rotation over Y */
		 break;
   }
  glutPostRedisplay();
}

static void idle(void)
{
    glutPostRedisplay();
}

static void init(void)
{
    glEnable(GL_NORMALIZE);
    glEnable(GL_COLOR_MATERIAL);

    glEnable(GL_DEPTH_TEST);
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    glShadeModel(GL_SMOOTH);

    glLightfv(GL_LIGHT0, GL_AMBIENT,  light_ambient);
    glLightfv(GL_LIGHT0, GL_DIFFUSE,  light_diffuse);
    glLightfv(GL_LIGHT0, GL_SPECULAR, light_specular);
    glLightfv(GL_LIGHT0, GL_POSITION, light_position);

    glMaterialfv(GL_FRONT, GL_AMBIENT,   mat_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE,   mat_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR,  mat_specular);
    glMaterialfv(GL_FRONT, GL_SHININESS, high_shininess);

    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);

    glEnable(GL_LIGHT0);
    glEnable(GL_LIGHTING);
		Loader.readOBJfile("testmodel.obj"); //Class the loader to input data from testmodel.obj
 //   Loader.readOBJfile("ateneam.obj"); // Load model
 //   Loader.readOBJfile("model/bunny.obj"); // Load model


}


/* Program entry point */

int main(int argc, char *argv[])
{
    glutInit(&argc, argv);

    glutInitWindowSize(800,600);
    glutInitWindowPosition(10,10);
    glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE | GLUT_DEPTH);

    glutCreateWindow("GLUT Shapes");
    init();
    glutReshapeFunc(resize);
    glutDisplayFunc(display);
    glutKeyboardFunc(key);
    glutSpecialFunc(Specialkeys);
    glutIdleFunc(idle);
    glutMainLoop();

    return EXIT_SUCCESS;
}

