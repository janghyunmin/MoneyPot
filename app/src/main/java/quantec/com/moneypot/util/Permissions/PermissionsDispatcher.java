package quantec.com.moneypot.util.Permissions;
import androidx.core.app.ActivityCompat;

import permissions.dispatcher.PermissionUtils;
import quantec.com.moneypot.Activity.MyInfo.ActivityMyInfo;

public class PermissionsDispatcher {

    private static final int TAKEPICTURE = 1;
    private static final String[] PERMISSION_TAKEPICTURE = new String[]{"android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};

    private static final int SELECTPHOTO = 2;
    private static final String[] PERMISSION_SELECTPHOTO = new String[]{"android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};

    private PermissionsDispatcher(){
    }

    public static void takePictureWithCheck(ActivityMyInfo target){
        if(PermissionUtils.hasSelfPermissions(target, PERMISSION_TAKEPICTURE)){
            target.takePicture();
        }else{
            ActivityCompat.requestPermissions(target, PERMISSION_TAKEPICTURE, TAKEPICTURE);
        }
    }

    public static void selectPhotoWithCheck(ActivityMyInfo target){
        if(PermissionUtils.hasSelfPermissions(target, PERMISSION_SELECTPHOTO)){
            target.selectPhoto();
        }else{
            ActivityCompat.requestPermissions(target, PERMISSION_SELECTPHOTO, SELECTPHOTO);
        }
    }

    public static void onRequestPermissionsResult(ActivityMyInfo target, int requestCode, int[] grantResults){

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
                if(PermissionUtils.verifyPermissions(grantResults)) {
                    target.selectPhoto();
                }
                break;
            default:
                break;
        }
    }


//    public static void takePictureWithCheck(ActivityPortProfileModify target){
//        if(PermissionUtils.hasSelfPermissions(target, PERMISSION_TAKEPICTURE)){
//            target.takePicture();
//        }else{
//            ActivityCompat.requestPermissions(target, PERMISSION_TAKEPICTURE, TAKEPICTURE);
//        }
//    }
//
//    public static void selectPhotoWithCheck(ActivityPortProfileModify target){
//        if(PermissionUtils.hasSelfPermissions(target, PERMISSION_SELECTPHOTO)){
//            target.selectPhoto();
//        }else{
//            ActivityCompat.requestPermissions(target, PERMISSION_SELECTPHOTO, SELECTPHOTO);
//        }
//    }
//
//    public static void onRequestPermissionsResult(ActivityPortProfileModify target, int requestCode, int[] grantResults){
//
//        switch (requestCode) {
//            case TAKEPICTURE:
//                if(PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_TAKEPICTURE)) {
//                    return;
//                }
//                if(PermissionUtils.verifyPermissions(grantResults)) {
//                    target.takePicture();
//                }
//                break;
//            case SELECTPHOTO:
//                if(PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_SELECTPHOTO)) {
//                    return;
//                }
//                if(PermissionUtils.verifyPermissions(grantResults)) {
//                    target.selectPhoto();
//                }
//                break;
//            default:
//                break;
//        }
//    }
}
