package utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class circle {

	FloatBuffer vertexBuffer;
	ShortBuffer indexBuffer;
	float x;
	float y;
	float r;




	public circle(float x,float y,float r,short parts) {


		this.r=r;
		this.x=x;
		this.y=y;


		short[] indices = new short[parts+2];
		float[] vertices = new float[(parts+1)*3];


		for(short i=1;i<=parts;i++){
			float deg=((i-1)/(float)parts)*(float)2*(float)Math.PI;
			vertices[i*3]=(float)Math.cos(deg);
			vertices[i*3+1]=(float)Math.sin(deg);

		}


		indices[parts+1]=1;
		for(short i=1;i<=parts;i++){

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

	public void setLoc(float x,float y){
		this.x=x;
		this.y=y;
	}
	public void setRad(float r){
		this.r=r;
	}
	public void draw(GL10 gl) {


		gl.glLoadIdentity();
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glScalef(r, r, 0);


		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLE_FAN, indexBuffer.capacity(),
				GL10.GL_UNSIGNED_SHORT, indexBuffer);

		gl.glLoadIdentity();


	}
}
