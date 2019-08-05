package quantec.com.moneypot.Activity.Main.Fragment.Tab5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import quantec.com.moneypot.Activity.Center.ActivityClientCenter;
import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Activity.MyInfo.ActivityMyInfo;
import quantec.com.moneypot.Activity.Notice.ActivityNotice;
import quantec.com.moneypot.Activity.Setting.ActivitySetting;
import quantec.com.moneypot.R;

public class FgTab5 extends Fragment implements View.OnClickListener {

    TextView topMyInfo;
    ImageView center, notice, setting;

    public FgTab5() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab5, container, false);

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.topMyInfo:
//                Intent intent = new Intent(getActivity(), ActivityMyInfo.class);
//                startActivity(intent);
                break;
            case R.id.center:
//                Intent intent1 = new Intent(getActivity(), ActivityClientCenter.class);
//                startActivity(intent1);
                break;
            case R.id.notice:
//                Intent intent2 = new Intent(getActivity(), ActivityNotice.class);
//                startActivity(intent2);
                break;
            case R.id.setting:
//                Intent intent3 = new Intent(getActivity(), ActivitySetting.class);
//                startActivity(intent3);
                break;

        }
    }
}
