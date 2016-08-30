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
import android.opengl.GLU;
import android.os.PowerManager;
import utils.*;


public class GLView extends GLSurfaceView implements Renderer ,OnTouchListener{

	public static GL10 gl;
	public boolean homeBtn=false;
    public float homeBtnC;
    public Float firstX=null;
    public Float firstY=null;
    public boolean p1=false,p2=false;
    public circle p1view,p2view;
    public Point p1c=new Point(),p2c=new Point();
    public Activity parentAct;
    public line aaaaa;
    public circle a;
    float distanceFromKey;


	public GLView(Context context) {
		super(context);
		parentAct=(Activity)context;
		setRenderer(this);
		setOnTouchListener(this);
	}
	public void onDrawFrame(GL10 gl) {

		gl.glClearColor(1-(homeBtnC-1/5f), 1-(homeBtnC-1/5f), 1-(homeBtnC-1/5f),1-(homeBtnC-1/5f));
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);



		a.draw(gl);
		p1view.draw(gl);
		p2view.draw(gl);






	}
	public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {

	}
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		gl.glClearColor(1, 1, 1, 0);
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);
		homeBtnC=1/5f;
		a=new circle(0, -1, homeBtnC, (short)128);
		aaaaa=new line(-1, -1, 0, 1, 1, 0);
		gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
		GLU.gluOrtho2D(gl, -(float)getWidth()/getHeight(),(float)getWidth()/getHeight(), -1, +1);
		gl.glColor4f(0, 0, 0, 0);
		gl.glClearColor(1, 1, 1, 0);
		p1view=p2view=new circle(0, 0, 0, (short)16);
	}
	public boolean onTouch(View v, MotionEvent event) {
		float screenSize=(float)Math.sqrt(Math.pow(getHeight(), 2)+Math.pow(getWidth(), 2));

		if(event.getAction()==event.ACTION_MOVE){





			if(homeBtn){

				homeBtnC+=(firstY-event.getY())/screenSize*4;
				homeBtnC+=(event.getX()-firstX)*Math.signum(firstX/getWidth()-0.5)/screenSize*4;




				a=new circle(0, -1, homeBtnC, (short)128);

				if(homeBtnC<=1/5f)
					homeBtnC=1/5f;



				if(homeBtnC>=6/5f)
					parentAct.setContentView(new DrawView(parentAct));

			}
			float glEventcX=((event.getX()/(float)getWidth())*(2*((float)getWidth()/getHeight())))-((float)getWidth()/getHeight());
			float glEventcY=event.getY()/getHeight()*-2+1;

			double distance1=Math.sqrt(Math.pow(p1c.x-glEventcX, 2)+Math.pow(p1c.y-glEventcY, 2));
			double distance2=Math.sqrt(Math.pow(p2c.x-glEventcX, 2)+Math.pow(p2c.y-glEventcY, 2));

			if(p2){
				p2c.x=glEventcX;
				p2c.y=glEventcY;
				p2view=new circle(p2c.x, p2c.y, screenSize/100000, (short)128);
//				p1view=new circle(p1c.x, p1c.y, screenSize/50000, (short)128);

			}
			else{
				p1c.x=glEventcX;
				p1c.y=glEventcY;
				p1view=new circle(p1c.x, p1c.y, screenSize/100000, (short)128);
//				p2view=new circle(p2c.x, p2c.y, screenSize/50000, (short)128);


			}

			firstX=event.getX();
			firstY=event.getY();
		}

		if(event.getAction()==event.ACTION_DOWN){
			firstX=event.getX();
			firstY=event.getY();
			p2=!p2;

/////////////////////////////////////////////////////////////////////////////////////////////////////////
			float glEventcX=((event.getX()/(float)getWidth())*(2*((float)getWidth()/getHeight())))-((float)getWidth()/getHeight());
			float glEventcY=event.getY()/getHeight()*-2+1;

			double distance1=Math.sqrt(Math.pow(p1c.x-glEventcX, 2)+Math.pow(p1c.y-glEventcY, 2));
			double distance2=Math.sqrt(Math.pow(p2c.x-glEventcX, 2)+Math.pow(p2c.y-glEventcY, 2));

			if(p2){
				p2c.x=glEventcX;
				p2c.y=glEventcY;
				p2view=new circle(p2c.x, p2c.y, screenSize/100000, (short)128);
//				p1view=new circle(p1c.x, p1c.y, screenSize/50000, (short)128);

			}
			else{
				p1c.x=glEventcX;
				p1c.y=glEventcY;
				p1view=new circle(p1c.x, p1c.y, screenSize/100000, (short)128);
//				p2view=new circle(p2c.x, p2c.y, screenSize/50000, (short)128);

			}
///////////////////////////////////////////////////////////////////////////////////////////////////////

			distanceFromKey=(float)Math.sqrt(
					Math.pow(this.getWidth()/2-event.getX(),2)+
					Math.pow(this.getHeight()-event.getY(),2)
					);
			if(distanceFromKey<getHeight()*homeBtnC/2){
				homeBtn=true;

			}
			else{
				homeBtn=false;


			}
		}


		if(event.getAction()==event.ACTION_UP){




		}

		return true;
	}

}



