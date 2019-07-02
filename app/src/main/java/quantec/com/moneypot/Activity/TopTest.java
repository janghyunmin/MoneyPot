package quantec.com.moneypot.Activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import quantec.com.moneypot.Activity.MyInfo.ActivityMyInfo;

public class TopTest extends BroadcastReceiver {

    //앱이 구동중인지 확인
    boolean check = false;

    boolean runningActivity = false;

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.e("체크값","값 : "+check);
        Log.e("푸쉬 인텐트","이동 페이지 : "+ intent.getStringExtra("pushType"));

        //             앱이 실행 중이면 앱을 FORGROUND로 이동시킨다.
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(100);
        if (!tasks.isEmpty()) {
            int taskSize = tasks.size();
            for (int i = 0; i < taskSize; i++) {
                ActivityManager.RunningTaskInfo taskInfo = tasks.get(i);

                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {

                    runningActivity = true;
//                    am.moveTaskToFront(taskInfo.id, 0);

                    Intent intent1 = new Intent(context, ActivityMyInfo.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);

                    break;
                }
            }

            if(!runningActivity){

                intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                intent.setAction(Intent.ACTION_MAIN);
                context.startActivity(intent);
            }
        }

//        if (!check) {
//
//            check = true;
//            // 앱이 실행 중이 아니면 앱을 기동시킨다.
//            intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//            intent.setAction(Intent.ACTION_MAIN);
//            context.startActivity(intent);
//
//        } else {
//
////             앱이 실행 중이면 앱을 FORGROUND로 이동시킨다.
//            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(100);
//            if (!tasks.isEmpty()) {
//                int taskSize = tasks.size();
//                for (int i = 0; i < taskSize; i++) {
//                    ActivityManager.RunningTaskInfo taskInfo = tasks.get(i);
//                    if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
//
//                        am.moveTaskToFront(taskInfo.id, 0);
//                    }
//                }
//            }else{
//                intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//                intent.setAction(Intent.ACTION_MAIN);
//                context.startActivity(intent);
//            }
//        }
    }

}
