package com.theleader.app.entity;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 3/5/16.
 *
 */
public class FeedbackEntity extends EntityX {
    public FeedbackEntity() {
        super();
    }
    @BindField("senderName") String senderName;
    @BindField("message") String message;
    @BindField("timestamp") Long timestamp;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
