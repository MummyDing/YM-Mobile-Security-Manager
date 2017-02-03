package com.github.mummyding.ymsecurity.model;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.github.mummyding.ymsecurity.util.MemoryCleaner;

/**
 * Created by MummyDing on 2017/1/29.
 */

public class MemoryCleanerModel {

    private String mPkgName;
    private String mAppName;
    private Drawable mAppLogo;
    private long mMemorySize;
    private boolean mNeedClean = true;

    public MemoryCleanerModel(ActivityManager activityManager, PackageManager packageManager, ActivityManager.RunningAppProcessInfo appProcessInfo) {
        if (activityManager == null || packageManager == null || appProcessInfo == null) {
            return;
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = packageManager.getApplicationInfo(appProcessInfo.processName, 0);
            String processName = appInfo.processName;
            if (processName.indexOf(":") == -1) {
                mPkgName = processName;
            } else {
                mPkgName = processName.split(":")[0];
            }
            mAppName = appInfo.loadLabel(packageManager).toString();
            mAppLogo = appInfo.loadIcon(packageManager);
            mMemorySize = activityManager.getProcessMemoryInfo(new int[]{appProcessInfo.pid})[0].getTotalPrivateDirty() * 1024;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getPkgName() {
        return mPkgName;
    }

    public long getMemoryCleaned() {
        return mMemorySize;
    }

    public String getAppName() {
        return mAppName;
    }

    public Drawable getAppLogo() {
        return mAppLogo;
    }

    public long getMemorySize() {
        return mMemorySize;
    }

    public boolean isNeedClean() {
        return mNeedClean;
    }

    public void setNeedClean(boolean needClean) {
        this.mNeedClean = needClean;
    }

    public boolean check() {
        if (TextUtils.isEmpty(mAppName)) {
            return false;
        }
        return true;
    }
}
