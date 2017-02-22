package com.github.mummyding.ymsecurity.util;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.github.mummyding.ymsecurity.model.FileInfoModel;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dingqinying on 17/2/22.
 */

public class CacheCleaner {

    private static final String TAG = "CacheCleaner";
    private static CacheCleaner sCacheCleaner;
    private DeleteFilesAsyncTask mDeleteFilesAsyncTask;

    public interface DeleteCacheListener {

        void onStateChanged(FileInfoModel fileInfo, boolean success);

        void onFinish(boolean success);
    }

    private CacheCleaner() {

    }

    public static CacheCleaner getInstance() {
        if (sCacheCleaner == null) {
            sCacheCleaner = new CacheCleaner();
        }
        return sCacheCleaner;
    }

    public boolean deleteCache(List<FileInfoModel> list) {
        if (mDeleteFilesAsyncTask != null && mDeleteFilesAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Log.d(TAG, "DeleteFilesTask is business");
            return false;
        }
        mDeleteFilesAsyncTask = new DeleteFilesAsyncTask(list);
        mDeleteFilesAsyncTask.execute();
        return true;
    }

    private class DeleteFilesAsyncTask extends AsyncTask<Void, FileInfoModel, Boolean> {

        private List<FileInfoModel> mCacheCleanerList;
        private boolean mIsDeleteStop;

        public DeleteFilesAsyncTask(List<FileInfoModel> cacheCleanerList) {
            this.mCacheCleanerList = cacheCleanerList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mCacheCleanerList == null) {
                mIsDeleteStop = true;
                notifyDeleteCacheFinish(false);
            }
        }

        @Override
        protected void onProgressUpdate(FileInfoModel... values) {
            super.onProgressUpdate(values);
            if (values != null && values.length > 0) {
                notifyDeleteCacheStateChanged(values[0], true);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            notifyDeleteCacheFinish(aBoolean);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (mIsDeleteStop) {
                return false;
            }
            for (FileInfoModel fileInfo : mCacheCleanerList) {
                if (TextUtils.isEmpty(fileInfo.getFileName())) {
                    continue;
                }
                File file = new File(fileInfo.getFilePath());
                if (!FileUtil.isExist(file)) {
                    continue;
                }
                if (file.delete()) {
                    file.delete();
                    publishProgress(fileInfo);
                }
            }
            return true;
        }
    }

    private Set<DeleteCacheListener> mDeleteCacheListeners = new HashSet<>();

    public boolean cancelDeleteCache() {
        if (mDeleteFilesAsyncTask == null || mDeleteFilesAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            return false;
        }
        mDeleteFilesAsyncTask.cancel(true);
        return true;
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

    private void notifyDeleteCacheStateChanged(FileInfoModel fileInfo, boolean success) {
        for (DeleteCacheListener listener : mDeleteCacheListeners) {
            listener.onStateChanged(fileInfo, success);
        }
    }

    private void notifyDeleteCacheFinish(boolean success) {
        for (DeleteCacheListener listener : mDeleteCacheListeners) {
            listener.onFinish(success);
        }
    }
}
