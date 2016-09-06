package utils;

import java.nio.FloatBuffer;

import android.graphics.Canvas.VertexMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RemoteViews.ActionException;

public class Button extends circle {
	onClick listener;
	public Button(float x,float y,float r,onClick listener){
		super(x,y,r,(short)6);
		this.listener=listener;
		}
	public void click(float x,float y){
		if(Math.sqrt(Math.pow(x-this.x, 2)+Math.pow(y-this.y, 2))<r)
			listener.clicked();
		}
}
