package quantec.com.moneypot.Network.Retrofit;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelEncryptData;
import quantec.com.moneypot.util.DeEncrypt.EncryptUtil;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    //    private static String baseUrl = "http://13.124.21.63/json/";
    private static String baseUrl = "https://dev.quantec.co.kr:7777/core/";
//    private static String baseUrl = "http://192.168.0.3:7777/core/";

    private static int CONNECT_TIMEOUT = 15;
    private static int WRITE_TIMEOUT = 15;
    private static int READ_TIMEOUT = 15;

    private static Context mContext;

    private static RetrofitClient ourInstance = new RetrofitClient();
    public static RetrofitClient getInstance(){
        return ourInstance;
    }
    public static RetrofitClient getInstance(Context context){
        mContext = context;
        return ourInstance;
    }

    public RetrofitClient() {
    }

    private static OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        String token = "";
                        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
                        if(mContext != null) {
                            //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
                            token = SharedPreferenceUtil.getInstance(mContext).getStringExtra("aToken");
                        }
                        Log.e("토큰", "값 : "+token);
//                        token = "dummy@quant.co.kr";

                        if(request.body() != null) {
                            String time = String.valueOf(new java.sql.Timestamp(System.currentTimeMillis()).getTime());

                            String decryptedConent = EncryptUtil.getInstance().EncryptToDecrypt(bodyToString(request.body()).replace("\\n", "").replace(" ", "")
                                    .replace("\\", "").replace("\"{", "{").replace("}\"", "}"), time);

                            ModelEncryptData modelEncryptData = new ModelEncryptData(time, decryptedConent, "aa");
                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), modelEncryptData.toString());

                            request = request.newBuilder()
                                    .method(request.method(), body)
                                    .header("Authorization","Bearer "+token).build();
                        }else{

                            request = request.newBuilder()
                                    .header("Authorization","Bearer "+token).build();
                        }

                        return chain.proceed(request);
                    }
                }).addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new ResponceInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();

        return client;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    RetrofitService service = retrofit.create(RetrofitService.class);
    public RetrofitService getService(){
        return service;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }


}




//public class RetrofitClient {
//
////    private static String baseUrl = "http://13.124.21.63/json/";
//    private static String baseUrl = "https://dev.quantec.co.kr:7777/core/";
////    private static String baseUrl = "http://192.168.0.3:7777/core/";
//
//    private static int CONNECT_TIMEOUT = 15;
//    private static int WRITE_TIMEOUT = 15;
//    private static int READ_TIMEOUT = 15;
//
//    private static Context mContext;
//
//    private static RetrofitClient ourInstance = new RetrofitClient();
//    public static RetrofitClient getInstance(){
//        return ourInstance;
//    }
//    public static RetrofitClient getInstance(Context context){
//        mContext = context;
//        return ourInstance;
//    }
//
//
//    public RetrofitClient() {
//    }
//
//    private static OkHttpClient createOkHttpClient() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Response response = chain.proceed(request);
//
//                        String bodyString = null;
//
////                        if (response.body() != null) {
////                            bodyString = response.body().string();
////                        }
//
//                        Log.e("응답값 가로채기","값 : "+ stringifyResponseBody(chain.proceed(request).body().toString()));
//
//
//                        String token = "";
//                        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
//                        if(mContext != null) {
//
//                            //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
//                            token = SharedPreferenceUtil.getInstance(mContext).getStringExtra("aToken");
//                        }
//                        Log.e("토큰", "값 : "+token);
////                        token = "dummy@quant.co.kr";
//
//
//                        RequestBody body = request.body();
//                        if (null != body) {
//                            Log.e("바디 가로채기", "값 : "+bodyToString(body));
//                        }
//
//
//
//                        request = request.newBuilder()
//                                .method(request.method(), body)
//                                .header("Authorization","Bearer "+token).build();
//
//
//                        return chain.proceed(request);
//                    }
//                }).addInterceptor(httpLoggingInterceptor)
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .build();
//        return client;
//    }
//
//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .client(createOkHttpClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build();
//
//    RetrofitService service = retrofit.create(RetrofitService.class);
//    public RetrofitService getService(){
//        return service;
//    }
//
//
//    public static String stringifyResponseBody(String responseBody) {
//        return responseBody;
//    }
//
//    private static String bodyToString(final RequestBody request){
//        try {
//            final RequestBody copy = request;
//            final Buffer buffer = new Buffer();
//            copy.writeTo(buffer);
//            return buffer.readUtf8();
//        }
//        catch (final IOException e) {
//            return "did not work";
//        }
//    }
//}
