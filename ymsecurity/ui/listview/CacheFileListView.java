package com.github.mummyding.ymsecurity.ui.listview;


import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.HashMap;
import java.util.List;

/**
 * Created by dingqinying on 17/2/22.
 */

public class CacheFileListView extends AbstractCacheFileListView implements IView<List<CacheFileViewModel>> {

    private LinearLayoutManager mLayoutManager;

    @Override
    protected void init() {
        setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(mLayoutManager);
    }

    public CacheFileListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
/*
    public void bindAdapter(FileViewInteractionHub f, FileIconHelper fileIcon) {
        mAdapter = new CacheFileAdapter(mContext, null,f,  fileIcon);
        setAdapter(mAdapter);
    }*/
/*
    private class CacheFileAdapter extends ListCursorAdapter<CacheFileAdapter.VH> {

        private final LayoutInflater mFactory;

        private FileViewInteractionHub mFileViewInteractionHub;

        private FileIconHelper mFileIcon;

        private HashMap<Integer, FileInfo> mFileNameList = new HashMap<Integer, FileInfo>();

        private Context mContext;

        public CacheFileAdapter(Context context, Cursor cursor,
                                FileViewInteractionHub f, FileIconHelper fileIcon) {
            super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER);
            mFactory = LayoutInflater.from(context);
            mFileViewInteractionHub = f;
            mFileIcon = fileIcon;
            mContext = context;
        }
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_cache_file, parent, false);
            VH vh = new VH(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(VH holder, Cursor cursor) {
            FileInfo fileInfo = getFileItem(cursor.getPosition());
            if (fileInfo == null) {
                // file is not existing, create a fake info
                fileInfo = new FileInfo();
                fileInfo.dbId = cursor.getLong(FileCategoryHelper.COLUMN_ID);
                fileInfo.filePath = cursor.getString(FileCategoryHelper.COLUMN_PATH);
                fileInfo.fileName = Util.getNameFromFilepath(fileInfo.filePath);
                fileInfo.fileSize = cursor.getLong(FileCategoryHelper.COLUMN_SIZE);
                fileInfo.ModifiedDate = cursor.getLong(FileCategoryHelper.COLUMN_DATE);
            }
        }

        public FileInfo getFileItem(int pos) {
            Integer position = Integer.valueOf(pos);
            if (mFileNameList.containsKey(position))
                return mFileNameList.get(position);

            Cursor cursor = (Cursor) getItem(position);
            FileInfo fileInfo = getFileInfo(cursor);
            if (fileInfo == null)
                return null;

            fileInfo.dbId = cursor.getLong(FileCategoryHelper.COLUMN_ID);
            mFileNameList.put(position, fileInfo);
            return fileInfo;
        }

        private FileInfo getFileInfo(Cursor cursor) {
            return (cursor == null || cursor.getCount() == 0) ? null : Util
                    .GetFileInfo(cursor.getString(FileCategoryHelper.COLUMN_PATH));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            Cursor cursor = (Cursor) getItem(position);
            FileInfo fileInfo = getFileItem(cursor.getPosition());
            if (fileInfo == null) {
                // file is not existing, create a fake info
                fileInfo = new FileInfo();
                fileInfo.dbId = cursor.getLong(FileCategoryHelper.COLUMN_ID);
                fileInfo.filePath = cursor.getString(FileCategoryHelper.COLUMN_PATH);
                fileInfo.fileName = Util.getNameFromFilepath(fileInfo.filePath);
                fileInfo.fileSize = cursor.getLong(FileCategoryHelper.COLUMN_SIZE);
                fileInfo.ModifiedDate = cursor.getLong(FileCategoryHelper.COLUMN_DATE);
            }

            holder.mName.setText(fileInfo.fileName);
            holder.mSize.setText(FileUtil.formatSize(fileInfo.fileSize));
            holder.mCheckBox.setChecked(fileInfo.Selected);
            *//* CacheFileViewModel cacheFile = getFileItem(); //getItem(position);
            if (cacheFile != null) {
                switch (cacheFile.getFileType()) {
                    case APK_FILE:
                        holder.mLogo.setDrawable(ApkUtil.getApkInfo(cacheFile.getFilePath()).getIcon());
                        break;
                    default:
                        holder.mLogo.setDrawable(getResources().getDrawable(FileTypeHelper.getFileIconResId(cacheFile.getFilePath())));
                }
                holder.mName.setText(cacheFile.getName());
                holder.mSize.setText(FileUtil.formatSize(cacheFile.getSize()));
                holder.mCheckBox.setChecked(cacheFile.isChecked());
            }*//*
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
    }*/
}
