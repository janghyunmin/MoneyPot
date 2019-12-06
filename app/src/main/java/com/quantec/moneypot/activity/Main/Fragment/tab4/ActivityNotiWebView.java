package com.quantec.moneypot.activity.Main.Fragment.tab4;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.quantec.moneypot.R;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.startActivity;

public class ActivityNotiWebView extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_web_view);

        webView=(WebView)findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
        webView.getSettings().setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl("https://mdev3.shinhaninvest.com/moneypot/account/new-account01.jsp?token=ldb");
//        webView.setWebViewClient(new MyWebViewClient());

//        webView.addJavascriptInterface();

        webView.setWebChromeClient(new WebChromeClient(){

        });

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                if (url.startsWith("http") || url.startsWith("https")) {
                    view.loadUrl(url);
                    Log.e("받은 유알엘1","값 : "+url);
                    return false;
                }
                Log.e("받은 유알엘2","값 : "+url);
                Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                marketIntent.setData(Uri.parse(url));
                startActivityForResult(marketIntent, 100);
//                startActivity(marketIntent);

                return true;
            }

            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("에러","값 : "+request.getUrl() + " || "+error.getDescription());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == 100){
                Log.e("받은 값", "베이스 64 : "+data.getStringExtra("base64"));
            }
        }

    }

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


