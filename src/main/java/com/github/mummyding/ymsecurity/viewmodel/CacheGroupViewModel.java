package com.github.mummyding.ymsecurity.viewmodel;

import android.graphics.drawable.Drawable;

import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;

/**
 * Created by dingqinying on 17/2/22.
 */

public class CacheGroupViewModel {

    private Drawable mDrawable;
    private String mName;
    private long mSize;


    public CacheGroupViewModel(FileTypeHelper.FileType fileType) {
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
        mSize += size;
    }
}
