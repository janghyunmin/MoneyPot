package quantec.com.moneypot.Activity.PortProfileModify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Fg_CookPage3;
import quantec.com.moneypot.Activity.PortProfileModify.Activity.ActivityPortProfileDescModify;
import quantec.com.moneypot.Activity.PortProfileModify.Activity.ActivityPortProfileNameModify;
import quantec.com.moneypot.Activity.PortProfileModify.Dialog.DialogImageReset;
import quantec.com.moneypot.Activity.PortProfileModify.Dialog.DialogModifyCancle;
import quantec.com.moneypot.Activity.PortProfileModify.Model.nModel.ModelImageSavedData;
import quantec.com.moneypot.BuildConfig;
import quantec.com.moneypot.Dialog.LoadingMakePort.DialogLoadingMakingPort;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.FileUtil.FileUtil;
import quantec.com.moneypot.Util.Permissions.PermissionsDispatcher;
import quantec.com.moneypot.databinding.ActivityPortProfileModifyBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPortProfileModify extends AppCompatActivity {

    ActivityPortProfileModifyBinding profileModifyBinding;

    private DialogImageReset imageResetDialog;
    private DialogModifyCancle portProfileModifyCancleDialog;

    String mCode;
    int mPhoto;
    String mName, mDesc, mImageUrl;

    static final int TAKE_PHOTO = 1;
    static final int CODE_FINISH = 999;
    static final int Modify_NameDesc = 200;

    private String mCurrentPhotoPath;
    Uri selectedUri = null;
    //서버 저장에 이미지를 저장 처리 여부
    int opt = 1;
    //수정된 포트 이름과 설명 / //저장할 포트 이름과 설명
    String ModifyName, ModifyDesc, SavePortName, SavePortDesc;
    //변경된 정보 여부
    boolean ChangedInfoState = false;
    //변경하려는 아이템 포지션 // 사진 회전된 각
    int ItemPositon, orientation;
    //저장시 로딩 상태
    boolean SavedLoadingState = false;
    private DialogLoadingMakingPort loadingCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileModifyBinding = DataBindingUtil.setContentView(this, R.layout.activity_port_profile_modify);
        profileModifyBinding.setPortProfileModify(this);

        // 스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Intent intent = getIntent();
        mCode = intent.getStringExtra("imageCode");
        mPhoto = intent.getIntExtra("imagePhoto",0);
        mName = intent.getStringExtra("imageName");
        mDesc = intent.getStringExtra("imageDesc");
        mImageUrl = intent.getStringExtra("imageUrl");
        ItemPositon = intent.getIntExtra("itemPosition", 0);
        //포트 설명 초기화
        profileModifyBinding.PortProfileModifyDescText.setText(mDesc);
        //포트 이름 초기화
        profileModifyBinding.PortProfileModifyNameText.setText(mName);
        //이미지 초기화 버튼 비지블 여부
        //커스텀 이미지 : mPhoto = 1 / 초기 이미지 : mPhoto = 0
        if(mPhoto == 0){
            profileModifyBinding.PortProfileModifyImageResetBt.setVisibility(View.GONE);
        }else{
            profileModifyBinding.PortProfileModifyImageResetBt.setVisibility(View.VISIBLE);
        }
        //포트 이미지 초기화
        Glide.with(this)
                .load(mImageUrl)
                .error(R.drawable.noname_img)
                .crossFade()
                .placeholder(R.drawable.noname_img)
                .bitmapTransform(new CropCircleTransformation(ActivityPortProfileModify.this))
                .into(profileModifyBinding.PortProfileModifyImage);
        //이미지 초기화 버튼
        profileModifyBinding.PortProfileModifyImageResetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageResetDialog = new DialogImageReset(ActivityPortProfileModify.this, leftListener, rightListener);
                imageResetDialog.show();
            }
        });
        //완료 버튼
        profileModifyBinding.PortProfileModifyTopOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangedInfoState) {
                    loadingCustom = new DialogLoadingMakingPort(ActivityPortProfileModify.this, "프로필 업로드 중 입니다.\n잠시만 기달려 주세요.");
                    loadingCustom.show();

                    String path = null;
                    try {
                        if (ModifyName == null) {
                            SavePortName = mName;
                        } else {
                            SavePortName = ModifyName;
                        }

                        if (ModifyDesc == null) {
                            SavePortDesc = mDesc;
                        } else {
                            SavePortDesc = ModifyDesc;
                        }

                        if (opt == 0) { // 이미지 변경이 있을때
                            if (selectedUri != null) {
                                //opt = 0
                                //이미지 + 이름
                                //opt = 1
                                //이름
                                path = FileUtil.getFilePath(ActivityPortProfileModify.this, selectedUri);
                                ExifInterface exif = null;
                                try {
                                    exif = new ExifInterface(path);
                                }catch (IOException e)
                                {e.printStackTrace();}
                                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                                File file = new File(path);

                                RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), saveBitmapToFile(file));
                                MultipartBody.Part archiveFile = MultipartBody.Part.createFormData("file", file.getName(), mFile);

                                Call<ModelImageSavedData> getchartItem = RetrofitClient.getInstance().getService().getImageTextUpload(archiveFile, mCode);
                                getchartItem.enqueue(new Callback<ModelImageSavedData>() {
                                    @Override
                                    public void onResponse(Call<ModelImageSavedData> call, Response<ModelImageSavedData> response) {
                                        if (response.code() == 200) {
                                            loadingCustom.dismiss();

                                            Intent ChangedInfo = new Intent(ActivityPortProfileModify.this, Fg_CookPage3.class);
                                            ChangedInfo.putExtra("PassPortIcon", response.body().getContent().getHome()+response.body().getContent().getFileFullPath());
//                                            ChangedInfo.putExtra("PassPortName", response.body().getContent().get());
//                                            ChangedInfo.putExtra("PassPortDesc", response.body().getProduct().get(0).getDescript());
                                            ChangedInfo.putExtra("PassPhoto",1);
                                            ChangedInfo.putExtra("PassPosition", ItemPositon);
                                            setResult(501, ChangedInfo);
                                            finish();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ModelImageSavedData> call, Throwable t) {
                                        Log.e("레트로핏 실패", "값 : " + t.getMessage());
                                    }
                                });
                            }
                        }
                        else if (opt == 1) { // 이미지 변경이 없을때
                            //opt = 0
                            //이미지 + 이름
                            //opt = 1
                            //이름
                            SavedEvent("1");
                        }
                        else { // opt == 2 // 이미지 초기화 일때
                            //opt = 2
                            //이미지 + 이름
                            //opt = 1
                            //이름
                            SavedEvent("2");
                        }
                    }
                    catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    finish();
                }
            }
        });
        //앨범 선택
        profileModifyBinding.PortProfileModifyImageAlbumBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsDispatcher.selectPhotoWithCheck(ActivityPortProfileModify.this);
            }
        });
        //사진 촬영 선택
        profileModifyBinding.PortProfileModifyImagePhotoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsDispatcher.takePictureWithCheck(ActivityPortProfileModify.this);
            }
        });
        //포트 이름, 설명 수정 페이지 이동
        profileModifyBinding.PortProfileModifyNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveName = new Intent(ActivityPortProfileModify.this, ActivityPortProfileNameModify.class);
                if(ModifyName==null) {
                    moveName.putExtra("mName",mName);
                    moveName.putExtra("mmCode", mCode);
                    moveName.putExtra("mDesc", mDesc);
                }else{
                    moveName.putExtra("mName",mName);
                    moveName.putExtra("mmCode", mCode);
                    moveName.putExtra("mDesc",mDesc);
                }
                startActivityForResult(moveName, 200);
            }
        });
        //포트 이름, 설명 수정 페이지 이동
        profileModifyBinding.PortProfileModifyDescLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveDesc = new Intent(ActivityPortProfileModify.this, ActivityPortProfileDescModify.class);
                if(ModifyDesc == null) {
                    moveDesc.putExtra("mDesc",mDesc);
                    moveDesc.putExtra("mmCode", mCode);
                    moveDesc.putExtra("mName",mName);
                }else{
                    moveDesc.putExtra("mDesc",mDesc);
                    moveDesc.putExtra("mName",mName);
                    moveDesc.putExtra("mmCode", mCode);
                }
                startActivityForResult(moveDesc, 200);
            }
        });

    }//onCreate 끝

    void SavedEvent(String optValue){

            MultipartBody.Part opt = MultipartBody.Part.createFormData("opt", optValue);
            MultipartBody.Part name = MultipartBody.Part.createFormData("name", SavePortName);
            MultipartBody.Part desc = MultipartBody.Part.createFormData("desc", SavePortDesc);
            MultipartBody.Part ucode = MultipartBody.Part.createFormData("ucode", String.valueOf(mCode));
            MultipartBody.Part wch = MultipartBody.Part.createFormData("wch", "0");

//            Call<ModelImageSavedData> getchartItem = RetrofitClient.getInstance().getService().getTextUpload(opt, ucode, name, desc, wch);

//        File file = new File("");

//        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), saveBitmapToFile(null));
//        MultipartBody.Part archiveFile = MultipartBody.Part.createFormData("file", "", null);
//            Call<ModelImageSavedData> getchartItem = RetrofitClient.getInstance().getService().getImageTextUpload(archiveFile, mCode);
//            getchartItem.enqueue(new Callback<ModelImageSavedData>() {
//                @Override
//                public void onResponse(Call<ModelImageSavedData> call, Response<ModelImageSavedData> response) {
//                    if (response.code() == 200) {
//                        loadingCustom.dismiss();
//                        Log.e("완료","초기화완료");
////                        Intent ChangedInfo = new Intent(ActivityPortProfileModify.this, Fg_CookPage3.class);
////                        ChangedInfo.putExtra("PassPortIcon", response.body().getProduct().get(0).getIcon());
////                        ChangedInfo.putExtra("PassPortName", response.body().getProduct().get(0).getName());
////                        ChangedInfo.putExtra("PassPortDesc", response.body().getProduct().get(0).getDescript());
////                        ChangedInfo.putExtra("PassPhoto",response.body().getProduct().get(0).getPhoto());
////                        ChangedInfo.putExtra("PassPosition", ItemPositon);
////                        setResult(501, ChangedInfo);
//                        finish();
//                    }
//                }
//                @Override
//                public void onFailure(Call<ModelImageSavedData> call, Throwable t) {
//                    Log.e("레트로핏 실패", "값 : " + t.getMessage());
//                }
//            });
    }

    //이미지 리셋 취소
    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            imageResetDialog.dismiss();
        }
    };

    //이미지 리셋 확인
    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            opt = 2;
            ChangedInfoState = true;
            Glide.with(ActivityPortProfileModify.this)
                    .load(R.drawable.noname_img)
                    .crossFade()
                    .bitmapTransform(new CropCircleTransformation(ActivityPortProfileModify.this))
                    .into(profileModifyBinding.PortProfileModifyImage);
            imageResetDialog.dismiss();
        }
    };

    //프로필 편집 끝내기 취소
    private View.OnClickListener cancleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            portProfileModifyCancleDialog.dismiss();
        }
    };
    //프로필 편집 끝내기 확인
    private View.OnClickListener okListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            portProfileModifyCancleDialog.dismiss();
            finish();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void takePicture(){
        dispatchTakePictur();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void selectPhoto(){
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(false)
                .showSingleMediaType(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .imageEngine(new GlideEngine())
                .forResult(CODE_FINISH);
    }

    private void dispatchTakePictur(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }catch (IOException e){}
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID +".fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir  = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_FINISH && resultCode == RESULT_OK) {
            profileModifyBinding.PortProfileModifyImageResetBt.setVisibility(View.VISIBLE);
            opt = 0;
            ChangedInfoState = true;
            selectedUri = Matisse.obtainResult(data).get(0);
            Glide.with(this)
                    .load(selectedUri)
                    .placeholder(R.drawable.noname_img)
                    .error(R.drawable.noname_img)
                    .crossFade()
                    .bitmapTransform(new CropCircleTransformation(ActivityPortProfileModify.this))
                    .into(profileModifyBinding.PortProfileModifyImage);
        }
        if(requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            profileModifyBinding.PortProfileModifyImageResetBt.setVisibility(View.VISIBLE);
            opt = 0;
            ChangedInfoState = true;
            File file = new File(mCurrentPhotoPath);
            selectedUri = Uri.fromFile(file);
            Glide.with(this)
                    .load(selectedUri)
                    .placeholder(R.drawable.noname_img)
                    .error(R.drawable.noname_img)
                    .crossFade()
                    .bitmapTransform(new CropCircleTransformation(ActivityPortProfileModify.this))
                    .into(profileModifyBinding.PortProfileModifyImage);
        }
        //resultCode == 333 포트이름만 변경 / resultCode == 334 포트 설명만 변경
        if(requestCode == Modify_NameDesc && resultCode == 333) {
            ChangedInfoState = true;
            profileModifyBinding.PortProfileModifyNameText.setText(data.getStringExtra("modiName"));
            mName = data.getStringExtra("modiName");
        }
        if(requestCode == Modify_NameDesc && resultCode == 334) {
            ChangedInfoState = true;
            profileModifyBinding.PortProfileModifyDescText.setText(data.getStringExtra("modiDesc"));
            mDesc = data.getStringExtra("modiDesc");
        }
    }

    @Override
    public void onBackPressed() {
        if(SavedLoadingState) {
        }else {
            if (ChangedInfoState) {
                portProfileModifyCancleDialog = new DialogModifyCancle(ActivityPortProfileModify.this, cancleListener, okListener);
                portProfileModifyCancleDialog.show();
            } else {
                finish();
            }
        }
    }

    public File saveBitmapToFile(File file){
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image
            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();
            // The new size we want to scale to
            final int REQUIRED_SIZE=75;
            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            selectedBitmap = rotateBitmap(selectedBitmap, orientation);

            inputStream.close();
            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1,1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1,1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1,1);
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
    //취소 버튼
    public void TopCancleBT(View view){
        if(ChangedInfoState) {
            portProfileModifyCancleDialog = new DialogModifyCancle(ActivityPortProfileModify.this, cancleListener, okListener);
            portProfileModifyCancleDialog.show();
        }else{
            finish();
        }
    }

}
