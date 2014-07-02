package com.example.idealportrait;

import android.hardware.Camera;
import android.hardware.Camera.Face;

class MyFaceDetectionListener implements Camera.FaceDetectionListener {

    @Override
    public void onFaceDetection(Face[] faces, Camera camera) {
        if (faces.length > 0){
            CameraActivity.getCameraProcessor().add(faces[0].rect);
        }
    }
}