package com.github.mummyding.ymsecurity.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by MummyDing on 2017/2/22.
 */

public class YMImageView extends SimpleDraweeView {

    public YMImageView(Context context) {
        super(context);
    }

    public void setDrawable(Drawable drawable) {
        if (getHierarchy() != null) {
            getHierarchy().setBackgroundImage(drawable);
        }
    }
}

