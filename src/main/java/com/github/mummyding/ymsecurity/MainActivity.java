package com.github.mummyding.ymsecurity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.base.BaseActivity;
import com.github.mummyding.ymsecurity.model.MemoryCleanerModel;
import com.github.mummyding.ymsecurity.util.MemoryCleaner;
import com.github.mummyding.ymsecurity.widget.YMProgressBar;

import java.io.IOException;

public class MainActivity extends BaseActivity implements MemoryCleaner.MemoryCleanerStateChangedListener {

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
        Log.d(TAG, "inner: " + Environment.getRootDirectory().getParentFile().listFiles());
        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryCleaner.getInstance().clearMemory(MainActivity.this, MemoryCleaner.CLEAN_LEVEL_DEEP);
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
}
