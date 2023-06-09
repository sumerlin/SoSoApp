package com.billy.android.soso.app;

import androidx.viewbinding.BuildConfig;

import com.billy.android.soso.app.configs.NetHttpManager;
import com.billy.android.soso.framwork.BaseApplication;
import com.billy.android.soso.framwork.common.image.Config;
import com.billy.android.soso.framwork.common.image.ImageLoader;
import com.billy.android.soso.framwork.view.status.Gloading;
import com.billy.android.soso.framwork.view.status.GlobalAdapter;

public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        NetConfig();
        UiViewStatusConfig();
        ImageConfig();

    }

    private void NetConfig() {
        NetHttpManager.init(this);
    }

    private void UiViewStatusConfig() {
        Gloading.debug(BuildConfig.DEBUG);
        //注册一个状态view， 每当Gloading.wrap 方法会给页面添加状态view
        //页面注册之后，会返回Gloading.Holder ，管理页面的状态，挂载自定义view，以及切换逻辑，以及失败重试回调， 都是在该类完成
        //Gloading.Holder 对外以提供加载成功、加载是把你、空页面、重试方法给activity或者 fragment外者调用
        //所以核心逻辑还是在Gloading.Holder，  提供Adapter 是给了不同需求添加不一样的页面
        Gloading.initDefault(new GlobalAdapter());
    }

    public void ImageConfig() {
        Config.Builder builder = new Config.Builder()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background);
        ImageLoader.init(builder);
    }
}
