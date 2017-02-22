package com.github.mummyding.ymsecurity.util;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.github.mummyding.ymsecurity.model.FileInfoModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by MummyDing on 2017/1/30.
 * 1. 垃圾查找
 * 2. 文件删除
 * 3. 权限获取&检查
 */

public class CacheScanner {

    private static final String TAG = "CacheScanner";
    private static CacheScanner sCacheScanner;
    private ScanCacheAsyncTask mScanCacheAsyncTask;
    private int mScanLevel = 4;
    private Set<ScanCacheListener> mScanCacheListeners = new HashSet<>();

    public interface ScanCacheListener {
        void onStateChanged(FileInfoModel fileInfoModel);

        void onFinish(boolean success);
    }

    private CacheScanner() {
    }

    public static CacheScanner getInstance() {
        if (sCacheScanner == null) {
            sCacheScanner = new CacheScanner();
        }
        return sCacheScanner;
    }

    public void addScanCacheListener(ScanCacheListener listener) {
        if (listener != null) {
            mScanCacheListeners.add(listener);
        }
    }

    public void removeScanCacheListener(ScanCacheListener listener) {
        if (listener != null) {
            mScanCacheListeners.remove(listener);
        }
    }

    public boolean scanCache(String rootPath, int scanLevel) {
        mScanLevel = scanLevel;
        return scanCache(rootPath);
    }

    public boolean scanCache(String rootPath) {
        if (mScanCacheAsyncTask != null && mScanCacheAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Log.d(TAG, "ScanCacheAsyncTask is business");
            return false;
        }
        mScanCacheAsyncTask = new ScanCacheAsyncTask(rootPath);
        mScanCacheAsyncTask.execute();
        return true;
    }

    public boolean cancelScanCache() {
        if (mScanCacheAsyncTask == null || mScanCacheAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            return false;
        }
        mScanCacheAsyncTask.cancel(true);
        return true;
    }

    private class ScanCacheAsyncTask extends AsyncTask<Void, FileInfoModel, Boolean> {

        private String mRootPath;
        private boolean mIsScanStop;

        public ScanCacheAsyncTask(String rootPath) {
            this.mRootPath = rootPath;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!FileUtil.isValidPath(mRootPath)) {
                mIsScanStop = true;
                notifyScanCacheFinish(false);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (mIsScanStop) {
                return false;
            }
            return scanCache(mRootPath, mScanLevel);
        }

        @Override
        protected void onProgressUpdate(FileInfoModel... values) {
            super.onProgressUpdate(values);
            if (values != null || values.length != 0) {
                notifyScanCacheStateChanged(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Boolean o) {
            super.onPostExecute(o);
            notifyScanCacheFinish(true);
        }

        private Boolean scanCache(String rootPath, int depth) {
            List<FileInfoModel> fileInfoModelList = new LinkedList<>();
            if (depth < 0) {
                return false;
            }
            if (TextUtils.isEmpty(rootPath)) {
                return false;
            }
            File rootFile = new File(rootPath);
            if (!FileUtil.isExist(rootFile)) {
                return false;
            }
            if (rootFile.isFile()) {
                FileTypeHelper.FileType fileType = getFileType(rootFile);
                if (fileType != FileTypeHelper.FileType.UNKNOWN) {
                    FileInfoModel fileInfoModel = buildCacheCleanerModel(fileType, rootFile);
                    fileInfoModelList.add(fileInfoModel);
                    publishProgress(fileInfoModel);
                }
            } else {
                File[] fileList = null;
                try {
                    fileList = rootFile.listFiles();
                } catch (Exception e) {

                }
                if (fileList != null) {
                    for (int i = 0; i < fileList.length; i++) {
                        if (fileList[i] == null) {
                            continue;
                        }
                        scanCache(fileList[i].getAbsolutePath(), depth - 1);
                    }
                }
            }
            return true;
        }
    }

    private FileTypeHelper.FileType getFileType(File rootFile) {
        if (rootFile != null && rootFile.exists() && rootFile.isFile()) {
            return FileTypeHelper.getFileType(rootFile.getPath());
        }
        return FileTypeHelper.FileType.UNKNOWN;
    }

    private FileInfoModel buildCacheCleanerModel(FileTypeHelper.FileType type, File rootFile) {
        return new FileInfoModel(type, rootFile);
    }

    private void notifyScanCacheStateChanged(FileInfoModel fileInfoModel) {
        for (ScanCacheListener listener : mScanCacheListeners) {
            listener.onStateChanged(fileInfoModel);
        }
    }

    private void notifyScanCacheFinish(boolean success) {
        for (ScanCacheListener listener : mScanCacheListeners) {
            listener.onFinish(success);
        }
    }

}
