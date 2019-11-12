package com.quantec.moneypot.rxandroid;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxEventBus {

    private final PublishSubject<Object> mBusSubject;
    private static RxEventBus mRxEventBus;

    private RxEventBus() {
        mBusSubject = PublishSubject.create();
    }
    public static RxEventBus getInstance(){
        if(mRxEventBus == null) {
            mRxEventBus = new RxEventBus();
        }
        return mRxEventBus;
    }
    public void post(Object object) {
        mBusSubject.onNext(object);
    }
    public Observable<Object> obervable(){
        return mBusSubject;
    }
    public <T> Observable<T> filteredObservable(final Class<T> eventClass) {
        return mBusSubject.ofType(eventClass);
    }

}
