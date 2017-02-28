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

public class CacheFileListView extends AbstractCacheFileListView implements IView<List<CacheFileViewModel>> {

    private LinearLayoutManager mLayoutManager;

    public CacheFileListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void init() {
        setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mAdapter = new CacheFileAdapter(mContext);
        mLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(mLayoutManager);
        setAdapter(mAdapter);
    }

    private class CacheFileAdapter extends BaseAdapter<CacheFileAdapter.VH> {

        public CacheFileAdapter(Context context) {
            super(context);
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
