package com.github.mummyding.ymsecurity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.github.mummyding.ymbase.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, new MainFragment());
        ft.commitAllowingStateLoss();
    }

}
