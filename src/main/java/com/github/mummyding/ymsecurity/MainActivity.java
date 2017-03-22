package com.github.mummyding.ymsecurity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.base.BaseActivity;
import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.model.MemoryCleanerModel;
import com.github.mummyding.ymsecurity.util.CacheScanner;
import com.github.mummyding.ymsecurity.util.MemoryCleaner;
import com.github.mummyding.ymsecurity.ui.widget.YMProgressBar;
import com.yzy.supercleanmaster.ui.RubbishCleanActivity;


import java.io.IOException;

public class MainActivity extends BaseActivity implements MemoryCleaner.MemoryCleanerStateChangedListener, CacheScanner.ScanCacheListener/*, CacheScanner.DeleteCacheListener*/ {

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
        CacheScanner.getInstance().addScanCacheListener(this);
//        CacheScanner.getInstance().addDeleteCacheListener(this);
        Log.d(TAG, "inner: " + Environment.getRootDirectory().getParentFile().listFiles());
        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MemoryCleaner.getInstance().scanMemory(MainActivity.this, MemoryCleaner.CLEAN_LEVEL_DEEP);
//                CacheScanner.getInstance().scanCache("/sdcard/Download/");
//                MemoryCleanActivity.launch(MainActivity.this);
//                CacheCleanActivity.launch(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, RubbishCleanActivity.class);
                startActivity(intent);
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
    public void onStateChanged(FileInfoModel fileInfoModel) {
        mDisplayText = "FileName:　" + fileInfoModel.getFileName() +
                " FilePath: " + fileInfoModel.getFilePath() + "\n";
        mDisplayView.setText(mDisplayText);

    }

    @Override
    public void onFinish(boolean success) {
        /*if (success) {
            mDisplayText += fileInfoModelList.size() + "\n";
            for (FileInfoModel model : fileInfoModelList) {
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
        }*/

        mDisplayView.setText(mDisplayText);
    }

   /* @Override
    public void onStateChanged(FileInfoModel file) {
        mDisplayText += "onStateChanged : " + file;
    }

    @Override
    public void onFinish(boolean success) {
        mDisplayText += success ;
        mDisplayView.setText(mDisplayText);
    }*/
}
