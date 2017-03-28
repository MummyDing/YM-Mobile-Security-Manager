package com.github.mummyding.ymsecurity.viewmodel;

import android.graphics.drawable.Drawable;

import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;

/**
 * Created by MummyDing on 2017/2/23.
 */

public class CacheFileViewModel {

    private FileTypeHelper.FileType mFileType;
    private String mName;
    private long mSize;
    private boolean mChecked;
    private String mPath;

    public CacheFileViewModel(FileInfoModel fileInfo) {
        if (fileInfo != null) {
            mFileType = fileInfo.getFileType();
            mName = fileInfo.getFileName();
            mSize = fileInfo.getCacheSize();
            mPath = fileInfo.getFilePath();
            mChecked = true;
        }
    }

    public FileTypeHelper.FileType getFileType() {
        return mFileType;
    }

    public String getFilePath() {
        return mPath;
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

    public void setChecked(boolean isChecked) {
        this.mChecked = isChecked;
    }
}
