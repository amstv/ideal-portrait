package com.example.idealportrait;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.navigation.NavigationSystem;

/**
 * @author Andrej
 *
 */
/**
 * @author Andrej
 *
 */
public class CameraActivity extends Activity {
	
	public static NavigationSystem nav;
	private static CameraProcessor cam;
    private Camera mCamera;
    private CameraPreview mPreview;
    private SensorListener lightSensor;
    private String TAG = "CameraActivity";
	private final int MEDIA_TYPE_IMAGE = 1;
    private final int MEDIA_TYPE_VIDEO = 2;
    private final String NAME_OF_CHECK_FILE = "check_play.txt";
    private final String CHECK_FILE_MESSAGE= "Played";
    private final String APP_PATH = "/data/data/com.example.idealportrait/files";
    private File checkFile = new File(APP_PATH
            + NAME_OF_CHECK_FILE);
    
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);		
        setContentView(R.layout.main_layout);
        getActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		nav = new NavigationSystem(this,Locale.US,this);	
		new Thread(nav).start();
		cam = new CameraProcessor(nav);
		new Thread(cam).start();
		
		lightSensor = new SensorListener(this);
		
		if(!checkFile.exists()){
			nav.speak("Welcome to Ideal Portrait. Vibrations and sound will navigate you through taking a photo. Most frequent means it is ready to take a photo. To take a photo click when the navigation is most frequent");
			try {
				Files.writeToFile(checkFile,CHECK_FILE_MESSAGE.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
    protected void onPause() {
        super.onPause();
        lightSensor.pause();
        releaseCamera();    // release the camera immediately on pause event
    }
    
	@Override
    protected void onResume(){
    	super.onResume();

    	final PictureCallback mPicture = new PictureCallback() {
    	    @Override
    	    public void onPictureTaken(byte[] data, Camera camera) {

    	    	File pictureFile = Files.getOutputMediaFile(MEDIA_TYPE_IMAGE);
    			if (pictureFile == null){
    	            Log.d(TAG , "Error creating media file, check storage permissions:");
    	            return;
    	        }

    			try {
					Files.writeToFile(pictureFile, data);
				} catch (IOException e) {
					e.printStackTrace();
				}
    	        
            	Intent intent = new Intent(getApplicationContext(),ShowPhotoActivity.class);
            	intent.putExtra("ImageName", pictureFile.getAbsolutePath());
            	startActivity(intent);
    	    }
    	};
    	
        mCamera = getCameraInstance();
        lightSensor.resume(mCamera);

        /*
         * Turning on white balance and autofocus 
         */
        Parameters param = mCamera.getParameters();
        param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
        param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(param);

        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.setOnClickListener(
			    new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			            // get an image from the camera
			        				        	
			        	 if(nav.isCatchable()){
			            	mCamera.takePicture(null, null, mPicture);
			            	nav.speak("Captured,double tap for share image, swipe for delete image");
			            }
			        }
			    });
        
        preview.addView(mPreview);
 }
	
	public void onDestroy(){
		nav.shutdown();
		cam.shutdown();
		releaseCamera();
		super.onDestroy();
	}
		
	
	/**
	 * Safe get instance of camera
	 * @return return camera instance or null
	 */
	private Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	    	e.printStackTrace();
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
	/**
	 * Method for safe release camera 
	 */
	private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
            mPreview.getHolder().removeCallback(mPreview);
        }
    }
	
	public void startFaceDetection(){
	    // Try starting Face Detection
	    Camera.Parameters params = mCamera.getParameters();

	    // start face detection only *after* preview has started
	    if (params.getMaxNumDetectedFaces() > 0){
	        // camera supports face detection, so can start it:
	        mCamera.startFaceDetection();
	    }
	}
	
	/**
	 *
	 * @return returns instance of CameraProcessor 
	 */
	public static CameraProcessor getCameraProcessor(){
		if(cam == null){
			throw new NullPointerException();
		}
		return cam;
	}
	
	public void setTorch(){
		if(mCamera == null){
			return;
		}
		
        Parameters param = mCamera.getParameters();
        param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(param);
	}
	
}
