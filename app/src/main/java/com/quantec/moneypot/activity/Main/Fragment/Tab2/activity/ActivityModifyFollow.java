package com.quantec.moneypot.activity.Main.Fragment.Tab2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.ModelCustomDel;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.ModelModifyCustom;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.ModelModifyFollow;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter.AdapterModifyFollow;
import com.quantec.moneypot.datamodel.nmodel.ModelAssetsCustom;
import com.quantec.moneypot.datamodel.nmodel.ModelNCustomDel;
import com.quantec.moneypot.dialog.DialogCustomDel;
import com.quantec.moneypot.dialog.DialogModiTitle;
import com.quantec.moneypot.network.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityModifyFollow extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelModifyFollow> modelModifyFollows;
    AdapterModifyFollow adapterModifyFollow;

    DialogModiTitle dialogModiTitle;
    DialogCustomDel dialogCustomDel;

    String selecCode;
    int selectPosition;
    ArrayList<String> delCode, allDelCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_follow);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        delCode = new ArrayList<>();
        allDelCode = new ArrayList<>();

        dialogCustomDel = new DialogCustomDel(this, delOkClick, delCancleClick);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelModifyFollows = new ArrayList<>();
        adapterModifyFollow = new AdapterModifyFollow(modelModifyFollows, this);
        recyclerView.setAdapter(adapterModifyFollow);

        dialogModiTitle = new DialogModiTitle(ActivityModifyFollow.this, okClick, cancleClick);

        Call<ModelAssetsCustom> getReList = RetrofitClient.getInstance().getService().getAssetsCustom("application/json", "all");
        getReList.enqueue(new Callback<ModelAssetsCustom>() {
            @Override
            public void onResponse(Call<ModelAssetsCustom> call, Response<ModelAssetsCustom> response) {
                if (response.code() == 200) {
                    if(response.body().getStatus() == 200){

                        if(response.body().getTotalElements() == 0){

                            modelModifyFollows.add(new ModelModifyFollow(true, response.body().getTotalElements(), "", ""));

                        }else{
                            modelModifyFollows.add(new ModelModifyFollow(false, response.body().getTotalElements(), "", ""));
                            for(int index = 0; index < response.body().getContent().size(); index++){
                                modelModifyFollows.add(new ModelModifyFollow(false,
                                        response.body().getTotalElements(),
                                        response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode()));

                                allDelCode.add(response.body().getContent().get(index).getCode());
                            }
                        }
                        adapterModifyFollow.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAssetsCustom> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });

        adapterModifyFollow.setModifyFollowAllDelClick(new AdapterModifyFollow.ModifyFollowAllDelClick() {
            @Override
            public void onClick(int position) {

            }
        });

        adapterModifyFollow.setModifyFollowDelClick(new AdapterModifyFollow.ModifyFollowDelClick() {
            @Override
            public void onClick(int position) {
                selectPosition = position;
                selecCode = modelModifyFollows.get(position).getCode();
                dialogCustomDel.show();
            }
        });

        adapterModifyFollow.setModifyFollowModiClick(new AdapterModifyFollow.ModifyFollowModiClick() {
            @Override
            public void onClick(int position) {
                selectPosition = position;
                selecCode = modelModifyFollows.get(position).getCode();
                dialogModiTitle.show();
            }
        });

        dialogModiTitle.setDialogListener(new DialogModiTitle.CustomDialogListener() {
            @Override
            public void onPositiveClicked(String name) {

                ModelModifyCustom modelModifyCustom = new ModelModifyCustom();
                modelModifyCustom.setCode(selecCode);
                modelModifyCustom.setDescript("");
                modelModifyCustom.setName(name);

                Call<Object> getReList = RetrofitClient.getInstance().getService().upAssetsCustom("application/json", modelModifyCustom);
                getReList.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() == 200) {
                            modelModifyFollows.get(selectPosition).setTitle(name);
                            adapterModifyFollow.notifyDataSetChanged();
                            dialogModiTitle.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e("실패","실패"+t.getMessage());
                        adapterModifyFollow.notifyDataSetChanged();
                        dialogModiTitle.dismiss();
                    }
                });
            }
            @Override
            public void onNegativeClicked() {
                dialogModiTitle.dismiss();
            }
        });
    }

    private View.OnClickListener okClick = new View.OnClickListener() {
        public void onClick(View v) {

//            ModelNCustomDel modelCustomDel = new ModelNCustomDel();
//            delCode.clear();
//            delCode.add(selecCode);
//            modelCustomDel.setCodes(delCode);
//
//            Call<Object> getReList = RetrofitClient.getInstance().getService().delAssetsCustom("application/json", modelCustomDel);
//            getReList.enqueue(new Callback<Object>() {
//                @Override
//                public void onResponse(Call<Object> call, Response<Object> response) {
//                    if (response.code() == 200) {
//
//                    }
//                }
//                @Override
//                public void onFailure(Call<Object> call, Throwable t) {
//                    Log.e("실패","실패"+t.getMessage());
//                }
//            });

//            dialogModiTitle.dismiss();
        }
    };

    private View.OnClickListener cancleClick = new View.OnClickListener() {
        public void onClick(View v) {
            dialogModiTitle.dismiss();
        }
    };



    private View.OnClickListener delOkClick = new View.OnClickListener() {
        public void onClick(View v) {

            ModelCustomDel modelCustomDel = new ModelCustomDel();
            delCode.clear();
            delCode.add(selecCode);
            modelCustomDel.setCodes(delCode);

            Call<ModelNCustomDel> getReList = RetrofitClient.getInstance().getService().delAssetsCustom("application/json", modelCustomDel);
            getReList.enqueue(new Callback<ModelNCustomDel>() {
                @Override
                public void onResponse(Call<ModelNCustomDel> call, Response<ModelNCustomDel> response) {
                    if (response.code() == 200) {

                        if(response.body().getStatus() == 200){
                            modelModifyFollows.remove(selectPosition);
                            adapterModifyFollow.notifyDataSetChanged();
                            dialogCustomDel.dismiss();
                        }

                    }
                }
                @Override
                public void onFailure(Call<ModelNCustomDel> call, Throwable t) {
                    Log.e("실패","실패"+t.getMessage());
                    dialogCustomDel.dismiss();
                }
            });

        }
    };

    private View.OnClickListener delCancleClick = new View.OnClickListener() {
        public void onClick(View v) {
            dialogCustomDel.dismiss();
        }
    };
}
