package utils;

import android.content.Context;
import circle2d.circle2d.Circle2dActivity;



public class Ball extends circle {

	private float speed;
	private float x,y;

	Ball(float x,float y,Context context){

		super(x,y,Math.max(Circle2dActivity.w,Circle2dActivity.h)/20,(short)128);



	}

}
