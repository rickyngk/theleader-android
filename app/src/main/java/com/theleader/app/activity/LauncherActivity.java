package com.theleader.app.activity;

import android.content.Context;
import android.os.Bundle;

import com.theleader.app.R;
import com.theleader.app.RestAPI;
import com.theleader.app.activity.auth.LoginActivity;
import com.theleader.app.activity.main.MainActivity;

import R.helper.BaseActivity;
import R.helper.Callback;
import R.helper.CallbackResult;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        RestAPI.autoLogin(this, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    setTimeout(new Runnable() {
                        @Override
                        public void run() {
                            openActivity(LoginActivity.class);
                        }
                    });
                } else {
                    setTimeout(new Runnable() {
                        @Override
                        public void run() {
                            openActivity(MainActivity.class);
                        }
                    });
                }
            }
        });

    }
}
