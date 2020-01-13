package com.quantec.moneypot.activity.Main.Fragment.tab4.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.center.ActivityAppVersion;
import com.quantec.moneypot.activity.center.ActivityInfo;
import com.quantec.moneypot.activity.center.ActivityInquiry;
import com.quantec.moneypot.activity.center.ActivityQuestion;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;

import java.util.concurrent.TimeUnit;

public class FgCenter extends Fragment {

    private ActivityMain activityMain;

    LinearLayout questionBt, inquiryBt, infoBt, versionBt, phoneBt;

    public FgCenter() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab4_center, container, false);
        initializeViews();

        questionBt = view.findViewById(R.id.questionBt);
        inquiryBt = view.findViewById(R.id.inquiryBt);
        infoBt = view.findViewById(R.id.infoBt);
        versionBt = view.findViewById(R.id.versionBt);
        phoneBt = view.findViewById(R.id.phoneBt);

        return view;
    }

    private void initializeViews(){
        activityMain = (ActivityMain) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityMain) {
            activityMain = (ActivityMain) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RxView.clicks(questionBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Intent intent = new Intent(activityMain, ActivityQuestion.class);
            startActivity(intent);
        });

        RxView.clicks(inquiryBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Intent intent = new Intent(activityMain, ActivityInquiry.class);
            startActivity(intent);
        });

        RxView.clicks(infoBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Intent intent = new Intent(activityMain, ActivityInfo.class);
            startActivity(intent);
        });

        RxView.clicks(versionBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Intent intent = new Intent(activityMain, ActivityAppVersion.class);
            startActivity(intent);
        });

        RxView.clicks(phoneBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:15880000"));
            startActivity(tt);
        });

    }
}
