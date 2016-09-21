package circle2d.circle2d;

import java.util.Vector;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import circle2d.circle2d.R.drawable;
import utils.OnSwipeTouchListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class ImagePane extends RelativeLayout {

	Vector<ImageItem> imgs;
	int currentPic;
	Context parentContext;
	int height;
	int width;
	static boolean moving=false;


	ImagePane(Context context){
		super(context);
		currentPic=0;
//		setBackgroundColor(Color.YELLOW);
		imgs=new Vector();
		parentContext=context;
		width=((Circle2dActivity)parentContext).w;
		height=((Circle2dActivity)parentContext).h;
		initialize();
	}


	private void initialize(){
		imgs.add(new ImageItem(parentContext,R.drawable.samsunet_1));
		imgs.add(new ImageItem(parentContext,R.drawable.samsunet_2));
		imgs.add(new ImageItem(parentContext,R.drawable.samsunet_3));
		imgs.add(new ImageItem(parentContext,R.drawable.samsunet_4));
		int i=0;
		for(ImageItem imgItm:imgs){

			RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
			params.setMargins(i*width, 0, -i*width, 0);
			addView(imgItm,params);
			imgItm.setBackgroundColor(Color.WHITE);
			i++;

		}
	}



	public void goNext(){
		move(true);

	}
	public void goPrev(){
		move(false);
	}
	private void move(final boolean right){

		final int[] currentLocation=new int[imgs.size()];
		for(int i=0;i<=imgs.size()-1;i++){
			currentLocation[i]=width*(i-currentPic);
		}
		final int stepsNum=right?100:-100;
		final double stepsLenght=(double)width/stepsNum;
		if ((1<=currentPic&&right||!right&&currentPic<=imgs.size()-2)&&!moving)
			new Thread(new Runnable() {



				public void run() {
					ImagePane.moving=true;
					for(int j=1;j<=Math.abs(stepsNum);j++){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						for(int i=0;i<=imgs.size()-1;i++){
							final int ii=i;
							final RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
							params.setMargins((i-currentPic)*width+(int)(stepsLenght*j), 0, -((i-currentPic)*width+(int)(stepsLenght*j)), 0);

							((Activity)parentContext).runOnUiThread(new Runnable() {
								public void run() {

									imgs.get(ii).setLayoutParams(params);


								}
							});

						}
					}






					currentPic+=right?-1:+1;
					ImagePane.moving=false;



				}
			}).start();







	}



}
class ImageItem extends ImageView{

	Context parentContext;
	public ImageItem(Context context,int imgRes) {
		super(context);
		parentContext=context;
		setImageResource(imgRes);

		setOnTouchListener(new OnSwipeTouchListener(context) {
		    public void onSwipeTop() {
		    }
		    public void onSwipeRight() {
		    	((ImagePane)getParent()).goNext();

		    }
		    public void onSwipeLeft() {
		    	((ImagePane)getParent()).goPrev();
		    }
		    public void onSwipeBottom() {
		    }

		});
	}






}
