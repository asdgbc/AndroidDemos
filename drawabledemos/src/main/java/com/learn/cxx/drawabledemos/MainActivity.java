package com.learn.cxx.drawabledemos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup vg = (ViewGroup) findViewById(R.id.ll);
        for (int i = 0;i<vg.getChildCount();i++){
            vg.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bitmap:
                break;
            case R.id.clip:
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
                break;
            case R.id.shape:
                break;
            case R.id.statelist:
                startActivity(new Intent(this,SelectorActivity.class));
                break;
            case R.id.transition:
                break;
        }
    }
}
