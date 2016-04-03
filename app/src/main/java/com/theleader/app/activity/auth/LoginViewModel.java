package com.theleader.app.activity.auth;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.theleader.app.R;
import com.theleader.app.RestAPI;
import com.theleader.app.TextBindingWatcher;

import R.helper.Callback;
import R.helper.CallbackResult;

/**
 * Created by duynk on 4/3/16.
 */
public class LoginViewModel {

    private SignInDataListener delegate;
    public interface SignInDataListener {
        void onSignInCompleted();
        void onSignInError(CallbackResult.ICallbackError error);
        void onStartSignIn();
        void onSignInValidateError(int type, int messageId);
    }

    private LoginActivity view;
    public String email = "";
    public String password = "";

    private UserLoginTask mAuthTask = null;

    public LoginViewModel(LoginActivity view, SignInDataListener listener) {
        this.view = view;
        delegate = listener;
    }

    public TextWatcher emailWatcher = new TextBindingWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            email = s.toString();
        }
    };

    public TextWatcher passwordWatcher = new TextBindingWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            password = s.toString();
        }
    };

    public View.OnClickListener onLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        };
    }

    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }

    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        int messageId = 0;
        boolean hasEmailError = false;
        boolean hasPasswordError = false;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            messageId = R.string.error_field_required;
            hasEmailError = true;
        } else if (!isEmailValid(email)) {
            messageId = R.string.error_invalid_email;
            hasEmailError = true;
        }

        if (!hasEmailError) {
            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(password)) {
                messageId = R.string.error_field_required;
                hasPasswordError = true;
            } else if (!isPasswordValid(password)) {
                messageId = R.string.error_invalid_password;
                hasPasswordError = true;
            }
        }


        if (hasEmailError) {
            delegate.onSignInValidateError(0, messageId);
        } else if (hasPasswordError) {
            delegate.onSignInValidateError(1, messageId);
        } else {
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
            delegate.onStartSignIn();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            RestAPI.login(view, mEmail, mPassword, new Callback() {
                @Override
                public void onCompleted(Context context, final CallbackResult result) {
                    if (result.hasError()) {
                        mAuthTask = null;
                        delegate.onSignInError(result.getError());
                    } else {
                        mAuthTask = null;
                        delegate.onSignInCompleted();
                    }
                }
            });
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }

}
