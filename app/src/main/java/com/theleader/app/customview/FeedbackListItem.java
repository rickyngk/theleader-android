package com.theleader.app.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theleader.app.R;
import com.theleader.app.entity.FeedbackEntity;

import R.helper.BaseActivity;
import R.helper.Common;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by duynk on 3/5/16.
 *
 */
public class FeedbackListItem extends LinearLayout {
    @Bind(R.id.txt_username)
    TextView senderName;

    @Bind(R.id.txt_message)
    TextView message;

    @Bind(R.id.txt_time)
    TextView timestamp;

    boolean hasInit = false;

    public FeedbackListItem(Context context) {
        super(context);
        initializeViews(context);
    }

    public FeedbackListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initializeViews(context);
    }

    public FeedbackListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cv_feedback_list_item, this);
        ButterKnife.bind(this);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        if (!hasInit) {
            super.onFinishInflate();
            hasInit = true;
        }
    }

    public void setData(final FeedbackEntity entity) {
        BaseActivity.timeout(new Runnable() {
            @Override
            public void run() {
                senderName.setText(entity.getSenderName());
                message.setText(entity.getMessage());
                timestamp.setText(Common.getDateString(entity.getTimestamp()));
            }
        });
    }
}