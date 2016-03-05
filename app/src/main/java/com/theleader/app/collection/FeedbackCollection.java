package com.theleader.app.collection;

import android.content.Context;

import com.theleader.app.entity.FeedbackEntity;

import java.util.ArrayList;

import R.helper.Callback;
import R.helper.Common;

/**
 * Created by duynk on 3/5/16.
 *
 */
public class FeedbackCollection extends BaseCollection<FeedbackEntity> {
    private static FeedbackCollection instance = new FeedbackCollection();
    public static FeedbackCollection getInstance() {
        return instance;
    }

    private FeedbackCollection() {}

    @Override
    public void load(Context ctx, final Callback callback) {
        data = new ArrayList<>();

        FeedbackEntity ee1 = new FeedbackEntity();
        ee1.setSenderName("Jack");
        ee1.setMessage("hello world");
        ee1.setTimestamp(Common.getMillis());
        data.add(ee1);

        FeedbackEntity ee2 = new FeedbackEntity();
        ee2.setSenderName("Mary");
        ee2.setMessage("hello world 2");
        ee2.setTimestamp(Common.getMillis() + 50000);
        data.add(ee2);

        callback.onCompleted(ctx, null);
    }

}
