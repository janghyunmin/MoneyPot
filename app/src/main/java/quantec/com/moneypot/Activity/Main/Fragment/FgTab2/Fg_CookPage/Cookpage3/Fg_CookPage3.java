package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Adapter.AdapterCookPage3;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Dialog.DialogDeleteMyPort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.dModel.ModelMyCookList;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelDeleteMyPort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelMyChartItem;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelgetMyPortList;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.Payment.ActivityPayment;
import quantec.com.moneypot.Activity.PortProfileModify.ActivityPortProfileModify;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.databinding.FgFgtab2Fgcookpage3Binding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Fg_CookPage3 extends Fragment {

    LinearLayoutManager linearLayoutManager;
    ArrayList<ModelMyCookList> modelMyCookLists;
    AdapterCookPage3 adapterCookPage3;

    List<Entry> entries3;
    LineDataSet lineDataSet3;
    LineData lineData3;

    //오픈된 차트가 있을때 편집시 닫아주기 위한 포지션 // 포트 삭제시 리스트에 위치
    int openChertPosition, DeletePosition;
    private MainActivity mainActivity;

    public static boolean ImageAnimState = false;
    public static boolean CookPage3_DeleteState = false;

    private DialogDeleteMyPort customDialog;
    //데이터 리프레쉬 할때 딜레이 줌 ( 반복적인 딜레이 막음 )
    boolean DelayedRefresh = false;

    FgFgtab2Fgcookpage3Binding fgcookpage3Binding;

    public Fg_CookPage3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgcookpage3Binding = DataBindingUtil.inflate(inflater, R.layout.fg_fgtab2_fgcookpage3, container, false);

        initializeViews();

        fgcookpage3Binding.fgCookpage3Recyclerview.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        fgcookpage3Binding.fgCookpage3Recyclerview.setLayoutManager(linearLayoutManager);

        modelMyCookLists = new ArrayList<>();

        entries3 = new ArrayList<>();
        lineDataSet3 = new LineDataSet(entries3, null);
        lineData3= new LineData(lineDataSet3);

        adapterCookPage3 = new AdapterCookPage3(modelMyCookLists, getContext(), entries3, lineDataSet3, lineData3);
        fgcookpage3Binding.fgCookpage3Recyclerview.setAdapter(adapterCookPage3);

        fgcookpage3Binding.fgCookpage3LoadingBar.setVisibility(View.GONE);

        return fgcookpage3Binding.getRoot();

    }//onViewCreate 끝


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fgcookpage3Binding.fgCookpage3RecyclerViewRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(DelayedRefresh){
                    fgcookpage3Binding.fgCookpage3RecyclerViewRefresh.setRefreshing(false);
                }else{
                    DelayedRefresh = true;
                    fgcookpage3Binding.fgCookpage3RecyclerViewRefresh.setRefreshing(false);
                    initData();
                }
            }
        });

        initData();

        adapterCookPage3.setCookPage3ItemClick(new AdapterCookPage3.CookPage3ItemClick() {
            @Override
            public void onClick(int position) {

                if(!CookPage3_DeleteState) {

                    if (modelMyCookLists.get(position).isOpenchart()) {
                        modelMyCookLists.get(position).setOpenchart(false);
                        adapterCookPage3.notifyDataSetChanged();
                    } else {
                        for (int a = 0; a < modelMyCookLists.size(); a++) {
                            modelMyCookLists.get(a).setOpenchart(false);
                        }

                        openChertPosition = position;

                        Call<ModelMyChartItem> getchartItem = RetrofitClient.getInstance().getService().getChartMyPortData(modelMyCookLists.get(position).getUcode(), "a");
                        getchartItem.enqueue(new Callback<ModelMyChartItem>() {
                            @Override
                            public void onResponse(Call<ModelMyChartItem> call, Response<ModelMyChartItem> response) {

                                if (response.code() == 200) {
                                    entries3.clear();
                                    for (int a = 0; a < response.body().getElements().size(); a++) {
                                        entries3.add(new Entry(a, response.body().getElements().get(a).getRate(), response.body().getElements().get(a).getDate()));
                                    }
                                    modelMyCookLists.get(position).setOpenchart(true);
                                    adapterCookPage3.notifyDataSetChanged();
                                    fgcookpage3Binding.fgCookpage3Recyclerview.smoothScrollToPosition(position);
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelMyChartItem> call, Throwable t) {
                                Log.e("레트로핏 실패", "값 : " + t.getMessage());
                            }
                        });
                    }
                }
                else{
                    Intent intent = new Intent(getActivity(), ActivityPortProfileModify.class);
                    intent.putExtra("imageCode", modelMyCookLists.get(position).getUcode());
                    intent.putExtra("imageName", modelMyCookLists.get(position).getTitle());
                    intent.putExtra("imageUrl", modelMyCookLists.get(position).getImage());
                    intent.putExtra("imagePhoto", modelMyCookLists.get(position).getPhoto());
                    intent.putExtra("imageDesc", modelMyCookLists.get(position).getDescript());
                    intent.putExtra("itemPosition", position);
                    startActivityForResult(intent,500);
                }
            }
        });

        adapterCookPage3.setCookPage3ItemDeleteClick(new AdapterCookPage3.CookPage3ItemDeleteClick() {
            @Override
            public void onClick(int position) {
                DeletePosition = position;
                customDialog = new DialogDeleteMyPort(getContext(), modelMyCookLists.get(position).getTitle(), leftListener, rightListener);
                customDialog.show();
            }
        });

        //상세페이지 이동
        adapterCookPage3.setCookPage3PortDetailPageClick(new AdapterCookPage3.CookPage3PortDetailPageClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityDetailPort.class);
                intent1.putExtra("detailcode", 15);
                intent1.putExtra("detailtitle", modelMyCookLists.get(position).getTitle());
                //상세페이지 이동시 타입을 false로 넘겨 주어 찜 버튼 대신 다른 이미지를 둠
                intent1.putExtra("detailtype", false);
                startActivity(intent1);
            }
        });

        //투자하기 클릭
        adapterCookPage3.setCookPage3PortInvestClick(new AdapterCookPage3.CookPage3PortInvestClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityPayment.class);
                intent1.putExtra("mincost", modelMyCookLists.get(position).getMincost());
                startActivity(intent1);
            }
        });

        adapterCookPage3.setCookPage3chartDur1Click(new AdapterCookPage3.CookPage3ChartDur1Click() {
            @Override
            public void onClick(int position) {
                ChartDur(position, "1");
            }
        });

        adapterCookPage3.setCookPage3ChartDur3Click(new AdapterCookPage3.CookPage3ChartDur3Click() {
            @Override
            public void onClick(int position) {
                ChartDur(position, "3");
            }
        });

        adapterCookPage3.setCookPage3ChartDur6Click(new AdapterCookPage3.CookPage3ChartDur6Click() {
            @Override
            public void onClick(int position) {
                ChartDur(position, "6");
            }
        });

        adapterCookPage3.setCookPage3ChartDuraClick(new AdapterCookPage3.CookPage3ChartDuraClick() {
            @Override
            public void onClick(int position) {
                ChartDur(position, "a");
            }
        });

        RxEventBus.getInstance()
                .filteredObservable(RxEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RxEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(RxEvent rxEvent) {

                        switch (rxEvent.getActiion()) {

                            case RxEvent.MY_PORT_DELETE_FINISH:
                                ChangedVisibleView();

                                ImageAnimState = false;
                                adapterCookPage3.notifyDataSetChanged();
                                break;

                            case RxEvent.COOK_PAGE3_MODIFY:
                                if(rxEvent.getBundle().getInt("page") == 2) {
                                    AdapterCookPage3.CookPage3_DeleteAnimState = false;
                                    // 0 : 편집 클릭시 이벤트 / 1 : 완료 클릭시 이벤트
                                    int modifyState = rxEvent.getBundle().getInt("page3Modify");
                                    if (modifyState == 0) {
                                        CookPage3_DeleteState = true;
                                        ImageAnimState = true;
                                    } else if (modifyState == 1) {
                                        CookPage3_DeleteState = false;
                                        ImageAnimState = false;
                                    }
                                    adapterCookPage3.notifyDataSetChanged();
                                }
                                break;

                            case RxEvent.ZZIM_PORT_TRANS_PAGE:

                                modelMyCookLists.add(new ModelMyCookList(rxEvent.getBundle().getString("myportname"), rxEvent.getBundle().getDouble("myportDrate"),
                                        rxEvent.getBundle().getInt("myportcode"), false, rxEvent.getBundle().getLong("myportcash"), null, 0, "", 1));
                                adapterCookPage3.notifyDataSetChanged();
                                break;

                            case RxEvent.COOK_PAGE_BASKET:
                                //전체삭제 이벤트
                                if(rxEvent.getBundle().getInt("basket") == 3) {
                                    CookMyDelete(0, 0, 1);
                                }
                                break;
                            case RxEvent.ZZIM_PORT_LOAD:
                                initData();
                                break;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });

    }//onViewCreate 끝


    //개별 삭제 취소
    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            customDialog.dismiss();
        }
    };

    //개별 삭제 확인
    //하나지울때는 opt=0
    //전체 지울 때는 opt=1
    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            CookMyDelete(modelMyCookLists.get(DeletePosition).getUcode(), DeletePosition, 0);
        }
    };

    //내가 만든 포트 삭제
    //개별 삭제 확인
    //하나지울때는 opt=0
    //전체 지울 때는 opt=1
    void CookMyDelete(int code, int position, int opt) {

        Call<ModelDeleteMyPort> getData = RetrofitClient.getInstance().getService().getDeleteMyPortData(code, opt);
        getData.enqueue(new Callback<ModelDeleteMyPort>() {
            @Override
            public void onResponse(Call<ModelDeleteMyPort> call, Response<ModelDeleteMyPort> response) {
                if (response.code() == 200) {
                    if(opt == 0) {
                        modelMyCookLists.remove(position);
                        adapterCookPage3.notifyItemRemoved(position);
                        adapterCookPage3.notifyItemRangeChanged(position, modelMyCookLists.size());
                        customDialog.dismiss();
                    }else{
                        modelMyCookLists.clear();
                        adapterCookPage3.notifyDataSetChanged();
                    }

                    SharedPreferences pref = getActivity().getSharedPreferences("myPortNumber", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("mpNumber", response.body().getNum());
                    editor.apply();
                }
            }
            @Override
            public void onFailure(Call<ModelDeleteMyPort> call, Throwable t) {
                Log.e("레트로핏 실패", "값 : " + t.getMessage());
            }
        });
    }

    //각 차트의 1개월 ~ 누적 버튼
    void ChartDur(int position, String dur) {

        Call<ModelMyChartItem> getchartItem = RetrofitClient.getInstance().getService().getChartMyPortData(modelMyCookLists.get(position).getUcode(),dur);
        getchartItem.enqueue(new Callback<ModelMyChartItem>() {
            @Override
            public void onResponse(Call<ModelMyChartItem> call, Response<ModelMyChartItem> response) {
                if(response.code() == 200) {
                    entries3.clear();
                    for(int a = 0 ; a < response.body().getElements().size() ; a++) {
                        entries3.add(new Entry(a, response.body().getElements().get(a).getRate(),response.body().getElements().get(a).getDate()));
                    }
                    modelMyCookLists.get(position).setOpenchart(true);
                    adapterCookPage3.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelMyChartItem> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }


    void initData(){

        fgcookpage3Binding.fgCookpage3LoadingBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DelayedRefresh = false;

                //내가 만든 포트 데이터 초기 불러옴
                Call<ModelgetMyPortList> getData = RetrofitClient.getInstance().getService().getMyPortData();
                getData.enqueue(new Callback<ModelgetMyPortList>() {
                    @Override
                    public void onResponse(Call<ModelgetMyPortList> call, Response<ModelgetMyPortList> response) {
                        if (response.code() == 200) {
                            modelMyCookLists.clear();
                            for(int a = 0 ; a < response.body().getProduct().size() ; a++) {
                                modelMyCookLists.add(new ModelMyCookList(response.body().getProduct().get(a).getName(),
                                        response.body().getProduct().get(a).getRate(), response.body().getProduct().get(a).getUcode(),
                                        false, 50L, response.body().getProduct().get(a).getIcon(), response.body().getProduct().get(a).getPhoto(), response.body().getProduct().get(a).getDescript(),
                                        response.body().getProduct().get(a).getType()));
                            }
                            adapterCookPage3.notifyDataSetChanged();
                            fgcookpage3Binding.fgCookpage3LoadingBar.setVisibility(View.GONE);
                            ChangedVisibleView();
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelgetMyPortList> call, Throwable t) {
                        Log.e("레트로핏 실패", "값 : " + t.getMessage());
                    }
                });
            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 500 && resultCode == 501) {
            modelMyCookLists.get(data.getIntExtra("PassPosition",0)).setPhoto(data.getIntExtra("PassPhoto",0));
            modelMyCookLists.get(data.getIntExtra("PassPosition",0)).setImage(data.getStringExtra("PassPortIcon"));
            modelMyCookLists.get(data.getIntExtra("PassPosition",0)).setTitle(data.getStringExtra("PassPortName"));
            modelMyCookLists.get(data.getIntExtra("PassPosition",0)).setDescript(data.getStringExtra("PassPortDesc"));
            adapterCookPage3.notifyDataSetChanged();
        }
    }

    //찜포트가 0개일때 삭제, 포트만들기 버튼 안보임/보임
    void ChangedVisibleView(){
//        mainActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if(modelMyCookLists.size() == 0) {
//                    fg_cookpage3_top_layout.setVisibility(View.INVISIBLE);
//                }else{
//                    fg_cookpage3_top_layout.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }


}
