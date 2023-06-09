package com.billy.android.soso.framwork.common.image;

import androidx.annotation.DrawableRes;

import com.billy.android.soso.framwork.R;


/**
 * Author：summer
 * Description：
 */
public class Config {
    @DrawableRes
    public static int Default_Placeholder = R.drawable.ic_launcher_background;
    @DrawableRes
    public static int Default_Error = R.drawable.ic_launcher_background;
    @DrawableRes
    public static int Default_Fallback = R.drawable.ic_launcher_background;

    public static void build(Builder builder) {
        Config.Default_Placeholder = builder.placeholder;
        Config.Default_Error = builder.error;
        Config.Default_Fallback = builder.fallback;
    }


    public static class Builder {

        private int placeholder;
        private int error;
        private int fallback;

        public Builder placeholder(@DrawableRes int resourceId) {
            this.placeholder = resourceId;
            return this;

        }

        public Builder error(@DrawableRes int resourceId) {
            this.error = resourceId;
            return this;
        }

        public Builder fallback(@DrawableRes int resourceId) {
            this.fallback = resourceId;
            return this;
        }

    }

}
