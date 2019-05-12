package quantec.com.moneypot.Activity.BaseActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    public static ArrayList<Activity> actList = new ArrayList<Activity>();

    public static ArrayList<Activity> mainList = new ArrayList<>();

    public void actFinish(){
        for(int i = 0; i < actList.size(); i++)
            actList.get(i).finish();
    }

    public void activityFinish(){
        for(int i = 0; i < mainList.size(); i++)
            mainList.get(i).finish();
    }

    public void activityRemove(){
        mainList.remove(0);
    }
}
