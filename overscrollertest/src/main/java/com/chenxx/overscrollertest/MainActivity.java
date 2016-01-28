package com.chenxx.overscrollertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.Scroller;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    OverScroller mScroller;
    Scroller mS;
    EditText mStartY;
    EditText mVelocityY;
    EditText mMinY;
    EditText mMaxY;
    EditText mOverY;

    StringBuilder mSb = new StringBuilder();

    TextView mRecord;

    FlingChecker mChecker = new FlingChecker();

    LinearLayout mTextContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScroller = new OverScroller(MainActivity.this,null);

        mStartY = (EditText) findViewById(R.id.startY);
        mVelocityY = (EditText) findViewById(R.id.velocityY);
        mMinY = (EditText) findViewById(R.id.minY);
        mMaxY = (EditText) findViewById(R.id.maxY);
        mOverY = (EditText) findViewById(R.id.overY);
        mRecord = (TextView) findViewById(R.id.record);
        mTextContainer = (LinearLayout) findViewById(R.id.container);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartY.removeCallbacks(mChecker);
//                mSb.delete(0,mSb.length());
//                mRecord.setText(mSb.toString());
                mScroller.fling(0, TextUtils.isEmpty(mStartY.getText()) ? 0 : Integer.parseInt(mStartY.getText().toString()),
                        0, TextUtils.isEmpty(mVelocityY.getText()) ? 0 : Integer.parseInt(mVelocityY.getText().toString()),
                        0, 0,
                        TextUtils.isEmpty(mMinY.getText()) ? 0 : Integer.parseInt(mMinY.getText().toString()), TextUtils.isEmpty(mMaxY.getText()) ? 0 : Integer.parseInt(mMaxY.getText().toString()),
                        0, TextUtils.isEmpty(mOverY.getText()) ? 0 : Integer.parseInt(mOverY.getText().toString()));
                mStartY.postDelayed(mChecker, 20);
            }
        });

    }

    class FlingChecker implements Runnable{
        @Override
        public void run() {
            boolean stillRunning = mScroller.computeScrollOffset();

//            mSb.append("cV:"+mScroller.getCurrVelocity()+";cY:"+mScroller.getCurrY()+";fY:"+mScroller.getFinalY()+";sY:"+mScroller.getStartY()+"\n");
            Log.w(getClass().getSimpleName(), "cV:" + mScroller.getCurrVelocity() + ";cY:" + mScroller.getCurrY() + ";fY:" + mScroller.getFinalY() + ";sY:" + mScroller.getStartY());
//            mRecord.setText(mSb.toString());
            mTextContainer.scrollTo(0,mScroller.getCurrY());
            if (stillRunning)
                mStartY.postDelayed(this,20);
        }
    }
}
