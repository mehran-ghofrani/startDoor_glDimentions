package utils;

import android.view.View.OnClickListener;
import android.widget.RemoteViews.ActionException;

public class Button extends circle {
	OnClickListener listener;
	Button(float x,float y,float r,OnClickListener listener){
		super(x,y,r,(short)6);
		this.listener=listener;
		}
}
