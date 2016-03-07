package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.RippleDrawable;
import android.util.Log;
import android.view.View;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年01月28日
 * <p/>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class RippleFragment extends BaseFragment {
    RippleDrawable drawable;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_ripple;
    }

    @Override
    protected void findViews(View v) {
        drawable = (RippleDrawable) findById(v,R.id.btn).getBackground();
        findById(v,R.id.btn).post(new Runnable() {
            @Override
            public void run() {
                Log.w("bound", drawable.getBounds().toString());
            }
        });

    }

    @Override
    protected void configViews() {

    }
}
