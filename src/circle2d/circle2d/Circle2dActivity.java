package circle2d.circle2d;

import android.R.layout;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Circle2dActivity extends Activity {

	static DrawView drawView;
	static DrawView drawView2;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();











    }

}









