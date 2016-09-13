package utils;

import java.nio.FloatBuffer;

import android.graphics.Canvas.VertexMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RemoteViews.ActionException;

public class Button extends circle {
	onClick listener;
	boolean clicked=true;
	public Button(float x,float y,float r,onClick listener){
		super(x,y,r,(short)6);
		this.listener=listener;

		}

	public void click(float x,float y){
		if(Math.sqrt(Math.pow(x-this.x, 2)+Math.pow(y-this.y, 2))<r)
			listener.clicked(this);
		clicked=!clicked;
		}
	public void move(final double x,final double y){
		final Button dis=this;
		new Thread(new Runnable() {

			public void run() {
				while(Math.abs(dis.x-x)>=0.1||Math.abs(dis.y-y)>=0.1){

					dis.x-=(dis.x-x)/3000000;
					dis.y-=(dis.y-y)/3000000;


				}



			}
		}).start();




	}
}
