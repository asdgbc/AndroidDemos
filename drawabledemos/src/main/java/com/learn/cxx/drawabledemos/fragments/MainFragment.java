package com.learn.cxx.drawabledemos.fragments;


import android.view.View;
import android.view.ViewGroup;

import com.learn.cxx.drawabledemos.R;

import java.util.concurrent.TransferQueue;

/**
 * @author hzchenxuexing
 * @date 2016年01月28日
 * <p/>
 * Copyright 2016 NetEase. All rights reserved.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void findViews(View v) {
        ViewGroup vg = findById(v, R.id.ll);
        for (int i = 0; i < vg.getChildCount(); i++) {
            vg.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    protected void configViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bitmap:
                break;
            case R.id.clip:
                gotoFragment(ClipFragment.class);
                break;
            case R.id.inset:
                break;
            case R.id.layer:
                break;
            case R.id.levellist:
                break;
            case R.id.ninepatch:
                break;
            case R.id.scale:
                gotoFragment(ScaleFragment.class);
                break;
            case R.id.shape:
                gotoFragment(ShapeFragment.class);
                break;
            case R.id.statelist:
                gotoFragment(SelectorFragment.class);
                break;
            case R.id.transition:
                gotoFragment(TransFragment.class);
                break;
            case R.id.ripple:
                gotoFragment(RippleFragment.class);
                break;
            case R.id.rotate:
                gotoFragment(RotateFragment.class);
                break;

        }
    }


}
