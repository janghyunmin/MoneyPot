package com.quantec.moneypot.activity.propensity;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BasePropensityActivity extends AppCompatActivity {

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
        actList.remove(1);
    }
}