package com.github.mummyding.ymsecurity.model;

import android.graphics.drawable.Drawable;

/**
 * Created by MummyDing on 2017/2/3.
 */

public class ApkInfoModel {

    private String mApkPath;
    private String mPkgName;
    private String mAppName;
    private int mVersionCode;
    private String mVersionName;
    private Drawable mIcon;
    private long mSize;

    public ApkInfoModel(String apkPath, String pkgName, String appName, int versionCode, String versionName, Drawable icon) {
        this.mApkPath = apkPath;
        this.mPkgName = pkgName;
        this.mAppName = appName;
        this.mVersionCode = versionCode;
        this.mVersionName = versionName;
        this.mIcon = icon;
    }

    public void setSize(long size) {
        this.mSize = size;
    }

    public String getApkPath() {
        return mApkPath;
    }

    public String getPkgName() {
        return mPkgName;
    }

    public String getAppName() {
        return mAppName;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public long getSize() {
        return mSize;
    }
}
