package com.theleader.app.collection;

import android.content.Context;

import java.util.ArrayList;

import R.helper.Callback;

/**
 * Created by duynk on 3/2/16.
 */
public class BaseCollection<T> {
    protected ArrayList<T> data = null;

    public boolean hasInit() {
        return data != null;
    }

    public void load(Context ctx, final Callback callback) {
        data = new ArrayList<>();
    }

    public ArrayList<T> getData() {
        return data;
    }

    public T getData(int index) {
        return data.get(index);
    }

    public int size() {
        return data.size();
    }
}
