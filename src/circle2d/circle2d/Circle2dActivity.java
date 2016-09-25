package circle2d.circle2d;

import android.R.layout;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Circle2dActivity extends Activity {



	public static View currentPage;
	public static int h,w;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        h=getWindowManager().getDefaultDisplay().getHeight();
        w=getWindowManager().getDefaultDisplay().getWidth();



        setContentView(currentPage=new DrawView(this));

//        setContentView(new ImagePane(this));

//        drawView.requestFocus();
//        setContentView(new GLView(this));



























    }

}









