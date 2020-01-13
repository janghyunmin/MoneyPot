package com.quantec.moneypot.activity.Main.Fragment.tab4;

import android.content.Context;
import android.os.Bundle;
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

public class FgTab4 extends Fragment implements View.OnClickListener {

    TextView topMyInfo, myInfoTab, centerTab, noticeTab, pushTab, currentTab;
    View myInfoB, centerB, noticeB, pushB, currentView;
    ImageView myInfoImage, centerImage, noticeImage, pushImage, currentImage;
    FrameLayout containerLayout;

    FgMyInfo fgMyInfo;
    FgCenter fgCenter;
    FgNotice fgNotice;
    FgPush fgPush;
    Fragment currentFragment;
    FragmentTransaction transaction;

    private ActivityMain activityMain;

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

        myInfoImage = view.findViewById(R.id.myInfoImage);
        centerImage = view.findViewById(R.id.centerImage);
        noticeImage = view.findViewById(R.id.noticeImage);
        pushImage = view.findViewById(R.id.pushImage);

        containerLayout = view.findViewById(R.id.containerLayout);



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
    }

    @Override
    public void onClick(View v) {

//        if(position == 0) {
//
//            if(fgMyInfo == null) {
//                fgMyInfo = new FgMyInfo();
//                transaction.add(R.id.ContainerContain, fgMyInfo).commit();
//                currentFragment = fgMyInfo;
//            }else {
//
//                transaction.hide(currentFragment).show(fgMyInfo).commit();
//                currentFragment = fgMyInfo;
//            }
//        }else if(position == 1){
//
//            if(fgCenter == null) {
//                fgCenter = new FgCenter();
//                transaction.hide(currentFragment).add(R.id.ContainerContain, fgCenter).commit();
//                currentFragment = fgCenter;
//            }else{
//                transaction.hide(currentFragment).show(fgCenter).commit();
//                currentFragment = fgCenter;
//            }
//
//        }
//
//        else if(position == 2){
//
//            if(fgNotice == null) {
//                fgNotice = new FgNotice();
//                transaction.hide(currentFragment).add(R.id.ContainerContain, fgNotice).commit();
//                currentFragment = fgNotice;
//            }else{
//                transaction.hide(currentFragment).show(fgNotice).commit();
//                currentFragment = fgNotice;
//            }
//        }
//
//        else if(position == 3){
//
//            if(fgPush == null) {
//                fgPush = new FgPush();
//                transaction.hide(currentFragment).add(R.id.ContainerContain, fgPush).commit();
//                currentFragment = fgPush;
//            }else{
//                transaction.hide(currentFragment).show(fgPush).commit();
//                currentFragment = fgPush;
//            }
//        }


//        switch (v.getId()) {
//            case R.id.topMyInfo:
//                Intent intent = new Intent(getActivity(), ActivityMyInfo.class);
//                startActivity(intent);
//                break;
//            case R.id.center:
//                Intent intent1 = new Intent(getActivity(), ActivityClientCenter.class);
//                startActivity(intent1);
//                break;
//            case R.id.notice:
//                Intent intent2 = new Intent(getActivity(), ActivityNotice.class);
//                startActivity(intent2);
//                break;
//            case R.id.setting:
//                Intent intent3 = new Intent(getActivity(), ActivitySetting.class);
//                startActivity(intent3);
//                break;
//            case R.id.history:
//                Intent intent4 = new Intent(getActivity(), ActivityHistory.class);
//                startActivity(intent4);
//                break;
//        }
    }
}
