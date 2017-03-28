package com.github.mummyding.ymsecurity.ui.listview;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.ui.IView;
import com.github.mummyding.ymsecurity.viewmodel.CacheFileViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingqinying on 17/2/28.
 */

public abstract class AbstractCacheFileListView extends RecyclerView implements IView<List<CacheFileViewModel>> {

    protected Context mContext;
    protected List<CacheFileViewModel> mCacheFileList = new ArrayList<>();
    protected Adapter mAdapter;

    protected abstract void init();

    public AbstractCacheFileListView(Context context) {
        this(context, null);
    }

    public AbstractCacheFileListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbstractCacheFileListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
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
//        mAdapter.setData(mCacheFileList);
    }

    protected abstract class BaseAdapter<T extends ViewHolder> extends RecyclerView.Adapter<T> {

        protected Context mContext;
        protected List<CacheFileViewModel> mData;

        public BaseAdapter(Context context) {
            this.mContext = context;
        }

        protected CacheFileViewModel getItem(int position) {
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
    }


    protected abstract class ListCursorAdapter<T extends ViewHolder> extends RecyclerViewCursorAdapter<T> {

        public ListCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }


        @Override
        protected void onContentChanged() {

        }

        @Override
        public T onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        public Object getItem(int position) {
            if (mDataValid && mCursor != null) {
                mCursor.moveToPosition(position);
                return mCursor;
            } else {
                return null;
            }
        }
    }
}
