package com.github.mummyding.ymsecurity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mummyding.ymbase.base.BaseFragment;
import com.github.mummyding.ymsecurity.lib_clean.ui.AutoStartManageActivity;
import com.github.mummyding.ymsecurity.lib_clean.ui.CacheCleanActivity;
import com.github.mummyding.ymsecurity.lib_clean.ui.MemoryCleanActivity;
import com.github.mummyding.ymsecurity.lib_clean.ui.SoftwareManageActivity;

import com.github.mummyding.ymsecurity.lib_device.holdyou.DeviceInfoActivity;
import com.github.mummyding.ymsecurity.lib_filemanager.FileManagerActivity;

/**
 * Created by MummyDing on 2017/3/23.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {

    private View mJunkClean;
    private View mMemoryClean;
    private View mSoftManager;
    private View mFileManager;
    private View mDeviceInfo;
    private View mAutoStartManager;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        mJunkClean = mRootView.findViewById(R.id.btn_junk_clean);
        mMemoryClean = mRootView.findViewById(R.id.btn_memory_clean);
        mSoftManager = mRootView.findViewById(R.id.btn_software_manager);
        mFileManager = mRootView.findViewById(R.id.btn_file_manager);
        mDeviceInfo = mRootView.findViewById(R.id.btn_device_info);
        mAutoStartManager = mRootView.findViewById(R.id.btn_auto_start_manager);

        mJunkClean.setOnClickListener(this);
        mMemoryClean.setOnClickListener(this);
        mSoftManager.setOnClickListener(this);
        mFileManager.setOnClickListener(this);
        mDeviceInfo.setOnClickListener(this);
        mAutoStartManager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        long id = v.getId();
        if (id == R.id.btn_junk_clean) {
            launchActivity(CacheCleanActivity.class);
        } else if (id == R.id.btn_memory_clean) {
            launchActivity(MemoryCleanActivity.class);
        } else if (id == R.id.btn_software_manager) {
            launchActivity(SoftwareManageActivity.class);
        } else if (id == R.id.btn_file_manager) {
            launchActivity(FileManagerActivity.class);
        } else if (id == R.id.btn_device_info) {
            launchActivity(DeviceInfoActivity.class);
        } else if (id == R.id.btn_auto_start_manager) {
            launchActivity(AutoStartManageActivity.class);
        }
    }

    private void launchActivity(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}
