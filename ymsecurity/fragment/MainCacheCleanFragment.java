package com.github.mummyding.ymsecurity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.ui.listview.CacheGroupListView;
import com.github.mummyding.ymsecurity.ui.widget.YMProgressBar;

/**
 * Created by dingqinying on 17/2/22.
 */

public class MainCacheCleanFragment extends Fragment {

    private YMProgressBar mProgress;
    private CacheGroupListView mCacheGropList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_cache_clean, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mProgress = (YMProgressBar) view.findViewById(R.id.progressbar);
        mCacheGropList = (CacheGroupListView) view.findViewById(R.id.cache_group_list);
    }
}

