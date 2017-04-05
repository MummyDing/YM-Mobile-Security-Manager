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
import java.util.Collection;
import java.util.List;

/**
 * Created by dingqinying on 17/2/23.
 */

public class CacheListFragment extends AbstractSubCacheListFragment /*implements IFileInteractionListener */{

    private CacheFileListView mCacheFileList;
//    private FileViewInteractionHub mFileViewInteractionHub;
//    private FileIconHelper mFileIconHelper;

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
//
//        mFileViewInteractionHub = new FileViewInteractionHub(this);
//        mFileViewInteractionHub.setMode(FileViewInteractionHub.Mode.View);
//        mFileViewInteractionHub.setRootPath("/");
//        mFileIconHelper = new FileIconHelper(getActivity());
//        mCacheFileList.bindAdapter(mFileViewInteractionHub, mFileIconHelper);
//        mCacheFileList.bindViewModel(cacheFileViewModelList);
        mCacheFileList.update();
    }


   /* @Override
    public View getViewById(int id) {
        return null;
    }

    @Override
    public void onDataChanged() {

    }

    @Override
    public void onPick(FileInfoModel f) {

    }

    @Override
    public boolean shouldShowOperationPane() {
        return false;
    }

    @Override
    public boolean onOperation(int id) {
        return false;
    }

    @Override
    public String getDisplayPath(String path) {
        return null;
    }

    @Override
    public String getRealPath(String displayPath) {
        return null;
    }

    @Override
    public void runOnUiThread(Runnable r) {

    }

    @Override
    public boolean onNavigation(String path) {
        return false;
    }

    @Override
    public boolean shouldHideMenu(int menu) {
        return false;
    }

    @Override
    public FileIconHelper getFileIconHelper() {
        return null;
    }

    @Override
    public FileInfoModel getItem(int pos) {
        return null;
    }

    @Override
    public void sortCurrentList(FileSortHelper sort) {

    }

    @Override
    public Collection<FileInfoModel> getAllFiles() {
        return null;
    }

    @Override
    public void addSingleFile(FileInfoModel file) {

    }

    @Override
    public boolean onRefreshFileList(String path, FileSortHelper sort) {
        return false;
    }

    @Override
    public int getItemCount() {
        return 0;
    }*/
}
