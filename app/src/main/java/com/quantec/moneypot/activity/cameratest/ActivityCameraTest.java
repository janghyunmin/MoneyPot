package com.quantec.moneypot.activity.cameratest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quantec.moneypot.BuildConfig;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.tab4.ActivityNotiWebView;
import com.quantec.moneypot.activity.Main.Fragment.tab4.PermissionsDispatcher2;
import com.quantec.moneypot.util.Permissions.PermissionsDispatcher;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.zhihu.matisse.Matisse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;

import static com.theartofdev.edmodo.cropper.CropImage.getCaptureImageOutputUri;

public class ActivityCameraTest extends AppCompatActivity {

//    Button bt;
    ImageView image;
    CropImageView cropImageView;

    InputStream imageStream;
    private String mCurrentPhotoPath;
    static final int PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

//        bt = findViewById(R.id.bt);
        image = findViewById(R.id.image);

        cropImageView = findViewById(R.id.cropImageView);

        PermissionsDispatcher2.takePictureWithCheck(ActivityCameraTest.this);


//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openCamera();
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsDispatcher2.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void takePicture(){
//        dispatchTakePictur();

        openCamera();
    }

//    private void dispatchTakePictur(){
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(intent.resolveActivity(getPackageManager()) != null)
//        {
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            }catch (IOException e){}
//            if(photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        BuildConfig.APPLICATION_ID +".fileprovider",
//                        photoFile);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(intent, PHOTO);
//            }
//        }
//    }


    private void openCamera() {
        Uri outputFileUri = getCaptureImageOutputUri(this);

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (outputFileUri != null) {
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        }

        startActivityForResult(captureIntent, CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri);

            }
        }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.e("들어옴","들어옴" + result.getUri());

            Glide.with(this)
                    .load(result.getUri())
                    .placeholder(R.drawable.noname_img)
                    .error(R.drawable.noname_img)
//                    .circleCrop()
                    .into(image);



            try {
                imageStream = getContentResolver().openInputStream(result.getUri());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = encodeImage(selectedImage);
            Log.e("베이스64", "값 : "+encodedImage);

            Intent intent1 = new Intent(ActivityCameraTest.this, ActivityNotiWebView.class);
            intent1.putExtra("base64", encodedImage);
            setResult(100,intent1);
            finish();
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setAutoZoomEnabled(true)
                .setAllowRotation(true)
                .setBorderCornerOffset(0)
                .setBorderLineThickness(1)
                .start(this);
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
}
