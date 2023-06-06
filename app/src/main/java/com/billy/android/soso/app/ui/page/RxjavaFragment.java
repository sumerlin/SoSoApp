package com.billy.android.soso.app.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentRxjavaBinding;
import com.billy.android.soso.app.databinding.FragmentWorkmanagerBinding;
import com.billy.android.soso.app.service.DownloadWorker;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import rxhttp.wrapper.utils.LogUtil;

/**
 * Author：summer
 * Description：
 */

public class RxjavaFragment extends VmDataBindingFragment {

    RxjavaUiState mRxjavaUiState;

    @Override
    protected void initViewModel() {

        mRxjavaUiState = getFragmentScopeViewModel(RxjavaUiState.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_rxjava, BR.workManagerUiState, mRxjavaUiState);
    }

    FragmentRxjavaBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = (FragmentRxjavaBinding) getBinding();
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(mActivity).cancelAllWorkByTag("WorkRequest");
            }
        });
        binding.btnRxjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxjavaTest();
                binding.tvRxjava.setText("doing...");
            }
        });
    }


    private void rxjavaTest_create() {
        LogUtil.log("====rxjavaTest_create()====::");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {

                emitter.onNext("你好");
                emitter.onNext("summer");
                emitter.onError(new Exception("出错了"));
                emitter.onComplete();
            }
        }).subscribe(new DisposableObserver<String>() {

            @Override
            protected void onStart() {
                super.onStart();
                LogUtil.log("====onStart()====::");

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                LogUtil.log("====onNext()====::" + s);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                LogUtil.log("====onError()====::" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.log("====onComplete()====::");
            }


        });
    }

    private void rxjavaTest_just() {
        LogUtil.log("====rxjavaTest_just()====::");
        //主要作用就是创建一个被观察者，并发送事件，但是发送的事件不可以超过10个以上。
        //create 需要手动发射数据事件， just 按顺序发射数据事件之后，结束执行onComplete(), 即有头有尾
        Observable.just("你好", "summer")
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        LogUtil.log("====onStart()====::");

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                        LogUtil.log("====onNext()====::" + s);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        LogUtil.log("====onError()====::" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.log("====onComplete()====::");
                    }
                });

    }

    private void rxjavaTest_time() {
        LogUtil.log("====rxjavaTest_time()====::");
        //当到指定时间后就会发送一个 0 的值给观察者。 在项目中，可以做一些延时的处理，类似于Handler中的延时
        Observable.timer(3, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Throwable {
                        return "summer";
                    }
                })
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull String aLong) {
                        LogUtil.log("====onNext()====::" + aLong);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxjavaTest_interval() {
        LogUtil.log("====rxjavaTest_interval()====::");
        //每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字。 类似于项目中的timer，做计时器
        //应用：比如心跳，定时上传日志
        //每间隔3秒发送一次
        Observable.interval(3, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Long aLong) throws Throwable {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                                emitter.onNext("summer" + aLong);
                            }
                        });
                    }
                })
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull String aLong) {
                        LogUtil.log("====onNext()====::" + aLong);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxjavaTest_range() {
        LogUtil.log("====rxjavaTest_range()====::");
        //同时发送一定范围的事件序列。
        Observable.range(0, 10)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Throwable {
                        return integer;
                    }
                })
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {
                        LogUtil.log(integer + "");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    private void rxjavaTest_intervalRange() {
        LogUtil.log("====rxjavaTest_intervalRange()====::");
        //可以指定发送事件的开始值和数量，其他与 interval() 的功能一样。
        Observable.intervalRange(2, 3, 1, 3, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Long aLong) throws Throwable {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                                emitter.onNext("summer" + aLong);
                            }
                        });
                    }
                })
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull String aLong) {
                        LogUtil.log("====onNext()====::" + aLong);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void rxjavaTest_map() {
        LogUtil.log("====rxjavaTest_map()====::");
        //map 可以将被观察者发送的数据类型转变成其他的类型
        Observable.just(1, 2).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer s) throws Throwable {
                return "summer" + s;
            }
        }).subscribe(new DisposableObserver<String>() {
            @Override
            protected void onStart() {
                super.onStart();
                LogUtil.log("====onStart()====::");

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                LogUtil.log("====onNext()====::" + s);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                LogUtil.log("====onError()====::" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.log("====onComplete()====::");
            }
        });
    }


    private void rxjavaTest_flatMap() {
        //这个方法可以将事件序列中的元素进行整合加工，返回一个新的被观察者。
        Observable.just("").flatMap(new Function<String, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(String s) throws Throwable {
                return Observable.just(Integer.valueOf(s));
            }
        }).subscribe(new DisposableObserver<Integer>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void rxjavaTest_concat() {
        LogUtil.log("====rxjavaTest_concat()====::");
        //可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat() 最多只可以发送4个事件。
        Observable.concat(Observable.just(1, 2, 3, 4, 5, 6).subscribeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Throwable {
                                LogUtil.log("====accept()====::");
                            }
                        }),
                        Observable.just(7, 8, 9, 10, 11, 12).subscribeOn(Schedulers.io()))
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        LogUtil.log("====onStart()====::");

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Integer s) {
                        LogUtil.log("====onNext()====::" + s);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        LogUtil.log("====onError()====::" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.log("====onComplete()====::");
                    }
                });
    }

    private void rxjavaTest_merge() {
        LogUtil.log("====rxjavaTest_merge()====::");
        //这个方法与 concat() 作用基本一样，但是 concat() 是串行发送事件，而 merge() 并行发送事件，也是只能发送4个
        Observable.merge(Observable.just(1, 2, 3, 4, 5, 6).subscribeOn(AndroidSchedulers.mainThread()),
                        Observable.just(7, 8, 9, 10, 11, 12).subscribeOn(Schedulers.io()))
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        LogUtil.log("====onStart()====::");

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Integer s) {
                        LogUtil.log("====onNext()====::" + s);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        LogUtil.log("====onError()====::" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.log("====onComplete()====::");
                    }
                });
    }

    private void rxjavaTest_zip() {
        LogUtil.log("====rxjavaTest_zip()====::");
        //zip操作符用于将多个数据源合并，并生成一个新的数据源。
        //新生成的数据源严格按照合并前的数据源的数据发射顺序，并且新数据源的数据个数等于合并前发射数据个数最少的那个数据源的数据个数。
        Observable.zip(Observable.just(1, 2), Observable.just("a", "b", "c"), new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Throwable {
                return null;
            }
        }).subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxjavaTest_doOnXxx() {
        LogUtil.log("====rxjavaTest_doOnXxx()====::");
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).doOnEach(new Consumer<Notification<Integer>>() {

            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {

                LogUtil.log("doOnEach 方法执行了, 结果：" + integerNotification.getValue());
            }
        }).doOnNext(new Consumer<Integer>() {

            @Override
            public void accept(Integer integer) throws Exception {

                LogUtil.log("doOnNext 方法执行了, 结果：" + integer);
            }
        }).doAfterNext(new Consumer<Integer>() {

            @Override
            public void accept(Integer integer) throws Exception {

                LogUtil.log("doAfterNext 方法执行了, 结果：" + integer);
            }
        }).doOnComplete(new Action() {

            @Override
            public void run() throws Exception {

                LogUtil.log("doOnComplete 方法执行了");
            }
        }).doOnError(new Consumer<Throwable>() {

            @Override
            public void accept(Throwable throwable) throws Exception {

                LogUtil.log("doOnError 方法执行了");
            }
        }).doOnSubscribe(new Consumer<Disposable>() {

            @Override
            public void accept(Disposable disposable) throws Exception {
                //doOnSubscribe(): Observable 每发送 onSubscribe()之前都会回调这个方法。
                LogUtil.log("doOnSubscribe 方法执行了");
            }
        }).doOnDispose(new Action() {

            @Override
            public void run() throws Exception {
                //doOnDispose(): 当调用 Disposable 的 dispose() 之后回调该方法。
                LogUtil.log("doOnDispose 方法执行了");
            }
        }).doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                //doOnTerminate(): 在 onError 或者 onComplete 发送之前回调。
                LogUtil.log("doOnTerminate 方法执行了");
            }
        }).doAfterTerminate(new Action() {
            //doAfterTerminate(): onError 或者 onComplete 发送之后回调。
            @Override
            public void run() throws Exception {

                LogUtil.log("doAfterTerminate 方法执行了");
            }
        }).doFinally(new Action() {

            @Override
            public void run() throws Exception {
                //doFinally(): 在所有事件发送完毕之后回调该方法。
                LogUtil.log("doFinally 方法执行了");
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {

                disposable = d;
                LogUtil.log("------观察者onSubscribe()执行");
            }

            @Override
            public void onNext(Integer integer) {

                LogUtil.log("------观察者onNext()执行：" + integer);
                if (integer == 2) {
//                    disposable.dispose(); // 取消订阅
                }
            }

            @Override
            public void onError(Throwable e) {

                LogUtil.log("------观察者onError()执行");
            }

            @Override
            public void onComplete() {

                LogUtil.log("------观察者onComplete()执行");
            }
        });

    }

    private void rxjavaTest_onErrorReturn() {
        LogUtil.log("====rxjavaTest_doOnXxx()====::");
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("小明：到");
                emitter.onError(new IllegalStateException("error"));
                emitter.onNext("小方：到");
            }
        }).onErrorReturn(new Function<Throwable, String>() {

            @Override
            public String apply(Throwable throwable) throws Exception {

                LogUtil.log("小红请假了");
                return "小李：到";
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {

                LogUtil.log(s);
            }

            @Override
            public void onError(Throwable e) {

                LogUtil.log("===onError===" + e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });

    }

    private void rxjavaTest_onErrorResumeNext() {
        //当接受到一个 onError() 事件之后回调，返回的值会回调 onNext() 方法，并正常结束该事件序列。
        //什么意思？有多个事件需要发射，中途出错，相当于捕获异常，保证后面的事件继续发射完毕。也就是不会调用观察者的onError(Throwable e) 方法
        LogUtil.log("====rxjavaTest_onErrorResumeNext()====::");
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("小明");
                emitter.onNext("小方");
                emitter.onError(new NullPointerException("error"));
                emitter.onNext("小红");
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {

            @Override
            public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {

                return Observable.just("1", "2", "3");
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

                LogUtil.log("准备监听");
            }

            @Override
            public void onNext(String s) {

                LogUtil.log(s);
            }

            @Override
            public void onError(Throwable e) {

                LogUtil.log(e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.log("onComplete");
            }
        });

    }

   /* private void rxjavaTest_onExceptionResumeNext(){
        //与 onErrorResumeNext() 作用基本一致，但是这个方法只能捕捉 Exception。
        LogUtil.log("====rxjavaTest_onErrorResumeNext()====::");
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("小明");
                emitter.onNext("小方");
                emitter.onNext("小红");
                emitter.onError(new Error("error"));
            }
        }).onExceptionResumeNext(new Observable<String>() {

            @Override
            protected void subscribeActual(Observer observer) {

                observer.onNext("小张");
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

                LogUtil.log(  "准备监听");
            }

            @Override
            public void onNext(String s) {

                LogUtil.log(  s);
            }

            @Override
            public void onError(Throwable e) {

                LogUtil.log(  e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.log(  "onComplete");
            }
        });

    }
*/

    private void rxjavaTest_retryUntil() {
        //出现错误事件之后，可以通过此方法判断是否继续发送事件。
        //什么意思？ 还是捕获异常，返回true/false 决定是否继续后面的事件发射
        LogUtil.log("====rxjavaTest_retryUntil()====::");
        Observable.create(new ObservableOnSubscribe<String>() {

            public void subscribe(@NonNull ObservableEmitter<String> emitter) {

                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onError(new NullPointerException("error"));
                emitter.onNext("4");
                emitter.onNext("5");
            }
        }).retryUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Exception {

                LogUtil.log("getAsBoolean");
                return true;
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {

                LogUtil.log(s);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }

    private void rxjavaTest() {
//        rxjavaTest_create();
//        rxjavaTest_just();
//        rxjavaTest_time();
//        rxjavaTest_interval();
//        rxjavaTest_intervalRange();
        rxjavaTest_range();
//        rxjavaTest_map();
//        rxjavaTest_concat();
//        rxjavaTest_merge();
//        rxjavaTest_doOnXxx();
//        rxjavaTest_onErrorReturn();
//        rxjavaTest_onErrorResumeNext();
//        rxjavaTest_retryUntil();

    }


    public static class RxjavaUiState extends UiStateHolder {

    }
}
