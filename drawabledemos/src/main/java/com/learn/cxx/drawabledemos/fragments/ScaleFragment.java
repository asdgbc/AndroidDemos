package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.ScaleDrawable;
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
public class ScaleFragment extends BaseFragment {
    private SeekBar seekBar;
    ScaleDrawable drawable;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_scal;
    }

    @Override
    protected void findViews(View v) {
        ImageView iv = findById(v, R.id.iv);
        seekBar = findById(v, R.id.seekbar);
        drawable = (ScaleDrawable) iv.getBackground();
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
