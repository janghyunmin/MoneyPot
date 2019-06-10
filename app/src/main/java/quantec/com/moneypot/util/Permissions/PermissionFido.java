package quantec.com.moneypot.util.Permissions;

//import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityRegInfo;

import androidx.core.app.ActivityCompat;

import permissions.dispatcher.PermissionUtils;
import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityRegInfo;

public class PermissionFido {
    private static final int FIDOSTATE = 1;
    private static final String[] PERMISSION_FIDOSTATE = new String[]{"android.permission.READ_PHONE_STATE"};

    private PermissionFido(){
    }

    public static void getFidoState(ActivityRegInfo target){
        if(PermissionUtils.hasSelfPermissions(target, PERMISSION_FIDOSTATE)){
            target.getFido();
        }else{
            ActivityCompat.requestPermissions(target, PERMISSION_FIDOSTATE, FIDOSTATE);
        }
    }

    public static void onRequestPermissionsResult(ActivityRegInfo target, int requestCode, int[] grantResults){

        switch (requestCode) {
            case FIDOSTATE:

                if(PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_FIDOSTATE)){
                    return;
                }
                if(PermissionUtils.verifyPermissions(grantResults)){
                    target.getFido();
                }else if(!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_FIDOSTATE)){
                    target.getReFido();
                }
                break;
            default:
                break;
        }
    }
}