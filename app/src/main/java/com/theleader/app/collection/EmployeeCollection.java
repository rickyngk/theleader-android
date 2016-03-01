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

        EmployeeEntity ee1 = new EmployeeEntity();
        ee1.setFirstName("Jack");
        ee1.setLastName("Chan");
        ee1.setEmail("jackie@email.com");
        data.add(ee1);

        EmployeeEntity ee2 = new EmployeeEntity();
        ee2.setFirstName("Mary");
        ee2.setLastName("Land");
        ee2.setEmail("marryland@email.com");
        data.add(ee2);

        callback.onCompleted(ctx, null);
    }
}
