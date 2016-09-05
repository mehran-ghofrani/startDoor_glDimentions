package utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class line {

	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;

	public line(float x,float y,float z,float x2,float y2,float z2) {



		short[] indices = { 0, 1};
		float[] vertices = {
				      x,  y, z,  // 0, Top Left
				      x2,y2, z2,  // 1, Bottom Left
				};





		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);


		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}
	public void draw(GL10 gl) {


		gl.glLoadIdentity();
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		gl.glDrawElements(GL10.GL_LINES, 2,
				GL10.GL_UNSIGNED_SHORT, indexBuffer);


	}

}