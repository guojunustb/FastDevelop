
package com.fastdevtest;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.fastdevtest.utils.AppUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.ButterKnife;

/**
 * Base Application init . Created by guojun on 16/4/6 22:35.
 */
public class MyApplication extends Application {
    public static final boolean DEBUG = true;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Logger
        Logger.init().hideThreadInfo().setLogLevel(DEBUG ? LogLevel.FULL : LogLevel.NONE);
        ButterKnife.setDebug(DEBUG);
        initFresco();
    }

    /**
     * 初始化Fresco
     */
    private void initFresco() {
        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };
        final ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(this)
                .setProgressiveJpegConfig(pjpegConfig)
                .setNetworkFetcher(new HttpUrlConnectionNetworkFetcher())
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(this).setBaseDirectoryName("image_cache")
                                .setBaseDirectoryPath(new File(AppUtils.getPicturePath(this)))
                                .setMaxCacheSize(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnLowDiskSpace(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnVeryLowDiskSpace(20 * ByteConstants.MB).build())
                .build();
        Fresco.initialize(this, config);
    }
}
