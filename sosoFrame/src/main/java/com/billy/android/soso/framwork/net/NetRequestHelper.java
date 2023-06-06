package com.billy.android.soso.framwork.net;


public class NetRequestHelper {

//    public NetRequestHelper(String url, Object... formatArgs) {
//        super(url, formatArgs);
//    }

    public static <T> NetBuilder<T> get(String url, Object... formatArgs) {
        return NetBuilder.createBuilder(NetBuilder.GET,url,formatArgs);
    }


    /**
     * 默认使用 post 表达提交， k=v & k2=v2   这种形式
     * @param url
     * @param formatArgs
     * @return
     * @param <T>
     */
    public static <T> NetBuilder<T> post(String url, Object... formatArgs) {
        return NetBuilder.createBuilder(NetBuilder.POST_FORM,url,formatArgs);
    }

    /**
     * 默认使用 post 表达提交， {k:v ,k2:v2}   这种形式
     * @param url
     * @param formatArgs
     * @return
     * @param <T>
     */
    public static <T> NetBuilder<T>  postJson(String url, Object... formatArgs){
        return NetBuilder.createBuilder(NetBuilder.POST_JSON,url,formatArgs);
    }



}
