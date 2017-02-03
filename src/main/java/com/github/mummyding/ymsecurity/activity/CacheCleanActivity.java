package com.github.mummyding.ymsecurity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.adapter.CacheCleanListAdapter;
import com.github.mummyding.ymsecurity.base.SwipeBackActivity;
import com.github.mummyding.ymsecurity.model.CacheCleanerModel;
import com.github.mummyding.ymsecurity.util.CacheCleaner;

import java.util.List;

/**
 * Created by MummyDing on 2017/2/3.
 */

public class CacheCleanActivity extends SwipeBackActivity implements CacheCleaner.ScanCacheListener {

    private ExpandableListView mExpandableListView;
    private CacheCleanListAdapter mAdapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, CacheCleanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_clean);
        initView();
    }

    private void initView() {
        mExpandableListView = (ExpandableListView) findViewById(R.id.expand_list_view);
        CacheCleaner.getInstance().addScanCacheListener(this);
        CacheCleaner.getInstance().scanCache("/mnt/sdcard/Download/", true);
    }

    @Override
    public void onStateChanged(CacheCleanerModel cacheCleanerModel) {

    }

    @Override
    public void onFinish(boolean success, String rootPath, List<CacheCleanerModel> cacheCleanerModelList) {
        Toast.makeText(this, "Finish" + success, Toast.LENGTH_LONG).show();
        if (success) {
            mAdapter = new CacheCleanListAdapter(this, cacheCleanerModelList);
            mExpandableListView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        CacheCleaner.getInstance().removeScanCacheListener(this);
        super.onDestroy();
    }
}
