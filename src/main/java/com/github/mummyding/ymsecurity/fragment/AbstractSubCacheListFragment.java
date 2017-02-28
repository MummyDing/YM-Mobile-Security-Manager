package com.github.mummyding.ymsecurity.fragment;

import android.support.v4.app.Fragment;

import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingqinying on 17/2/28.
 */

public abstract class AbstractSubCacheListFragment extends Fragment {

    protected List<FileInfoModel> mFileInfoList = new ArrayList<>();
    protected FileTypeHelper.FileType mFileType;

    protected void setData(List<FileInfoModel> list, FileTypeHelper.FileType fileType) {
        mFileType = fileType;
        if (list != null) {
            mFileInfoList = list;
        }
    }
}
