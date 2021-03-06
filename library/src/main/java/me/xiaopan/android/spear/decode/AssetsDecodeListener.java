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

package me.xiaopan.android.spear.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import me.xiaopan.android.spear.Spear;
import me.xiaopan.android.spear.request.LoadRequest;

public class AssetsDecodeListener implements ImageDecoder.DecodeListener {
    private static final String NAME = AssetsDecodeListener.class.getSimpleName();
	private String assetsFilePath;
    private LoadRequest loadRequest;
	
	public AssetsDecodeListener(String assetsFilePath, LoadRequest loadRequest) {
		this.assetsFilePath = assetsFilePath;
		this.loadRequest = loadRequest;
	}

    @Override
    public Bitmap onDecode(BitmapFactory.Options options) {
        InputStream inputStream = null;
        try {
            inputStream = loadRequest.getSpear().getConfiguration().getContext().getAssets().open(assetsFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        if(inputStream != null){
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    @Override
    public void onDecodeSuccess(Bitmap bitmap, Point originalSize, int inSampleSize) {
        StringBuilder stringBuilder = new StringBuilder(NAME)
        .append("；").append("解码成功");
        if(bitmap != null && loadRequest.getMaxsize() != null){
            stringBuilder.append("；").append("原始尺寸").append("=").append(originalSize.x).append("x").append(originalSize.y);
            stringBuilder.append("；").append("目标尺寸").append("=").append(loadRequest.getMaxsize().getWidth()).append("x").append(loadRequest.getMaxsize().getHeight());
            stringBuilder.append("；").append("缩放比例").append("=").append(inSampleSize);
            stringBuilder.append("；").append("最终尺寸").append("=").append(bitmap.getWidth()).append("x").append(bitmap.getHeight());
        }else{
        	stringBuilder.append("；").append("未缩放");
        }
        stringBuilder.append("；").append(loadRequest.getName());
        Log.d(Spear.LOG_TAG, stringBuilder.toString());
    }

    @Override
    public void onDecodeFailure() {
        if(Spear.isDebugMode()){
        	Log.e(Spear.LOG_TAG, NAME + "；" + "解码失败" + "；" + assetsFilePath);
        }
    }
}
