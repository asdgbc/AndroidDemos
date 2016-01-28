package com.learn.cxx.drawabledemos;

import android.os.Bundle;
import android.view.ViewGroup;

import com.chenxx.library.ui.BaseActivity;
import com.learn.cxx.drawabledemos.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    public final static int CONTAINER_ID = R.id.root;

    private ViewGroup root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findById(this, R.id.root);
        getSupportFragmentManager().beginTransaction().add(CONTAINER_ID, new MainFragment(), MainFragment.class.getSimpleName()).commit();
    }

}
