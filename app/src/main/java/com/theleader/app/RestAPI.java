package com.theleader.app;

import android.content.Context;

import com.R.volleyhelper.VolleyHelper;

import org.json.JSONObject;

import java.util.HashMap;

import R.helper.Callback;
import R.helper.CallbackResult;

/**
 * Created by duynk on 3/15/16.
 */
public class RestAPI {
    public enum ELoginError implements CallbackResult.ICallbackError {
        ANY(-1),
        WRONG_CREDENTIAL (10003)

        ;
        private final int code;

        ELoginError(int code) {
            this.code = code;
        }

        @Override
        public boolean is(int code) {
            return this.code == code;
        }

        @Override
        public boolean is(CallbackResult.ICallbackError code) {
            return this.code == code.getCode();
        }

        @Override
        public int getCode() {return this.code;}

        @Override
        public String getMessage() {return "";}
    }


    public static class Credential {
        private String userId;
        private String token;
    }
    public static Credential auth;

    public final static String ENDPOINT = "http://theleader1.meteor.com/api/";
    public static void login(Context context, String email, String password, final Callback callbackResult) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        VolleyHelper.post(context, ENDPOINT + "user/login", null, data, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callbackResult.onCompleted(context, CallbackResult.error(ELoginError.ANY));
                } else {
                    try {
                        JSONObject re = (JSONObject) result.getData();

                            if ("SUCCESS".equalsIgnoreCase(re.getString("code"))) {
                                auth = new Credential();
                                JSONObject payload = re.getJSONObject("payload");
                                auth.userId = payload.getString("userId");
                                auth.token = payload.getString("token");
                                callbackResult.onCompleted(context, CallbackResult.success());
                            } else {
                                callbackResult.onCompleted(context, CallbackResult.error(ELoginError.WRONG_CREDENTIAL));
                            }


                    } catch (Exception E) {
                        callbackResult.onCompleted(context, CallbackResult.error(ELoginError.ANY));
                    }
                }
            }
        });
    }
}
