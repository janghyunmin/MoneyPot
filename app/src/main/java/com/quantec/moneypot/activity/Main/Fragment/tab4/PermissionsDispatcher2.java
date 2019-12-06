package com.quantec.moneypot.activity.Main.Fragment.tab4;

import androidx.core.app.ActivityCompat;

import com.quantec.moneypot.activity.MyInfo.ActivityMyInfo;
import com.quantec.moneypot.activity.cameratest.ActivityCameraTest;

import permissions.dispatcher.PermissionUtils;

public class PermissionsDispatcher2 {
    private static final int TAKEPICTURE = 1;
    private static final String[] PERMISSION_TAKEPICTURE = new String[]{"android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};

    private static final int SELECTPHOTO = 2;
    private static final String[] PERMISSION_SELECTPHOTO = new String[]{"android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};

    private PermissionsDispatcher2(){
    }

    public static void takePictureWithCheck(ActivityCameraTest target){
        if(PermissionUtils.hasSelfPermissions(target, PERMISSION_TAKEPICTURE)){
            target.takePicture();
        }else{
            ActivityCompat.requestPermissions(target, PERMISSION_TAKEPICTURE, TAKEPICTURE);
        }
    }


    public static void onRequestPermissionsResult(ActivityCameraTest target, int requestCode, int[] grantResults){

        switch (requestCode) {
            case TAKEPICTURE:
                if(PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_TAKEPICTURE)) {
                    return;
                }
                if(PermissionUtils.verifyPermissions(grantResults)) {
                    target.takePicture();
                }
                break;
            case SELECTPHOTO:
                if(PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_SELECTPHOTO)) {
                    return;
                }
                break;
            default:
                break;
        }
    }
}
