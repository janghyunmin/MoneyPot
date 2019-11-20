package com.quantec.moneypot.activity.Center;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quantec.moneypot.R;

public class ActivityWebViewQuestion extends AppCompatActivity {

    private WebView webView;
    private TextView questionBt;
    private ImageView backBt;

    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_question);

        webView=(WebView)findViewById(R.id.webView);
        questionBt = findViewById(R.id.questionBt);
        backBt = findViewById(R.id.backBt);

        Intent intent = getIntent();
        Url = intent.getStringExtra("url");

        WebSettings mws=webView.getSettings();//Mobile Web Setting
        mws.setJavaScriptEnabled(true);//자바스크립트 허용
        mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(Url);

        questionBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityWebViewQuestion.this, "1:1 문의로 이동합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
        marketIntent.setData(Uri.parse("testdeep://deeplink.com"));
        startActivity(marketIntent);

    }// onCreate 끝

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
