/*
Cole Hannel
CSCI 172
Model Transformations
ID #108754886
*/
#include <string.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#include <stdlib.h>
#include <iostream>
#include <GL/freeglut.h>

using namespace std;

bool WireFrame= false;
double arr[3][4] = {{4.0, 0.0, 2.0, 0.0},{0.0, 0.0, -3.0, 0.0},{-3.5, 0.0, -1.0, 0.0}};//Array of values that will be modified
																																											 //when moving objects
double mod[3] = {1.0,1.0,1.0}; //this array is to determine which objects will move when certain keys are pressed


const GLfloat light_ambient[]  = { 0.0f, 0.0f, 0.0f, 1.0f };
const GLfloat light_diffuse[]  = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light_position[] = { 2.0f, 5.0f, 5.0f, 0.0f };

const GLfloat mat_ambient[]    = { 0.7f, 0.7f, 0.7f, 1.0f };
const GLfloat mat_diffuse[]    = { 0.8f, 0.8f, 0.8f, 1.0f };
const GLfloat mat_specular[]   = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat high_shininess[] = { 100.0f };


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

    gluLookAt(0,5,10,0.0,0.0,0.0,0.0,1.0,0.0);

    if(WireFrame)
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);		//Draw Our Mesh In Wireframe Mesh
	else
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);		//Toggle WIRE FRAME

		glPushMatrix();//makes cube. Cube can be rotated and translated from the global array arr
			glTranslated(arr[0][0], arr[0][1], arr[0][2]);
			glRotated(arr[0][3], 0.0, 1.0, 0.0);
			glColor3f(1.0, 0.0, 0.0);
			glutSolidCube ( 2.0 ); 
		glPopMatrix();
		glPushMatrix();//makes teapot. Values determined by what is in arr.
			glTranslated(arr[1][0], arr[1][1], arr[1][2]);
			glRotated(arr[1][3], 0.0, 1.0, 0.0);
			glColor3f(0.0,1.0,0.0);
			glutSolidTeapot ( 2.0 );
		glPopMatrix();
		glPushMatrix();//makes sphere.
			glTranslated(arr[2][0],arr[2][1],arr[2][2]);		
			glRotated(arr[2][3], 0.0, 1.0, 0.0);
			glColor3f(0.0, 0.0, 1.0);
			glutSolidSphere ( 2.0, 20, 20);
		glPopMatrix();
    glutSwapBuffers();
}

static void key(unsigned char key, int x, int y)
{
    switch (key)
    {
        case 27 :
				case 'c': {mod[1] = 0.0; mod[2] = 0.0;break;}//only cube will be modified while pressed
				case 's': {mod[0] = 0.0; mod[1] = 0.0;break;}//only sphere will be modified while pressed
				case 't': {mod[0] = 0.0; mod[2] = 0.0;break;}//only teapot will be modified while pressed
        case 'q':
            //exit(0);
            break;
    }
}
//Added this function to reset mod[] once someone stops pressing a key
static void upkey(unsigned char key, int x, int y)
{
    switch (key)
    {
        case 27 :
				case 'c': {mod[1] = 1.0; mod[2] = 1.0;break;}//the 'while pressed' part of the comments in key()
				case 's': {mod[0] = 1.0; mod[1] = 1.0;break;}
				case 't': {mod[0] = 1.0; mod[2] = 1.0;break;}
    }
}

//when a key is pressed, will attempt to modify the arr[] values of all objects for whatever parameter is affected by the key press
//if c s or t are pressed, then only the cup, sphere, or teapot will be modified, respectively. Afterwards, the display is refreshed.
//Up and down move the object(s) forward or backwards, while left or right rotate the object(s)
void Specialkeys(int key, int x, int y)
{
    switch(key)
    {
    case GLUT_KEY_UP: {arr[0][2] += .5*mod[0]; arr[1][2] += .5*mod[1]; arr[2][2] += .5*mod[2];glutPostRedisplay();break;}
		case GLUT_KEY_DOWN:{arr[0][2] -= .5*mod[0]; arr[1][2] -= .5*mod[1]; arr[2][2] -= .5*mod[2];glutPostRedisplay();break;}
		case GLUT_KEY_LEFT:{arr[0][3] += 5.0*mod[0]; arr[1][3] += 5.0*mod[1]; arr[2][3] += 5.0*mod[2];glutPostRedisplay();break;}
		case GLUT_KEY_RIGHT:{arr[0][3] -= 5.0*mod[0]; arr[1][3] -= 5.0*mod[1]; arr[2][3] -= 5.0*mod[2];glutPostRedisplay();break;}
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

    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);

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
		glutKeyboardUpFunc(upkey);
    glutSpecialFunc(Specialkeys);

    glutIdleFunc(idle);
    glutMainLoop();

    return EXIT_SUCCESS;
}
