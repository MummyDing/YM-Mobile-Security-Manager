package com.github.mummyding.ymsecurity.viewmodel;

import android.graphics.drawable.Drawable;

import com.github.mummyding.ymsecurity.util.FileTypeHelper;

/**
 * Created by dingqinying on 17/2/22.
 */

public class CacheGroupViewModel {

    private Drawable mDrawable;
    private String mName;
    private long mSize;
    private FileTypeHelper.FileType mFileType;


    public CacheGroupViewModel(FileTypeHelper.FileType fileType) {
        mFileType = fileType;
        mName = FileTypeHelper.getTypeName(fileType);
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

    public FileTypeHelper.FileType getFileType() {
        return mFileType;
    }
}
