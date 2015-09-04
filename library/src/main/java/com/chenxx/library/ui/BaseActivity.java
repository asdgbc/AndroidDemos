package com.chenxx.library.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by chenxx on 2015/9/3.
 */
public class BaseActivity extends AppCompatActivity {

    protected BaseActivity mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = BaseActivity.this;
    }

    public <T> T findViewById(Activity activity,int id){
        return (T) activity.getWindow().findViewById(id);
    }

    public <T> T findViewById(View view,int id){
        return (T) view.findViewById(id);
    }

}
