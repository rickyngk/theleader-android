package com.theleader.app.activity;

import android.os.Bundle;

import com.theleader.app.R;
import com.theleader.app.activity.auth.LoginActivity;

import R.helper.BaseActivity;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        pushActivity(LoginActivity.class);
    }
}
