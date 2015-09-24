package com.example.hardwarecontrole;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends Activity {

	public static ImageView imgCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {

		case R.id.action_settings:
			return true;
		case R.id.action_camera:
			dispatchTakePictureIntent();
			return true;
		case R.id.action_camera_recording:
		   startRecording();
			return true;
			

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			imgCamera = (ImageView) rootView.findViewById(R.id.img);
			return rootView;
		}
	}

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		/*if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File

			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		}*/
		
		  if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		 startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); }
		 
	}

	private void setPic() {
		// Get the dimensions of the View
		int targetW = imgCamera.getWidth();
		int targetH = imgCamera.getHeight();

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		imgCamera.setImageBitmap(bitmap);
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = uri;
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	String mCurrentPhotoPath;
	private Uri uri;
	private String path;

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = "jai";// new
									// SimpleDateFormat("yyyyMMdd_HHmmss").format(new
									// Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			imgCamera.setImageBitmap(imageBitmap);
			uri=(Uri) extras.get("data");
		}
		
		
		
		//galleryAddPic();

		// setPic();
		if(requestCode==CAMERA_REQUEST_CODE_VEDIO)
		{
			Uri videoUri = data.getData();
           // path = Utils.getRealPathFromURI(videoUri, this);
           // manageVideo(path);
		}
	}
	int                     CAMERA_REQUEST_CODE_VEDIO=10;
	void startRecording()
	{
		/*Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
        	
        	takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        	
            startActivityForResult(takeVideoIntent,
                    CAMERA_REQUEST_CODE_VEDIO);*/
		
		Camera cam=Camera.open();
		cam.takePicture(null, null, null);
            
           
        }
	}

//
