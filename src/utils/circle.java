package utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class circle {
	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;
	private int indicesL;

	public circle(float x,float y,float r,short parts) {



		indicesL=parts+2;
		short[] indices = new short[parts+2];
		float vertices[] = new float[(parts+1)*3];

		vertices[0]=x;
		vertices[1]=y;
		vertices[2]=0;
		for(short i=1;i<=parts;i++){
			float deg=((i-1)/(float)parts)*(float)2*(float)Math.PI;
			float rx=r*(float)Math.cos(deg);
			float ry=r*(float)Math.sin(deg);
			vertices[i*3]=x+rx;
			vertices[i*3+1]=y+ry;
			vertices[i*3+2]=0;
		}


		indices[parts+1]=1;
		for(short i=0;i<=parts;i++){

			indices[i]=i;

		}







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


		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLE_FAN, indicesL,
				GL10.GL_UNSIGNED_SHORT, indexBuffer);
	}
}
