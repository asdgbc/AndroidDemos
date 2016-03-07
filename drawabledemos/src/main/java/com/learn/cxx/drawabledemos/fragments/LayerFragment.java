package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.LayerDrawable;
import android.view.View;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年02月01日
 * <p/>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class LayerFragment extends BaseFragment {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_layer;
    }

    @Override
    protected void findViews(View v) {
        ((LayerDrawable)findById(v,R.id.iv).getBackground()).setLevel(5000);
    }

    @Override
    protected void configViews() {

    }
}
