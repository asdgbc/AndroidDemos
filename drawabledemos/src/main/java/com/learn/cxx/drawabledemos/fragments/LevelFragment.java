package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.LevelListDrawable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年02月15日
 * <p>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class LevelFragment extends BaseFragment {

    TextView tv;
    LevelListDrawable drawable;
    SeekBar seekBar;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_level;
    }

    @Override
    protected void findViews(View v) {
        drawable = (LevelListDrawable) findById(v, R.id.iv).getBackground();
        seekBar = findById(v, R.id.seekbar);
        tv = findById(v, R.id.tv);
    }

    @Override
    protected void configViews() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawable.setLevel(progress);
                tv.setText(progress + "");
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
