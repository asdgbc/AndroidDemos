package com.learn.cxx.drawabledemos.fragments;

import com.learn.cxx.drawabledemos.MainActivity;
import com.learn.cxx.drawabledemos.R;

/**
 * @author hzchenxuexing
 * @date 2016年01月28日
 * <p>
 * Copyright 2016 NetEase. All rights reserved.
 */
public abstract class BaseFragment extends com.chenxx.library.ui.BaseFragment {

    protected void gotoFragment(Class<? extends BaseFragment> fragment) {
        try {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.push_up, R.anim.push_down, R.anim.push_up, R.anim.push_down).replace(MainActivity.CONTAINER_ID, fragment.newInstance(), fragment.getSimpleName()).addToBackStack(null).commit();
        } catch (Exception e) {

        }

    }

}
