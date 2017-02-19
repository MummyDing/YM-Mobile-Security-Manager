package com.github.mummyding.ymsecurity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.model.ApkInfoModel;
import com.github.mummyding.ymsecurity.model.CacheCleanerModel;
import com.github.mummyding.ymsecurity.util.ApkUtil;
import com.github.mummyding.ymsecurity.util.FileTypeHelper;
import com.github.mummyding.ymsecurity.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MummyDing on 2017/2/3.
 */

public class CacheCleanListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ItemGroup> mGroupDataList = new ArrayList<>();
    private List<List<ItemChild>> mChildDataList = new ArrayList<>();

    public CacheCleanListAdapter(Context mContext, List<CacheCleanerModel> cacheModelList) {
        this.mContext = mContext;
        buildItem(cacheModelList);
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_cache_clean, null);
        }
        final ItemGroup itemGroup = (ItemGroup) getGroup(groupPosition);
        if (itemGroup != null) {
            TextView groupTitle = (TextView) convertView.findViewById(R.id.group_title);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
            TextView cacheSize = (TextView) convertView.findViewById(R.id.cache_size);
            groupTitle.setText(itemGroup.mTitle);
            cacheSize.setText(FileUtil.formatSize(itemGroup.mCacheSize));
            checkBox.setChecked(itemGroup.isChecked());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        itemGroup.mChildChecked = itemGroup.mChildCount;
                    } else {
                        itemGroup.mChildChecked = 0;
                    }
                    resetChildItemCheckState(groupPosition, isChecked);
                    notifyDataSetChanged();
                }
            });
            convertView.setVisibility(itemGroup.isEmpty() ? View.GONE : View.VISIBLE);
        } else {
            convertView.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void resetChildItemCheckState(int groupPosition, boolean isChecked) {
        List<ItemChild> childList = mChildDataList.get(groupPosition);
        for (ItemChild itemChild : childList) {
            itemChild.mIsChecked = isChecked;
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_cache_clean, null);
        }
        ItemChild itemChild = (ItemChild) getChild(groupPosition, childPosition);
        if (itemChild != null) {
            ImageView childLogo = (ImageView) convertView.findViewById(R.id.logo);
            TextView childTitle = (TextView) convertView.findViewById(R.id.child_title);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
            TextView cacheSize = (TextView) convertView.findViewById(R.id.cache_size);
            childLogo.setImageDrawable(itemChild.mIcon);
            childTitle.setText(itemChild.mTitle);
            checkBox.setChecked(itemChild.mIsChecked);
            cacheSize.setText(FileUtil.formatSize(itemChild.mCacheSize));
        }
        return convertView;
    }

    private void buildItem(List<CacheCleanerModel> cacheModelList) {
        if (cacheModelList == null) {
            return;
        }
        mGroupDataList.clear();
        ItemGroup docGroup = new ItemGroup();
        docGroup.mTitle = "文档";
        mGroupDataList.add(docGroup);
        mChildDataList.add(new ArrayList<ItemChild>());
        ItemGroup imageGroup = new ItemGroup();
        imageGroup.mTitle = "图片";
        mGroupDataList.add(imageGroup);
        mChildDataList.add(new ArrayList<ItemChild>());
        ItemGroup apkGroup = new ItemGroup();
        apkGroup.mTitle = "安装包";
        mGroupDataList.add(apkGroup);
        mChildDataList.add(new ArrayList<ItemChild>());
        ItemGroup videoGroup = new ItemGroup();
        videoGroup.mTitle = "视频";
        mGroupDataList.add(videoGroup);
        mChildDataList.add(new ArrayList<ItemChild>());
        ItemGroup compressGroup = new ItemGroup();
        compressGroup.mTitle = "压缩包";
        mGroupDataList.add(compressGroup);
        mChildDataList.add(new ArrayList<ItemChild>());
        ItemGroup audioGroup = new ItemGroup();
        audioGroup.mTitle = "音频";
        mGroupDataList.add(audioGroup);
        mChildDataList.add(new ArrayList<ItemChild>());

        for (CacheCleanerModel model : cacheModelList) {
            FileTypeHelper.FileType type = model.getFileType();
            if (type == FileTypeHelper.FileType.APK_FILE) {
                ApkInfoModel apkInfoModel = ApkUtil.getApkInfo(model.getFilePath());
                if (apkInfoModel != null) {
                    ItemApk itemApk = new ItemApk();
                    itemApk.setSrc(model);
                    itemApk.mIcon = apkInfoModel.getIcon();
                    ((List)mChildDataList.get(getGroupIndex(FileTypeHelper.FileType.APK_FILE)))
                            .add(itemApk);
                    apkGroup.mCacheSize += itemApk.mCacheSize;
                    apkGroup.mChildChecked++;
                    apkGroup.mChildCount++;
                }
            } else if (type == FileTypeHelper.FileType.AUDIO_FILE) {
                ItemAudio itemAudio = new ItemAudio();
                itemAudio.setSrc(model);
                itemAudio.mIcon = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
                ((List)mChildDataList.get(getGroupIndex(FileTypeHelper.FileType.AUDIO_FILE)))
                        .add(itemAudio);
                audioGroup.mChildChecked += itemAudio.mCacheSize;
                audioGroup.mChildChecked++;
                audioGroup.mChildCount++;
            } else if (type == FileTypeHelper.FileType.COMPRESS_FILE) {
                ItemCompress itemCompress = new ItemCompress();
                itemCompress.setSrc(model);
                itemCompress.mIcon = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
                ((List)mChildDataList.get(getGroupIndex(FileTypeHelper.FileType.COMPRESS_FILE)))
                        .add(itemCompress);
                compressGroup.mCacheSize += itemCompress.mCacheSize;
                compressGroup.mChildChecked++;
                compressGroup.mChildCount++;
            } else if (type == FileTypeHelper.FileType.DOCUMENT_FILE) {
                ItemDocument itemDocument = new ItemDocument();
                itemDocument.setSrc(model);
                itemDocument.mIcon = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
                ((List)mChildDataList.get(getGroupIndex(FileTypeHelper.FileType.DOCUMENT_FILE)))
                        .add(itemDocument);
                docGroup.mCacheSize += itemDocument.mCacheSize;
                docGroup.mChildChecked++;
                docGroup.mChildCount++;
            } else if (type == FileTypeHelper.FileType.IMAGE_FILE) {
                ItemImage itemImage = new ItemImage();
                itemImage.setSrc(model);
                itemImage.mIcon = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
                ((List)mChildDataList.get(getGroupIndex(FileTypeHelper.FileType.IMAGE_FILE)))
                        .add(itemImage);
                imageGroup.mCacheSize += itemImage.mCacheSize;
                imageGroup.mChildChecked++;
                imageGroup.mChildCount++;
            } else if (type == FileTypeHelper.FileType.VIDEO_FILE) {
                ItemVideo itemVideo = new ItemVideo();
                itemVideo.setSrc(model);
                itemVideo.mIcon = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
                ((List)mChildDataList.get(getGroupIndex(FileTypeHelper.FileType.VIDEO_FILE)))
                        .add(itemVideo);
                videoGroup.mCacheSize += itemVideo.mCacheSize;
                videoGroup.mChildChecked++;
                videoGroup.mChildCount++;
            }
        }
    }

    @Override
    public int getGroupCount() {
        return mGroupDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mChildDataList.get(groupPosition) == null) {
            return 0;
        }
        return mChildDataList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mChildDataList.get(groupPosition) == null) {
            return null;
        }
        return ((List)mChildDataList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private int getGroupIndex(FileTypeHelper.FileType type) {
        if (type == FileTypeHelper.FileType.DOCUMENT_FILE) {
            return 0;
        } else if (type == FileTypeHelper.FileType.IMAGE_FILE) {
            return 1;
        } else if (type == FileTypeHelper.FileType.APK_FILE) {
            return 2;
        } else if (type == FileTypeHelper.FileType.VIDEO_FILE) {
            return 3;
        } else if (type == FileTypeHelper.FileType.COMPRESS_FILE) {
            return 4;
        } else if (type == FileTypeHelper.FileType.AUDIO_FILE) {
            return 5;
        }
        return 5;
    }

    private class ItemGroup {

        String mTitle;
        long mCacheSize;
        int mChildChecked;
        int mChildCount;

        boolean isChecked() {
            return mChildChecked > 0;
        }

        boolean isEmpty() {
            return mChildCount <= 0;
        }

     }

    private class ItemChild {
        String mTitle;
        Drawable mIcon;
        String mPath;
        boolean mIsChecked;
        long mCacheSize;

        void setSrc(CacheCleanerModel model) {
            mTitle = model.getFileName();
            mCacheSize = model.getCacheSize();
            mIsChecked = true;
            mPath = model.getFilePath();
        }
    }

    private class ItemApk extends ItemChild {

    }

    private class ItemImage extends ItemChild {

    }

    private class ItemDocument extends ItemChild {

    }

    private class ItemCompress extends ItemChild {

    }

    private class ItemAudio extends ItemChild {

    }

    private class ItemVideo extends ItemChild {

    }
}
