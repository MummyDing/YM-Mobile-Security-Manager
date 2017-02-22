package com.github.mummyding.ymsecurity.model;

import com.github.mummyding.ymsecurity.util.FileTypeHelper;
import com.github.mummyding.ymsecurity.util.FileUtil;

import java.io.File;

/**
 * Created by MummyDing on 2017/1/31.
 */

public class FileInfoModel {

    private FileTypeHelper.FileType mFileType;
    private String mFileName;
    private String mFilePath;
    private long mCacheSize;

    public FileInfoModel(FileTypeHelper.FileType type, File rootFile) {
        this.mFileType = type;
        mFileName = rootFile.getName();
        mFilePath = rootFile.getAbsolutePath();
        mCacheSize = FileUtil.getFileSize(rootFile);
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

    public long getCacheSize() {
        return mCacheSize;
    }
}
