package com.billy.android.soso.framwork.net;

import android.util.ArrayMap;


import com.billy.android.soso.framwork.net.callback.NetObserver;
import com.billy.android.soso.framwork.net.callback.NetObserverWrapper;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import rxhttp.wrapper.param.ObservableCall;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.param.RxHttpFormParam;
import rxhttp.wrapper.param.RxHttpJsonParam;
import rxhttp.wrapper.param.RxHttpNoBodyParam;

public class NetBuilder<T> {
    private String url;
    private Class<T> type;

    private Object[] formatArgs;

    private String netMethod;
    public final static String GET = "GET";
    public final static String POST_FORM = "POST_FORM";
    public final static String POST_JSON = "GET_JSON";

    public static <T> NetBuilder<T> createBuilder(String netMethod, String url, Object... formatArgs) {
        return new NetBuilder<>(netMethod, url, formatArgs);
    }

    public NetBuilder(String netMethod, String url, Object... formatArgs) {
        this.netMethod = netMethod;
        this.url = url;
        this.formatArgs = formatArgs;
    }


    public NetBuilder<T> toObservableResult(Class<T> type) {
        this.type = type;
        return this;
    }


    private Map<String, String> headers;
    private Map<String, Object > params;


    public NetBuilder<T> addHeader(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public NetBuilder<T> addParams(Map<String, Object > params) {
        if(this.params==null){
            this.params = new ArrayMap<String,Object>();
        }
        this.params.putAll(params);
        return this;
    }

    public NetBuilder<T> addParam(String key,Object param){
        if(params==null){
           this.params = new ArrayMap<String,Object>();
        }
        params.put(key,param);
        return  this;
    }


    public final static int MAIN_THREAD = 1;
    public final static int IO_THREAD = 2;

    /**
     *
     */
    private int schedulerFlag = MAIN_THREAD;

    public NetBuilder<T> observeOn(int schedulerFlag) {
        this.schedulerFlag = schedulerFlag;
        return this;
    }

    /**
     * RxHttp默认在IO线程执行请求，也默认在IO线程回调，通过以下操作，我们可以指定请求和回调所在线程。
     * 修改成，默认在main h回调。
     * @return
     */
    private Scheduler switchScheduler() {
        switch (schedulerFlag) {
            case MAIN_THREAD:
                return AndroidSchedulers.mainThread();
            case IO_THREAD:
                return Schedulers.io();

        }
        return AndroidSchedulers.mainThread();
    }

    public void subscribe(@NonNull Observer<T> observer) {
        RxHttp rxHttp;
        switch (netMethod) {
            case GET:
                rxHttp = RxHttp.get(url, formatArgs);
//                RxHttpNoBodyParam   rxHttp = RxHttp.get(url, params);
//                rxHttp.addAllHeader(headers);
//                observableCall =  rxHttp.toObservableResult(type);
                break;
            case POST_FORM:
                rxHttp = RxHttp.postForm(url, formatArgs);
//                RxHttpFormParam rxHttp2 = RxHttp.postForm(url, params);
//                observableCall = rxHttp2.addAllHeader(headers).toObservable(type);
                break;
            case POST_JSON:
                rxHttp = RxHttp.postJson(url, formatArgs);
//                RxHttpJsonParam rxHttp3 = RxHttp.postJson(url, params);
//                observableCall = rxHttp3.addAllHeader(headers).toObservable(type);
                break;

            default:
                rxHttp = RxHttp.get(url, formatArgs);
//                RxHttpNoBodyParam   rxHttpGet = RxHttp.get(url, params);
//                rxHttpGet.addAllHeader(headers);
//                observableCall =  rxHttpGet.toObservableResult(type);
        }
        //1请求方法判断, addAllQuery 都是是追加在链接后面， addAll 分不同情况， get就是追加在链接后面， post 就不是
        if (headers != null && headers.size() > 0) {
            rxHttp.addAllHeader(headers);
        }
        if (params != null && params.size() > 0) {

            if(rxHttp instanceof  RxHttpNoBodyParam)
                ((RxHttpNoBodyParam)rxHttp).addAllQuery(params);

            if(rxHttp instanceof RxHttpFormParam)
                ( (RxHttpFormParam)(rxHttp)).addAll(params);
            if(rxHttp instanceof RxHttpJsonParam)
                ( (RxHttpJsonParam)(rxHttp)).addAll(params);
        }
        //2配置不同请求参数
        rxHttp.toObservableResult(type)
                .observeOn(switchScheduler())
                .subscribe(observer);

    }

    public void subscribeWrapper(@NonNull NetObserverWrapper<T> observer) {
        subscribe(observer);
    }





}
