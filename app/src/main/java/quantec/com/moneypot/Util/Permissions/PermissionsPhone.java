package quantec.com.moneypot.Util.Permissions;

import android.support.v4.app.ActivityCompat;
import android.util.Log;

import permissions.dispatcher.PermissionUtils;
import quantec.com.moneypot.Activity.Login.ActivityLogin;

public class PermissionsPhone {
    private static final int PHONESTATE = 1;
    private static final String[] PERMISSION_PHONESTATE = new String[]{"android.permission.READ_PHONE_STATE"};

    private PermissionsPhone(){
    }

    public static void getPhoneState(ActivityLogin target){
        if(PermissionUtils.hasSelfPermissions(target, PERMISSION_PHONESTATE)){
            target.getPhoneNumber();
        }else{
            ActivityCompat.requestPermissions(target, PERMISSION_PHONESTATE, PHONESTATE);
        }
    }

    public static void onRequestPermissionsResult(ActivityLogin target, int requestCode, int[] grantResults){

        switch (requestCode) {
            case PHONESTATE:
                if(PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_PHONESTATE)){
                    return;
                }
                if(PermissionUtils.verifyPermissions(grantResults)){
                    target.getPhoneNumber();
                }
                break;
            default:
                break;
        }
    }
}
