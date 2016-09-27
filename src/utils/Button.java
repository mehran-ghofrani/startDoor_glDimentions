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
	public Button(float x,float y,float r,final String text,final int textSize,onClick listener){
		super(x,y,r,(short)6);
		this.listener=listener;
		parentActivity.runOnUiThread(new Runnable() {

			public void run() {

				Display disp=parentActivity.getWindow().getWindowManager().getDefaultDisplay();
		        tv = new TextView(parentActivity);
		        tv.setTextSize(textSize*Math.max(disp.getWidth(),disp.getHeight())/40);
		        tv.setText(text);
		        tv.setBackgroundColor(Color.RED);
		        GLView.rl.addView(tv);
		        params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		        moveText();

		        tv.setVisibility(View.GONE);




//		        parentAct.addContentView(GLView.rl,  new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));




			}
		});


		}

	public void click(float x,float y){
		if(Math.sqrt(Math.pow(x-this.x, 2)+Math.pow(y-this.y, 2))<r&&visible==true)
			if(!GLView.moving){

			clicked=!clicked;
			listener.clicked(this);




			}



		}
	public void move(final double x,final double y){

		new Thread(new Runnable() {

			public void run() {
				while(Math.abs(Button.this.x-x)>=0.1||Math.abs(Button.this.y-y)>=0.1){

					Button.this.x-=(Button.this.x-x)/3000000;
					Button.this.y-=(Button.this.y-y)/3000000;





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



		parentActivity.runOnUiThread(new Runnable() {

			public void run() {

//				float textWidth=tv.getPaint().measureText((String) Button.this.tv.getText());
//				float textHeight=tv.getPaint().measureText("o")*2;
				moveText();


			}
		});


	}
	public void moveText(){

//		Rect bounds=new Rect();
//        tv.getPaint().getTextBounds((String) Button.this.tv.getText(),0, ((String) Button.this.tv.getText()).length(),bounds);
//        float textWidth=bounds.width();
//        float textHeight=bounds.height();
		float w=((Circle2dActivity)parentActivity).w;
		float h=((Circle2dActivity)parentActivity).h;
		float glw=((Button.this.x+(float)w/h)/((float)w*2/h))*w;
		float glh=((Button.this.y-1)/-2)*h;
        params.setMargins((int)(glw-tv.getWidth()/2), (int)(glh-tv.getHeight()/2),0 ,0);

//        params.setMargins((int)(glw-textWidth/2), (int)(glh-textHeight/2), (int)((w-glw)-textWidth/2), (int)((h-glh)-textHeight/2));
		tv.setLayoutParams(params);



	}

}
