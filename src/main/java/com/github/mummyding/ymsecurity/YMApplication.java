package com.github.mummyding.ymsecurity;

import android.app.Application;
import android.content.Context;

/**
 * Created by MummyDing on 2017/2/3.
 */

public class YMApplication extends Application {

    private static Context sApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = getApplicationContext();
    }

    public static Context getContext() {
        return sApplicationContext;
    }
}
