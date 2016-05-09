
package com.fastdevtest.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 应用相关的工具类 Created by guojun on 16/4/14 21:29.
 */
public class AppUtils {
    /**
     * 获取图片(Pictures)的路径,如果没有直接返加File dir
     * 
     * @return picture path
     */
    public static String getPicturePath(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return context.getFilesDir().getAbsolutePath();
        File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file != null)
            return file.getAbsolutePath();
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (file != null)
            return file.getAbsolutePath();
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取下载(Download文件夹)的路径,如果没有直接返加File dir
     *
     * @return picture path
     */
    public static String getDownloadPath(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return context.getFilesDir().getAbsolutePath();
        File file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (file != null)
            return file.getAbsolutePath();
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (file != null)
            return file.getAbsolutePath();
        return context.getFilesDir().getAbsolutePath();
    }
}
