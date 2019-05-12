package quantec.com.moneypot.Network.Retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import quantec.com.moneypot.Util.DeEncryptUtil.DecryptUtil;

public class ResponceInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        String decryptedBody;

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        ModelResponce modelResponce = new ModelResponce();

        if(response.code() == 200) {

            if (response.body() != null) {
                modelResponce = gson.fromJson(response.body().string(), ModelResponce.class);
            }

            Log.e("응답값", "값 : "+ modelResponce.getContent());
            Log.e("응답값", "값 : "+ modelResponce.getTimestamp());

            String decryptedConent = DecryptUtil.getInstance().EncryptToDecrypt(modelResponce.getContent(), modelResponce.getTimestamp());
            modelResponce.setContent(decryptedConent);

            decryptedBody = gson.toJson(modelResponce).replace("\\n", "").replace(" ", "")
                    .replace("\\", "").replace("\"{", "{").replace("}\"", "}")
                    .replace("\"[{", "[{").replace("}]\"", "}]");

        }else{
            decryptedBody = response.body().string();
        }

//        String decryptedConent = DecryptUtil.getInstance().EncryptToDecrypt(modelResponce.getContent(), modelResponce.getTimestamp());
//
//        modelResponce.setContent(decryptedConent);
//
//        String decryptedBody = gson.toJson(modelResponce).replace("\\n", "").replace(" ", "")
//                .replace("\\", "").replace("\"{", "{").replace("}\"", "}")
//                .replace("\"[{", "[{").replace("}]\"", "}]");

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), decryptedBody))
                .build();
    }
}
