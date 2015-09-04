package com.chenxx.library.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by chenxx on 2015/9/3.
 */
public class BaseFragment extends Fragment{
    public <T> T findViewById(View view,int id){
        return (T) view.findViewById(id);
    }
}
