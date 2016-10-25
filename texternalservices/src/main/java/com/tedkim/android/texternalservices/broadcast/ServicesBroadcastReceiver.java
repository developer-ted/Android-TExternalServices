package com.tedkim.android.texternalservices.broadcast;

/**
 * Services Broadcast Receiver
 * Created by Ted
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tedkim.android.texternalservices.config.ExternalServiceConfig;
import com.tedkim.android.texternalservices.facebook.FacebookManager;
import com.tedkim.android.texternalservices.google.GoogleManager;

/**
 * Services broadcast receiver
 */
public class ServicesBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_TYPE = "ACTION_TYPE";
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String LOGIN_FAIL = "LOGIN_FAIL";
    public static final String LOGIN_CANCEL = "LOGIN_CANCEL";
    public static final String LOGOUT_SUCCESS = "LOGOUT_SUCCESS";
    public static final String LOGOUT_FAIL = "LOGOUT_FAIL";

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getStringExtra(ExternalServiceConfig.SERVICE)) {
            case ExternalServiceConfig.SERVICE_FACEBOOK:
                onReceiveFacebook(context, intent);
                break;

            case ExternalServiceConfig.SERVICE_GOOGLE:
                onReceiveGoogle(context, intent);
                break;
        }
    }

    private void onReceiveFacebook(Context context, Intent intent) {
        switch (intent.getStringExtra(ACTION_TYPE)) {
            case LOGIN_SUCCESS:
                FacebookManager.getInstance(context).onSuccessLogin(intent.getStringExtra(ACCESS_TOKEN));
                break;

            case LOGIN_FAIL:
                FacebookManager.getInstance(context).onFailLogin(intent.getStringExtra(ERROR_MESSAGE));
                break;

            case LOGIN_CANCEL:
                FacebookManager.getInstance(context).onCancelLogin();
                break;

            case LOGOUT_SUCCESS:
                FacebookManager.getInstance(context).onSuccessLogout();
                break;
        }
    }

    private void onReceiveGoogle(Context context, Intent intent) {
        switch (intent.getStringExtra(ACTION_TYPE)) {
            case LOGIN_SUCCESS:
                GoogleManager.getInstance(context).onSuccessLogin(intent.getStringExtra(ACCESS_TOKEN));
                break;

            case LOGIN_FAIL:
                GoogleManager.getInstance(context).onFailLogin(intent.getStringExtra(ERROR_MESSAGE));
                break;

            case LOGIN_CANCEL:
                GoogleManager.getInstance(context).onCancelLogin();
                break;

            case LOGOUT_SUCCESS:
                GoogleManager.getInstance(context).onSuccessLogout();
                break;

            case LOGOUT_FAIL:
                GoogleManager.getInstance(context).onFailLogout();
                break;
        }
    }
}
