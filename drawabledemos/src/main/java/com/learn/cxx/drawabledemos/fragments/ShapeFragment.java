package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年01月28日
 * <p/>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class ShapeFragment extends BaseFragment {
    private SeekBar seekBar;
    ImageView iv ;
    GradientDrawable drawable;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_shape;
    }

    @Override
    protected void findViews(View v) {
        iv = findById(v,R.id.iv);
        drawable = (GradientDrawable) iv.getBackground();
        seekBar = findById(v,R.id.seekbar);
    }

    @Override
    protected void configViews() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawable.setLevel(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
