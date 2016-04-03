package com.theleader.app.activity.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.theleader.app.R;
import com.theleader.app.RestAPI;
import com.theleader.app.activity.main.MainActivity;
import com.theleader.app.databinding.ActivityLoginBinding;

import R.helper.BaseActivity;
import R.helper.CallbackResult;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(new LoginViewModel(this, new LoginViewModel.SignInDataListener() {
            @Override
            public void onSignInCompleted() {
                setTimeout(new Runnable() {
                    @Override
                    public void run() {
                        showProgress(false);
                        finish();
                        openActivity(MainActivity.class);
                    }
                });
            }

            @Override
            public void onSignInError(final CallbackResult.ICallbackError error) {
                setTimeout(new Runnable() {
                    @Override
                    public void run() {
                        showProgress(false);
                        if (error.is(RestAPI.ELoginError.WRONG_CREDENTIAL)) {
                            setPasswordError(getString(R.string.error_incorrect_password));
                        } else {
                            setSnackError();
                        }
                        focusPassword();
                    }
                });
            }

            @Override
            public void onStartSignIn() {
                setEmailError(null);
                setPasswordError(null);
                showProgress(true);
            }

            @Override
            public void onSignInValidateError(int type, int messageId) {
                setEmailError(null);
                setPasswordError(null);
                if (type == 0) {
                    setEmailError(getString(messageId));
                    focusEmail();
                } else {
                    setPasswordError(getString(messageId));
                    focusPassword();
                }
            }
        }));
    }

    public void setEmailError(String message) {
        binding.email.setError(message);
    }

    public void setPasswordError(String message) {
        binding.password.setError(message);
    }

    public void focusEmail() {
        binding.email.requestFocus();
    }

    public void focusPassword() {
        binding.password.requestFocus();
    }

    public void setSnackError() {
        Snackbar snackbar = Snackbar.make(binding.viewHolder, getString(R.string.error_something_wrong), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            binding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            binding.loginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    binding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            binding.loginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            binding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}

