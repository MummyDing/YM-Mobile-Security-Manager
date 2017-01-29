package com.github.mummyding.ymsecurity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.base.BaseActivity;
import com.github.mummyding.ymsecurity.model.MemoryCleanerModel;
import com.github.mummyding.ymsecurity.util.MemoryCleaner;
import com.github.mummyding.ymsecurity.widget.YMProgressBar;

public class MainActivity extends BaseActivity implements MemoryCleaner.MemoryCleanerStateChangedListener {

    private YMProgressBar mProgressBar;
    private TextView mDisplayView;
    private String mDisplayText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (YMProgressBar) findViewById(R.id.progressbar);
        mDisplayView = (TextView) findViewById(R.id.displayView);

        MemoryCleaner.getInstance().addMemoryStateChangedListener(this);
        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryCleaner.getInstance().clearMemory(MainActivity.this);
            }
        });
    }

    @Override
    public void onStart(int processCount, ActivityManager.MemoryInfo memoryInfo) {
        mDisplayText += "processCount: " + processCount + " avaiMemory: " + memoryInfo.availMem / MemoryCleaner.MB
                + "totalMemory: " + memoryInfo.totalMem / MemoryCleaner.MB +"\n";
    }

    @Override
    public void onMemoryStateChanged(int processIndex, MemoryCleanerModel memoryCleanerModel) {
        mDisplayText += "processIndex: " + processIndex +"\n";

    }

    @Override
    public void onFinish(boolean success, ActivityManager.MemoryInfo memoryInfo) {
        mDisplayText += "processCount: " + success + " avaiMemory: " + memoryInfo.availMem / MemoryCleaner.MB
                + "totalMemory: " + memoryInfo.totalMem / MemoryCleaner.MB +"\n";
        mDisplayView.setText(mDisplayText);
    }

    @Override
    protected void onDestroy() {
        MemoryCleaner.getInstance().removeMemoryStateChangedListener(this);
        super.onDestroy();
    }
}
