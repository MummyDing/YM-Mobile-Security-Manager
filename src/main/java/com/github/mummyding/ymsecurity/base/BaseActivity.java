package com.github.mummyding.ymsecurity.base;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.github.mummyding.ymsecurity.R;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
