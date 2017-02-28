package com.github.mummyding.ymsecurity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.ui.listview.CacheFileGridView;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;
import com.github.mummyding.ymsecurity.viewmodel.CacheFileViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingqinying on 17/2/22.
 */

public class ImageGridFragment extends AbstractSubCacheListFragment {

    private CacheFileGridView mCacheFileGrid;

    public static AbstractSubCacheListFragment newInstance(FileTypeHelper.FileType fileType, List<FileInfoModel> list) {
        ImageGridFragment fragment = new ImageGridFragment();
        fragment.setData(list, fileType);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cache_grid, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mCacheFileGrid = (CacheFileGridView) view.findViewById(R.id.cache_file_grid);

        List<CacheFileViewModel> cacheFileViewModelList = new ArrayList<>();
        for (FileInfoModel fileInfo: mFileInfoList) {
            cacheFileViewModelList.add(new CacheFileViewModel(fileInfo));
        }
        mCacheFileGrid.bindViewModel(cacheFileViewModelList);
        mCacheFileGrid.update();
    }
}
