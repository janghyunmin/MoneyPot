package quantec.com.moneypot.Activity.Center.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Center.ActivityInquiry;
import quantec.com.moneypot.Activity.Center.Adapter.AdapterMyInquiry;
import quantec.com.moneypot.Dialog.DialogInquiryCategory;
import quantec.com.moneypot.ModelCommon.dModel.ModelMyInquiryList;
import quantec.com.moneypot.R;

public class FgMyInquiry extends Fragment{

    ActivityInquiry activityInquiry;

    RecyclerView recyclerView;
    ArrayList<ModelMyInquiryList> modelMyInquiryLists;
    RecyclerView.LayoutManager layoutManager;
    AdapterMyInquiry adapterMyInquiry;


    public FgMyInquiry() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_myinquiry, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        initializeViews();

        return view;
    }

    private void initializeViews(){
        activityInquiry = (ActivityInquiry) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityInquiry) {
            activityInquiry = (ActivityInquiry) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityInquiry);
        recyclerView.setLayoutManager(layoutManager);
        modelMyInquiryLists = new ArrayList<>();


        modelMyInquiryLists.add(new ModelMyInquiryList("기타 문의","문의 내용은 제한을 두면안됩니다 .일단은 많이 적게 하고 난 후에 제한을 두고 제한을 두면 앱을 이용하면서 이게 뭐지 싶었는데 이건아니지 않나요?\n" +
                "사진을 첨부하고 싶은데 어디로 보내야하나요? 회사 이메일로 보내면되나요 어ㅈ떻게해야할까요","문의하신 내용만으로는 정확한 확인이 어려워 답변을 드리지 못하는 점 양해 부탁드립니다. 번거로우시겠지만 정확한 확인을 위해 아래 내용을 포함하여 재문의 부탁드립니다. 투자하신 상품의 이름과 금융상품을 위해 개설한 통장의 명의자 본인 성함 및 휴대폰 번호를 확인 후 기재"));


        modelMyInquiryLists.add(new ModelMyInquiryList("기타 문의","문의 내용은 제한을 두면안됩니다 .일단은 많이 적게 하고 난 후에 제한을 두고 제한을 두면 앱을 이용하면서 이게 뭐지 싶었는데 이건아니지 않나요?\n" +
                "사진을 첨부하고 싶은데 어디로 보내야하나요? 회사 이메일로 보내면되나요 어ㅈ떻게해야할까요","문의하신 내용만으로는 정확한 확인이 어려워 답변을 드리지 못하는 점 양해 부탁드립니다. 번거로우시겠지만 정확한 확인을 위해 아래 내용을 포함하여 재문의 부탁드립니다. 투자하신 상품의 이름과 금융상품을 위해 개설한 통장의 명의자 본인 성함 및 휴대폰 번호를 확인 후 기재"));



        modelMyInquiryLists.add(new ModelMyInquiryList("기타 문의","문의 내용은 제한을 두면안됩니다 .일단은 많이 적게 하고 난 후에 제한을 두고 제한을 두면 앱을 이용하면서 이게 뭐지 싶었는데 이건아니지 않나요?\n" +
                "사진을 첨부하고 싶은데 어디로 보내야하나요? 회사 이메일로 보내면되나요 어ㅈ떻게해야할까요","문의하신 내용만으로는 정확한 확인이 어려워 답변을 드리지 못하는 점 양해 부탁드립니다. 번거로우시겠지만 정확한 확인을 위해 아래 내용을 포함하여 재문의 부탁드립니다. 투자하신 상품의 이름과 금융상품을 위해 개설한 통장의 명의자 본인 성함 및 휴대폰 번호를 확인 후 기재"));



        modelMyInquiryLists.add(new ModelMyInquiryList("기타 문의","문의 내용은 제한을 두면안됩니다 .일단은 많이 적게 하고 난 후에 제한을 두고 제한을 두면 앱을 이용하면서 이게 뭐지 싶었는데 이건아니지 않나요?\n" +
                "사진을 첨부하고 싶은데 어디로 보내야하나요? 회사 이메일로 보내면되나요 어ㅈ떻게해야할까요","문의하신 내용만으로는 정확한 확인이 어려워 답변을 드리지 못하는 점 양해 부탁드립니다. 번거로우시겠지만 정확한 확인을 위해 아래 내용을 포함하여 재문의 부탁드립니다. 투자하신 상품의 이름과 금융상품을 위해 개설한 통장의 명의자 본인 성함 및 휴대폰 번호를 확인 후 기재"));



        adapterMyInquiry = new AdapterMyInquiry(modelMyInquiryLists, activityInquiry);
        recyclerView.setAdapter(adapterMyInquiry);

    }

}
