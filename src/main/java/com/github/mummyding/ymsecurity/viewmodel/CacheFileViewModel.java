package com.github.mummyding.ymsecurity.viewmodel;

import android.graphics.drawable.Drawable;

/**
 * Created by MummyDing on 2017/2/23.
 */

public class CacheFileViewModel {
    private Drawable mDrawable;
    private int mName;
    private long mSize;
    private boolean mChecked;

    public Drawable getDrawable() {
        return mDrawable;
    }

    public int getName() {
        return mName;
    }

    public long getSize() {
        return mSize;
    }

    public boolean isChecked() {
        return mChecked;
    }
}
