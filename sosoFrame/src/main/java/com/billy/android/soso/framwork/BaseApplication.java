package com.billy.android.soso.framwork;

import android.app.Application;

public class BaseApplication extends Application {

    private static Application instance;

    public static Application getInstance() {
        return instance;
    }
}
