package com.billy.android.soso.framwork.common.image;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.billy.android.soso.framwork.common.image.transformation.BlurTransformation;
import com.billy.android.soso.framwork.common.image.transformation.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * Author：summer
 * Description：
 */
public class ImageLoader {
    private ImageBuilder mBuilder;

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
    public static ImageBuilder with(Context context) {
        return new ImageBuilder(context);
    }

    /**
     * 配置基本参数
     */
    public static class ImageBuilder {

        private Context context;
        private ImageView imageView;
        private Object source;

        private ImageOption imageOption;

        public ImageBuilder(Context context) {
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
        Glide.with(mBuilder.context)
                .load(mBuilder.source)
                .apply(options)
                .into(mBuilder.imageView);

    }


}
