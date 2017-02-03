package com.github.mummyding.ymsecurity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.activity.CacheCleanActivity;
import com.github.mummyding.ymsecurity.activity.MemoryCleanActivity;
import com.github.mummyding.ymsecurity.base.BaseActivity;
import com.github.mummyding.ymsecurity.model.CacheCleanerModel;
import com.github.mummyding.ymsecurity.model.MemoryCleanerModel;
import com.github.mummyding.ymsecurity.util.CacheCleaner;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;
import com.github.mummyding.ymsecurity.util.MemoryCleaner;
import com.github.mummyding.ymsecurity.widget.YMProgressBar;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends BaseActivity implements MemoryCleaner.MemoryCleanerStateChangedListener, CacheCleaner.ScanCacheListener, CacheCleaner.DeleteCacheListener {

    private static final String TAG = "MainActivity";
    private YMProgressBar mProgressBar;
    private TextView mDisplayView;
    private String mDisplayText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (YMProgressBar) findViewById(R.id.progressbar);
        mDisplayView = (TextView) findViewById(R.id.displayView);
        try {
            Process process = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        MemoryCleaner.getInstance().addMemoryStateChangedListener(this);
        CacheCleaner.getInstance().addScanCacheListener(this);
        CacheCleaner.getInstance().addDeleteCacheListener(this);
        Log.d(TAG, "inner: " + Environment.getRootDirectory().getParentFile().listFiles());
        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MemoryCleaner.getInstance().scanMemory(MainActivity.this, MemoryCleaner.CLEAN_LEVEL_DEEP);
//                CacheCleaner.getInstance().scanCache("/sdcard/Download/");
//                MemoryCleanActivity.launch(MainActivity.this);
                CacheCleanActivity.launch(MainActivity.this);
            }
        });
    }

    @Override
    public void onStart(int processCount, ActivityManager.MemoryInfo memoryInfo) {
        mDisplayText += "processCount: " + processCount + " availMemory: " + memoryInfo.availMem / MemoryCleaner.MB
                + "totalMemory: " + memoryInfo.totalMem / MemoryCleaner.MB +"\n";
    }

    @Override
    public void onMemoryStateChanged(int processIndex, MemoryCleanerModel memoryCleanerModel) {
        mDisplayText += "processIndex: " + processIndex + memoryCleanerModel.getPkgName() + ": " + memoryCleanerModel.getMemoryCleaned() + "MB \n";
    }

    @Override
    public void onFinish(boolean success, ActivityManager.MemoryInfo memoryInfo) {
        if (success) {
            mDisplayText += "processCount: " + success + " availMemory: " + memoryInfo.availMem / MemoryCleaner.MB
                    + "totalMemory: " + memoryInfo.totalMem / MemoryCleaner.MB + "\n";
        } else {
            mDisplayText += "fail \n";
        }
        mDisplayView.setText(mDisplayText);
    }

    @Override
    protected void onDestroy() {
        MemoryCleaner.getInstance().removeMemoryStateChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void onStateChanged(CacheCleanerModel cacheCleanerModel) {
        mDisplayText = "FileName:　" + cacheCleanerModel.getFileName() +
                " FilePath: " + cacheCleanerModel.getFilePath() + "\n";
        mDisplayView.setText(mDisplayText);

    }

    @Override
    public void onFinish(boolean success, String rootPath, List<CacheCleanerModel> cacheCleanerModelList) {
        if (success) {
            mDisplayText += rootPath + cacheCleanerModelList.size() + "\n";
            for (CacheCleanerModel model : cacheCleanerModelList) {
                if (model.getFileType() == FileTypeHelper.FileType.IMAGE_FILE) {
                    mDisplayText += "image: " + model.getFileName() + "\n";
                } else if (model.getFileType() == FileTypeHelper.FileType.APK_FILE) {
                    mDisplayText += "apk: " + model.getFileName() + "\n";
                } else if (model.getFileType() == FileTypeHelper.FileType.DOCUMENT_FILE) {
                    mDisplayText += "document: " + model.getFileName() + "\n";
                } else if (model.getFileType() == FileTypeHelper.FileType.VIDEO_FILE) {
                    mDisplayText += "video: " + model.getFileName() + "\n";
                } else if (model.getFileType() == FileTypeHelper.FileType.COMPRESS_FILE) {
                    mDisplayText += "compress : " + model.getFileName() + "\n";
                } else if (model.getFileType() == FileTypeHelper.FileType.AUDIO_FILE) {
                    mDisplayText += "audio : " + model.getFileName() + "\n";
                }
            }
        } else {
            mDisplayText += "fail";
        }

        mDisplayView.setText(mDisplayText);
    }

    @Override
    public void onStateChanged(File file) {
        mDisplayText += "onStateChanged : " + file;
    }

    @Override
    public void onFinish(boolean success, String rootPath) {
        mDisplayText += success + rootPath;
        mDisplayView.setText(mDisplayText);
    }
}
