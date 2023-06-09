package com.billy.android.soso.framwork.common.image;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Author：summer
 * Description：
 */
public class ImageOption {

    private int resourceId_placeholder;
    private int resourceId_error;
    private int resourceId_fallback;
    private boolean skipMemoryCache = false; //跳过内存缓存， appn内置的图片也有可能加载不进来
    private boolean onlyRetrieveFromCache = false;

    private ScaleType scaleType = ScaleType.CenterCrop;

    private CacheStrategy diskCacheStrategy = CacheStrategy.DiskCacheStrategy_ALL;

    public enum CacheStrategy {
        DiskCacheStrategy_ALL,
        DiskCacheStrategy_NONE,
        DiskCacheStrategy_DATA,
        DiskCacheStrategy_RESOURCE,
        DiskCacheStrategy_AUTOMATIC,
    }


    public enum ScaleType {
        CenterCrop,
        CircleCrop,//圆角
        FitCenter,
        CenterInside;


    }

    public ImageOption() {

    }


    public ImageOption scaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public ImageOption placeholder(@DrawableRes int resourceId) {
        this.resourceId_placeholder = resourceId;
        return this;

    }

    public ImageOption error(@DrawableRes int resourceId) {
        this.resourceId_error = resourceId;
        return this;
    }

    public ImageOption fallback(@DrawableRes int resourceId) {
        this.resourceId_fallback = resourceId;
        return this;
    }

    public ImageOption diskCacheStrategy(CacheStrategy strategy) {
        this.diskCacheStrategy = strategy;
        return this;
    }

    public ImageOption skipMemoryCache(boolean isSkip) {
        this.skipMemoryCache = isSkip;
        return this;
    }

    public ImageOption onlyRetrieveFromCache(boolean flag) {
        this.onlyRetrieveFromCache = flag;
        return this;
    }

    public static ImageOption cloneOption(ImageOption imageOption) {
        if (imageOption == null) {
            return defaultOption();
        }
        return imageOption;

    }

    public static ImageOption defaultOption() {
        return new ImageOption()
                .scaleType(ScaleType.CenterCrop)
                .placeholder(Config.Default_Placeholder)
                .error(Config.Default_Error)
                .fallback(Config.Default_Fallback)
                .skipMemoryCache(false)
                .onlyRetrieveFromCache(false)
                .diskCacheStrategy(CacheStrategy.DiskCacheStrategy_ALL);
    }


    /**
     * 转换为Glide 的RequestOptions
     *
     * @return
     */
    public RequestOptions applyOption() {
        RequestOptions options = new RequestOptions();
        switch (this.scaleType) {
            case CircleCrop:
                options.circleCrop();
                break;
            case FitCenter:
                options.fitCenter();
                break;
            case CenterInside:
                options.centerInside();
                break;
            default:
                options.centerCrop();
                break;
        }
        if (resourceId_placeholder > 0)
            options.placeholder(resourceId_placeholder);
        if (resourceId_error > 0)
            options.error(resourceId_error);
        if (resourceId_fallback > 0)
            options.fallback(resourceId_fallback);


        switch (this.diskCacheStrategy) {
            case DiskCacheStrategy_NONE:
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case DiskCacheStrategy_DATA:
                options.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case DiskCacheStrategy_RESOURCE:
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case DiskCacheStrategy_AUTOMATIC:
                options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }
        options.skipMemoryCache(skipMemoryCache);
        options.onlyRetrieveFromCache(onlyRetrieveFromCache);
        return options;
    }

}
