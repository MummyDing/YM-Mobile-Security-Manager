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
import com.github.mummyding.ymsecurity.ui.widget.YMImageView;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;
import com.github.mummyding.ymsecurity.util.FileUtil;
import com.github.mummyding.ymsecurity.viewmodel.CacheGroupViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingqinying on 17/2/22.
 */

public class CacheGroupListView extends RecyclerView implements IView<List<CacheGroupViewModel>> {

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private CacheGroupListAdapter mAdapter;
    private List<CacheGroupViewModel>  mCacheGroupList = new ArrayList<>();

    public CacheGroupListView(Context context) {
        this(context, null);
    }

    public CacheGroupListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CacheGroupListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(mLayoutManager);
        mAdapter = new CacheGroupListAdapter(mContext);
        setAdapter(mAdapter);
    }

    @Override
    public void bindViewModel(List<CacheGroupViewModel> cacheGroupList) {
        if (cacheGroupList == null || cacheGroupList.isEmpty()) {
            return;
        }
        mCacheGroupList.clear();
        mCacheGroupList.addAll(cacheGroupList);
    }

    @Override
    public void update() {
        if (mCacheGroupList == null || mCacheGroupList.isEmpty()) {
            return;
        }
        mAdapter.setData(mCacheGroupList);
    }

    private class CacheGroupListAdapter extends RecyclerView.Adapter<VH> {

        private Context mContext;
        private List<CacheGroupViewModel> mData;

        public CacheGroupListAdapter(Context context) {
            this.mContext = context;
        }

        public void setData(List<CacheGroupViewModel> list) {
            mData = list;
            notifyDataSetChanged();
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_cache_group, parent, false);
            VH vh = new VH(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(VH holder, final int position) {
            CacheGroupViewModel cacheGroup = getItem(position);
            if (cacheGroup != null) {
                holder.mLogo.setDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                holder.mTypeName.setText(cacheGroup.getName());
                holder.mMemorySize.setText(FileUtil.formatSize(cacheGroup.getSize()));
                holder.mItemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onClick(position);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        private CacheGroupViewModel getItem(int pos) {
            if (mData == null) {
                return null;
            }
            return mData.get(pos);
        }
    }

    private class VH extends RecyclerView.ViewHolder {
        View mItemView;
        YMImageView mLogo;
        TextView mTypeName;
        TextView mMemorySize;
        public VH(View itemView) {
            super(itemView);
            mItemView = itemView;
            mLogo = (YMImageView) itemView.findViewById(R.id.logo);
            mTypeName = (TextView) itemView.findViewById(R.id.typeName);
            mMemorySize = (TextView) itemView.findViewById(R.id.memory_size);
        }
    }
}
