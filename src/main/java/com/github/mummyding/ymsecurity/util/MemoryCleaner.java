package com.github.mummyding.ymsecurity.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;

import com.github.mummyding.ymsecurity.model.MemoryCleanerModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MummyDing on 2017/1/28.
 */

public class MemoryCleaner {

    private static final String TAG = "MemoryCleaner";
    public final static long MB = 1024 * 1024;

    private Set<MemoryCleanerStateChangedListener> mListeners = new HashSet<>();
    private static MemoryCleaner sMemoryCleaner;

    private MemoryCleaner() {
    }

    public interface MemoryCleanerStateChangedListener {
        void onStart(int processCount, ActivityManager.MemoryInfo memoryInfo);
        void onMemoryStateChanged(int processIndex, MemoryCleanerModel memoryCleanerModel);
        void onFinish(boolean success, ActivityManager.MemoryInfo memoryInfo);
    }

    private class MemoryCleanerTask extends AsyncTask<Void, MemoryCleanerModel, ActivityManager.MemoryInfo> {

        private ActivityManager mActivityManager;
        private List<ActivityManager.RunningAppProcessInfo> mProcessInfoList;
        private boolean mIsTaskStop = true;
        private int mCurrentProcessIndex;

        public MemoryCleanerTask(Context context) {
            super();
            if (context != null) {
                mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                mIsTaskStop = false;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mIsTaskStop) {
                return;
            }
            if (mActivityManager == null) {
                notifyMemoryCleanerFinish(false, null);
                return;
            }
            mProcessInfoList = mActivityManager.getRunningAppProcesses();
            ActivityManager.MemoryInfo memoryInfo = getMemoryInfo(mActivityManager);
            if (mProcessInfoList == null || memoryInfo == null) {
                notifyMemoryCleanerFinish(false, null);
                return;
            }
            notifyMemoryCleanerStart(mProcessInfoList.size(), memoryInfo);
        }

        @Override
        protected ActivityManager.MemoryInfo doInBackground(Void... params) {
            if (mIsTaskStop) {
                return null;
            }
            if (mActivityManager == null || mProcessInfoList == null) {
                return null;
            }

            mCurrentProcessIndex = 0;
            ActivityManager.MemoryInfo lastMemoryInfo = getMemoryInfo(mActivityManager);
            ActivityManager.MemoryInfo currentMemoryInfo = lastMemoryInfo;
            for (int i = 0; i < mProcessInfoList.size(); i++) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = mProcessInfoList.get(i);
                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; j++) {
                        mActivityManager.killBackgroundProcesses(pkgList[j]);
                        currentMemoryInfo = getMemoryInfo(mActivityManager);
                        mCurrentProcessIndex++;
                        publishProgress(new MemoryCleanerModel(pkgList[j], lastMemoryInfo, currentMemoryInfo));
                        lastMemoryInfo = currentMemoryInfo;
                    }
                }
            }
            return currentMemoryInfo;
        }

        @Override
        protected void onPostExecute(ActivityManager.MemoryInfo memoryInfo) {
            super.onPostExecute(memoryInfo);
            if (memoryInfo == null) {
                notifyMemoryCleanerFinish(false, null);
                return;
            }
            notifyMemoryCleanerFinish(true, memoryInfo);
        }

        @Override
        protected void onProgressUpdate(MemoryCleanerModel... values) {
            super.onProgressUpdate(values);
            if (mIsTaskStop) {
                return;
            }
            if (values == null || values.length == 0) {
                notifyMemoryCleanerFinish(false, null);
                return;
            }
            notifyMemoryCleanerStateChanged(mCurrentProcessIndex, values[0]);
        }
    }

    public static MemoryCleaner getInstance() {
        if (sMemoryCleaner == null) {
            sMemoryCleaner = new MemoryCleaner();
        }
        return sMemoryCleaner;
    }

    public void clearMemory(Context context) {
        MemoryCleanerTask task = new MemoryCleanerTask(context);
        task.execute();
    }

    public ActivityManager.MemoryInfo getMemoryInfo(ActivityManager am) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi;
    }

    public void addMemoryStateChangedListener(MemoryCleanerStateChangedListener listener) {
        if (listener != null) {
            mListeners.add(listener);
        }
    }

    public void removeMemoryStateChangedListener(MemoryCleanerStateChangedListener listener) {
        mListeners.remove(listener);
    }

    private void notifyMemoryCleanerStart(int processCount, ActivityManager.MemoryInfo memoryInfo) {
        if (mListeners == null) {
            return;
        }
        for (MemoryCleanerStateChangedListener listener : mListeners) {
            listener.onStart(processCount, memoryInfo);
        }
    }

    private void notifyMemoryCleanerStateChanged(int processIndex, MemoryCleanerModel memoryCleanerModel) {
        if (mListeners == null) {
            return;
        }
        for (MemoryCleanerStateChangedListener listener : mListeners) {
            listener.onMemoryStateChanged(processIndex, memoryCleanerModel);
        }
    }

    private void notifyMemoryCleanerFinish(boolean success, ActivityManager.MemoryInfo memoryInfo) {
        if (mListeners == null) {
            return;
        }
        for (MemoryCleanerStateChangedListener listener : mListeners) {
            listener.onFinish(success, memoryInfo);
        }
    }
 }
