package circle2d.circle2d;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.R.drawable;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import utils.*;
public class DrawView extends View implements OnTouchListener {
	public static DrawView d;
	public static boolean end=false;

    private static final String TAG = "DrawView";
    public Handler h;
    List<Point> points = new ArrayList<Point>();
    static Paint paint = new Paint();
    Point p=new Point();
    door d1;
    static Activity parentActivity;
    public DrawView(Context context) {
    	super(context);
    	end=false;
    	parentActivity=(Activity)context;


        h=new Handler();

        setBackgroundColor(Color.WHITE);
        d=this;
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }


    @Override
    public void onDraw(Canvas canvas) {

    	if (d1==null)
    		d1=new door();
    	if(d1.opened>=canvas.getWidth()/2-0.11){
    		end=true;
    		GLView glview=new GLView(parentActivity);
	    	parentActivity.setContentView(glview);
    	}
    	Paint white=new Paint();
    	white.setColor(Color.WHITE);
    	white.setAntiAlias(true);
    	white.setStrokeWidth(3);



    	canvas.drawRect(
    			0f,
    			0f,
    			canvas.getWidth()/2-d1.opened ,
    			canvas.getHeight(), paint);
    	canvas.drawRect(canvas.getWidth()/2+d1.opened,
    			0f,
    			canvas.getWidth(),
    			canvas.getHeight(), paint);
















		float x=canvas.getWidth()/20;
		float y=canvas.getHeight()/10;




		PointF[] points=new PointF[7];
		points[0]=new PointF(1*x-d1.opened+d1.arrowMove, 5*y);
		points[1]=new PointF(2*x-d1.opened+d1.arrowMove, 4*y);
		points[2]=new PointF(2*x-d1.opened+d1.arrowMove, 4.5f*y);
		points[3]=new PointF(4*x-d1.opened+d1.arrowMove, 4.5f*y);
		points[4]=new PointF(4*x-d1.opened+d1.arrowMove, 5.5f*y);
		points[5]=new PointF(2*x-d1.opened+d1.arrowMove, 5.5f*y);
		points[6]=new PointF(2*x-d1.opened+d1.arrowMove, 6*y);





		Path path = new Path();
		path.setFillType(Path.FillType.EVEN_ODD);
		path.moveTo(points[0].x,points[0].y);
		path.lineTo(points[1].x,points[1].y);
		path.lineTo(points[2].x,points[2].y);
		path.lineTo(points[3].x,points[3].y);
		path.lineTo(points[4].x,points[4].y);
		path.lineTo(points[5].x,points[5].y);
		path.lineTo(points[6].x,points[6].y);
		path.lineTo(points[0].x,points[0].y);
		path.close();

		canvas.drawPath(path, d1.p);


		for(int i=0;i<=6;i++)
			points[i]=new PointF(canvas.getWidth()-points[i].x,points[i].y);

		path = new Path();
		path.setFillType(Path.FillType.EVEN_ODD);
		path.moveTo(points[0].x,points[0].y);
		path.lineTo(points[1].x,points[1].y);
		path.lineTo(points[2].x,points[2].y);
		path.lineTo(points[3].x,points[3].y);
		path.lineTo(points[4].x,points[4].y);
		path.lineTo(points[5].x,points[5].y);
		path.lineTo(points[6].x,points[6].y);
		path.lineTo(points[0].x,points[0].y);
		path.close();

		canvas.drawPath(path, d1.p);


		boolean nextCOl=true;
		for(int i=0;i<=100;i++){
			nextCOl=!nextCOl;
			if(nextCOl){
				canvas.drawLine(canvas.getWidth()*0.1f, i*(canvas.getHeight()/100f), canvas.getWidth()*0.1f,  (i+1)*(canvas.getHeight()/100f), white);
				canvas.drawLine(canvas.getWidth()*0.9f, i*(canvas.getHeight()/100f), canvas.getWidth()*0.9f,  (i+1)*(canvas.getHeight()/100f), white);
			}
		}





    }

    public boolean onTouch(View view, MotionEvent event) {
        // if(event.getAction() != MotionEvent.ACTION_DOWN)
        // return super.onTouchEvent(event);
        Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();
        points.add(point);
        invalidate();
        Log.d(TAG, "point: " + point);

        if(d1.opened>view.getWidth()*0.4)
    		paint.setColor(Color.GRAY);
    	else
    		paint.setColor(Color.BLACK);
        if(d1.opened<1)
        	d1.opened=1;
        if(d1.closed!=true){
        if(event.getAction()==event.ACTION_DOWN){
        	if(event.getX()<view.getWidth()/2-d1.opened)
        		d1.left=true;
        	if(event.getX()>view.getWidth()/2+d1.opened)
        		d1.right=true;
        }
        if(event.getAction()==event.ACTION_MOVE){




        	if(d1.right==true)
        		d1.opened+=event.getX()-p.x;

        	if(d1.left==true)
        		d1.opened+=p.x-event.getX();

        	int lit=(int)((2*d1.opened/view.getWidth())*255);
			DrawView.paint.setColor(Color.rgb(lit,lit,lit));
        }


        if(event.getAction()==event.ACTION_UP)
        	d1.close(view.getWidth());


        p.x=event.getX();
        p.y=event.getY();
}
        return true;


    }
}


class door{

	boolean right=false;
	boolean left=false;
	boolean closed=false;
	float opened=1;
	int arrowIn;
	int arrowMove=0;
	Paint p=new Paint();


	door(){

		p.setAntiAlias(true);

		arrowIn=DrawView.d.getWidth()/10;
		new Thread(new Runnable() {
			public void run() {
				int r=0;
//				int offsetColor=1;
				int offsetMove=1;
				while(!DrawView.d.end){
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					arrowMove+=offsetMove;
					if(arrowMove==DrawView.d.getWidth()/10+arrowIn||arrowMove==-DrawView.d.getWidth()/10+arrowIn)
						offsetMove=-offsetMove;

//					r+=offsetColor;

//					if(r==255||r==0)
//						offsetColor=-offsetColor;

					float distance =(DrawView.d.getWidth()/10+arrowIn)+(DrawView.d.getWidth()/10+arrowIn);
					int lit=(int)(((arrowMove)/distance*2)*255);
					p.setColor(Color.rgb(lit, lit, lit));
					DrawView.d.h.post(new Runnable() {

						public void run() {
							DrawView.d.invalidate();
						}
					});
				}


			}
		}).start();




	}


	void close(final float widthF){


		right=left=false;
		if(opened>widthF*0.4)
			closed=true;
		new Thread(new Runnable() {

			public void run() {
				while(!DrawView.d.end&&((!left&&!right)&&opened>0.1||opened<-0.1)){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				    opened+=closed?opened/widthF*2:-opened/widthF*25;



					int lit=(int)((2*opened/widthF)*255);
					DrawView.paint.setColor(Color.rgb(lit,lit,lit));
					DrawView.d.h.post(new Runnable() {

						public void run() {
							DrawView.d.invalidate();
						}
					});
				}




			}
		}).start();






	}











}
