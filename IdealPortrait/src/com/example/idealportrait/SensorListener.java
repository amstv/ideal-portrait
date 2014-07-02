package com.example.idealportrait;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mLight;

    /***
     * SENSOR VALUE */
    private float currentLux = 30.0f;
    private final float dark = 10.0f;
    private Camera mCamera;
    private Parameters param;
    private boolean torch = false;


    public SensorListener(Context c){
        mSensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            currentLux = event.values[0];
        }

        if(currentLux < dark){
            param = mCamera.getParameters();
            param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(param);
            torch = true;
        }else if(currentLux >= dark && torch == true){
            param = mCamera.getParameters();
            param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(param);
            torch = false;
        }
    }

    public void resume(Camera camera){
        mCamera = camera;
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pause(){
        mSensorManager.unregisterListener(this);
    }
}