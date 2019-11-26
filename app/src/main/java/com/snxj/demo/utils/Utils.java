package com.snxj.demo.utils;

import android.os.Handler;
import android.os.Looper;


public class Utils {

    public static void fakeRequest(String url, final HttpCallBack callBack) {
        ///fake request
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.onOk();
            }
        }, 500);
    }

}
