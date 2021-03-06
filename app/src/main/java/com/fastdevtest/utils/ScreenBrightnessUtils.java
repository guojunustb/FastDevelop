
package com.fastdevtest.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager;

/**
 * 屏幕亮度相关的工具类
 */
public class ScreenBrightnessUtils {
    /**
     * 判断是否开启了自动亮度调节
     */

    public static boolean isAutoBrightness(ContentResolver aContentResolver) {

        boolean automicBrightness = false;

        try {

            automicBrightness = Settings.System.getInt(aContentResolver,

            Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }

    /**
     * 获取屏幕的亮度
     */

    public static int getScreenBrightness(Activity activity) {

        int nowBrightnessValue = 0;

        ContentResolver resolver = activity.getContentResolver();

        try {

            nowBrightnessValue = Settings.System.getInt(resolver,
                    Settings.System.SCREEN_BRIGHTNESS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nowBrightnessValue;
    }

    // 那如何修改屏幕的亮度呢？

    /**
     * 设置亮度
     */

    public static void setBrightness(Activity activity, int brightness) {

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
//        Trace.d("lxy", "set  lp.screenBrightness == " + lp.screenBrightness);

        activity.getWindow().setAttributes(lp);
    }

    // 那么，能设置了，但是为什么还是会出现，设置了，没反映呢？

    // 嘿嘿，那是因为，开启了自动调节功能了，那如何关闭呢？这才是最重要的：

    /**
     * 停止自动亮度调节
     */

    public static void stopAutoBrightness(Activity activity) {

        Settings.System.putInt(activity.getContentResolver(),

        Settings.System.SCREEN_BRIGHTNESS_MODE,

        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    // 能开启，那自然应该能关闭了哟哟，那怎么关闭呢？很简单的：

    /**
     * * 开启亮度自动调节 *
     *
     * @param activity
     */

    public static void startAutoBrightness(Activity activity) {

        Settings.System.putInt(activity.getContentResolver(),

        Settings.System.SCREEN_BRIGHTNESS_MODE,

        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

    }

    // 至此，应该说操作亮度的差不多都有了，结束！
    // 哎，本来认为是应该结束了，但是悲剧得是，既然像刚才那样设置的话，只能在当前的activity中有作用，一段退出的时候，会发现毫无作用，悲剧，原来是忘记了保存了。汗！

    /**
     * 保存亮度设置状态
     */

    public static void saveBrightness(ContentResolver resolver, int brightness) {

        Uri uri = Settings.System.getUriFor("screen_brightness");

        Settings.System.putInt(resolver, "screen_brightness", brightness);

        resolver.notifyChange(uri, null);
    }
}
