package com.quantec.moneypot.activity.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.article.adapter.AdapterArticle;
import com.quantec.moneypot.activity.webview.ActivityWebViewArticle;
import com.quantec.moneypot.datamodel.dmodel.ModelArticle;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.util.view.refresh.Footer.LoadingView;
import com.quantec.moneypot.util.view.refresh.RefreshListenerAdapter;
import com.quantec.moneypot.util.view.refresh.TwinklingRefreshLayout;
import com.quantec.moneypot.util.view.refresh.header.SinaRefreshView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.quantec.moneypot.util.replacetag.ReplaceTag.ReplaceTag;

public class ActivityArticle extends AppCompatActivity {

    ImageView backBt;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelArticle> modelArticles;
    AdapterArticle adapterArticle;

    String code, type;

    TwinklingRefreshLayout refresh;
    int nextPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

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

        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        type = intent.getStringExtra("type");

        refresh = findViewById(R.id.refresh);

        backBt = findViewById(R.id.backBt);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        modelArticles = new ArrayList<>();

        adapterArticle = new AdapterArticle(modelArticles, this);
        recyclerView.setAdapter(adapterArticle);

        Call<com.quantec.moneypot.datamodel.nmodel.ModelArticle> tt = RetrofitClient.getInstance(this).getService().getNewsData(nextPage, 20, type, code);
        tt.enqueue(new Callback<com.quantec.moneypot.datamodel.nmodel.ModelArticle>() {

            @Override
            public void onResponse(Call<com.quantec.moneypot.datamodel.nmodel.ModelArticle> call, Response<com.quantec.moneypot.datamodel.nmodel.ModelArticle> response) {
                if(response.code() == 200) {

                    for(int index = 0; index < response.body().getContent().getNewsData().size(); index++){
                        modelArticles.add(new ModelArticle(response.body().getContent().getTotal(),
                                ReplaceTag(response.body().getContent().getNewsData().get(index).getTitle(), "decode"),
                                response.body().getContent().getNewsData().get(index).getFrom(),
                                response.body().getContent().getNewsData().get(index).getDate(),
                                response.body().getContent().getNewsData().get(index).getNewsUrl(),
                                response.body().getContent().getNewsData().get(index).getImgUrl()));
                    }

                    nextPage++;
                }
                adapterArticle.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<com.quantec.moneypot.datamodel.nmodel.ModelArticle> call, Throwable t) {
            }
        });

        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.anim_loading_view);
        headerView.setTextColor(0xff745D5C);
        refresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(this);
        refresh.setBottomView(loadingView);
        refresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextPage = 0;
                        getFollowList(refreshLayout, nextPage, true);
                    }
                },1500);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFollowList(refreshLayout, nextPage, false);
                    }
                },1500);
            }
        });

        adapterArticle.setArticleClick(new AdapterArticle.ArticleClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityArticle.this, ActivityWebViewArticle.class);
                intent.putExtra("url", modelArticles.get(position).getUrl());
                startActivity(intent);
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate 끝.


    void getFollowList(TwinklingRefreshLayout refreshLayout, int page, boolean init){

        Call<com.quantec.moneypot.datamodel.nmodel.ModelArticle> tt = RetrofitClient.getInstance(this).getService().getNewsData(page, 20, "0", code);
        tt.enqueue(new Callback<com.quantec.moneypot.datamodel.nmodel.ModelArticle>() {

            @Override
            public void onResponse(Call<com.quantec.moneypot.datamodel.nmodel.ModelArticle> call, Response<com.quantec.moneypot.datamodel.nmodel.ModelArticle> response) {
                if(response.code() == 200) {
                    if(init){
                        modelArticles.clear();
                    }

                    for(int index = 0; index < response.body().getContent().getNewsData().size(); index++){
                        modelArticles.add(new ModelArticle(response.body().getContent().getTotal(),
                                ReplaceTag(response.body().getContent().getNewsData().get(index).getTitle(), "decode"),
                                response.body().getContent().getNewsData().get(index).getFrom(),
                                response.body().getContent().getNewsData().get(index).getDate(),
                                response.body().getContent().getNewsData().get(index).getNewsUrl(),
                                response.body().getContent().getNewsData().get(index).getImgUrl()));
                    }

                    adapterArticle.notifyDataSetChanged();
                    nextPage++;
                    if(init) {
                        refreshLayout.finishRefreshing();
                        Toast.makeText(getApplicationContext(), "새로고침 완료.", Toast.LENGTH_SHORT).show();
                    }else{
                        refreshLayout.finishLoadmore();
                        Toast.makeText(getApplicationContext(), "추가 완료.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<com.quantec.moneypot.datamodel.nmodel.ModelArticle> call, Throwable t) {
                if(init) {
                    refreshLayout.finishRefreshing();
                    Toast.makeText(getApplicationContext(), "새로고침 실패.", Toast.LENGTH_SHORT).show();
                }else{
                    refreshLayout.finishLoadmore();
                    Toast.makeText(getApplicationContext(), "추가 실패.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
