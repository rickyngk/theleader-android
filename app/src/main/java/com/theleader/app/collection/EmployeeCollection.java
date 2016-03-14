package com.theleader.app.collection;

import android.content.Context;

import com.theleader.app.entity.EmployeeEntity;

import java.util.ArrayList;

import R.helper.Callback;

/**
 * Created by duynk on 3/2/16.
 *
 */
public class EmployeeCollection extends BaseCollection<EmployeeEntity> {
    private static EmployeeCollection instance = new EmployeeCollection();
    private EmployeeCollection() {}

    public static EmployeeCollection getInstance() {
        return instance;
    }

    @Override
    public void load(Context ctx, final Callback callback) {
        data = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            EmployeeEntity ee1 = new EmployeeEntity();
            ee1.setFirstName("User");
            ee1.setLastName((i +  1) + "");
            ee1.setEmail("user" + i + "@email.com");
            data.add(ee1);
        }
        callback.onCompleted(ctx, null);
    }
}
