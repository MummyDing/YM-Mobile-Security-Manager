package com.github.mummyding.ymsecurity.viewmodel;

import android.graphics.drawable.Drawable;

/**
 * Created by dingqinying on 17/2/22.
 */

public class CacheGroupViewModel {

    private Drawable mDrawable;
    private String mName;
    private long mSize;

    public CacheGroupViewModel(String name, Drawable drawable) {
        this.mName = name;
        this.mDrawable = drawable;
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

    public void addSize(long size) {
        this.mSize += size;
    }
}
