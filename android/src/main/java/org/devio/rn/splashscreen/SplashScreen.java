package org.devio.rn.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
    private static Dialog mSplashDialog;
    private static WeakReference<Activity> mActivity;

    public static void show(final Activity activity, final int themeResId, final boolean hideUI) {
      if (activity == null) return;
      mActivity = new WeakReference<Activity>(activity);
      activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
              if (!activity.isFinishing()) {
                  mSplashDialog = new Dialog(activity, themeResId);
                  mSplashDialog.setContentView(R.layout.launch_screen);
                  mSplashDialog.setCancelable(false);

                  if (!mSplashDialog.isShowing()) {
                      mSplashDialog.show();
                  }

                  if (hideUI) {
                      mSplashDialog.getWindow().getDecorView().setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                              | View.SYSTEM_UI_FLAG_FULLSCREEN
                              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                  }
              }
          }
      });
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final boolean fullScreen, final boolean hideUI) {
      int resourceId = fullScreen ? R.style.SplashScreen_Fullscreen : R.style.SplashScreen_SplashTheme;
      show(activity, resourceId, hideUI);
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity) {
      show(activity, false, false);
    }

    /**
     * 关闭启动屏
     */
    public static void hide(Activity activity) {
        if (activity == null) {
            if (mActivity == null) {
                return;
            }
            activity = mActivity.get();
        }

        if (activity == null) return;

        final Activity _activity = activity;

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog != null && mSplashDialog.isShowing()) {
                    boolean isDestroyed = false;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        isDestroyed = _activity.isDestroyed();
                    }

                    if (!_activity.isFinishing() && !isDestroyed) {
                        mSplashDialog.dismiss();
                    }
                    mSplashDialog = null;
                }
            }
        });
    }
}
