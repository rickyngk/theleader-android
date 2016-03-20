package com.theleader.app.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theleader.app.R;

import R.helper.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by duynk on 3/15/16.
 */
public class ContactListItem extends RelativeLayout {
    @Bind(R.id.checkBox1)
    CheckBox checkBox;

    @Bind(R.id.textView)
    TextView textView;

    @Bind(R.id.textView2)
    TextView textView2;

    boolean hasInit = false;

    public ContactListItem(Context context) {
        super(context);
        initializeViews(context);
    }

    public ContactListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initializeViews(context);
    }

    public ContactListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cv_contact_list_item, this);
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

    public void setData(final String name, final String email, final boolean checked) {
        BaseActivity.timeout(new Runnable() {
            @Override
            public void run() {
                checkBox.setChecked(checked);
                if (name.isEmpty()) {
                    textView.setText(email);
                    textView2.setVisibility(GONE);
                } else {
                    textView.setText(name);
                    textView2.setText(email);
                }
            }
        });
    }

    public void setSelected(final boolean checked) {
        BaseActivity.timeout(new Runnable() {
            @Override
            public void run() {
                checkBox.setChecked(checked);
            }
        });
    }
}
