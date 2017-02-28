package com.github.mummyding.ymsecurity.ui.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.ui.widget.YMImageView;
import com.github.mummyding.ymsecurity.viewmodel.CacheFileViewModel;

/**
 * Created by dingqinying on 17/2/28.
 */

public class CacheFileGridView extends AbstractCacheFileListView {

    private static final int WIDTH_COUNT = 3;
    private GridLayoutManager mGridLayoutManager;

    public CacheFileGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(mContext, WIDTH_COUNT);
        mAdapter = new CacheFileGridAdapter(mContext);
        mGridLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(mGridLayoutManager);
        setAdapter(mAdapter);
    }

    private class CacheFileGridAdapter extends BaseAdapter<CacheFileGridAdapter.VH> {

        public CacheFileGridAdapter(Context mContext) {
            super(mContext);
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_cache_file, parent, false);
            VH vh = new VH(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            CacheFileViewModel cacheFile = getItem(position);
            holder.mImage.setImageURI("file://" + cacheFile.getFilePath());
        }

        class VH extends RecyclerView.ViewHolder {
            View mItemView;
            YMImageView mImage;
            public VH(View itemView) {
                super(itemView);
                mItemView = itemView;
                mImage = (YMImageView) itemView.findViewById(R.id.image);
            }
        }
    }
}
