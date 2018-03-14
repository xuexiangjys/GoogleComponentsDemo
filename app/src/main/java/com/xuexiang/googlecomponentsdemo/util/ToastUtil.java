package com.xuexiang.googlecomponentsdemo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.xuexiang.googlecomponentsdemo.DemoApp;

/**
 * 管理toast的类，整个app用该类来显示toast
 */
public final class ToastUtil {

    private static Toast sToast = null;
    private static Context sContext;

    private static Handler sMainThreadHandler;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
        sMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    /**
     *
     * @param text 提示信息
     * @param duration 提示长度
     */
    public static void showToast(String text, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(sContext, text, duration);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    public static void showToast(final String text) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToast(text, Toast.LENGTH_SHORT);
        } else {
            sMainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    showToast(text, Toast.LENGTH_SHORT);
                }
            });
        }

    }

    public static void showToast(int resId) {
        showToast(sContext.getResources().getString(resId));
    }

    public void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

}
