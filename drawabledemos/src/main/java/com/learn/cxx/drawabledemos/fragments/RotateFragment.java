package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.RotateDrawable;
import android.view.View;
import android.widget.SeekBar;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年01月28日
 * <p>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class RotateFragment extends BaseFragment {

    SeekBar seekBar;
    RotateDrawable rotateDrawable;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_rotate;
    }

    @Override
    protected void findViews(View v) {
        rotateDrawable =
                (RotateDrawable) findById(v, R.id.iv).getBackground();
        seekBar = findById(v, R.id.seekbar);
    }

    @Override
    protected void configViews() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rotateDrawable.setLevel(progress);
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
