package framework;

import javax.microedition.khronos.opengles.GL10;

import implemented.GLGraphics;

public class GLPrimitives {

	public static void drawLine(GLGraphics graphics, float x1, float y1,
			float x2, float y2) {
		float verticesBuffer[] = { x1, y1, 1, 0, 0, 1, x2, y2, 1, 0, 0, 1 };
		Vertices vertices = new Vertices(graphics, 12, 0, true, false);
		vertices.setVertices(verticesBuffer, 0, 12);
		vertices.bind();
		vertices.draw(GL10.GL_LINES, 0, 2);
		vertices.unbind();
	}
	
	public static void filledPolygon(GLGraphics graphics, int color, float[] x, float[] y)
	{
	   float verticesBuffer[] = new float[x.length*6];
	   int j = 0;
	   for(int i = 0; i<verticesBuffer.length; i+=6){
		   verticesBuffer[i] = x[j];
		   verticesBuffer[i+1] = y[j];
		   verticesBuffer[i+2] = 1;
		   verticesBuffer[i+3] = 0;
		   verticesBuffer[i+4] = 0;
		   verticesBuffer[i+5] = 0.2f;
		   j++;
	   }
	   
	   Vertices vertices = new Vertices(graphics, verticesBuffer.length, 0, true, false);
	   vertices.setVertices(verticesBuffer, 0, verticesBuffer.length);
	   vertices.bind();
	   vertices.draw(GL10.GL_TRIANGLE_STRIP, 0, x.length);
	   vertices.unbind();
	}

}
