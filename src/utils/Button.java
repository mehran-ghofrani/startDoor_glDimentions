package utils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.R.color;
import android.R.layout;
import android.R.string;
import android.app.Activity;
import android.graphics.Canvas.VertexMode;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RemoteViews.ActionException;
import circle2d.circle2d.Circle2dActivity;
import circle2d.circle2d.GLView;



public class Button extends circle {

	onClick listener;
	public TextView tv;
	public RelativeLayout.LayoutParams params;
	public boolean clicked=false;
	public boolean visible=true;
	public Button(final float x,final float y,float r,final String text,final int textSize,onClick listener){
		super(x,y,r,(short)6);
		this.listener=listener;
		tv = new TextView(parentActivity);

		parentActivity.runOnUiThread(new Runnable() {

			public void run() {



				Display disp=parentActivity.getWindow().getWindowManager().getDefaultDisplay();

		        tv.setTextSize(textSize*Math.max(disp.getWidth(),disp.getHeight())/40);
		        tv.setText(text);
//		        tv.setBackgroundColor(Color.RED);
		        params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		        GLView.rl.addView(tv,params);













//		        parentAct.addContentView(GLView.rl,  new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));




			}
		});
        setVisible(false);




		}

	public void click(float x,float y){
		if(Math.sqrt(Math.pow(x-this.x, 2)+Math.pow(y-this.y, 2))<r&&visible==true)
			if(!GLView.moving){

			clicked=!clicked;
			listener.clicked(this);




			}



		}
	public void move(final float x,final float y){

		new Thread(new Runnable() {

			public void run() {
				for(int i=1;i<=100;i++){

					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					float xMove=Button.this.x+((x-Button.this.x)/((101-i)*0.5f));
					float yMove=Button.this.y+((y-Button.this.y)/((101-i)*0.5f));
					setLoc(xMove, yMove);


				}


			}
		}).start();




	}


	public void draw(GL10 gl){
		if(visible==true)
			super.draw(gl);
	}
	public void setLoc(float x,float y){

		super.setLoc(x, y);
		moveText();






	}
	public void moveText(){

//		Rect bounds=new Rect();
//        tv.getPaint().getTextBounds((String) Button.this.tv.getText(),0, ((String) Button.this.tv.getText()).length(),bounds);
//        float textWidth=bounds.width();
//        float textHeight=bounds.height();
		parentActivity.runOnUiThread(new Runnable() {

			public void run() {


				float w=((Circle2dActivity)parentActivity).w;
				float h=((Circle2dActivity)parentActivity).h;
				float glw=((Button.this.x+(float)w/h)/((float)w*2/h))*w;
				float glh=((Button.this.y-1)/-2)*h;

		        params.setMargins((int)(glw-tv.getWidth()/2), (int)(glh-tv.getHeight()/2),0 ,0);

//		        params.setMargins((int)(glw-textWidth/2), (int)(glh-textHeight/2), (int)((w-glw)-textWidth/2), (int)((h-glh)-textHeight/2));
				tv.setLayoutParams(params);

			}
		});




	}

	public void setVisible(final boolean visibility){
		visible=visibility;
		parentActivity.runOnUiThread(new Runnable() {

			public void run() {
				tv.setVisibility(visibility?View.VISIBLE:View.INVISIBLE);

			}
		});

	}
}
