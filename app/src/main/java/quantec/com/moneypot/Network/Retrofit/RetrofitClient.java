package quantec.com.moneypot.Network.Retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static quantec.com.moneypot.mApplication.UID;

public class RetrofitClient {

//    private static String baseUrl = "http://13.124.21.63/json/";
//    private static String baseUrl = "https://dev.quantec.co.kr:7777/core/";
    private static String baseUrl = "http://192.168.0.3:7777/core/";

    private static int CONNECT_TIMEOUT = 15;
    private static int WRITE_TIMEOUT = 15;
    private static int READ_TIMEOUT = 15;

    private static RetrofitClient ourInstance = new RetrofitClient();
    public static RetrofitClient getInstance(){
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
//                        request = request.newBuilder().header("mid",UID).build();
                        request = request.newBuilder().header("Authorization","Bearer ").build();
                        return chain.proceed(request);
                    }
                }).addInterceptor(httpLoggingInterceptor)
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
}
