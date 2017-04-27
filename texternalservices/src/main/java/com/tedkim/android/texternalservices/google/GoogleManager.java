package com.tedkim.android.texternalservices.google;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tedkim.android.texternalservices.config.ExternalServiceConfig;
import com.tedkim.android.texternalservices.interfaces.OnSNSLoginListener;

/**
 * Created by Ted
 */
public class GoogleManager {

    private static final String TAG = GoogleManager.class.getSimpleName();
    public static final String ERROR_TIMEOUT = "ERROR_TIMEOUT";
    public static final String ERROR_FAIL = "ERROR_FAIL";

    private static GoogleManager mInstance;
    private Context mContext;

    private OnSNSLoginListener mOnSnsLoginListener;

    public static GoogleManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new GoogleManager();
        mInstance.mContext = context;
        return mInstance;
    }

    /**
     * Set OnSnsLoginListener
     *
     * @param listener OnSnsLoginListener
     * @return GoogleManager
     */
    public GoogleManager setListener(OnSNSLoginListener listener) {
        this.mOnSnsLoginListener = listener;
        return this;
    }

    /**
     * Google Login
     */
    public void login() {
        Log.d(TAG, "[Google Login]");
        if (mOnSnsLoginListener == null)
            throw new NullPointerException("You must setListener");

        Intent intent = new Intent(mContext, GoogleActivity.class);
        intent.putExtra(GoogleActivity.ACTION, GoogleActivity.ACTION_LOGIN);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * Google Logout
     */
    public void logout() {
        Log.d(TAG, "[Google Logout]");
        if (mOnSnsLoginListener == null)
            throw new NullPointerException("You must setListener");

        Intent intent = new Intent(mContext, GoogleActivity.class);
        intent.putExtra(GoogleActivity.ACTION, GoogleActivity.ACTION_LOGOUT);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void onSuccessLogin(String token) {
        Log.d(TAG, "[onSuccessLogin] token : " + token);
        mOnSnsLoginListener.onSuccessLogin(ExternalServiceConfig.SERVICE_GOOGLE, token);
    }

    public void onFailLogin(String message) {
        Log.d(TAG, "[onFailLogin] message : " + message);
        mOnSnsLoginListener.onFailLogin(ExternalServiceConfig.SERVICE_GOOGLE, message);
    }

    public void onCancelLogin() {
        Log.d(TAG, "[onCancelLogin]");
        mOnSnsLoginListener.onCancelLogin(ExternalServiceConfig.SERVICE_GOOGLE);
    }

    public void onSuccessLogout() {
        Log.d(TAG, "[onSuccessLogout]");
        mOnSnsLoginListener.onSuccessLogout(ExternalServiceConfig.SERVICE_GOOGLE);
    }

    public void onFailLogout() {
        Log.d(TAG, "[onFailLogout]");
        mOnSnsLoginListener.onFailLogout(ExternalServiceConfig.SERVICE_GOOGLE);
    }
}
