package com.github.mummyding.ymsecurity.util;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.github.mummyding.ymsecurity.model.CacheCleanerModel;

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

public class CacheCleaner {

    private static final String TAG = "CacheCleaner";
    private static CacheCleaner sCacheCleaner;
    private ScanCacheAsyncTask mScanCacheAsyncTask;
    private DeleteCacheAsyncTask mDeleteCacheAsyncTask;

    private CacheCleaner() {
    }

    public static CacheCleaner getInstance() {
        if (sCacheCleaner == null) {
            sCacheCleaner = new CacheCleaner();
        }
        return sCacheCleaner;
    }

    public interface ScanCacheListener {

        void onStateChanged(CacheCleanerModel cacheCleanerModel);

        void onFinish(boolean success, String rootPath, List<CacheCleanerModel> cacheCleanerModelList);
    }

    public interface DeleteCacheListener {

        void onStateChanged(File file);

        void onFinish(boolean success, String rootPath);
    }

    private Set<ScanCacheListener> mScanCacheListeners = new HashSet<>();
    private Set<DeleteCacheListener> mDeleteCacheListeners = new HashSet<>();

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

    public void addDeleteCacheListener(DeleteCacheListener listener) {
        if (listener != null) {
            mDeleteCacheListeners.add(listener);
        }
    }

    public void removeDeleteCacheListener(DeleteCacheListener listener) {
        if (listener != null) {
            mDeleteCacheListeners.remove(listener);
        }
    }

    public boolean scanCache(String rootPath) {
        return scanCache(rootPath, false);
    }

    public boolean scanCache(String rootPath, boolean onlyCurrentDirectory) {
        if (mScanCacheAsyncTask != null && mScanCacheAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Log.d(TAG, "ScanCacheAsyncTask is business");
            return false;
        }
        mScanCacheAsyncTask = new ScanCacheAsyncTask(rootPath, onlyCurrentDirectory);
        mScanCacheAsyncTask.execute();
        return true;
    }

    public boolean deleteCache(String rootPath) {
        return deleteCache(rootPath, false);
    }

    public boolean deleteCache(String rootPath, boolean onlyCurrentDirectory) {
        if (mDeleteCacheAsyncTask != null && mDeleteCacheAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Log.d(TAG, "DeleteCacheTask is business");
            return false;
        }
        mDeleteCacheAsyncTask = new DeleteCacheAsyncTask(rootPath, onlyCurrentDirectory);
        mDeleteCacheAsyncTask.execute();
        return true;
    }

    public boolean cancelScanCache() {
        if (mScanCacheAsyncTask == null || mScanCacheAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            return false;
        }
        mScanCacheAsyncTask.cancel(true);
        return true;
    }

    public boolean cancelDeleteCache() {
        if (mDeleteCacheAsyncTask == null || mDeleteCacheAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            return false;
        }
        mDeleteCacheAsyncTask.cancel(true);
        return true;
    }

    private class ScanCacheAsyncTask extends AsyncTask<Void, CacheCleanerModel , List<CacheCleanerModel>> {

        private String mRootPath;
        private boolean mOnlyCurrentDirectory;
        private boolean mIsScanStop;

        public ScanCacheAsyncTask(String rootPath, boolean onlyCurrentDirectory) {
            this.mRootPath = rootPath;
            this.mOnlyCurrentDirectory = onlyCurrentDirectory;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isValidPath(mRootPath)) {
                mIsScanStop = true;
                notifyScanCacheFinish(false, mRootPath, new ArrayList<CacheCleanerModel>());
            }
        }

        @Override
        protected List<CacheCleanerModel> doInBackground(Void ... params) {
            if (mIsScanStop) {
                return new ArrayList<>();
            }
            return scanCache(mRootPath, mOnlyCurrentDirectory);
        }

        @Override
        protected void onProgressUpdate(CacheCleanerModel ... values) {
            super.onProgressUpdate(values);
            if (values != null || values.length != 0) {
                notifyScanCacheStateChanged(values[0]);
            }
        }

        @Override
        protected void onPostExecute(List<CacheCleanerModel> o) {
            super.onPostExecute(o);
            notifyScanCacheFinish(true, mRootPath, o);
        }
        private List<CacheCleanerModel> scanCache(String rootPath, boolean onlyCurrentDirectory) {
            List<CacheCleanerModel> cacheCleanerModelList = new LinkedList<>();
            if (TextUtils.isEmpty(rootPath)) {
                return cacheCleanerModelList;
            }
            File rootFile = new File(rootPath);
            if (rootFile == null && !rootFile.exists()) {
                return cacheCleanerModelList;
            }
            if (rootFile.isFile()) {
                FileTypeHelper.FileType fileType = getFileType(rootFile);
                if (fileType != FileTypeHelper.FileType.UNKNOWN) {
                    CacheCleanerModel cacheCleanerModel = buildCacheCleanerModel(fileType, rootFile);
                    cacheCleanerModelList.add(cacheCleanerModel);
                    publishProgress(cacheCleanerModel);
                }
            } else {
                File [] fileList = rootFile.listFiles();
                if (fileList != null) {
                    for (int i = 0 ; i < fileList.length ; i++) {
                        if (fileList[i] == null) {
                            continue;
                        }
                        if (fileList[i].isFile() || onlyCurrentDirectory == false) {
                            cacheCleanerModelList.addAll(scanCache(fileList[i].getAbsolutePath(), onlyCurrentDirectory));
                        }
                    }
                }
            }
            return cacheCleanerModelList;
        }
    }

    private class DeleteCacheAsyncTask extends AsyncTask<Void, File, Boolean> {

        private String mRootPath;
        private boolean mOnlyCurrentDirectory;
        private boolean mIsScanStop;

        public DeleteCacheAsyncTask(String rootPath, boolean onlyCurrentDirectory) {
            this.mRootPath = rootPath;
            this.mOnlyCurrentDirectory = onlyCurrentDirectory;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isValidPath(mRootPath)) {
                mIsScanStop = true;
                notifyDeleteCacheFinish(false, mRootPath);
            }
        }

        @Override
        protected Boolean doInBackground(Void ... params) {
            if (mIsScanStop) {
                return false;
            }
            return deleteCache(mRootPath, mOnlyCurrentDirectory);
        }

        @Override
        protected void onProgressUpdate(File ... values) {
            super.onProgressUpdate(values);
            if (values != null && values.length > 0) {
                notifyDeleteCacheStateChanged(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            notifyDeleteCacheFinish(aBoolean, mRootPath);
        }

        private boolean deleteCache(String rootPath, boolean onlyCurrentDirectory) {
            if (TextUtils.isEmpty(rootPath)) {
                return false;
            }
            File rootFile = new File(rootPath);
            if (rootFile == null || !rootFile.exists()) {
                return false;
            }
            if (rootFile.isFile()) {
                publishProgress(rootFile);
                return rootFile.delete();
            } else {
                File [] fileList = rootFile.listFiles();
                if (fileList != null) {
                    for (int i = 0 ; i < fileList.length ; i++) {
                        if (fileList[i] == null) {
                            continue;
                        }
                        if (fileList[i].isFile() || onlyCurrentDirectory == false) {
                            deleteCache(fileList[i].getAbsolutePath(), onlyCurrentDirectory);
                        }
                    }
                }
            }
            return true;
        }
    }

    private boolean isValidPath(String rootPath) {
        if (TextUtils.isEmpty(rootPath)) {
            return false;
        }
        File rootFile = new File(rootPath);
        if (rootFile == null && !rootFile.exists()) {
            return false;
        }
        return true;
    }

    private FileTypeHelper.FileType getFileType(File rootFile) {
        if (rootFile != null && rootFile.exists() && rootFile.isFile()) {
            return FileTypeHelper.getFileType(rootFile.getPath());
        }
        return FileTypeHelper.FileType.UNKNOWN;
    }


    private CacheCleanerModel buildCacheCleanerModel(FileTypeHelper.FileType type, File rootFile) {
        return new CacheCleanerModel(type, rootFile);
    }

    private void notifyScanCacheStateChanged(CacheCleanerModel cacheCleanerModel) {
        for (ScanCacheListener listener : mScanCacheListeners) {
            listener.onStateChanged(cacheCleanerModel);
        }
    }

    private void notifyScanCacheFinish(boolean success, String rootPath, List<CacheCleanerModel> cacheCleanerModelList) {
        for (ScanCacheListener listener : mScanCacheListeners) {
            listener.onFinish(success, rootPath, cacheCleanerModelList);
        }
    }

    private void notifyDeleteCacheStateChanged(File file) {
        for (DeleteCacheListener listener : mDeleteCacheListeners) {
            listener.onStateChanged(file);
        }
    }

    private void notifyDeleteCacheFinish(boolean success, String rootPath) {
        for (DeleteCacheListener listener : mDeleteCacheListeners) {
            listener.onFinish(success, rootPath);
        }
    }

}
