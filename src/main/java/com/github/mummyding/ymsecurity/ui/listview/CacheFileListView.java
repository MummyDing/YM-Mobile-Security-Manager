package com.github.mummyding.ymsecurity.ui.listview;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.ui.IView;
import com.github.mummyding.ymsecurity.ui.widget.YMCheckBox;
import com.github.mummyding.ymsecurity.ui.widget.YMImageView;
import com.github.mummyding.ymsecurity.util.FileUtil;
import com.github.mummyding.ymsecurity.viewmodel.CacheFileViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingqinying on 17/2/22.
 */

public class CacheFileListView extends RecyclerView implements IView<List<CacheFileViewModel>> {

    private Context mContext;
    private CacheFileAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<CacheFileViewModel> mCacheFileList = new ArrayList<>();

    public CacheFileListView(Context context) {
        this(context, null);
    }

    public CacheFileListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CacheFileListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mAdapter = new CacheFileAdapter(mContext);
        mLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(mLayoutManager);
        setAdapter(mAdapter);
    }

    @Override
    public void bindViewModel(List<CacheFileViewModel> viewModel) {
        if (viewModel == null || viewModel.isEmpty()) {
            return;
        }
        mCacheFileList.clear();
        mCacheFileList.addAll(viewModel);
    }

    @Override
    public void update() {
        if (mCacheFileList.isEmpty()) {
            return;
        }
        mAdapter.setData(mCacheFileList);
    }

    private class CacheFileAdapter extends RecyclerView.Adapter<CacheFileAdapter.VH> {

        private Context mContext;
        private List<CacheFileViewModel> mData;

        public CacheFileAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_cache_file, parent, false);
            VH vh = new VH(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            CacheFileViewModel cacheFile = getItem(position);
            if (cacheFile != null) {
                holder.mLogo.setDrawable(cacheFile.getDrawable());
                holder.mName.setText(cacheFile.getName());
                holder.mSize.setText(FileUtil.formatSize(cacheFile.getSize()));
                holder.mCheckBox.setChecked(cacheFile.isChecked());
            }
        }

        private CacheFileViewModel getItem(int position) {
            if (mData == null) {
                return null;
            }
            return mData.get(position);
        }

        @Override
        public int getItemCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        public void setData(List<CacheFileViewModel> data) {
            this.mData = data;
        }

        public class VH extends ViewHolder {

            YMImageView mLogo;
            TextView mName;
            TextView mSize;
            YMCheckBox mCheckBox;
            public VH(View itemView) {
                super(itemView);
                mLogo = (YMImageView) itemView.findViewById(R.id.logo);
                mName = (TextView) itemView.findViewById(R.id.name);
                mSize = (TextView) itemView.findViewById(R.id.memory_size);
                mCheckBox = (YMCheckBox) itemView.findViewById(R.id.check_box);
            }
        }
    }
}
