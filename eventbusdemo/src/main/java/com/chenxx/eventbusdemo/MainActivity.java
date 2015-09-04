package com.chenxx.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chenxx.library.ui.BaseActivity;
import com.orhanobut.logger.Logger;

import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {

    private Button mBtn;
    private TextView mMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        EventTest et = new EventTest();
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mInstance, SecondActivity.class));
            }
        });
        mMsg = findViewById(mInstance, R.id.content);
    }

    public void onEvent(String msg) {
        Logger.e("onEvent:" + msg);
    }

    public void onEventAsync(String msg) {
        Logger.e("onEventAsync:" + msg);
    }

    public void onEventMainThread(String msg) {
        Logger.e("onEventMainThread:" + msg);
    }

    public void onEventBackgroundThread(String msg) {
        Logger.e("onEventBackgroundThread:" + msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class EventTest {
    public EventTest() {
        EventBus.getDefault().register(this);
    }


    public void onEvent(String msg) {
        Logger.e(getClass().getSimpleName() + "\nonEvent:" + msg);
    }

    public void onEventAsync(String msg) {
        Logger.e(getClass().getSimpleName() + "\nonEventAsync:" + msg);
    }

    public void onEventMainThread(String msg) {
        Logger.e(getClass().getSimpleName() + "\nonEventMainThread:" + msg);
    }

    public void onEventBackgroundThread(String msg) {
        Logger.e(getClass().getSimpleName() + "\nonEventBackgroundThread:" + msg);
    }

}
