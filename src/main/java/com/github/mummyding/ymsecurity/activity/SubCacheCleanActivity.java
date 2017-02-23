package com.github.mummyding.ymsecurity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.base.SwipeBackActivity;
import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingqinying on 17/2/23.
 */

public class SubCacheCleanActivity extends SwipeBackActivity {

    private static FileTypeHelper.FileType mFileType;
    private static List<FileInfoModel> mFileInfoList = new ArrayList<>();

    public static void launch(Context context, List<FileInfoModel> fileInfoList, FileTypeHelper.FileType fileType) {
        Intent intent = new Intent(context, SubCacheCleanActivity.class);
        mFileType = fileType;
        if (fileInfoList != null) {
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.layout.frame_layout,)
    }
}
