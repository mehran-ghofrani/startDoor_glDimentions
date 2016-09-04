package circle2d.circle2d;

import java.lang.Math;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.w3c.dom.Text;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RemoteViews.ActionException;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.PowerManager;
import utils.*;


public class GLView extends GLSurfaceView implements Renderer ,OnTouchListener{

	public static TextView tv;
	public static RelativeLayout.LayoutParams params;
	public static RelativeLayout rl;
	public static GL10 gl;
	static float cmToGL=(float)2/53;
	public boolean homeBtn=false;
    public float homeBtnC;
    public Float firstX=null;
    public Float firstY=null;
    public circle c,c2;
    public boolean p1=false,p2=false;
    public line pView;
    public Point p1c=new Point(),p2c=new Point();
    public Activity parentAct;
    public line aaaaa;
    public circle a;
    float distanceFromKey;
    float screenSize;
    float lit;
    float litInc=0.01f;
    circle menuKey;


	public GLView(Context context) {

		super(context);

		parentAct=(Activity)context;
		setRenderer(this);
		setOnTouchListener(this);

		p2c.x=(21/2)*cmToGL;
		p2c.y=(34-53/2)*cmToGL;








	}
	public void onDrawFrame(GL10 gl) {

		gl.glClearColor(1-(homeBtnC/1.5f-1/5f), 1-(homeBtnC/1.5f-1/5f), 1-(homeBtnC/1.5f-1/5f),1-(homeBtnC/1.5f-1/5f));
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);


		if(!homeBtn&&homeBtnC>0.2){
			if(lit>1)
				litInc=-0.01f;
			if(lit<0)
				litInc=0.01f;
			lit+=litInc;

			gl.glColor4f(lit, lit, lit, 0);
			homeBtnC-=(homeBtnC-0.2)/10;///////executes forever
			a=new circle(0, -1, homeBtnC, (short)4);
		}




		a.draw(gl);
		gl.glColor4f(0, 0, 0, 0);

		pView.draw(gl);
		c.draw(gl);
//		c2.draw(gl);

		menuKey.draw(gl);







	}
	public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {

	}
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		screenSize=(float)Math.sqrt(Math.pow(getHeight(), 2)+Math.pow(getWidth(), 2));

		gl.glClearColor(1, 1, 1, 0);
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);
		homeBtnC=0.4f;
//		a=new circle(0, -1, homeBtnC, (short)128);
//		aaaaa=new line(-1, -1, 0, 1, 1, 0);

		c2=new circle(0,0,0,(short)0);
		gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
		GLU.gluOrtho2D(gl,-(float)getWidth()/getHeight(),(float)getWidth()/getHeight(), -1, +1);
		gl.glColor4f(0, 0, 0, 0);
		gl.glClearColor(1, 1, 1, 0);
		p1c.x=p2c.x;
		p1c.y=p1c.y-screenSize/10000;

		pView=new line(p1c.x, p1c.y, 0, p2c.x,p2c.y,0);
		c=new circle(p1c.x, p1c.y, screenSize/100000, (short)19);

		menuKey=new circle(0, 0, 0.3f, (short)6);

//		RelativeLayout rl = new RelativeLayout(parentAct);
//        TextView tv = new TextView(parentAct);
//        tv.setBackgroundColor(Color.YELLOW);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(((Circle2dActivity)parentAct).w,((Circle2dActivity)parentAct).h);
//
//
//
//        rl.addView(tv, params);
//
//
//        params.setMargins(100, 100, 100, 200);
//
//
//
//        parentAct.addContentView(rl,  new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));




	}
	public boolean onTouch(View v, MotionEvent event) {

		if(event.getAction()==event.ACTION_MOVE){





			if(homeBtn){

				homeBtnC+=(firstY-event.getY())/screenSize*4;
				homeBtnC+=(event.getX()-firstX)*Math.signum(firstX/getWidth()-0.5)/screenSize*4;




				a=new circle(0, -1, homeBtnC, (short)4);

				if(homeBtnC<=1/5f)
					homeBtnC=1/5f;



				if(homeBtnC>=1.9f)
					parentAct.setContentView(new DrawView(parentAct));

			}


/////////////////////////////////////////////////////////////////////////////////////////////////////////

			float glEventcX=((event.getX()/(float)getWidth())*(2*((float)getWidth()/getHeight())))-((float)getWidth()/getHeight());
			float glEventcY=event.getY()/getHeight()*-2+1;

//			double distance1=Math.sqrt(Math.pow(p1c.x-glEventcX, 2)+Math.pow(p1c.y-glEventcY, 2));
//			double distance2=Math.sqrt(Math.pow(p2c.x-glEventcX, 2)+Math.pow(p2c.y-glEventcY, 2));

//			if(p2){
//				p2c.x=glEventcX;
//				p2c.y=glEventcY;
//				pView=new line(p2c.x, p2c.y, 0,p1c.x,p1c.y,0);
//
//			}


			float x=glEventcX-p2c.x;
			float y=glEventcY-p2c.y;

			if(p1){

				if(y<x&&y<-x){
					p1c.y=glEventcY;
					p1c.x=p2c.x;
				}else
				if(y<0){
					p1c.y=p2c.y;
					p1c.x=glEventcX;
				}else
					{
					p1c.x=glEventcX;
					p1c.y=glEventcY;




				}
				pView=new line(p2c.x, p2c.y, 0,p1c.x,p1c.y,0);












//				p1c.x=glEventcX;
//				p1c.y=glEventcY;
//				pView=new line(p2c.x, p2c.y, 0,p1c.x,p1c.y,0);


			}
/////////////////////////////////////////////////////////////////////////////////////////////////////////


			firstX=event.getX();
			firstY=event.getY();
		}

		if(event.getAction()==event.ACTION_DOWN){
			firstX=event.getX();
			firstY=event.getY();


/////////////////////////////////////////////////////////////////////////////////////////////////////////
			float glEventcX=((event.getX()/(float)getWidth())*(2*((float)getWidth()/getHeight())))-((float)getWidth()/getHeight());
			float glEventcY=event.getY()/getHeight()*-2+1;

			double distance1=Math.sqrt(Math.pow(p1c.x-glEventcX, 2)+Math.pow(p1c.y-glEventcY, 2));
			double distance2=Math.sqrt(Math.pow(p2c.x-glEventcX, 2)+Math.pow(p2c.y-glEventcY, 2));

			p1=(distance1>0.05)?false:true;
			p2=(distance2>0.05)?false:true;
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


		c=new circle(p1c.x, p1c.y, screenSize/100000, (short)19);
//		c2=new circle(p2c.x, p2c.y, screenSize/100000, (short)19);
		parentAct.runOnUiThread(new Runnable() {

			public void run() {
				tv.setText((int)(Math.sqrt(Math.pow(p1c.x-p2c.x, 2)+Math.pow(p1c.y-p2c.y, 2))/cmToGL)+"cm");

				params.setMargins((int)(getWidth()*(  ((p1c.x/((float)getWidth()/getHeight())+1)/2)  )), (int)(getHeight()*(p1c.y-1)/-2), 0, 0);

				GLView.rl.removeView(GLView.tv);
				GLView.rl.addView(GLView.tv, GLView.params);
			}
		});
		return true;
	}

}



