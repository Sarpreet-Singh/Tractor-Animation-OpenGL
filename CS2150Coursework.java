/* CS2150Coursework.java
 * TODO: 180126121 and Sarpreet Singh
 *
 * Scene Graph:
 * Scene Graph:
 *  Scene origin
 *  *  Scene origin
 *  |
 *  +-- [S(20,1,20) T(0,-1,-10)] Ground plane
 *  |
 *  +-- [S(20,1,10) Rx(90) T(0,4,-20)] Sky plane
 *  |
 *  +-- [T(currentTractorX,-0.5,-5f) S(0.25,0.25,0.25)] Tractor
 *      |
 *      +--[] Front Part
 *		|
 *		+--[T(0.2,0.5,0) S(1.2,0.2,1)] Bottom part
 *		|
 *		+--[S(1,2,1.3) T(3,-0.65,0.2)] Main body
 *		|
 *		+--[S(0.25,0.25,0.25) T(2.5,4,-3.1)] Left rear tyre
 *		|
 *		+--[S(0.25,0.25,0.75) T(2.5, 4, 1.3)] Right rear tyre
 *		|
 *		+--[S(0.15,0.15, 0.5) T(-25, 4, -4.1)] Left front tyre
 *		|
 *		+--[S(0.15f,0.15f, 0.5f) T(-25, 4, 1.1)] Right front tyre
 */
package coursework.sarpreet;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import org.lwjgl.util.glu.Cylinder;
import GraphicsLab.*;


/**
 * TODO: I decided to recreate a tractor in a farm that moves around.
 *
 * <p>Controls:
 * <ul>
 * <li>Press the escape key to exit the application.
 * <li>Hold the x, y and z keys to view the scene along the x, y and z axis, respectively
 * <li>While viewing the scene along the x, y or z axis, use the up and down cursor keys
 *      to increase or decrease the viewpoint's distance from the scene origin
 * <li> Press R to rotate the tractor anti-clockwise
 * <li> Press T to rotate the tractor clockwise
 * <li> Press M to move the tractor to the right 
 * <li> Press L to move the tractor to the left 
 * <li> Press Space -  To reset the initial Position 
 * </ul>
 * TODO: Add any additional controls for your sample to the list above
 *
 */
public class CS2150Coursework extends GraphicsLab
{

	private final static int bottomList = 1;
	private final static int frontList = 2;
	private final static int mainList = 4;
	private final static int tyreList = 5;
	private final static int windowList = 6;

	private Texture planeTexture;
	private Texture skyTexture;

	private float currentTractorX= 0f;



	private float centreAngle= 35.0f;
	/** display list id for the unit plane */
	private final int planeList = 3;

	//TODO: Feel free to change the window title and default animation scale here
	public static void main(String args[])
	{   new CS2150Coursework().run(WINDOWED,"CS2150 Coursework Submission",0.01f);
	}

	protected void initScene() throws Exception
	{//TODO: Initialise your resources here - might well call other methods you write.

		planeTexture = loadTexture("coursework/sarpreet/textures/land.bmp");
		skyTexture = loadTexture("coursework/sarpreet/textures/sky.bmp");

		GL11.glNewList(frontList,GL11.GL_COMPILE);
		{
			Colour GREEN1 = new Colour(0,153,0);
			drawUnitCube(GREEN1, GREEN1, GREEN1, GREEN1, Colour.GREEN, GREEN1);
		}

		GL11.glEndList();

		GL11.glNewList(bottomList,GL11.GL_COMPILE);
		{
			Colour darkRed = new Colour(153,0,0);
			drawUnitCube(darkRed, darkRed, darkRed, darkRed, darkRed, darkRed);
		}

		GL11.glEndList();

		GL11.glNewList(mainList,GL11.GL_COMPILE);
		{
			Colour black = new Colour(32,32,32);
			drawUnitCube(black, black, black, black, black, black);
		}

		GL11.glEndList();

		GL11.glNewList(tyreList,GL11.GL_COMPILE);
		{
			Colour GREY = new Colour(64,64,64);
			drawTyre(Colour.BLACK,GREY) ;
		}

		GL11.glEndList();

		GL11.glNewList(planeList,GL11.GL_COMPILE);
		{   
			drawUnitPlane();
		}
		GL11.glEndList();

	}
	protected void checkSceneInput()
	{//TODO: Check for keyboard and mouse input here
		if(Keyboard.isKeyDown(Keyboard.KEY_R))
		{   centreAngle += 1.0f * getAnimationScale(); // Make the house go around if the R key is pressed
		if (centreAngle > 360.0f) // Wrap the angle back around into 0-360 degrees.
		{  centreAngle = 0.0f;
		}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_T))
		{   centreAngle -= 1.0f * getAnimationScale(); // Make the house go around if the R key is pressed
		if (centreAngle > 360.0f) // Wrap the angle back around into 0-360 degrees.
		{  centreAngle = 0.0f;
		}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_M))
		{   
			currentTractorX -= 0.025f * getAnimationScale();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_L))
		{   currentTractorX += 0.025f * getAnimationScale();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{   currentTractorX = 0.0f;
		}
	}
	protected void updateScene()
	{
		//TODO: Update your scene variables here - remember to use the current animation scale value
		//        (obtained via a call to getAnimationScale()) in your modifications so that your animations
		//        can be made faster or slower depending on the machine you are working on

	}


	protected void renderScene()
	{//TODO: Render your scene here - remember that a scene graph will help you write this method! 
		//      It will probably call a number of other methods you will write.

		// draw ground
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture 
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);

			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();

			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,planeTexture.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(0.0f,-1.0f,-10.0f);
			GL11.glScaled(25.0f, 1.0f, 20.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the back sky
		GL11.glPushMatrix();
		{

			// sky texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			Colour.WHITE.submit();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, skyTexture.getTextureID());;

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(0.0f,4.0f,-20.0f);
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			GL11.glScaled(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		GL11.glPushMatrix();{

			GL11.glTranslatef(currentTractorX, -0.5f, -5f);
			GL11.glScalef(0.25f, 0.25f, 0.25f);
			GL11.glRotatef(centreAngle, 0f, 0.5f, 0f);

			// front part
			GL11.glPushMatrix();
			{
				GL11.glCallList(frontList);
			}
			GL11.glPopMatrix();

			//bottom part
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(0.2f, 0.5f, 0f);
				GL11.glScalef(1.2f, 0.2f, 1f);
				GL11.glCallList(bottomList);
			}
			GL11.glPopMatrix();

			// main body
			GL11.glPushMatrix();
			{
				GL11.glScalef(1f, 2f, 1.3f);
				GL11.glTranslatef(3f, -0.65f, 0.2f);
				GL11.glCallList(mainList);	
			}
			GL11.glPopMatrix();

			// left rear tyre
			GL11.glPushMatrix();
			{

				GL11.glScalef(0.25f,0.25f, 0.75f);
				GL11.glTranslatef(2.5f, 4f, -3.1f);
				GL11.glCallList(tyreList);

			}
			GL11.glPopMatrix();

			//right rear tyre
			GL11.glPushMatrix();
			{
				GL11.glScalef(0.25f,0.25f, 0.75f);
				GL11.glTranslatef(2.5f, 4f, 1.3f);
				GL11.glCallList(tyreList);

			}
			GL11.glPopMatrix();

			//left front tyre
			GL11.glPushMatrix();
			{
				GL11.glScalef(0.15f,0.15f, 0.5f);
				GL11.glTranslatef(-25f, 4f, -4.1f);
				GL11.glCallList(tyreList);

			}
			GL11.glPopMatrix();

			//right front tyre
			GL11.glPushMatrix();
			{
				GL11.glScalef(0.15f,0.15f, 0.5f);
				GL11.glTranslatef(-25f, 4f, 1.1f);
				GL11.glCallList(tyreList);

			}
			GL11.glPopMatrix();

		}
		GL11.glPopMatrix();

	}
	protected void setSceneCamera()
	{
		// call the default behaviour defined in GraphicsLab. This will set a default perspective projection
		// and default camera settings ready for some custom camera positioning below...  
		super.setSceneCamera();

		//TODO: If it is appropriate for your scene, modify the camera's position and orientation here
		//        using a call to GL11.gluLookAt(...)
	}

	protected void cleanupScene()
	{//TODO: Clean up your resources here
	}


	private void drawUnitCube(Colour right, Colour front, Colour left, Colour back, Colour top, Colour bottom) 
	{
		// the vertices 
		Vertex 	v1 = new Vertex(-4f, 3f,  0f);
		Vertex 	v2 = new Vertex(-4f, 1f,  0f);
		Vertex 	v3 = new Vertex(-1f, 1f,  0f);
		Vertex 	v4 = new Vertex(-1f, 3f,  0f);
		Vertex 	v5 = new Vertex(-1f, 3f, -2f);
		Vertex 	v6 = new Vertex(-4f, 3f, -2f);
		Vertex 	v7 = new Vertex(-4f, 1f, -2f);
		Vertex 	v8 = new Vertex(-1f, 1f, -2f);

		//draw the right face

		GL11.glBegin(GL11.GL_POLYGON);
		{

			right.submit();

			v1.submit();
			v2.submit();
			v3.submit();
			v4.submit();

		}
		GL11.glEnd();

		// draw front face 

		GL11.glBegin(GL11.GL_POLYGON);
		{
			front.submit();

			v1.submit();
			v6.submit();
			v7.submit();
			v2.submit();

		}
		GL11.glEnd();


		// draw left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			left.submit();

			v6.submit();
			v5.submit();
			v8.submit();
			v7.submit();
		}
		GL11.glEnd();

		//draw back face 
		GL11.glBegin(GL11.GL_POLYGON);
		{
			back.submit();

			v5.submit();
			v4.submit();
			v3.submit();
			v8.submit();
		}
		GL11.glEnd();

		//draw top face 
		GL11.glBegin(GL11.GL_POLYGON);
		{
			top.submit();

			v5.submit();
			v6.submit();
			v1.submit();
			v4.submit();
		}
		GL11.glEnd();

		//draw bottom face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			bottom.submit();

			v3.submit();
			v2.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();
	}


	private void drawTyre(Colour outerTyre, Colour centre) {
		Vertex v1 = new Vertex( 0f,  6f, 0f);
		Vertex v2 = new Vertex(-3f,  5f, 0f);
		Vertex v3 = new Vertex(-5f,  3f, 0f);
		Vertex v4 = new Vertex(-6f,  0f, 0f);
		Vertex v5 = new Vertex(-5f, -3f, 0f);
		Vertex v6 = new Vertex(-3f, -5f, 0f);
		Vertex v7 = new Vertex( 0f, -6f, 0f);
		Vertex v8 = new Vertex( 3f, -5f, 0f);	 
		Vertex v9 = new Vertex( 5f, -3f, 0f);
		Vertex v10 = new Vertex(6f,  0f, 0f);
		Vertex v11 = new Vertex(5f,  3f, 0f);
		Vertex v12 = new Vertex(3f,  5f, 0f);

		Vertex v13 = new Vertex( 0f,  6f, -1f);
		Vertex v14 = new Vertex(-3f,  5f, -1f);
		Vertex v15 = new Vertex(-5f,  3f, -1f);
		Vertex v16 = new Vertex(-6f,  0f, -1f);
		Vertex v17 = new Vertex(-5f, -3f, -1f);
		Vertex v18 = new Vertex(-3f, -5f, -1f);
		Vertex v19 = new Vertex( 0f, -6f, -1f);
		Vertex v20 = new Vertex( 3f, -5f, -1f);	 
		Vertex v21 = new Vertex( 5f, -3f, -1f);
		Vertex v22 = new Vertex( 6f,  0f, -1f);
		Vertex v23 = new Vertex( 5f,  3f, -1f);
		Vertex v24 = new Vertex( 3f,  5f, -1f);



		//draw front tyre
		GL11.glBegin(GL11.GL_POLYGON);
		{
			outerTyre.submit();

			v1.submit();
			v2.submit();
			v3.submit();
			v4.submit();
			v5.submit();
			v6.submit();
			v7.submit();
			v8.submit();
			v9.submit();
			v10.submit();
			v11.submit();
			v12.submit();

		}
		GL11.glEnd();


		//draw back tyre
		GL11.glBegin(GL11.GL_POLYGON);
		{
			outerTyre.submit();

			v13.submit();
			v24.submit();
			v23.submit();
			v22.submit();
			v21.submit();
			v20.submit();
			v19.submit();
			v18.submit();
			v17.submit();
			v16.submit();
			v15.submit();
			v14.submit();


		}
		GL11.glEnd();	


		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v13.submit();
			v14.submit();
			v2.submit();
			v1.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v14.submit();
			v15.submit();
			v3.submit();
			v2.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v15.submit();
			v16.submit();
			v4.submit();
			v3.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v16.submit();
			v17.submit();
			v5.submit();
			v4.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v17.submit();
			v18.submit();
			v6.submit();
			v5.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v18.submit();
			v19.submit();
			v7.submit();
			v6.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v19.submit();
			v20.submit();
			v8.submit();
			v7.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v20.submit();
			v21.submit();
			v9.submit();
			v8.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v21.submit();
			v22.submit();
			v10.submit();
			v9.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v22.submit();
			v23.submit();
			v11.submit();
			v10.submit();
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v23.submit();
			v24.submit();
			v12.submit();
			v11.submit();
		}
		GL11.glEnd();


		GL11.glBegin(GL11.GL_POLYGON);
		{
			centre.submit();

			v24.submit();
			v13.submit();
			v1.submit();
			v12.submit();
		}
		GL11.glEnd();
	}


	private void drawUnitPlane()
	{
		Vertex v1 = new Vertex(-0.5f, 0.0f,-0.5f); // left,  back
		Vertex v2 = new Vertex( 0.5f, 0.0f,-0.5f); // right, back
		Vertex v3 = new Vertex( 0.5f, 0.0f, 0.5f); // right, front
		Vertex v4 = new Vertex(-0.5f, 0.0f, 0.5f); // left,  front

		// draw the plane geometry. order the vertices so that the plane faces up
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(),v3.toVector(),v2.toVector(),v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f,0.0f);
			v4.submit();

			GL11.glTexCoord2f(1.0f,0.0f);
			v3.submit();

			GL11.glTexCoord2f(1.0f,1.0f);
			v2.submit();

			GL11.glTexCoord2f(0.0f,1.0f);
			v1.submit();
		}
		GL11.glEnd();

		// if the user is viewing an axis, then also draw this plane
		// using lines so that axis aligned planes can still be seen
		if(isViewingAxis())
		{
			// also disable textures when drawing as lines
			// so that the lines can be seen more clearly
			GL11.glPushAttrib(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			{
				v4.submit();
				v3.submit();
				v2.submit();
				v1.submit();
			}
			GL11.glEnd();
			GL11.glPopAttrib();
		}
	}
}
