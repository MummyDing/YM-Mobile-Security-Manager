package com.github.mummyding.ymsecurity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.base.SwipeBackActivity;
import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.ui.listview.CacheGroupListView;
import com.github.mummyding.ymsecurity.util.CacheScanner;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;
import com.github.mummyding.ymsecurity.util.FileUtil;
import com.github.mummyding.ymsecurity.viewmodel.CacheGroupViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MummyDing on 2017/2/3.
 */

public class CacheCleanActivity extends SwipeBackActivity implements CacheScanner.ScanCacheListener {

    private TextView mTip;
    private CacheGroupListView mGroupList;
    private Map<FileTypeHelper.FileType, List<FileInfoModel>> mFileInfoListMap = new HashMap<>();
    private List<CacheGroupViewModel> mCacheGroupViewModelList = new ArrayList<>();
    private Map<FileTypeHelper.FileType, Integer> mPositionMap = new LinkedHashMap<>();

    public static void launch(Context context) {
        Intent intent = new Intent(context, CacheCleanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_clean);
        init();
    }

    private void init() {
        mTip = (TextView) findViewById(R.id.tip);
        mGroupList = (CacheGroupListView) findViewById(R.id.cache_group_list);
        mGroupList.setOnItemClickListener(new CacheGroupListView.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                SubCacheCleanActivity.launch(CacheCleanActivity.this, mFileInfoListMap.get(mPositionMap.keySet().toArray()[position]), (FileTypeHelper.FileType) mPositionMap.keySet().toArray()[position]);
            }
        });
        CacheScanner.getInstance().addScanCacheListener(this);
        CacheScanner.getInstance().scanCache("/");

        mPositionMap.put(FileTypeHelper.FileType.DOCUMENT_FILE, 0);
        mPositionMap.put(FileTypeHelper.FileType.IMAGE_FILE, 1);
        mPositionMap.put(FileTypeHelper.FileType.APK_FILE, 2);
        mPositionMap.put(FileTypeHelper.FileType.VIDEO_FILE, 3);
        mPositionMap.put(FileTypeHelper.FileType.COMPRESS_FILE, 4);
        mPositionMap.put(FileTypeHelper.FileType.AUDIO_FILE, 5);

        mFileInfoListMap.put(FileTypeHelper.FileType.DOCUMENT_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.IMAGE_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.APK_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.VIDEO_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.COMPRESS_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.AUDIO_FILE, new ArrayList<FileInfoModel>());


        for (Iterator<FileTypeHelper.FileType> it = mPositionMap.keySet().iterator(); it.hasNext(); ) {
            FileTypeHelper.FileType fileType = it.next();
            mCacheGroupViewModelList.add(new CacheGroupViewModel(fileType));
        }
    }

    private void resetData() {

    }

    @Override
    public void onStateChanged(FileInfoModel fileInfoModel) {
        if (fileInfoModel != null) {
            mTip.setText(fileInfoModel.getFileName() + " : " + FileUtil.formatSize(fileInfoModel.getCacheSize()));
            mFileInfoListMap.get(fileInfoModel.getFileType()).add(fileInfoModel);
            mCacheGroupViewModelList.get(mPositionMap.get(fileInfoModel.getFileType())).addSize(fileInfoModel.getCacheSize());
        }
    }

    @Override
    public void onFinish(boolean success) {
        Toast.makeText(this, "Finish" + success, Toast.LENGTH_LONG).show();
        if (success) {
            mGroupList.bindViewModel(mCacheGroupViewModelList);
            mGroupList.update();
        }
    }

    @Override
    protected void onDestroy() {
        CacheScanner.getInstance().removeScanCacheListener(this);
        super.onDestroy();
    }
}
