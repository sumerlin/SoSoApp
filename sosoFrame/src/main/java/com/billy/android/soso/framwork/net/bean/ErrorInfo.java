package com.billy.android.soso.framwork.net.bean;

import android.text.TextUtils;

import com.billy.android.soso.framwork.BaseApplication;
import com.billy.android.soso.framwork.net.exception.ExceptionHelper;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;

public class ErrorInfo {
    private final static String network_error = "当前无网络，请检查你的网络设置";
    private final static String notify_no_network = "网络连接不可用，请稍后重试！";
    private final static String esky_service_exception = "网络不给力，请稍候重试！";
    private final static String time_out_please_try_again_later = "连接超时,请稍后再试";
    private int errorCode;  //仅指服务器返回的错误码
    private String errorMsg; //错误文案，网络错误、请求失败错误、服务器返回的错误等文案
    private Throwable throwable; //异常信息

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Throwable getThrowable() {
        return throwable;
    }


    public ErrorInfo(Throwable throwable) {
        this.throwable = throwable;
        //先判断拿 错误码
//        String errorCode = throwable.getLocalizedMessage();
//        if (errorCode != null) {
//            this.errorCode = Integer.parseInt(errorCode);
//        }
        String errorMsg = null;
        if (throwable instanceof UnknownHostException) {
            if (!ExceptionHelper.isNetworkConnected(BaseApplication.getInstance())) {
                errorMsg = ErrorInfo.network_error;
            } else {
                errorMsg = ErrorInfo.notify_no_network;
            }
        } else if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
            //前者是通过OkHttpClient设置的超时引发的异常，后者是对单个请求调用timeout方法引发的超时异常
            errorMsg = ErrorInfo.time_out_please_try_again_later;
        } else if (throwable instanceof ConnectException) {
            errorMsg = ErrorInfo.esky_service_exception;
        } else if (throwable instanceof HttpStatusCodeException) { //请求失败异常
            //Http 状态码 code < 200 || code >= 300, 抛出此异常
            String code = throwable.getLocalizedMessage();
            if ("416".equals(code)) {
                errorMsg = "请求范围不符合要求";
            } else if("404".equals(code)){
                errorMsg= "服务器异常，请稍后再试";
            } else if("408".equals(code)){
                errorMsg= "请求超时";
            }
            else {
                errorMsg = throwable.getMessage();
            }
        } else if (throwable instanceof JsonSyntaxException) { //请求成功，但Json语法异常,导致解析失败
            errorMsg = "数据解析失败,请稍后再试";
        } else if (throwable instanceof ParseException) { // ParseException异常表明请求成功，但是数据不正确
            String errorCode = throwable.getLocalizedMessage();
            if (errorCode != null) {
                this.errorCode = Integer.parseInt(errorCode);
            }
            errorMsg = throwable.getMessage();
            if (TextUtils.isEmpty(errorMsg)) errorMsg = errorCode;//errorMsg为空，显示errorCode
        } else {
            errorMsg = throwable.getMessage();
        }
        this.errorMsg = errorMsg;
    }

}
