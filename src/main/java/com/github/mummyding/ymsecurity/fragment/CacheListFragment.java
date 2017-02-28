package com.github.mummyding.ymsecurity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.ui.listview.CacheFileListView;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;
import com.github.mummyding.ymsecurity.viewmodel.CacheFileViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingqinying on 17/2/23.
 */

public class CacheListFragment extends AbstractSubCacheListFragment {

    private CacheFileListView mCacheFileList;

    public static AbstractSubCacheListFragment newInstance(FileTypeHelper.FileType fileType, List<FileInfoModel> list) {
        CacheListFragment fragment = new CacheListFragment();
        fragment.setData(list, fileType);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cache_list, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ((TextView)view.findViewById(R.id.info)).setText(mFileInfoList.size() + " " + FileTypeHelper.getTypeName(mFileType));
        mCacheFileList = (CacheFileListView) view.findViewById(R.id.cache_file_list);

        List<CacheFileViewModel> cacheFileViewModelList = new ArrayList<>();
        for (FileInfoModel fileInfo: mFileInfoList) {
            cacheFileViewModelList.add(new CacheFileViewModel(fileInfo));
        }
        mCacheFileList.bindViewModel(cacheFileViewModelList);
        mCacheFileList.update();
    }


}
