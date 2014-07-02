package com.example.idealportrait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class Files {
	public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

	public Files(){
	}
	
	/**
	 * Method creates basic android intent for sharing image with text
	 * @param f file for sharing
	 * @param text image caption 
	 * @return intent
	 */
	public static Intent shareFile(File f,String text){
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("*/*");
		Uri uri = Uri.fromFile(f);
	    share.putExtra(Intent.EXTRA_TEXT, text);
		share.putExtra(Intent.EXTRA_STREAM, uri);
		share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		return share;
	}
	
	/**
	 * Creates new instance of File
	 * @param path absolute path of file
	 * @return file
	 */
	public static File createFile(String path){
		return new File(path);
	}
	
	
	/**
	 * Simple write to file
	 * @param f 
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFile(File f,byte[] content) throws IOException{
		FileOutputStream out = null;
		try {
		    out = new FileOutputStream(f);
		    out.write(content);
		    out.flush();  // will create the file physically.
		} catch (IOException e) {
		    Log.w("Create File", "Failed to write into " + f.getName());
		} finally {
		    if (out != null) {
		        try {
		            out.close();
		        } catch (IOException e) {
		        }
		    }
		}
	}
	
	/**
	 * Creates empty mediafile of given type, generating filename
	 * @param type type of media file (Image/Video)
	 * @return
	 */
	public static File getOutputMediaFile(int type){
	    
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "Ideal Portrait");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	        
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }
	    
	    return mediaFile;
	}
	
	/**
	 * Get URI of file type
	 * @param type type of media file
	 * @return
	 */
	public static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
}
