package com.theleader.app;

import R.helper.BaseApplication;

/**
 * Created by duynk on 2/22/16.
 */
public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setDefaultFont(FontType.SERIF, "fonts/RobotoSlab-Regular.ttf");
    }
}
