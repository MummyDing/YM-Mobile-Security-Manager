package com.github.mummyding.ymsecurity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.model.MemoryCleanerModel;

import java.util.List;


/**
 * Created by MummyDing on 2017/2/2.
 */

public class MemoryCleanerListAdapter extends BaseAdapter {

    private Context mContext;
    private List<MemoryCleanerModel> mMemoryCleanerModelList;

    public MemoryCleanerListAdapter(Context context, List<MemoryCleanerModel> memoryCleanerModelList) {
        this.mContext = context;
        this.mMemoryCleanerModelList = memoryCleanerModelList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_memory_cleaner, null);
        }
        ImageView logo = (ImageView) convertView.findViewById(R.id.process_logo);
        TextView name = (TextView) convertView.findViewById(R.id.process_name);
        TextView memorySize = (TextView) convertView.findViewById(R.id.memory_size);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
        final MemoryCleanerModel model = (MemoryCleanerModel) getItem(position);
        if (model != null) {
            logo.setImageDrawable(model.getAppLogo());
            name.setText(model.getAppName());
            memorySize.setText(model.getMemorySize() + "");
            checkBox.setChecked(model.isNeedClean());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    model.setNeedClean(isChecked);
                }
            });
        }
        return convertView;
    }

    @Override
    public int getCount() {
        if (mMemoryCleanerModelList != null) {
            return mMemoryCleanerModelList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mMemoryCleanerModelList != null) {
            return mMemoryCleanerModelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
