/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.android.spear.request;

import android.graphics.drawable.BitmapDrawable;

import me.xiaopan.android.spear.display.ImageDisplayer;
import me.xiaopan.android.spear.process.ImageProcessor;
import me.xiaopan.android.spear.util.DrawableHolder;
import me.xiaopan.android.spear.util.FailureCause;
import me.xiaopan.android.spear.util.ImageViewHolder;

/**
 * 显示请求
 */
public class DisplayRequest extends LoadRequest{
    public static final boolean DEFAULT_ENABLE_MEMORY_CACHE = true;

    private String id;	//ID
    private boolean enableMemoryCache = DEFAULT_ENABLE_MEMORY_CACHE;	//是否每次加载图片的时候先从内存中去找，并且加载完成后将图片缓存在内存中
    private DrawableHolder loadFailDrawableHolder;	//当加载失败时显示的图片
    private ImageDisplayer imageDisplayer;	//图片显示器
    private ImageViewHolder imageViewHolder;	//ImageView持有器

    private DisplayListener displayListener;	//监听器
    private ProgressListener displayProgressListener; // 显示进度监听器

    // Results
    private BitmapDrawable bitmapDrawable;
    private FailureCause failureCause;
    private DisplayListener.From from;

    private boolean resizeByImageViewLayoutSizeAndFromDisplayer;

    /**
     * 获取ID，此ID用来在内存缓存Bitmap时作为其KEY
     * @return ID
     */
	public String getId() {
		return id;
	}

    /**
     * 获取显示监听器
     * @return 显示监听器
     */
	public DisplayListener getDisplayListener() {
		return displayListener;
	}

    /**
     * 获取ImageView持有器
     * @return ImageView持有器
     */
	public ImageViewHolder getImageViewHolder() {
		return imageViewHolder;
	}

    /**
     * 是否开启内存缓存
     * @return 是否开启内存缓存
     */
    public boolean isEnableMemoryCache() {
        return enableMemoryCache;
    }

    /**
     * 获取图片显示器用于在图片加载完成后显示图片
     * @return 图片显示器
     */
    public ImageDisplayer getImageDisplayer() {
        return imageDisplayer;
    }

    /**
     * 获取加载失败时显示的图片
     * @return 加载失败时显示的图片
     */
    public BitmapDrawable getLoadFailDrawable() {
        if(loadFailDrawableHolder == null){
            return null;
        }
        ImageProcessor processor;
        if(getImageProcessor() != null){
            processor = getImageProcessor();
        }else if(getResize() != null){
          processor = getSpear().getConfiguration().getDefaultCutImageProcessor();
        }else{
            processor = null;
        }
        return loadFailDrawableHolder.getDrawable(getSpear().getConfiguration().getContext(), getResize(), getScaleType(), processor, resizeByImageViewLayoutSizeAndFromDisplayer);
    }

    @Override
    public boolean isCanceled() {
        boolean isCanceled = super.isCanceled();
        if(!isCanceled){
            isCanceled = imageViewHolder != null && imageViewHolder.isCollected();
            if(isCanceled){
                setStatus(Status.CANCELED);
            }
        }
        return isCanceled;
    }

    /**
     * 获取显示进度监听器
     * @return 显示进度监听器
     */
    public ProgressListener getDisplayProgressListener() {
        return displayProgressListener;
    }

    public BitmapDrawable getBitmapDrawable() {
        return bitmapDrawable;
    }

    public void setBitmapDrawable(BitmapDrawable bitmapDrawable) {
        this.bitmapDrawable = bitmapDrawable;
    }

    public FailureCause getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(FailureCause failureCause) {
        this.failureCause = failureCause;
    }

    public DisplayListener.From getFrom() {
        return from;
    }

    public void setFrom(DisplayListener.From from) {
        this.from = from;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEnableMemoryCache(boolean enableMemoryCache) {
        this.enableMemoryCache = enableMemoryCache;
    }

    public DrawableHolder getLoadFailDrawableHolder() {
        return loadFailDrawableHolder;
    }

    public void setLoadFailDrawableHolder(DrawableHolder loadFailDrawableHolder) {
        this.loadFailDrawableHolder = loadFailDrawableHolder;
    }

    public void setImageDisplayer(ImageDisplayer imageDisplayer) {
        this.imageDisplayer = imageDisplayer;
    }

    public void setImageViewHolder(ImageViewHolder imageViewHolder) {
        this.imageViewHolder = imageViewHolder;
    }

    public void setDisplayListener(DisplayListener displayListener) {
        this.displayListener = displayListener;
    }

    public void setDisplayProgressListener(ProgressListener displayProgressListener) {
        this.displayProgressListener = displayProgressListener;
    }

    public void setResizeByImageViewLayoutSizeAndFromDisplayer(boolean resizeByImageViewLayoutSizeAndFromDisplayer) {
        this.resizeByImageViewLayoutSizeAndFromDisplayer = resizeByImageViewLayoutSizeAndFromDisplayer;
    }
}
