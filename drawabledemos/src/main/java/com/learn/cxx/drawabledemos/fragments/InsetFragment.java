package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.InsetDrawable;
import android.view.View;
import android.widget.SeekBar;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年01月28日
 * <p>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class InsetFragment extends BaseFragment {

    SeekBar seekBar;
    InsetDrawable insetDrawable;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_inset;
    }

    @Override
    protected void findViews(View v) {
        insetDrawable = (InsetDrawable) findById(v,R.id.iv).getBackground();
        seekBar = findById(v,R.id.seekbar);
    }

    @Override
    protected void configViews() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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
