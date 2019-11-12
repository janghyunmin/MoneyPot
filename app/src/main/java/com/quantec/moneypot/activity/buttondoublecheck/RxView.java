package com.quantec.moneypot.activity.buttondoublecheck;

import android.view.View;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;


import rx.Observable;

import static androidx.core.util.Preconditions.checkNotNull;

public class RxView {
    @CheckResult
    @NonNull
    public static Observable<Void> clicks(@NonNull View view) {
        // view는 null이면 안 된다.
        checkNotNull(view, "view == null");
        // Observable 을 만든다 인자로는 Subscriber를 implement한 객체를 가진다
        // Subscriber는 구독될 때 onNext, onCompleted, onError를 호출한다.
        return Observable.create(new ViewClickOnSubscribe(view));
    }
}


//// > 사용법 <
//  RxView.clicks(yesBt).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//          modelPropensities.add(new ModelPropensity(13, "권유를 원함"));
//          ViewAnimator.animate(bottomLayout).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//          adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//
//          modelPropensities.add(new ModelPropensity(2, ""));
//          adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//          recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//          });