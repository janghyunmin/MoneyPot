package quantec.com.moneypot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import quantec.com.moneypot.R;

public class DialogLoadingLifeC extends Dialog {

    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_loadinglifec);

        loading = findViewById(R.id.lottie);
        loading.setAnimation("loading_animation.json");
        loading.setRepeatCount(LottieDrawable.INFINITE);
        loading.playAnimation();
    }

    public DialogLoadingLifeC(Context context){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        loading.cancelAnimation();
    }
}
