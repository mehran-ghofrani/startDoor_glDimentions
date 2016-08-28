package circle2d.circle2d;

import java.lang.Math;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;
import android.widget.RemoteViews.ActionException;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.PowerManager;

public class GLView extends GLSurfaceView implements Renderer ,OnTouchListener{

	public static GL10 gl;
	public boolean homeBtn=false;
    public int homeBtnC=0;
    public int lastX,lastY;
    public boolean p1=false,p2=false;
    public Point p1c,p2c;
    public Activity parentAct;


	public GLView(Context context) {
		super(context);
		parentAct=(Activity)context;
		setRenderer(this);
		setOnTouchListener(this);
	}
	public void onDrawFrame(GL10 gl) {







	}
	public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {

	}
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {

	}
	public void line(float x1,float y1,float z1,float x2,float y2,float z2){

	}
	public boolean onTouch(View v, MotionEvent event) {

		if(event.getAction()==event.ACTION_DOWN){
			double distanceFromKey=Math.sqrt(
					Math.pow(this.getWidth()/2-event.getX(),2)+
					Math.pow(this.getHeight()-event.getY(),2)
					);
			if(distanceFromKey>this.getHeight()/5){
				homeBtn=true;
			}
			else{
				homeBtn=false;

				if(p2)
					p1=p2=false;
				if(p1)
					p2=true;
				else
					p1=true;
			}
		}

		if(event.getAction()==event.ACTION_MOVE){
			if(homeBtn==true){
				homeBtnC+=event.getY()-lastY;
				homeBtnC+=event.getX()-lastX;
				if(homeBtnC==this.getHeight()/5)
					parentAct.setContentView(new DrawView(parentAct));

			}
			if(p2==true){
				p2c.x=event.getX();
				p2c.y=event.getY();
			}
			else{
				p1c.x=event.getX();
				p1c.y=event.getY();


			}


		}
		return false;
	}





}

public class Line {
    private FloatBuffer VertexBuffer;

    private final String VertexShaderCode =
        // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +

        "attribute vec4 vPosition;" +
        "void main() {" +
        // the matrix must be included as a modifier of gl_Position
        "  gl_Position = uMVPMatrix * vPosition;" +
        "}";

    private final String FragmentShaderCode =
        "precision mediump float;" +
        "uniform vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
        "}";

    protected int GlProgram;
    protected int PositionHandle;
    protected int ColorHandle;
    protected int MVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float LineCoords[] = {
        0.0f, 0.0f, 0.0f,
        1.0f, 0.0f, 0.0f
    };

    private final int VertexCount = LineCoords.length / COORDS_PER_VERTEX;
    private final int VertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.0f, 0.0f, 0.0f, 1.0f };

    public Line() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
            // (number of coordinate values * 4 bytes per float)
            LineCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        VertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        VertexBuffer.put(LineCoords);
        // set the buffer to read the first coordinate
        VertexBuffer.position(0);

        int vertexShader = ArRenderer.loadShader(GLES20.GL_VERTEX_SHADER, VertexShaderCode);
        int fragmentShader = ArRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FragmentShaderCode);

        GlProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(GlProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(GlProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(GlProgram);                  // creates OpenGL ES program executables
    }

    public void SetVerts(float v0, float v1, float v2, float v3, float v4, float v5) {
        LineCoords[0] = v0;
        LineCoords[1] = v1;
        LineCoords[2] = v2;
        LineCoords[3] = v3;
        LineCoords[4] = v4;
        LineCoords[5] = v5;

        VertexBuffer.put(LineCoords);
        // set the buffer to read the first coordinate
        VertexBuffer.position(0);
    }

    public void SetColor(float red, float green, float blue, float alpha) {
        color[0] = red;
        color[1] = green;
        color[2] = blue;
        color[3] = alpha;
    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(GlProgram);

        // get handle to vertex shader's vPosition member
        PositionHandle = GLES20.glGetAttribLocation(GlProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(PositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(PositionHandle, COORDS_PER_VERTEX,
                                 GLES20.GL_FLOAT, false,
                                 VertexStride, VertexBuffer);

        // get handle to fragment shader's vColor member
        ColorHandle = GLES20.glGetUniformLocation(GlProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(ColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        MVPMatrixHandle = GLES20.glGetUniformLocation(GlProgram, "uMVPMatrix");
        ArRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mvpMatrix, 0);
        ArRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, VertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(PositionHandle);
    }
 }
