package com.github.mummyding.ymsecurity.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.R;

/**
 * Created by dingqinying on 16/11/19.
 */
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "TitleBar";

    public interface OnTitleBarListener {
        void onBackButtonClick();
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private View mTitleView;
    private View mBackButton;
    private TextView mTitle;
    private Activity mActivity;

    private void initViews() {
        if (getContext() instanceof Activity) {
            mActivity = (Activity) getContext();
        }
        mTitleView = LayoutInflater.from(mActivity).inflate(R.layout.layout_widget_titlebar, this, true);
        mBackButton = mTitleView.findViewById(R.id.title_back);
        mTitle = (TextView) mTitleView.findViewById(R.id.titlebar_title);

        mBackButton.setOnClickListener(this);
        if (mActivity != null) {
            setTitle(mActivity.getTitle());
        }
    }

    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back) {
            if (mActivity == null) {
                return;
            }
            if (mActivity instanceof OnTitleBarListener) {
                ((OnTitleBarListener) mActivity).onBackButtonClick();
            }
            mActivity.onBackPressed();
        }
    }

}
