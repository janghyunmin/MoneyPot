package quantec.com.moneypot.Activity.propensity;

import android.view.View;

import com.jakewharton.rxbinding.internal.MainThreadSubscription;

import rx.Observable;
import rx.Subscriber;

import static io.reactivex.android.MainThreadDisposable.verifyMainThread;

public class ViewClickOnSubscribe implements Observable.OnSubscribe<Void> {
    final View view;

    public ViewClickOnSubscribe(View view) {
        this.view = view;
    }

    @Override
    public void call(Subscriber<? super Void> subscriber) {
        verifyMainThread();

        View.OnClickListener listener = v -> {
            // view가 눌리면 구독자의 onNext를 호출한다
            if(!subscriber.isUnsubscribed()) subscriber.onNext(null);
        };

        // 버튼은 main thread에서 구독되야 한다 그래서 main thread 구독자에 추가한다
        subscriber.add(new MainThreadSubscription() {
            // 구독이 끝나면 null 값을 주어 가비지 컬렉터가 처리할 수 있도록 해줘야한다
            // 안그러면 메모리 릭이 생길 수 있다
            @Override
            protected void onUnsubscribe() {
                view.setOnClickListener(null);
            }
        });
        // 모두가 알고 있는 바로 그것이다 리스너 등록
        view.setOnClickListener(listener);

    }
}
