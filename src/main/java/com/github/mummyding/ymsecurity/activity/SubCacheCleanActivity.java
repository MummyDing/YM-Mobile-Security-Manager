package com.github.mummyding.ymsecurity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.base.SwipeBackActivity;
import com.github.mummyding.ymsecurity.fragment.AbstractSubCacheListFragment;
import com.github.mummyding.ymsecurity.fragment.CacheListFragment;
import com.github.mummyding.ymsecurity.fragment.ImageGridFragment;
import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingqinying on 17/2/23.
 */

public class SubCacheCleanActivity extends SwipeBackActivity {

    private AbstractSubCacheListFragment mSubCacheListFragment;
    private static FileTypeHelper.FileType mFileType;
    private static List<FileInfoModel> mFileInfoList = new ArrayList<>();

    public static void launch(Context context, List<FileInfoModel> fileInfoList, FileTypeHelper.FileType fileType) {
        Intent intent = new Intent(context, SubCacheCleanActivity.class);
        mFileType = fileType;
        if (fileInfoList != null) {
            mFileInfoList.clear();
            mFileInfoList.addAll(fileInfoList);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cache_clean);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.info)).setText(mFileInfoList.size() + " " + FileTypeHelper.getTypeName(mFileType));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (mFileType) {
            case IMAGE_FILE:
                mSubCacheListFragment = ImageGridFragment.newInstance(mFileType, mFileInfoList);
            case APK_FILE:
            case DOCUMENT_FILE:
            default:
                mSubCacheListFragment = CacheListFragment.newInstance(mFileType, mFileInfoList);
        }
        ft.replace(R.id.frame_layout, mSubCacheListFragment);
        ft.commitAllowingStateLoss();
    }
}
