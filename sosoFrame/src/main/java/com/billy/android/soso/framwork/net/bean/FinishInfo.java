package com.billy.android.soso.framwork.net.bean;

/**
 * Author：summer
 * Description：
 */
public class FinishInfo {
    //仅指服务器返回的错误码
    //错误文案，网络错误、请求失败错误、服务器返回的错误等文案
    private int errorLevel=0;//0成功，或者即使错误也显示页面   1低级别
    private int resultCode;
    private String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public FinishInfo(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public FinishInfo() {
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    /**
     * 是否显示
     * @return
     */
    public  boolean isLowestLevel (){
        return errorLevel == 0;

    }


}
