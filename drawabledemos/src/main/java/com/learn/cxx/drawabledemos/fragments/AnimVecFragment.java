package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;
import android.widget.EditText;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年02月24日
 * <p/>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class AnimVecFragment extends BaseFragment {
    AnimatedVectorDrawable drawable;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_animvec;
    }

    @Override
    protected void findViews(View v) {
        drawable = (AnimatedVectorDrawable) findById(v,R.id.btn).getBackground();
        findById(v,R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable.start();
            }
        });
        EditText editText;
    }

    @Override
    protected void configViews() {

    }
}
