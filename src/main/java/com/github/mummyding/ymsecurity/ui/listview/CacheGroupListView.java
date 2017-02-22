package com.github.mummyding.ymsecurity.ui.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.github.mummyding.ymsecurity.ui.IView;
import com.github.mummyding.ymsecurity.viewmodel.CacheGroupViewModel;

import java.util.List;

/**
 * Created by dingqinying on 17/2/22.
 */

public class CacheGroupListView extends RecyclerView implements IView<List<CacheGroupViewModel>> {

    public CacheGroupListView(Context context) {
        super(context);
    }

    public CacheGroupListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CacheGroupListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void bindViewModel(List<CacheGroupViewModel> cacheGroupList) {

    }

    @Override
    public void update() {

    }

    private class CacheGroupListAdapter extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class VH extends RecyclerView.ViewHolder {

        public VH(View itemView) {
            super(itemView);
        }
    }
}
