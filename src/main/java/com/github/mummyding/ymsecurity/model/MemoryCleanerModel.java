package com.github.mummyding.ymsecurity.model;

import android.app.ActivityManager;
import android.text.TextUtils;

import com.github.mummyding.ymsecurity.util.MemoryCleaner;

/**
 * Created by MummyDing on 2017/1/29.
 */

public class MemoryCleanerModel {

    private String mPkgName;
    private long mMemoryCleaned;

    public MemoryCleanerModel(String pkgName, ActivityManager.MemoryInfo lastMemoryInfo, ActivityManager.MemoryInfo currentMemoryInfo) {
        if (TextUtils.isEmpty(pkgName) || lastMemoryInfo == null || currentMemoryInfo == null) {
            return;
        }
        mPkgName = pkgName;
        mMemoryCleaned = (currentMemoryInfo.availMem - lastMemoryInfo.availMem) / MemoryCleaner.MB;
    }

    public String getPkgName() {
        return mPkgName;
    }

    public long getMemoryCleaned() {
        return mMemoryCleaned;
    }
}
