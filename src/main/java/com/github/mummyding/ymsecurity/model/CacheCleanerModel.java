package com.github.mummyding.ymsecurity.model;

import com.github.mummyding.ymsecurity.util.FileTypeHelper;

import java.io.File;

/**
 * Created by MummyDing on 2017/1/31.
 */

public class CacheCleanerModel {

    private FileTypeHelper.FileType mFileType;
    private String mFileName;
    private String mFilePath;

    public CacheCleanerModel(FileTypeHelper.FileType type, File rootFile) {
        this.mFileType = type;
        mFileName = rootFile.getName();
        mFilePath = rootFile.getAbsolutePath();
    }

    public FileTypeHelper.FileType getFileType() {
        return mFileType;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getFilePath() {
        return mFilePath;
    }
}
