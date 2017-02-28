package com.github.mummyding.ymsecurity.fragment;


import com.github.mummyding.ymsecurity.model.FileInfoModel;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;

import java.util.List;

/**
 * Created by dingqinying on 17/2/22.
 */

public class ImageGridFragment extends AbstractSubCacheListFragment {

    public static AbstractSubCacheListFragment newInstance(FileTypeHelper.FileType fileType, List<FileInfoModel> list) {
        ImageGridFragment fragment = new ImageGridFragment();
        fragment.setData(list, fileType);
        return fragment;
    }

}
