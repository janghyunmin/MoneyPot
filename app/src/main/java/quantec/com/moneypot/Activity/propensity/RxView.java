package quantec.com.moneypot.Activity.propensity;

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