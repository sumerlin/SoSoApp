package com.billy.android.soso.app.ui.bind;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.billy.android.soso.framwork.common.image.ImageLoader;

/**
 * Author：summer
 * Description：
 */

public class ImageBindingAdapter {
    @BindingAdapter(value = {"url", "blurLevel", "radius", "circle"}, requireAll = false)
    public static void show(ImageView view, String url, int blurLevel, int radius, boolean circle) {

        if (blurLevel == 0 && radius == 0 && !circle)
            ImageLoader
                    .with(view)
                    .load(url)
                    .into(view)
                    .show();


        if (blurLevel > 0)
            ImageLoader
                    .with(view)
                    .load(url)
                    .into(view)
                    .showBlur(blurLevel);

        if (radius > 0)
            ImageLoader
                    .with(view)
                    .load(url)
                    .into(view)
                    .showRadius(radius);

        if (circle)
            ImageLoader
                    .with(view)
                    .load(url)
                    .into(view)
                    .showCircle();


    }
}
