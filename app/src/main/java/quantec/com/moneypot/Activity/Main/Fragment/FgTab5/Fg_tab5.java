package quantec.com.moneypot.Activity.Main.Fragment.FgTab5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.ClientCenter.ActivityClientCenter;
import quantec.com.moneypot.Activity.MyInfomation.ActivityMyInfomation;
import quantec.com.moneypot.Activity.ActivityNotice.ActivityNotice;
import quantec.com.moneypot.Activity.ActivitySetting.ActivitySetting;
import quantec.com.moneypot.R;

public class Fg_tab5 extends Fragment implements View.OnClickListener {

    TextView topMyInfo;
    ImageView center, notice, setting;
    private MainActivity mainActivity;

    public Fg_tab5() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab5, container, false);

        initializeViews();

        topMyInfo = view.findViewById(R.id.topMyInfo);
        center = view.findViewById(R.id.center);
        notice = view.findViewById(R.id.notice);
        setting = view.findViewById(R.id.setting);


        topMyInfo.setOnClickListener(this);
        center.setOnClickListener(this);
        notice.setOnClickListener(this);
        setting.setOnClickListener(this);


        return view;
    }

    private void initializeViews(){
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.topMyInfo:
                Intent intent = new Intent(getActivity(), ActivityMyInfomation.class);
                startActivity(intent);
                break;
            case R.id.center:
                Intent intent1 = new Intent(getActivity(), ActivityClientCenter.class);
                startActivity(intent1);
                break;
            case R.id.notice:
                Intent intent2 = new Intent(getActivity(), ActivityNotice.class);
                startActivity(intent2);
                break;
            case R.id.setting:
                Intent intent3 = new Intent(getActivity(), ActivitySetting.class);
                startActivity(intent3);
                break;

        }

    }
}
