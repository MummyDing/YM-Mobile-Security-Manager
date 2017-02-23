package com.github.mummyding.ymsecurity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.base.SwipeBackActivity;
import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.ui.listview.CacheGroupListView;
import com.github.mummyding.ymsecurity.ui.widget.YMProgressBar;
import com.github.mummyding.ymsecurity.util.CacheScanner;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;
import com.github.mummyding.ymsecurity.viewmodel.CacheGroupViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MummyDing on 2017/2/3.
 */

public class CacheCleanActivity extends SwipeBackActivity implements CacheScanner.ScanCacheListener {

    private static final String[] GROUP_NAMES = {"文档", "图片", "安装包", "视频", "压缩包", "音频"};
    private static final Integer[] ICONS = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    private YMProgressBar mProgressBar;
    private CacheGroupListView mCacheGroupList;
    private List<CacheGroupViewModel> mCacheGroupModelList = new ArrayList<>();
    private Map<Enum, List<FileInfoModel>> mFileInfoListMap = new HashMap<>();

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
        mProgressBar = (YMProgressBar) findViewById(R.id.progressbar);
        mCacheGroupList = (CacheGroupListView) findViewById(R.id.cache_group_list);
        mCacheGroupList.setOnItemClickListener(new CacheGroupListView.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                // TODO: 17/2/23 跳转
            }
        });
        resetData();
        CacheScanner.getInstance().scanCache("/");
    }

    private void resetData() {
        mCacheGroupModelList.clear();
        mFileInfoListMap.clear();
        for (int i = 0; i < GROUP_NAMES.length; i++) {
            mCacheGroupModelList.add(new CacheGroupViewModel(GROUP_NAMES[i], getResources().getDrawable(ICONS[i])));
        }
        mFileInfoListMap.put(FileTypeHelper.FileType.DOCUMENT_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.IMAGE_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.APK_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.VIDEO_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.COMPRESS_FILE, new ArrayList<FileInfoModel>());
        mFileInfoListMap.put(FileTypeHelper.FileType.AUDIO_FILE, new ArrayList<FileInfoModel>());
    }

    @Override
    public void onStateChanged(FileInfoModel fileInfoModel) {
        if (fileInfoModel != null) {
            int index = getIndexByType(fileInfoModel.getFileType());
            // TODO: 17/2/23 需要更新 mProgressBar
            mCacheGroupModelList.get(index).addSize(fileInfoModel.getCacheSize());
            mFileInfoListMap.get(fileInfoModel.getFileType()).add(fileInfoModel);
        }
    }

    @Override
    public void onFinish(boolean success) {
        mCacheGroupList.bindViewModel(mCacheGroupModelList);
        mCacheGroupList.update();
    }

    private int getIndexByType(FileTypeHelper.FileType fileType) {
        switch (fileType) {
            case DOCUMENT_FILE:
                return 0;
            case IMAGE_FILE:
                return 1;
            case APK_FILE:
                return 2;
            case VIDEO_FILE:
                return 3;
            case COMPRESS_FILE:
                return 4;
            case AUDIO_FILE:
                return 5;
            default:
                return 0;
        }
    }

    @Override
    protected void onDestroy() {
        CacheScanner.getInstance().removeScanCacheListener(this);
        super.onDestroy();
    }
}
