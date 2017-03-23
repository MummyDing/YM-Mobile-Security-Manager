package com.github.mummyding.ymsecurity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mummyding.ymsecurity.R;
import com.github.mummyding.ymsecurity.base.BaseFragment;

/**
 * Created by MummyDing on 2017/3/23.
 */

public class MainFragment extends BaseFragment {

    View mJunkClean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        mJunkClean = mRootView.findViewById(R.id.btn_junk_clean);
        mJunkClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "hhh ", Toast.LENGTH_LONG).show();
            }
        });
    }
}
