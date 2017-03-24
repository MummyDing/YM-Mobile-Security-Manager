package com.github.mummyding.ymsecurity.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.adapter.MemoryCleanerListAdapter;
import com.github.mummyding.ymsecurity.base.SwipeBackActivity;
import com.github.mummyding.ymsecurity.model.MemoryCleanerModel;
import com.github.mummyding.ymsecurity.util.MemoryCleaner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MummyDing on 2017/2/2.
 */

public class MemoryCleanActivity2 extends SwipeBackActivity implements MemoryCleaner.MemoryCleanerStateChangedListener {

    private ListView mListView;
    private MemoryCleanerListAdapter mListAdapter;
    private List<MemoryCleanerModel> mMemoryCleanerModelList = new ArrayList<>();

    public static void launch(Context context) {
        Intent intent = new Intent(context, MemoryCleanActivity2.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_clean);
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_view);
        MemoryCleaner.getInstance().addMemoryStateChangedListener(this);
        MemoryCleaner.getInstance().scanMemory(this, MemoryCleaner.CLEAN_LEVEL_DEEP);
        TextView textView = new TextView(this);
        textView.setText("一键清理");
        mListView.addFooterView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryCleaner.getInstance().killProcess(MemoryCleanActivity2.this, mMemoryCleanerModelList.get(0).getPkgName());
            }
        });
    }

    @Override
    public void onStart(int processCount, ActivityManager.MemoryInfo memoryInfo) {

    }

    @Override
    public void onMemoryStateChanged(int processIndex, MemoryCleanerModel memoryCleanerModel) {
        mMemoryCleanerModelList.add(memoryCleanerModel);
    }

    @Override
    public void onFinish(boolean success, ActivityManager.MemoryInfo memoryInfo) {
        mListAdapter = new MemoryCleanerListAdapter(this, mMemoryCleanerModelList);
        mListView.setAdapter(mListAdapter);
    }

    @Override
    protected void onDestroy() {
        MemoryCleaner.getInstance().removeMemoryStateChangedListener(this);
        super.onDestroy();
    }
}
