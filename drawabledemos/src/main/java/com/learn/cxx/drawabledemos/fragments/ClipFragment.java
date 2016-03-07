package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.ClipDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年01月28日
 * <p>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class ClipFragment extends BaseFragment {

    SeekBar seekBar;
    ClipDrawable clipDrawable;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_clip;
    }

    @Override
    protected void findViews(View v) {
//        clipDrawable = (ClipDrawable)((ImageView) findById(v,R.id.iv)).getDrawable();
        clipDrawable = (ClipDrawable) findById(v,R.id.iv).getBackground();
        seekBar = findById(v,R.id.seekbar);
    }

    @Override
    protected void configViews() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clipDrawable.setLevel(progress);
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
