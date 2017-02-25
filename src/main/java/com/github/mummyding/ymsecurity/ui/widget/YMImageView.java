package com.github.mummyding.ymsecurity.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by MummyDing on 2017/2/22.
 */

public class YMImageView extends SimpleDraweeView {

    public YMImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public YMImageView(Context context) {
        super(context);
    }

    public YMImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YMImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public YMImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setDrawable(Drawable drawable) {
        if (getHierarchy() != null) {
            getHierarchy().setBackgroundImage(drawable);
        }
    }
}

