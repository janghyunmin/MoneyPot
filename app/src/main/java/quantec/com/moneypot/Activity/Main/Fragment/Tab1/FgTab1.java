package quantec.com.moneypot.Activity.Main.Fragment.Tab1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;
import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Activity.propensity.ActivityPropensity;
import quantec.com.moneypot.DataModel.nModel.ModelAccounts;
import quantec.com.moneypot.DataModel.nModel.ModelDeleteLife;
import quantec.com.moneypot.DataModel.nModel.ModelInsertLife;
import quantec.com.moneypot.DataModel.nModel.ModelLifeList;
import quantec.com.moneypot.Database.Realm.accountData.AccountData;
import quantec.com.moneypot.Database.Realm.accountData.AccountDetail;
import quantec.com.moneypot.Dialog.DialogDeleteLife;
import quantec.com.moneypot.Dialog.DialogLoadingLifeC;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgTab1Binding;
import quantec.com.moneypot.util.ChangeUnitToPrice.ChangeUnitToPrice;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import quantec.com.moneypot.util.diffToDate.DiffToDate;
import quantec.com.moneypot.util.objectUtils.ObjectUtils;
import quantec.com.moneypot.util.removezero.RemoveZero;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgTab1 extends Fragment {

    FgTab1Binding binding;
    private ActivityMain activityMain;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelFitPotList> modelFitPotLists;
    AdapterFitPot adapterFitPot;

    ArrayList<ModelAccountWebView> modelAccountWebViews;

    private String nowPrice="";
    private String addPrice="";
    private String finalPrice="";

    private int nowPriceResult, addPriceResult, finalPriceResult, chartDate;
    private DialogLoadingLifeC dialogLoadingLifeC;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    Realm realm;
    AccountData accountData;
    AccountDetail accountDetail;

    private DialogDeleteLife dialogDeleteLife;
    private int deletePosition;

    RealmResults<AccountData> preAccountData;

    public FgTab1(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab1, container, false);

        initializeViews();

        return binding.getRoot();
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

        binding.recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain);

        binding.recyclerView.setLayoutManager(layoutManager);
        modelFitPotLists = new ArrayList<>();

        modelAccountWebViews = new ArrayList<>();

        modelFitPotLists.add(new ModelFitPotList(
                true, false, false, false, false, false, false, false,
                0,0,0,"", "", "", "", "", "", "",
                0, "", "", "", ""));
        modelAccountWebViews.add(new ModelAccountWebView(true, ""));

        adapterFitPot = new AdapterFitPot(modelFitPotLists, activityMain, modelAccountWebViews);
        binding.recyclerView.setAdapter(adapterFitPot);

        dialogLoadingLifeC = new DialogLoadingLifeC(activityMain);
        dialogLoadingLifeC.show();

        realm = Realm.getDefaultInstance();
        RealmResults<AccountData> results = realm.where(AccountData.class)
                .findAllAsync();

        Log.e("렘 사이즈","값 : "+results.where().count());

        //전체 삭제 구문
//        realm.beginTransaction();
//        RealmResults<AccountData> results = realm.where(AccountData.class)
//                .findAll();
//        results.deleteAllFromRealm();
//        RealmResults<AccountDetail> results2 = realm.where(AccountDetail.class)
//                .findAll();
//        results2.deleteAllFromRealm();
//        realm.commitTransaction();
//        realm.close();

        Call<ModelAccounts> getAccountData = RetrofitClient.getInstance().getService().getAccounts();
        getAccountData.enqueue(new Callback<ModelAccounts>() {
            @Override
            public void onResponse(Call<ModelAccounts> call, Response<ModelAccounts> response) {
                if (response.code() == 200) {

                    if (response.body().getTotalElements() != 0) {

                        realm.beginTransaction();

                        if(results.where().count() == 0){
                            accountData = realm.createObject(AccountData.class);
                        }else{
                            preAccountData = realm.where(AccountData.class).findAllAsync();
                        }

                        for (int index = 0; index < response.body().getContent().size(); index++) {

                            String account = response.body().getContent().get(index).getAccount();
                            double valance = response.body().getContent().get(index).getValance();

                            accountDetail = realm.where(AccountDetail.class).equalTo("account", account)
                                    .findFirst();
                            if (ObjectUtils.isEmpty(accountDetail)) {

                                accountDetail = realm.createObject(AccountDetail.class);

                                accountDetail.setAccount(account);
                                accountDetail.setValance(valance);
                                accountData.getAccountDetails().add(accountDetail);

                            } else {
                                accountDetail = realm.where(AccountDetail.class).equalTo("account", account)
                                        .findFirst();
                                preAccountData.get(0).getAccountDetails().get(index).setValance(valance);
                            }
                        }
                        realm.commitTransaction();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAccounts> call, Throwable t) {
                Toast.makeText(activityMain, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                Log.e("레트로핏 실패", "값 : " + t.getMessage());
            }
        });

//        if(results.where().count() == 0){
//            Call<ModelAccounts> getAccountData = RetrofitClient.getInstance().getService().getAccounts();
//            getAccountData.enqueue(new Callback<ModelAccounts>() {
//                @Override
//                public void onResponse(Call<ModelAccounts> call, Response<ModelAccounts> response) {
//                    if(response.code() == 200) {
//
//                        realm.beginTransaction();
//                        accountData = realm.createObject(AccountData.class);
//
//                        for(int index = 0 ; index < response.body().getContent().size() ; index++){
//
//                            accountDetail = realm.createObject(AccountDetail.class);
//
//                            accountDetail.setAccount(response.body().getContent().get(index).getAccount());
//                            accountDetail.setValance(response.body().getContent().get(index).getValance());
//                            accountData.getAccountDetails().add(accountDetail);
//                        }
//                        realm.commitTransaction();
//
////                        Log.e("어카운트 값","값 : "+results.get(0).getAccountDetails().get(0).getAccount());
////                        Log.e("보유금액 값","값 : "+results.get(0).getAccountDetails().get(0).getValance());
////
////                        String time = "2019-04-1308:54오전KST";
////                        Log.e("1번", "값 : "+time.substring(0,10));
////                        String date2 = time.substring(0,10);
////                        Log.e("현재시간","값 : "+getTime());
////                        String date1 = getTime();
////                        Log.e("날자 차이", DiffToDate.diffToDate(date1, date2) + " ");
////
////                        Log.e("도달률 전",  String.valueOf((1000 / results.get(0).getAccountDetails().get(0).getValance()) * 100));
////                        Log.e("도달률 후",  String.valueOf(DecimalScale.decimalScale(String.valueOf(1000 / results.get(0).getAccountDetails().get(0).getValance() * 100), 1, 1)));
////
////                        modelFitPotLists.add(new ModelFitPotList(
////                                false, true, false, false, false, false, true, false,
////                                0,0,0,"", "", "", "", "", "", "",
////                                0, "", "", String.valueOf((1000 / results.get(0).getAccountDetails().get(0).getValance()) * 100), DiffToDate.diffToDate(date1, date2)));
////                        adapterFitPot.notifyDataSetChanged();
//
//                    }
//                }
//                @Override
//                public void onFailure(Call<ModelAccounts> call, Throwable t) {
//                    Toast.makeText(activityMain, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
//                    Log.e("레트로핏 실패","값 : "+t.getMessage());
//                }
//            });
//        }


//        String time = "2019-04-1308:54오전KST";
//        Log.e("1번", "값 : "+time.substring(0,10));
//        String date2 = time.substring(0,10);
//        Log.e("현재시간","값 : "+getTime());
//        String date1 = getTime();
//        Log.e("날자 차이", DiffToDate.diffToDate(date1, date2) + " ");
//
//        Log.e("도달률 전",  String.valueOf((1000 / results.get(0).getAccountDetails().get(0).getValance()) * 100));
//        Log.e("도달률 후",  String.valueOf(DecimalScale.decimalScale(String.valueOf(1000 / results.get(0).getAccountDetails().get(0).getValance() * 100), 1, 1)));
//
//        modelFitPotLists.add(new ModelFitPotList(
//                false, true, false, false, false, false, true, false,
//                0,0,0,"", "", "", "", "", "", "",
//                0, "", "", String.valueOf((1000 / results.get(0).getAccountDetails().get(0).getValance()) * 100), DiffToDate.diffToDate(date1, date2)));
//        adapterFitPot.notifyDataSetChanged();


//        Log.e("어카운트 값","값 : "+results.get(0).getAccountDetails().get(0).getAccount());
//        Log.e("보유금액 값","값 : "+results.get(0).getAccountDetails().get(0).getValance());
//
//        String time = "2019-04-1308:54오전KST";
//        Log.e("1번", "값 : "+time.substring(0,10));
//        String date2 = time.substring(0,10);
//        Log.e("현재시간","값 : "+getTime());
//        String date1 = getTime();
//        Log.e("날자 차이", DiffToDate.diffToDate(date1, date2) + " ");
//
//        Log.e("도달률",  String.valueOf((1000 / results.get(0).getAccountDetails().get(0).getValance()) * 100));
//
//
//        modelFitPotLists.add(new ModelFitPotList(
//                false, true, false, false, false, false, true, false,
//                0,0,0,"", "", "", "", "", "", "",
//                0, "", "", String.valueOf((1000 / results.get(0).getAccountDetails().get(0).getValance()) * 100), DiffToDate.diffToDate(date1, date2)));
//        adapterFitPot.notifyDataSetChanged();

        Call<ModelLifeList> getTest2 = RetrofitClient.getInstance().getService().getLifes();
        getTest2.enqueue(new Callback<ModelLifeList>() {
            @Override
            public void onResponse(Call<ModelLifeList> call, Response<ModelLifeList> response) {
                if(response.code() == 200) {

                    if(response.body().getTotalElements() == 0){

                        dialogLoadingLifeC.dismiss();
                        lifeDrawView();
                    }
                    else{

                        String plan ="";
                        for(int index = 0; index < response.body().getTotalElements(); index++){

                            if(response.body().getContent().get(index).getLifeYear() <= 2){
                                plan = "#단기 플랜";
                            }else if(response.body().getContent().get(index).getLifeYear() >= 3 && response.body().getContent().get(index).getLifeYear() <= 9){
                                plan = "#중장기 플랜";
                            }else{
                                plan = "#장기 플랜";
                            }

                            String lifeImageUrl = "";
                            if(response.body().getContent().get(index).getFile() != null){
                                lifeImageUrl = response.body().getContent().get(index).getFile().getHome()+response.body().getContent().get(index).getFile().getFileFullPath();
                            }

                            modelFitPotLists.add(new ModelFitPotList(false, true, false, false, false, true, false,false,
                                    response.body().getContent().get(index).getNowPrice(),
                                    response.body().getContent().get(index).getMonPrice(),
                                    response.body().getContent().get(index).getHopePrice(),
                                    response.body().getContent().get(index).getName(),
                                    response.body().getContent().get(index).getDescript(),
                                    response.body().getContent().get(index).getCode(),
                                    "#"+response.body().getContent().get(index).getLifeYear()+"년 동안",
                                    "#"+ChangeUnitToPrice.changeUnitToPrice(String.valueOf(response.body().getContent().get(index).getHopePrice()))+"모으기",
                                    plan,
                                    response.body().getContent().get(index).getPname(),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getPrate()*100), 2, 2),
                                    response.body().getContent().get(index).getLcCode(),
                                    lifeImageUrl,
                                    "",
                                    ""));
                        }

                        AddViewDraw(response.body().getTotalElements());

                        dialogLoadingLifeC.dismiss();
                        adapterFitPot.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelLifeList> call, Throwable t) {
                dialogLoadingLifeC.dismiss();
                Toast.makeText(activityMain, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });


        adapterFitPot.setInsertNextBtClick(new AdapterFitPot.InsertNextBtClick() {
            @Override
            public void onClick(int position) {

                if(!nowPrice.isEmpty() && !finalPrice.isEmpty()){
                    modelFitPotLists.get(position).setInsertView(false);
                    modelFitPotLists.get(position).setChartView(true);
                    modelFitPotLists.get(position).setNowPrice(Integer.valueOf(nowPrice));

                    if(addPrice.isEmpty()){
                        addPrice = "0";
                    }
                    modelFitPotLists.get(position).setMonPrice(Integer.valueOf(addPrice));
                    modelFitPotLists.get(position).setFinalPrice(Integer.valueOf(finalPrice));
                    adapterFitPot.notifyDataSetChanged();

                    nowPriceResult = Integer.valueOf(nowPrice);
                    addPriceResult = Integer.valueOf(addPrice);
                    finalPriceResult = Integer.valueOf(finalPrice);

                    nowPrice = "";
                    addPrice = "";
                    finalPrice = "";

                }else{
                    Toast.makeText(activityMain, "금액을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


        adapterFitPot.setChartNextBtClick(new AdapterFitPot.ChartNextBtClick() {
            @Override
            public void onClick(int position) {

                dialogLoadingLifeC = new DialogLoadingLifeC(activityMain);
                dialogLoadingLifeC.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        insertLife(position, finalPriceResult, 704, chartDate, addPriceResult, nowPriceResult);
                    }
                }, 1000);
            }
        });

        adapterFitPot.setAddItemViewClick(new AdapterFitPot.AddItemViewClick() {
            @Override
            public void onClick(int position) {
                modelFitPotLists.get(position).setAddView(false);
                modelFitPotLists.get(position).setInsertView(true);
                adapterFitPot.notifyDataSetChanged();
            }
        });
        adapterFitPot.setNowPriceText(new AdapterFitPot.NowPriceText() {
            @Override
            public void onText(String text) {
                nowPrice = RemoveZero.removeZero(text);
            }
        });
        adapterFitPot.setAddPriceText(new AdapterFitPot.AddPriceText() {
            @Override
            public void onText(String text) {
                addPrice = RemoveZero.removeZero(text);
            }
        });
        adapterFitPot.setFinalPriceText(new AdapterFitPot.FinalPriceText() {
            @Override
            public void onText(String text) {
                finalPrice = RemoveZero.removeZero(text);
            }
        });

        adapterFitPot.setChartBackBt(new AdapterFitPot.ChartBackBt() {
            @Override
            public void onClick(int position) {
                modelFitPotLists.get(position).setChartView(false);
                modelFitPotLists.get(position).setInsertView(true);
                adapterFitPot.notifyDataSetChanged();
            }
        });
        adapterFitPot.setChartDate(new AdapterFitPot.ChartDate() {
            @Override
            public void onText(int year) {
                chartDate = year;
            }
        });

        adapterFitPot.setDeleteLifeBt(new AdapterFitPot.DeleteLifeBt() {
            @Override
            public void onClick(int position) {

                deletePosition = position;

                dialogDeleteLife = new DialogDeleteLife(activityMain, lifeDeleteCancle, lifeDeleteOk);
                dialogDeleteLife.show();
            }
        });


        adapterFitPot.setShowChartView(new AdapterFitPot.ShowChartView() {
            @Override
            public void onClick(int position) {

                if(modelFitPotLists.get(position).isShowChart()){
                    modelFitPotLists.get(position).setShowChart(false);
                }else{
                    for(int index = 1; index < modelFitPotLists.size()-1 ; index++){
                        modelFitPotLists.get(index).setShowChart(false);
                    }
                    modelFitPotLists.get(position).setShowChart(true);
                }
                adapterFitPot.notifyDataSetChanged();
            }
        });

        adapterFitPot.setAccountBtClick(new AdapterFitPot.AccountBtClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(activityMain, ActivityPropensity.class);
                startActivity(intent);
            }
        });

    }//onCreateView 끝


    //라이프 챌린지 삭제 취소
    private View.OnClickListener lifeDeleteCancle = new View.OnClickListener() {
        public void onClick(View v) {
            dialogDeleteLife.dismiss();
        }
    };

    //라이프 챌린지 삭제 확인
    private View.OnClickListener lifeDeleteOk = new View.OnClickListener() {
        public void onClick(View v) {

            Call<ModelDeleteLife> getSaveLifeList = RetrofitClient.getInstance().getService().getDelMyPot( modelFitPotLists.get(deletePosition).getLifeCode());
            getSaveLifeList.enqueue(new Callback<ModelDeleteLife>() {
                @Override
                public void onResponse(Call<ModelDeleteLife> call, Response<ModelDeleteLife> response) {
                    if (response.code() == 200) {
                        modelFitPotLists.remove(deletePosition);

                        AddViewDraw(response.body().getTotalElements());
                        adapterFitPot.notifyDataSetChanged();

                        dialogDeleteLife.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<ModelDeleteLife> call, Throwable t) {
                    Log.e("에러값","값 : "+t.getMessage());
                    dialogDeleteLife.dismiss();
                }
            });

        }
    };


    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    void lifeDrawView(){
        modelFitPotLists.add(new ModelFitPotList(
                false, true, true, false, false, false, false, false,
                0,0,0,"", "", "", "", "", "", "",
                0, "", "", "", ""));
        adapterFitPot.notifyDataSetChanged();
    }

    void insertLife(int position, int hopePrice, int investType, int lifeYear, int monPrice, int nowPrice){

        LifeMap lifeMap = new LifeMap("","", hopePrice, investType, lifeYear, monPrice, "", nowPrice);
        Call<ModelInsertLife> getSaveLifeList = RetrofitClient.getInstance().getService().getSaveLife("application/json", lifeMap);
        getSaveLifeList.enqueue(new Callback<ModelInsertLife>() {
            @Override
            public void onResponse(Call<ModelInsertLife> call, Response<ModelInsertLife> response) {
                if (response.code() == 200) {

                    modelFitPotLists.remove(position);

                    String plan = "";
                    if(response.body().getContent().get(0).getLifeYear() <= 2){
                        plan = "#단기 플랜";
                    }else if(response.body().getContent().get(0).getLifeYear() >= 3 && response.body().getContent().get(0).getLifeYear() <= 9){
                        plan = "#중장기 플랜";
                    }else{
                        plan = "#장기 플랜";
                    }

                    String lifeImageUrl = "";
                    if(response.body().getContent().get(0).getFile() != null){
                        lifeImageUrl = response.body().getContent().get(0).getFile().getHome()+response.body().getContent().get(0).getFile().getFileFullPath();
                    }

                    modelFitPotLists.add(new ModelFitPotList(false, true, false, false, false, true, false,false,
                            response.body().getContent().get(0).getNowPrice(),
                            response.body().getContent().get(0).getMonPrice(),
                            response.body().getContent().get(0).getHopePrice(),
                            response.body().getContent().get(0).getName(),
                            response.body().getContent().get(0).getDescript(),
                            response.body().getContent().get(0).getCode(),
                            "#"+response.body().getContent().get(0).getLifeYear()+"년 동안",
                            "#"+ChangeUnitToPrice.changeUnitToPrice(String.valueOf(response.body().getContent().get(0).getHopePrice()))+"모으기",
                            plan,
                            response.body().getContent().get(0).getPname(),
                            DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(0).getPrate()*100), 2, 2),
                            response.body().getContent().get(0).getLcCode(),
                            lifeImageUrl,
                            "",
                            ""));

                    AddViewDraw(response.body().getTotalElements());

                    dialogLoadingLifeC.dismiss();
                    adapterFitPot.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelInsertLife> call, Throwable t) {
                Log.e("에러값","값 : "+t.getMessage());
                Toast.makeText(activityMain, "네트워크가 불안정 합니다.", Toast.LENGTH_SHORT).show();
                dialogLoadingLifeC.dismiss();
            }
        });
    }

    void AddViewDraw(int potSize){

        if(potSize < 3){
            boolean addFlag = false;
            for(int index = 1; index < modelFitPotLists.size(); index++){
                if(modelFitPotLists.get(index).isAddView()){
                    addFlag = true;
                    break;
                }else{
                    addFlag = false;
                }
            }
            if(!addFlag){
                modelFitPotLists.add(new ModelFitPotList(false, true, false, false,
                        true, false, false,false,
                        0,0,0,"", "", "", "",
                        "", "", "", 0, "", "", "", ""));
            }
        }
    }
}