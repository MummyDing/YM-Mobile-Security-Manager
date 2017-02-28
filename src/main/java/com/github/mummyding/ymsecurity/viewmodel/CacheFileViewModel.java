package com.github.mummyding.ymsecurity.viewmodel;

import android.graphics.drawable.Drawable;

import com.github.mummyding.ymsecurity.model.FileInfoModel;

/**
 * Created by MummyDing on 2017/2/23.
 */

public class CacheFileViewModel {
    private Drawable mDrawable;
    private String mName;
    private long mSize;
    private boolean mChecked;

    public CacheFileViewModel(FileInfoModel fileInfo) {
        if (fileInfo != null) {
            mName = fileInfo.getFileName();
            mSize = fileInfo.getCacheSize();
        }
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public String getName() {
        return mName;
    }

    public long getSize() {
        return mSize;
    }

    public boolean isChecked() {
        return mChecked;
    }
}
