package com.github.mummyding.ymsecurity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mummyding.ymsecurity.R;

/**
 * Created by MummyDing on 2017/3/23.
 */

public class YMMainItem extends LinearLayout implements IView {

    private ImageView mItemImage;
    private TextView mItemName;

    public YMMainItem(Context context) {
        this(context, null);
    }

    public YMMainItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YMMainItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_main, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YMMainItem, defStyleAttr, 0);
        initView(a);
    }

    private void initView(TypedArray a) {
        int src = a.getResourceId(R.styleable.YMMainItem_mi_src, 0);
        String name = a.getString(R.styleable.YMMainItem_mi_text);
        int color = a.getColor(R.styleable.YMMainItem_mi_backgroundColor, Color.WHITE);
        mItemImage = (ImageView) findViewById(R.id.item_image);
        mItemName = (TextView) findViewById(R.id.item_name);
        if (src != 0) {
            mItemImage.setBackgroundResource(src);
        }
        ((CardView)findViewById(R.id.card_view)).setCardBackgroundColor(color);
        mItemName.setText(name);
    }

    @Override
    public void bindViewModel(Object viewModel) {

    }

    @Override
    public void update() {

    }
}
