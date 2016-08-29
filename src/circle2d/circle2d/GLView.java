package circle2d.circle2d;

import java.lang.Math;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;
import android.widget.LinearLayout;
import android.widget.RemoteViews.ActionException;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.PowerManager;

class line {

	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;

	public line(float x,float y,float z,float x2,float y2,float z2) {



		short[] indices = { 0, 1};
		float vertices[] = {
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


		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		gl.glDrawElements(GL10.GL_LINES, 2,
				GL10.GL_UNSIGNED_SHORT, indexBuffer);


	}

}

class circle {
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


public class GLView extends GLSurfaceView implements Renderer ,OnTouchListener{

	public static GL10 gl;
	public boolean homeBtn=false;
    public float homeBtnC=0;
    public float lastX,lastY;
    public boolean p1=false,p2=false;
    public Point p1c,p2c;
    public Activity parentAct;
    public line aaaaa;
    circle a;
    float distanceFromKey;


	public GLView(Context context) {
		super(context);
		parentAct=(Activity)context;
		setRenderer(this);
		setOnTouchListener(this);
	}
	public void onDrawFrame(GL10 gl) {

		gl.glClear(gl.GL_COLOR_BUFFER_BIT);

		aaaaa.draw(gl);


		a.draw(gl);






	}
	public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {

	}
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		a=new circle(0, 0, 1, (short)15);
		aaaaa=new line(0,0,0,0.5f,0.5f,0.5f);
		gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
	}
	public boolean onTouch(View v, MotionEvent event) {



		if(event.getAction()==event.ACTION_DOWN){
			distanceFromKey=(float)Math.sqrt(
					Math.pow(this.getWidth()/2-event.getX(),2)+
					Math.pow(this.getHeight()-event.getY(),2)
					);
			if(distanceFromKey<this.getHeight()/5){
				parentAct.setContentView(new DrawView(parentAct));

			}
			else{
				homeBtn=false;

				if(p2)
					p1=p2=false;
				if(p1)
					p2=true;
				else
					p1=true;
			}
		}

//		if(event.getAction()==event.ACTION_MOVE){
//
//			if(homeBtn){
//				homeBtnC+=event.getY()-lastY;
//				homeBtnC+=event.getX()-lastX;
//
//
//
//
//				if(homeBtnC>=this.getHeight()/5)
//					parentAct.setContentView(new DrawView(parentAct));
//
//			}
//			if(p2==true){
//				p2c.x=event.getX();
//				p2c.y=event.getY();
//			}
//			else{
//				p1c.x=event.getX();
//				p1c.y=event.getY();
//
//
//			}
//
//
//		}
//		if(event.getAction()==event.ACTION_UP){
//			homeBtnC=0;
//
//
//
//		}
		lastX=event.getX();
		lastY=event.getY();
		return false;
	}

}



