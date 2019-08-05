package quantec.com.moneypot.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import quantec.com.moneypot.Activity.Main.Foreground;
import quantec.com.moneypot.Activity.MyInfo.ActivityMyInfo;

public class TopTest extends BroadcastReceiver{

    boolean runningActivity = false;

    boolean fidoState = false;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("백그라운드","상태  : "+isAppIsInBackground(context));
//        com.dreamsecurity.lib_passcode.ui.Passcode_UI_Square
//        com.dream.magic.fido.authenticator.finger.local.UserLocal_Normal_Activity
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = activityManager.getRunningTasks(10);
        int taskSize2 = runningTaskInfo.size();
        for (int i = 0; i < taskSize2; i++) {
            Log.e("1111포그라운드","1111이름  : "+runningTaskInfo.get(i).topActivity.getClassName() +" 2222이름 : "+runningTaskInfo.get(i).baseActivity.getClassName());
        }
        Log.e("포그라운드","이름  : "+runningTaskInfo.get(0).topActivity.getClassName());

        //앱이 실행 중이면 앱을 FORGROUND로 이동시킨다.
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(100);
        if (!tasks.isEmpty()) {
            int taskSize = tasks.size();
            for (int i = 0; i < taskSize; i++) {
                ActivityManager.RunningTaskInfo taskInfo = tasks.get(i);

                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                    runningActivity = true;

                    for(int index = 0; index < taskSize; index++){
                        ActivityManager.RunningTaskInfo taskInfo2 = tasks.get(index);

                        if(taskInfo2.topActivity.getShortClassName().equals("com.dream.magic.fido.authenticator.asm.api.ASMProcessorActivity")){

                            fidoState = true;
                            am.moveTaskToFront(taskInfo2.id, 0);
                            break;
                        }
                    }
                }
            }

            if(!runningActivity){
                intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                intent.setAction(Intent.ACTION_MAIN);
                context.startActivity(intent);
            }else{
                if(!fidoState){

                    String url ="activity://"+intent.getStringExtra("pushType");
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            }
        }
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        //앱이 실행 중이면 앱을 FORGROUND로 이동시킨다.
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(100);
//        if (!tasks.isEmpty()) {
//            int taskSize = tasks.size();
//            for (int i = 0; i < taskSize; i++) {
//                ActivityManager.RunningTaskInfo taskInfo = tasks.get(i);
//
////                Log.e("탑 패키지네임","이름 : "+taskInfo.topActivity.getShortClassName());
////                Log.e("패키지네임","이름 : "+context.ge);
//
////                com.dream.magic.fido.authenticator.asm.api.ASMProcessorActivity
//
//                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
//
//                    runningActivity = true;
//
//                    for(int index = 0; index < taskSize; index++){
//                        ActivityManager.RunningTaskInfo taskInfo2 = tasks.get(i);
//
//                        if(taskInfo.topActivity.getShortClassName().equals("com.dream.magic.fido.authenticator.asm.api.ASMProcessorActivity")){
//
//                            fidoState = true;
//
//                            am.moveTaskToFront(taskInfo.id, 0);
//                            Intent intent1 = new Intent(context, ActivityMyInfo.class);
//                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intent1);
//
//                            break;
//                        }
//                    }
//                }
//
////                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
////
////                    runningActivity = true;
//////                    am.moveTaskToFront(taskInfo.id, 0);
//////                    Intent intent1 = new Intent(context, ActivityMyInfo.class);
//////                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//////                    context.startActivity(intent1);
////
////                    String url ="activity://"+intent.getStringExtra("pushType");
////                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    context.startActivity(intent1);
////
////                    break;
////                }
//            }
//
//            if(!fidoState){
//
//                String url ="activity://"+intent.getStringExtra("pushType");
//                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }
//
//            if(!runningActivity){
//                intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//                intent.setAction(Intent.ACTION_MAIN);
//                context.startActivity(intent);
//            }
//        }
//    }



}
