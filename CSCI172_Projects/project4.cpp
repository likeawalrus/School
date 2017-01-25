//Cole Hannel
//ID#108754886
//Project 4 Object Creation
//Goal is to make a program that makes a cube
#include <math.h>
#include <string.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif
#include <GL/gl.h>
#include <stdlib.h>
#include <iostream>
#include <math.h>

using namespace std;

bool WireFrame= false;

GLuint tex;
GLdouble TranslateX,TranslateY,Zoom,RotateX,RotateY,RotateZ;

//the ultimate number
#define PI 3.14159
GLfloat x, y, z, w;
//used with keys to rotate a specific amount
static GLint rotateX = 0;
static GLint rotateY = 0;
static GLint rotateZ = 0;



const GLfloat light_ambient[]  = { 0.0f, 0.0f, 0.0f, 1.0f };
const GLfloat light_diffuse[]  = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light_position[] = { 2.0f, 5.0f, 5.0f, 0.0f };

const GLfloat mat_ambient[]    = { 0.7f, 0.7f, 0.7f, 1.0f };
const GLfloat mat_diffuse[]    = { 0.8f, 0.8f, 0.8f, 1.0f };
const GLfloat mat_specular[]   = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat high_shininess[] = { 100.0f };

float Tex[4][2] = {{0.0, 0.0},{1.0, 0.0},{0.0, 1.0},{1.0,1.0}};
float Vertices[8][3]={{0.0, 0.0, 0.0},{0.0, 0.0, 1.0},{0.0, 1.0, 0.0},{0.0, 1.0, 1.0},{1.0, 0.0, 0.0},{1.0, 0.0, 1.0},{1.0, 1.0, 0.0},{1.0, 1.0, 1.0}};
float Normals[6][3] ={{0.0, 0.0, 1.0},{0.0, 0.0, -1.0},{0.0, 1.0, 0.0},{0.0, -1.0, 0.0},{1.0, 0.0, 0.0},{-1.0, 0.0, 0.0}};
int ind[12][9]={{1,1,2, 7,4,2, 5,2,2},{1,1,2, 3,3,2, 7,4,2},{1,1,6, 4,4,6, 3,2,6},{1,1,6, 2,3,6, 4,4,6},{3,1,3, 8,4,3, 7,2,3},{3,1,3, 4,3,3, 8,4,3},{5,2,5, 7,1,5, 8,3,5},{5,2,5, 8,3,5, 6,4,5},{1,2,4, 5,1,4, 6,3,4},{1,2,4, 6,3,4, 2,4,4},{2,2,1, 6,1,1, 8,3,1},{2,2,1, 8,3,1, 4,4,1}};

/* GLUT callback Handlers */

static void resize(int width, int height)
{
     double Ratio;

   if(width<=height)
     {
         glViewport(0,(GLsizei) (height-width)/2,(GLsizei) width,(GLsizei) width);
         Ratio =  height/width;
      }
    else
      {
        glViewport((GLsizei) (width-height)/2 ,0 ,(GLsizei) height,(GLsizei) height);
        Ratio = width /height;
      }

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
	gluPerspective (50.0f,Ratio,0.1f, 100.0f);
 }

static void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    gluLookAt(0,0,3,0.0,0.0,0.0,0.0,1.0,0.0);
    //glColor3f(0.5f,0.5f,0.5f);
    if(WireFrame)
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);		//Draw Our Mesh In Wireframe Mesh
	else
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);		//Toggle WIRE FRAME

		//the three rotation matrices
		/*createMatrix(matrixX);
		createFromAxisAngle(1,0,0,rotateX);	
		glMultMatrixf(matrixX);
		createMatrix(matrixY);
		createFromAxisAngle(0,1,0,rotateY);	
		glMultMatrixf(matrixY);
		createMatrix(matrixZ);
		createFromAxisAngle(0,1,0,rotateZ);	
		glMultMatrixf(matrixZ);*/

		glPushMatrix();
      glTranslated(0.0,0.0,0.0);//moves the model over slightly fromt he point of origin
      glRotated(rotateX,1,0,0);//uses rotation value from keys in order to rotate the model
      glRotated(rotateY,0,1,0);
      glRotated(rotateZ,0,0,1);
		  glBegin(GL_TRIANGLES);//start drawing triangles
/*				
				{
*/	 for(int i= 0; i < 12; i++){
        		glColor3f(Vertices[ind[i][0]-1][0],Vertices[ind[i][0]-1][1],Vertices[ind[i][0]-1][2]);
						glNormal3f(Normals[ind[i][2]-1][0],Normals[ind[i][2]-1][1],Normals[ind[i][2]-1][2]);
						glVertex3f(Vertices[ind[i][0]-1][0],Vertices[ind[i][0]-1][1],Vertices[ind[i][0]-1][2]);

        		glColor3f(Vertices[ind[i][3]-1][0],Vertices[ind[i][3]-1][1],Vertices[ind[i][3]-1][2]);
						glNormal3f(Normals[ind[i][5]-1][0],Normals[ind[i][5]-1][1],Normals[ind[i][5]-1][2]);
						glVertex3f(Vertices[ind[i][3]-1][0],Vertices[ind[i][3]-1][1],Vertices[ind[i][3]-1][2]);

        		glColor3f(Vertices[ind[i][6]-1][0],Vertices[ind[i][6]-1][1],Vertices[ind[i][6]-1][2]);
						glNormal3f(Normals[ind[i][8]-1][0],Normals[ind[i][8]-1][1],Normals[ind[i][8]-1][2]);
						glVertex3f(Vertices[ind[i][6]-1][0],Vertices[ind[i][6]-1][1],Vertices[ind[i][6]-1][2]);

     }
            
      

			glEnd();
		glPopMatrix();
    glutSwapBuffers();
}

static void key(unsigned char key, int x, int y)
{
    switch (key)
    {
        case 27 :
				case 'w':
					rotateX=(rotateX+5)%360;//rotation x amount, by 5* per click either left or right
					break;
				case 's':
					rotateX=(rotateX-5)%360;
					break;
				case 'a':
					rotateY=(rotateY+5)%360;//like X, but for Y
					break;
				case 'd':
					rotateY=(rotateY-5)%360;
					break;
				case 'z':
					rotateZ=(rotateZ+5)%360;//like the other two, but for Z
					break;
				case 'x':
					rotateZ=(rotateZ-5)%360;
					break;
        case 'q':
            exit(0);
            break;
//toggles wireframe
	  case 't':
		WireFrame =!WireFrame;
	       break;	
    }
}

void Specialkeys(int key, int x, int y)
{
    switch(key)
    {
    case GLUT_KEY_UP:
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
    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);

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

    glEnable(GL_LIGHT0);
    glEnable(GL_NORMALIZE);
    glEnable(GL_LIGHTING);
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
