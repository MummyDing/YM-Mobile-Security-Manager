package com.github.mummyding.ymsecurity.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.github.mummyding.ymsecurity.YMApplication;
import com.github.mummyding.ymsecurity.model.ApkInfoModel;

/**
 * Created by MummyDing on 2017/2/3.
 */

public class ApkUtil {

    private static Context sContext;
    private static PackageManager sPackageManager;
    public static ApkInfoModel getApkInfo(String path) {
        if (sContext == null) {
            sContext = YMApplication.getContext();
        }
        if (sPackageManager == null) {
            sPackageManager = sContext.getPackageManager();
        }
        PackageInfo packageInfo = sPackageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        ApkInfoModel model = null;
        if (packageInfo != null) {
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            String appName = sPackageManager.getApplicationLabel(appInfo).toString();
            String pkgName = appInfo.packageName;
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            Drawable icon = sPackageManager.getApplicationIcon(appInfo);
            model = new ApkInfoModel(path, pkgName, appName, versionCode, versionName, icon);
        }
        return model;
    }

}
