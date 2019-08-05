package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity.ActivityModifyPot;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.AdapterMyPotPage;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.ModelMyPotPage;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Fragment.FragmentLifcycle;
import quantec.com.moneypot.DataModel.dModel.Filter;
import quantec.com.moneypot.DataModel.nModel.ModelPotList;
import quantec.com.moneypot.Dialog.DialogMyPotFilter;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.util.DateTransForm;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static quantec.com.moneypot.Activity.Main.Fragment.Tab2.FgTab2.refreshPage;

public class FgMyPotPage extends Fragment implements FragmentLifcycle {

    private ActivityMain activityMain;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterMyPotPage adapterMyPotPage;
    ArrayList<ModelMyPotPage> modelMyPotPages;

    TextView modifyPot, number, filterBt;

    private DialogMyPotFilter dialogMyPotFilter;

    public FgMyPotPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_makepot_fgmypotpage, container, false);
        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(activityMain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        modelMyPotPages = new ArrayList<>();
        adapterMyPotPage = new AdapterMyPotPage(modelMyPotPages, activityMain);

        recyclerView.setAdapter(adapterMyPotPage);

        modifyPot = view.findViewById(R.id.modifyPot);

        number = view.findViewById(R.id.number);
        filterBt = view.findViewById(R.id.filter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Filter filter = new Filter();
        Call<ModelPotList> getReList = RetrofitClient.getInstance().getService().getPotList("application/json", filter, "U", 0,0,10);
        getReList.enqueue(new Callback<ModelPotList>() {
            @Override
            public void onResponse(Call<ModelPotList> call, Response<ModelPotList> response) {
                if (response.code() == 200) {
                    if (response.body().getTotalElements() == 0) {

                        filterBt.setEnabled(false);
                        modifyPot.setEnabled(false);
                        modelMyPotPages.add(new ModelMyPotPage(true, "",
                                "", 0, ""));
                    } else {

                        filterBt.setEnabled(true);
                        modifyPot.setEnabled(true);
                        for (int index = 0; index < response.body().getContent().size(); index++) {

                            modelMyPotPages.add(new ModelMyPotPage(false, response.body().getContent().get(index).getName(),
                                    response.body().getContent().get(index).getCode(),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRate()*100), 2, 2),
                                    DateTransForm.dateTransForm(response.body().getContent().get(index).getDate())));
                        }

                        Collections.sort(modelMyPotPages, new Comparator<ModelMyPotPage>() {
                            @Override
                            public int compare(ModelMyPotPage o1, ModelMyPotPage o2) {
                                return o2.getDate().compareTo(o1.getDate());
                            }
                        });

                    }
                    number.setText(String.valueOf(response.body().getTotalElements()));
                    adapterMyPotPage.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelPotList> call, Throwable t) {
                Log.e("받은값 에러","값 : "+t.getMessage());
            }
        });


        modifyPot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(activityMain, ActivityModifyPot.class);
                startActivityForResult(intent1, 100);
            }
        });

        filterBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialogMyPotFilter = new DialogMyPotFilter(activityMain, mostBtListen, pastBtListen, profitBtListen, dateBtListen);
                dialogMyPotFilter.show();
            }
        });

    }//onViewCreate 끝


    private View.OnClickListener mostBtListen = new View.OnClickListener() {
        public void onClick(View v) {

            Collections.sort(modelMyPotPages, new Comparator<ModelMyPotPage>() {
                @Override
                public int compare(ModelMyPotPage o1, ModelMyPotPage o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
            adapterMyPotPage.notifyDataSetChanged();
            filterBt.setText("최신순");
            dialogMyPotFilter.dismiss();
        }
    };
    private View.OnClickListener pastBtListen = new View.OnClickListener() {
        public void onClick(View v) {

            Collections.sort(modelMyPotPages, new Comparator<ModelMyPotPage>() {
                @Override
                public int compare(ModelMyPotPage o1, ModelMyPotPage o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            adapterMyPotPage.notifyDataSetChanged();
            filterBt.setText("과거순");
            dialogMyPotFilter.dismiss();
        }
    };
    private View.OnClickListener profitBtListen = new View.OnClickListener() {
        public void onClick(View v) {

            Collections.sort(modelMyPotPages, new Comparator<ModelMyPotPage>() {
                @Override
                public int compare(ModelMyPotPage o1, ModelMyPotPage o2) {
                    return String.valueOf(o2.getRate()).compareTo(String.valueOf(o1.getRate()));
                }
            });
            adapterMyPotPage.notifyDataSetChanged();

            filterBt.setText("수익률순");
            dialogMyPotFilter.dismiss();
        }
    };
    private View.OnClickListener dateBtListen = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(activityMain, "아직 미정입니다.", Toast.LENGTH_SHORT).show();
            filterBt.setText("미정");
            dialogMyPotFilter.dismiss();
        }
    };

    @Override
    public void onResumeFragment() {

        if(refreshPage){
            refreshPage = false;

            Filter filter = new Filter();
            Call<ModelPotList> getReList = RetrofitClient.getInstance().getService().getPotList("application/json", filter, "U", 0,0,10);
            getReList.enqueue(new Callback<ModelPotList>() {
                @Override
                public void onResponse(Call<ModelPotList> call, Response<ModelPotList> response) {
                    if (response.code() == 200) {

                        filterBt.setEnabled(true);
                        modifyPot.setEnabled(true);
                        modelMyPotPages.clear();

                        for(int index = 0; index < response.body().getContent().size() ; index++){

                            modelMyPotPages.add(new ModelMyPotPage(false, response.body().getContent().get(index).getName(),
                                    response.body().getContent().get(index).getCode(),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRate()*100), 2, 2),
                                    DateTransForm.dateTransForm(response.body().getContent().get(index).getDate())));
                        }
                        number.setText(String.valueOf(response.body().getTotalElements()));
                        adapterMyPotPage.notifyDataSetChanged();

                    }
                }
                @Override
                public void onFailure(Call<ModelPotList> call, Throwable t) {
                }
            });

        }
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == 100){
                RxEventBus.getInstance().post(new RxEvent(RxEvent.REFRESH_MY_POT, null));

                Filter filter = new Filter();
                Call<ModelPotList> getReList = RetrofitClient.getInstance().getService().getPotList("application/json", filter, "U", 0,0,10);
                getReList.enqueue(new Callback<ModelPotList>() {
                    @Override
                    public void onResponse(Call<ModelPotList> call, Response<ModelPotList> response) {
                        if (response.code() == 200) {

                            modelMyPotPages.clear();

                            if(response.body().getTotalElements() == 0){

                                filterBt.setEnabled(false);
                                modifyPot.setEnabled(false);
                                modelMyPotPages.add(new ModelMyPotPage(true, "",
                                        "", 0, ""));
                            }else{
                                filterBt.setEnabled(true);
                                modifyPot.setEnabled(true);
                                for(int index = 0; index < response.body().getContent().size() ; index++){
                                    modelMyPotPages.add(new ModelMyPotPage(false, response.body().getContent().get(index).getName(),
                                            response.body().getContent().get(index).getCode(),
                                            DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRate()*100), 2, 2),
                                            DateTransForm.dateTransForm(response.body().getContent().get(index).getDate())));
                                }
                            }
                            number.setText(String.valueOf(response.body().getTotalElements()));
                            adapterMyPotPage.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelPotList> call, Throwable t) {
                    }
                });

            }
        }
    }
}
