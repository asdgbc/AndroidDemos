package com.chenxx.overscrolldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    VelocityTracker mTracker;

    FrameLayout mRoot;
    TextView mTv;

    OverScroller mScroller;
    FlingChecker mChecker = new FlingChecker();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoot = (FrameLayout) findViewById(R.id.root);
        mTv = (TextView) findViewById(R.id.tv);
        mScroller = new OverScroller(MainActivity.this,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mTracker == null)
                    mTracker = VelocityTracker.obtain();
                mTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mRoot.removeCallbacks(mChecker);
                mTracker.computeCurrentVelocity(1000);
                mScroller.fling(0, mRoot.getHeight()/2, 0, (int) mTracker.getYVelocity(), 0, 0, 200, mRoot.getHeight() - 200, 0, 200);
                mRoot.postDelayed(mChecker,20);
                break;
        }
        return true;
    }

    class FlingChecker implements Runnable{
        @Override
        public void run() {
            boolean stillRunning = mScroller.computeScrollOffset();

            Log.w(getClass().getSimpleName(), "cV:" + mScroller.getCurrVelocity() + ";cY:" + mScroller.getCurrY() + ";fY:" + mScroller.getFinalY() + ";sY:" + mScroller.getStartY());
            mTv.setTranslationY(mScroller.getCurrY()-mRoot.getHeight()/2);
            if (stillRunning)
                mRoot.postDelayed(this,20);
        }
    }

}
