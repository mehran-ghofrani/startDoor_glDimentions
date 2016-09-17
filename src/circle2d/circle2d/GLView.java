package circle2d.circle2d;

import java.lang.Math;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.w3c.dom.Text;

import android.view.Display;
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
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.PowerManager;
import android.provider.Settings.System;
import android.util.Log;
import utils.*;


public class GLView extends GLSurfaceView implements Renderer ,OnTouchListener{

	public static boolean moving=false;
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
    Vector<utils.Button> menuKeys=new Vector<utils.Button>();



	public GLView(Context context) {

		super(context);

		parentAct=(Activity)context;
		utils.Button.parentActivity=parentAct;
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
			a.setRad(homeBtnC);

		}




		a.draw(gl);
		gl.glColor4f(0, 0, 0, 0);

		pView.draw(gl);
		c.draw(gl);
//		c2.draw(gl);

//		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
		for(utils.Button btn: menuKeys){
			btn.draw(gl);
		}
//		gl.glColor4f(0, 0, 0, 0);








	}
	public void onSurfaceChanged(GL10 gl, int arg1, int arg2) {



	}
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {







		parentAct.runOnUiThread(new Runnable() {

			public void run() {


				Display disp=parentAct.getWindow().getWindowManager().getDefaultDisplay();
				GLView.rl = new RelativeLayout(parentAct);
		        GLView.tv = new TextView(parentAct);
		        GLView.tv.setTextSize(disp.getWidth()*disp.getHeight()/40000);
		        GLView.tv.setText("Move the ruler to mesure");
		        GLView.params = new RelativeLayout.LayoutParams(((Circle2dActivity)parentAct).w,((Circle2dActivity)parentAct).h);
		        GLView.rl.addView(GLView.tv, GLView.params);


		        TextView tv2=new TextView(parentAct);
		        tv2.setTextSize(disp.getWidth()*disp.getHeight()/40000);
		        tv2.setText("Drag up");
		        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(((Circle2dActivity)parentAct).w,((Circle2dActivity)parentAct).h);
		        GLView.rl.addView(tv2,params2);
		        float width=tv2.getPaint().measureText("Drag up");
		        params2.setMargins((int)((disp.getWidth()-width)/2), (int)(disp.getHeight()*0.95), 0, 0);



		        parentAct.addContentView(GLView.rl,  new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));



			}
		});




		screenSize=(float)Math.sqrt(Math.pow(getHeight(), 2)+Math.pow(getWidth(), 2));


		homeBtnC=0.4f;

		c2=new circle(0,0,0,(short)0);
		gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
		gl.glMatrixMode(gl.GL_PROJECTION);
		GLU.gluOrtho2D(gl,-(float)getWidth()/getHeight(),(float)getWidth()/getHeight(), -1, +1);
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glColor4f(0, 0, 0, 0);
		gl.glClearColor(1, 1, 1, 0);
		p1c.x=p2c.x;
		p1c.y=p1c.y-screenSize/10000;
		a=new circle(0, -1, homeBtnC, (short)4);

		pView=new line(p1c.x, p1c.y, p2c.x,p2c.y);
		c=new circle(p1c.x, p1c.y, screenSize/100000, (short)19);








		final float x=-((float)getWidth()/getHeight())*0.7f,
					y=1-((float)getWidth()/getHeight())*0.3f,
					c=((float)getWidth()/getHeight())*0.1f;
		menuKeys.add(new utils.Button(x, y, c,"+", new onClick() {


			public void clicked(utils.Button btn) {




					if(btn.clicked==false){


						new Thread(new Runnable() {

							public void run() {
								GLView.moving=true;


								for(int i=0;i<screenSize*menuKeys.get(1).r/4.2;i++){
									try {
										Thread.sleep(4);
									} catch (InterruptedException e) {

										e.printStackTrace();
									}
									for(int j=1;j<=menuKeys.size()-1;j++){
										menuKeys.get(j).setLoc(x+(float)Math.sin((j/3d)*Math.PI)*i/200,
												y+(float) Math.cos((j/3d)*Math.PI)*i/200);

									}
									java.lang.System.out.println();


								}
								GLView.moving=false;
							}
						}).start();
						for(int j=1;j<=menuKeys.size()-1;j++){
							menuKeys.get(j).visible=true;
						}
					}
					else{

						for(int j=1;j<=menuKeys.size()-1;j++){
							menuKeys.get(j).visible=false;


						}

					}




			}
		}));
		menuKeys.add(new utils.Button(x, y, c,"?", new onClick() {

			public void clicked(utils.Button btn) {

			}
		}));
		menuKeys.add(new utils.Button(x, y, c,"?",  new onClick() {

			public void clicked(utils.Button btn) {

			}
		}));
		menuKeys.add(new utils.Button(x, y, c, "Film", new onClick() {

			public void clicked(utils.Button btn) {

			}
		}));
		menuKeys.add(new utils.Button(x, y, c, "Photo", new onClick() {

			public void clicked(utils.Button btn) {

			}
		}));
		menuKeys.add(new utils.Button(x, y, c, "Movie", new onClick() {

			public void clicked(utils.Button btn) {

			}
		}));
		menuKeys.add(new utils.Button(x, y, c, "Game", new onClick() {

			public void clicked(utils.Button btn) {

			}
		}));






	}
	public boolean onTouch(View v, MotionEvent event) {

		float glEventcX=((event.getX()/(float)getWidth())*(2*((float)getWidth()/getHeight())))-((float)getWidth()/getHeight());
		float glEventcY=event.getY()/getHeight()*-2+1;


		if(event.getAction()==event.ACTION_MOVE){


			if(homeBtn){

				homeBtnC+=(firstY-event.getY())/screenSize*4;
				homeBtnC+=(event.getX()-firstX)*Math.signum(firstX/getWidth()-0.5)/screenSize*4;




				a.setRad(homeBtnC);


				if(homeBtnC<=1/5f)
					homeBtnC=1/5f;



				if(homeBtnC>=1.9f)
					parentAct.setContentView(new DrawView(parentAct));

			}


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
				pView=new line(p2c.x, p2c.y,p1c.x,p1c.y);
			}
			firstX=event.getX();
			firstY=event.getY();
		}





		if(event.getAction()==event.ACTION_DOWN){



			for(utils.Button key:menuKeys){
				key.click(glEventcX, glEventcY);

			}
			firstX=event.getX();
			firstY=event.getY();



			double distance1=Math.sqrt(Math.pow(p1c.x-glEventcX, 2)+Math.pow(p1c.y-glEventcY, 2));
			double distance2=Math.sqrt(Math.pow(p2c.x-glEventcX, 2)+Math.pow(p2c.y-glEventcY, 2));

			p1=(distance1>0.05)?false:true;
			p2=(distance2>0.05)?false:true;

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
			homeBtn=false;
		}


		c.setLoc(p1c.x, p1c.y);
		parentAct.runOnUiThread(new Runnable() {

			public void run() {
				double a=(Math.sqrt(Math.pow(p1c.x-p2c.x, 2)+Math.pow(p1c.y-p2c.y, 2))/cmToGL);



				tv.setText(String.format( "%.2f", a )+" cm");

				GLView.params.setMargins((int)(getWidth()*(  ((p1c.x/((float)getWidth()/getHeight())+1)/2)  )), (int)(getHeight()*(p1c.y-1)/-2), 0, 0);

				GLView.rl.removeView(GLView.tv);
				GLView.rl.addView(GLView.tv, GLView.params);

			}
		});
		return true;
	}

}



