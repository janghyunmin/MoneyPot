package com.quantec.moneypot.fido;

import android.content.res.AssetManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyManager2 {
    public static final String PROPNAME2 = "rpclient.properties";
    private static final String SERVERURL2 = "ServerURL2";
    private static final String REQURL2 = "RequestURL2";
    private static final String RESURL2 = "ResponseURL2";
    private static final String VERIFIER2 = "HostNameVerifier2";

    private static Properties prop;
    private static AppCompatActivity act;

    public static void load(AppCompatActivity param){

        act = param;
        try {
            FileInputStream fis = act.openFileInput(PROPNAME2);
            prop = new Properties();
            prop.load(fis);
            fis.close();
        } catch (FileNotFoundException e) {

            try {

                AssetManager assetManager = act.getApplicationContext().getAssets();
                InputStream ins = assetManager.open(PROPNAME2);
                prop = new Properties();
                prop.load(ins);
                ins.close();

                FileOutputStream fos = act.openFileOutput(PROPNAME2, AppCompatActivity.MODE_PRIVATE);
                prop.store(fos, "fromAssets");
                fos.close();

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getReqUrl(){
        return prop.getProperty(SERVERURL2) + prop.getProperty(REQURL2);
    }

    public static String getResUrl(){
        return prop.getProperty(SERVERURL2) + prop.getProperty(RESURL2);
    }

    public static String getRestReq(){
        return prop.getProperty(REQURL2);
    }

    public static String getRestRes(){
        return prop.getProperty(RESURL2);
    }

    public static String getHostNameVerifier(){
        return prop.getProperty(VERIFIER2);
    }

    public static void setServerUrl(String url, String reqUrl, String resUrl){
        prop.setProperty(SERVERURL2, url);
        prop.setProperty(REQURL2, reqUrl);
        prop.setProperty(RESURL2, resUrl);
    }

    public static String getServerUrl(){
        return prop.getProperty(SERVERURL2);
    }

}
