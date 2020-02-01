package com.quantec.moneypot.activity.Main.Fragment.tab4;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.tab4.fragment.FgCenter;
import com.quantec.moneypot.activity.Main.Fragment.tab4.fragment.FgMyInfo;
import com.quantec.moneypot.activity.Main.Fragment.tab4.fragment.FgNotice;
import com.quantec.moneypot.activity.Main.Fragment.tab4.fragment.FgPush;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.dialog.DialogProfileImg;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;

import java.util.concurrent.TimeUnit;

public class FgTab4 extends Fragment{

    TextView topMyInfo, myInfoTab, centerTab, noticeTab, pushTab, currentTab;
    View myInfoB, centerB, noticeB, pushB, currentView;
    ImageView myInfoImage, centerImage, noticeImage, pushImage, currentImage, profileImg;
    FrameLayout containerLayout;

    FgMyInfo fgMyInfo;
    FgCenter fgCenter;
    FgNotice fgNotice;
    FgPush fgPush;
    Fragment currentFragment;
    FragmentTransaction transaction;

    private ActivityMain activityMain;

    DialogProfileImg dialogProfileImg;
    int img;

    public FgTab4() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab4, container, false);

        initializeViews();

        myInfoTab = view.findViewById(R.id.myInfoTab);
        centerTab = view.findViewById(R.id.centerTab);
        noticeTab = view.findViewById(R.id.noticeTab);
        pushTab = view.findViewById(R.id.pushTab);

        myInfoB = view.findViewById(R.id.myInfoB);
        centerB = view.findViewById(R.id.centerB);
        noticeB = view.findViewById(R.id.noticeB);
        pushB = view.findViewById(R.id.pushB);

        profileImg = view.findViewById(R.id.profileImg);

        myInfoImage = view.findViewById(R.id.myInfoImage);
        centerImage = view.findViewById(R.id.centerImage);
        noticeImage = view.findViewById(R.id.noticeImage);
        pushImage = view.findViewById(R.id.pushImage);

        containerLayout = view.findViewById(R.id.containerLayout);

        img = SharedPreferenceUtil.getInstance(activityMain).getIntExtra("profileImg");
        changedImg(img);

        transaction = activityMain.getSupportFragmentManager().beginTransaction();
        if(fgMyInfo == null) {
            fgMyInfo = new FgMyInfo();
            transaction.add(R.id.containerLayout, fgMyInfo).commit();
            currentFragment = fgMyInfo;
            currentView = myInfoB;
            currentTab = myInfoTab;
            currentImage = myInfoImage;
        }else {

            transaction.hide(currentFragment).show(fgMyInfo).commit();
            currentView.setVisibility(View.GONE);
            currentImage.setVisibility(View.GONE);
            currentTab.setTextColor(activityMain.getResources().getColor(R.color.c_9a9a9a));

            currentFragment = fgMyInfo;
            currentView = myInfoB;
            currentTab = myInfoTab;
            currentImage = myInfoImage;
        }

        return view;
    }

    private void initializeViews() {
        activityMain = (ActivityMain) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivityMain) {
            activityMain = (ActivityMain) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myInfoTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = activityMain.getSupportFragmentManager().beginTransaction();
                if(fgMyInfo == null) {
                    fgMyInfo = new FgMyInfo();
                    transaction.add(R.id.containerLayout, fgMyInfo).commit();
                    currentFragment = fgMyInfo;
                    currentView = myInfoB;
                    currentTab = myInfoTab;
                    currentImage = myInfoImage;

                    myInfoB.setVisibility(View.VISIBLE);
                    myInfoTab.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    myInfoImage.setVisibility(View.VISIBLE);
                }else {

                    transaction.hide(currentFragment).show(fgMyInfo).commit();
                    currentView.setVisibility(View.GONE);
                    currentImage.setVisibility(View.GONE);
                    currentTab.setTextColor(activityMain.getResources().getColor(R.color.c_9a9a9a));


                    currentFragment = fgMyInfo;
                    currentView = myInfoB;
                    currentTab = myInfoTab;
                    currentImage = myInfoImage;

                    myInfoB.setVisibility(View.VISIBLE);
                    myInfoTab.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    myInfoImage.setVisibility(View.VISIBLE);
                }
            }
        });

        centerTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = activityMain.getSupportFragmentManager().beginTransaction();
                if(fgCenter == null) {
                    fgCenter = new FgCenter();
                    transaction.hide(currentFragment).add(R.id.containerLayout, fgCenter).commit();
                    currentView.setVisibility(View.GONE);
                    currentImage.setVisibility(View.GONE);
                    currentTab.setTextColor(activityMain.getResources().getColor(R.color.c_9a9a9a));

                    currentFragment = fgCenter;
                    currentView = centerB;
                    currentTab = centerTab;
                    currentImage = centerImage;

                    centerB.setVisibility(View.VISIBLE);
                    centerTab.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    centerImage.setVisibility(View.VISIBLE);
                }else{
                    transaction.hide(currentFragment).show(fgCenter).commit();
                    currentView.setVisibility(View.GONE);
                    currentImage.setVisibility(View.GONE);
                    currentTab.setTextColor(activityMain.getResources().getColor(R.color.c_9a9a9a));

                    currentFragment = fgCenter;
                    currentView = centerB;
                    currentTab = centerTab;
                    currentImage = centerImage;

                    centerB.setVisibility(View.VISIBLE);
                    centerTab.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    centerImage.setVisibility(View.VISIBLE);
                }
            }
        });

        noticeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = activityMain.getSupportFragmentManager().beginTransaction();
                if(fgNotice == null) {
                    fgNotice = new FgNotice();
                    transaction.hide(currentFragment).add(R.id.containerLayout, fgNotice).commit();
                    currentView.setVisibility(View.GONE);
                    currentImage.setVisibility(View.GONE);
                    currentTab.setTextColor(activityMain.getResources().getColor(R.color.c_9a9a9a));

                    currentFragment = fgNotice;
                    currentView = noticeB;
                    currentTab = noticeTab;
                    currentImage = noticeImage;

                    noticeB.setVisibility(View.VISIBLE);
                    noticeTab.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    noticeImage.setVisibility(View.VISIBLE);
                }else{
                    transaction.hide(currentFragment).show(fgNotice).commit();
                    currentView.setVisibility(View.GONE);
                    currentImage.setVisibility(View.GONE);
                    currentTab.setTextColor(activityMain.getResources().getColor(R.color.c_9a9a9a));

                    currentFragment = fgNotice;
                    currentView = noticeB;
                    currentTab = noticeTab;
                    currentImage = noticeImage;

                    noticeB.setVisibility(View.VISIBLE);
                    noticeTab.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    noticeImage.setVisibility(View.VISIBLE);
                }
            }
        });

        pushTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = activityMain.getSupportFragmentManager().beginTransaction();
                if(fgPush == null) {
                    fgPush = new FgPush();
                    transaction.hide(currentFragment).add(R.id.containerLayout, fgPush).commit();
                    currentView.setVisibility(View.GONE);
                    currentImage.setVisibility(View.GONE);
                    currentTab.setTextColor(activityMain.getResources().getColor(R.color.c_9a9a9a));


                    currentFragment = fgPush;
                    currentView = pushB;
                    currentTab = pushTab;
                    currentImage = pushImage;

                    pushB.setVisibility(View.VISIBLE);
                    pushTab.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    pushImage.setVisibility(View.VISIBLE);

                }else{
                    transaction.hide(currentFragment).show(fgPush).commit();
                    currentView.setVisibility(View.GONE);
                    currentImage.setVisibility(View.GONE);
                    currentTab.setTextColor(activityMain.getResources().getColor(R.color.c_9a9a9a));

                    currentFragment = fgPush;
                    currentView = pushB;
                    currentTab = pushTab;
                    currentImage = pushImage;

                    pushB.setVisibility(View.VISIBLE);
                    pushTab.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    pushImage.setVisibility(View.VISIBLE);
                }
            }
        });

        RxView.clicks(profileImg).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {

            img = SharedPreferenceUtil.getInstance(activityMain).getIntExtra("profileImg");

            dialogProfileImg = new DialogProfileImg(activityMain, img, okListener, closeListener);
            dialogProfileImg.show();

            dialogProfileImg.setOnDialogResult(new DialogProfileImg.OnDialogResult() {
                @Override
                public void finish(int img) {
                    SharedPreferenceUtil.getInstance(activityMain).putProfileImg("profileImg", img);
                    changedImg(img);
                    dialogProfileImg.dismiss();
                }
            });
        });

    }//onViewCreate 끝

    private View.OnClickListener okListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogProfileImg.dismiss();
        }
    };

    private View.OnClickListener closeListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogProfileImg.dismiss();
        }
    };

    void changedImg(int img){
        if(img == 0 || img == 1){
            profileImg.setBackgroundResource(R.drawable.char_profile_edit_1);
        }else if(img == 2){
            profileImg.setBackgroundResource(R.drawable.char_profile_edit_2);
        }else if(img == 3){
            profileImg.setBackgroundResource(R.drawable.char_profile_edit_3);
        }else{
            profileImg.setBackgroundResource(R.drawable.char_profile_edit_4);
        }
    }

}
