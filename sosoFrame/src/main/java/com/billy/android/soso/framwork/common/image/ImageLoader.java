package com.billy.android.soso.framwork.common.image;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.billy.android.soso.framwork.R;
import com.billy.android.soso.framwork.common.image.transformation.BlurTransformation;
import com.billy.android.soso.framwork.common.image.transformation.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * Author：summer
 * Description：
 */
public class ImageLoader {
    private final ImageBuilder mBuilder;

    public static void init(Config.Builder builder) {
        Config.build(builder);
    }

    public ImageLoader(ImageBuilder builder) {
        this.mBuilder = builder;
    }

    /**
     * 图片加载器，入口
     *
     * @param context
     * @return
     */
    public static ImageBuilder with(Object context) {
        return new ImageBuilder(context);
    }

    /**
     * 配置基本参数
     */
    public static class ImageBuilder {

        private final Object context;
        private ImageView imageView;
        private Object source;

        private ImageOption imageOption;

        public ImageBuilder(Object context) {
            this.context = context;
        }


        public ImageBuilder load(Object source) {
            this.source = source;
            return this;
        }

        public ImageBuilder apply(ImageOption imageOption) {
            this.imageOption = imageOption;
            return this;

        }

        public ImageLoader into(@Nullable ImageView imageView) {
            this.imageView = imageView;
            return new ImageLoader(this);
        }

    }

    public void show() {
        RequestOptions options = ImageOption
                .cloneOption(mBuilder.imageOption)
                .applyOption();
        doGlide(options);

    }

    public void showBlur(int blurLevel) {
        RequestOptions options = ImageOption
                .cloneOption(mBuilder.imageOption)
                .applyOption()
                .transform(new MultiTransformation<>(new CenterCrop(), new BlurTransformation(blurLevel)));
        doGlide(options);
    }

    public void showRadius(int radius) {
        RequestOptions options = ImageOption
                .cloneOption(mBuilder.imageOption)
                .applyOption()
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCornersTransformation(radius, 0)));
        doGlide(options);
    }


    public void showCircle() {
        //圆形直接使用 RequestOptions.circleCrop().
        RequestOptions options = ImageOption
                .cloneOption(mBuilder.imageOption)
                .scaleType(ImageOption.ScaleType.CircleCrop)
                .applyOption();
        doGlide(options);

    }


    /**
     * glide 执行加载
     *
     * @param options
     */
    public void doGlide(RequestOptions options) {
        RequestManager requestManager = null;
        if (mBuilder.context instanceof Context)
            requestManager = Glide.with((Context) mBuilder.context);

        if (mBuilder.context instanceof Fragment)
            requestManager =  Glide.with((Fragment) mBuilder.context);

        if (mBuilder.context instanceof FragmentActivity)
            requestManager =    Glide.with((FragmentActivity) mBuilder.context);

        if (mBuilder.context instanceof View)
            requestManager =  Glide.with((View) mBuilder.context);

        if (requestManager != null)
            requestManager
                    .load(mBuilder.source)
                    .apply(options)
                    .into(mBuilder.imageView);

    }


}
