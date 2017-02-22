package com.github.mummyding.ymsecurity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.adapter.CacheCleanListAdapter;
import com.github.mummyding.ymsecurity.base.SwipeBackActivity;
import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.util.CacheScanner;
import com.github.mummyding.ymsecurity.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MummyDing on 2017/2/3.
 */

public class CacheCleanActivity extends SwipeBackActivity implements CacheScanner.ScanCacheListener {

    private TextView mTip;
    private Button mCleanButton;
    private ExpandableListView mExpandableListView;
    private CacheCleanListAdapter mAdapter;
    private List<FileInfoModel> mFileInfoList = new ArrayList<>();

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
        mTip = (TextView) findViewById(R.id.tip);
        mCleanButton = (Button) findViewById(R.id.clean_button);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expand_list_view);
        CacheScanner.getInstance().addScanCacheListener(this);
        CacheScanner.getInstance().scanCache("/");
        mCleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CacheScanner.getInstance().deleteCache()
            }
        });
    }

    @Override
    public void onStateChanged(FileInfoModel fileInfoModel) {
        if (fileInfoModel != null) {
            mTip.setText(fileInfoModel.getFileName() + " : " + FileUtil.formatSize(fileInfoModel.getCacheSize()));
            mFileInfoList.add(fileInfoModel);
        }
    }

    @Override
    public void onFinish(boolean success) {
        Toast.makeText(this, "Finish" + success, Toast.LENGTH_LONG).show();
        if (success) {
            mAdapter = new CacheCleanListAdapter(this, mFileInfoList);
            mExpandableListView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        CacheScanner.getInstance().removeScanCacheListener(this);
        super.onDestroy();
    }
}
