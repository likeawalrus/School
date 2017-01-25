#include <string.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#include <stdlib.h>
#include <time.h>
/* GLUT callback Handlers */

double x = 0.0, y = 0.0;//global variables
//these are the four transformation functions
void f1(){
	x = 0.0;
	y = .16*y;
}

void f2(){
	double xn = x;
	x = .85*x+.04*y;
	y = 1.6 + 0.85*y - 0.04*xn;
}

void f3(){
	double xn = x;
	x = 0.2*x - 0.26*y;
	y = 0.23*xn + 0.24*y + 1.6;
}

void f4(){
	double xn = x;
	x = 0.28*y - 0.15*x;
	y = 0.26*xn+0.24*y+0.44;
}

void resize(int width, int height)
{
    if(width<=height)
        glViewport(0,(GLsizei) (height-width)/2,(GLsizei) width,(GLsizei) width);

    else
        glViewport((GLsizei) (width-height)/2 ,0 ,(GLsizei) height,(GLsizei) height);
}

void init()
{
 glMatrixMode(GL_PROJECTION);
 glLoadIdentity();
 glOrtho(-2.75,2.75,10.5,0, -1.0, 1.0); // adjusted for suitable viewport
	//left, right, bottom, top
}

void display(void)
{
  glClear (GL_COLOR_BUFFER_BIT);        // clear display screen
  glPointSize(1.0f);										// size of the point(1 pixel in this case)
	glBegin(GL_TRIANGLES);
	float x, y;										
	for(int i = 0; i < 1000; i++){

		x = (rand()%550-275)/100.0;
		y = rand()%105/10.0;
		glColor3f(0.0f, 1.0f, 0.0f);
		glVertex2f(x, y);

		x = (rand()%550-275)/100.0;
		y = rand()%105/10.0;
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex2f(x, y);

		x = (rand()%550-275)/100.0;
		y = rand()%105/10.0;
		glColor3f(0.0f, 0.0f, 1.0f);
		glVertex2f(x, y);
	}

		//double c1 = rand() %1000/1000.0;		//random colors to make it look cooler
		//double c2 = rand() %1000/1000.0;		//All are between 0.0 and 0.999
		//double c3 = rand() %1000/1000.0;
	  //glColor3f(c3,c1,c2);	//decides color of pixel
		//glVertex3f(x, y,0);		//draws pixel
	glEnd();								//done with the work stuff
  glFlush ();                           // clear buffer
}


void key(unsigned char key, int x, int y)
{
    switch (key)
    {
        case 27 :                       // esc key to exit
        case 'q':
            exit(0);
            break;
    }

    glutPostRedisplay();
}



/* Program entry point */

int main(int argc, char *argv[])
{
	 srand (time(NULL));	//choose a random number
   glutInit(&argc, argv);
   glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
   glutInitWindowSize (250, 250);                //window screen
   glutInitWindowPosition (100, 100);            //window position
   glutCreateWindow ("Program1");                //program title
   init();
   glutDisplayFunc(display);                     //callback function for display
   glutReshapeFunc(resize);                      //callback for reshape
   glutKeyboardFunc(key);                        //callback function for keyboard
   glutMainLoop();                               //loop

    return EXIT_SUCCESS;
}
