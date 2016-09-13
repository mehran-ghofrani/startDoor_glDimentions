package utils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.R.string;
import android.app.Activity;
import android.graphics.Canvas.VertexMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RemoteViews.ActionException;



public class Button extends circle {

	onClick listener;
	public boolean clicked=false;
	public boolean visible=true;
	public Button(float x,float y,float r,String text,onClick listener){
		super(x,y,r,(short)6);
		this.listener=listener;
		parentActivity.runOnUiThread(new Runnable() {

			public void run() {
				//set text to btn 47

			}
		});


		}

	public void click(float x,float y){
		if(Math.sqrt(Math.pow(x-this.x, 2)+Math.pow(y-this.y, 2))<r&&visible==true)
			listener.clicked(this);
		clicked=!clicked;
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
}
