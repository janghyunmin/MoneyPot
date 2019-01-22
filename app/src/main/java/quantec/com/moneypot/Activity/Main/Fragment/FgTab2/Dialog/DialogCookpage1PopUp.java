package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;

public class DialogCookpage1PopUp  extends Dialog {


    private View.OnClickListener ZzimClickListener, ShareClickListener, CloseClickListener;

    TextView dialog_popup_cookpage1_zzim,
            dialog_popup_cookpage1_share;

    LinearLayout dialog_popup_cookpage1_close, dialog_popup_cookpage1_layout;

    ImageView dialog_popup_cookpage1_zzim_image, dialog_popup_cookpage1_share_Image;

    int ZzimState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_fgtab2_cookpage1popup);

        dialog_popup_cookpage1_share = findViewById(R.id.dialog_popup_cookpage1_share);
        dialog_popup_cookpage1_zzim = findViewById(R.id.dialog_popup_cookpage1_zzim);

        dialog_popup_cookpage1_close = findViewById(R.id.dialog_popup_cookpage1_close);

        dialog_popup_cookpage1_layout = findViewById(R.id.dialog_popup_cookpage1_layout);

        dialog_popup_cookpage1_share_Image = findViewById(R.id.dialog_popup_cookpage1_share_Image);
        dialog_popup_cookpage1_zzim_image = findViewById(R.id.dialog_popup_cookpage1_zzim_image);

        if(ZzimState == 0) {
            dialog_popup_cookpage1_zzim_image.setBackgroundResource(R.drawable.ic_star_gray_off);
            dialog_popup_cookpage1_zzim.setText("찜 하기");
            dialog_popup_cookpage1_zzim.setTextColor(getContext().getResources().getColor(R.color.dark_gray_color));
        }else{
            dialog_popup_cookpage1_zzim_image.setBackgroundResource(R.drawable.ic_star_yellow_on);
            dialog_popup_cookpage1_zzim.setText("찜 완료");
            dialog_popup_cookpage1_zzim.setTextColor(getContext().getResources().getColor(R.color.normal_text_color));
        }

        dialog_popup_cookpage1_share_Image.setBackgroundResource(R.drawable.ic_share_gray);

        TranslateAnimation animation = new TranslateAnimation(0, 0 , 500, 0);
        animation.setDuration(300);
        animation.setFillAfter(false);
        animation.setFillEnabled(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        dialog_popup_cookpage1_layout.startAnimation(animation);

        dialog_popup_cookpage1_zzim.setOnClickListener(ZzimClickListener);
        dialog_popup_cookpage1_share.setOnClickListener(ShareClickListener);
        dialog_popup_cookpage1_close.setOnClickListener(CloseClickListener);


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

                            case RxEvent.COOK_DIOLOG:

                                if(ZzimState == 0) {
                                    ZzimState = 1;
                                    dialog_popup_cookpage1_zzim_image.setBackgroundResource(R.drawable.ic_star_yellow_on);
                                    dialog_popup_cookpage1_zzim.setText("찜 완료");
                                    dialog_popup_cookpage1_zzim.setTextColor(getContext().getResources().getColor(R.color.normal_text_color));

                                    final Animation animTransRight = AnimationUtils
                                            .loadAnimation(getContext(), R.anim.dialog_size_up);
                                    dialog_popup_cookpage1_zzim_image.startAnimation(animTransRight);

                                }else{
                                    ZzimState = 0;
                                    dialog_popup_cookpage1_zzim_image.setBackgroundResource(R.drawable.ic_star_gray_off);
                                    dialog_popup_cookpage1_zzim.setText("찜 하기");
                                    dialog_popup_cookpage1_zzim.setTextColor(getContext().getResources().getColor(R.color.dark_gray_color));
                                }
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

    }


    public DialogCookpage1PopUp(Context context, int ZzimState, View.OnClickListener ZzimClick,
                          View.OnClickListener ShareClick, View.OnClickListener CloseClick) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.ZzimState = ZzimState;
        this.ZzimClickListener = ZzimClick;
        this.ShareClickListener = ShareClick;
        this.CloseClickListener = CloseClick;
    }
}
