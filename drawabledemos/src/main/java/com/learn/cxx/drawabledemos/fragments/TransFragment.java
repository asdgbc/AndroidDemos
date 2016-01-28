package com.learn.cxx.drawabledemos.fragments;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.ImageView;

import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年01月28日
 * <p>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class TransFragment extends BaseFragment {

    TransitionDrawable transitionDrawable;
    ImageView iv;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_trans;
    }

    @Override
    protected void findViews(View v) {
        iv = findById(v,R.id.iv);
        transitionDrawable = (TransitionDrawable) iv.getBackground();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionDrawable.startTransition(2000);
                iv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        transitionDrawable.reverseTransition(2000);
                    }
                },2000);
            }
        });

    }

    @Override
    protected void configViews() {

    }
}
