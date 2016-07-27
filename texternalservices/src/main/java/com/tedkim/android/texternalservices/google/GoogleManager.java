package com.tedkim.android.texternalservices.google;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tedkim.android.texternalservices.ServicesManager;
import com.tedkim.android.texternalservices.interfaces.OnServicesListener;

/**
 * Created by Ted
 */
public class GoogleManager {

    private static final String TAG = GoogleManager.class.getSimpleName();
    public static final String ERROR_TIMEOUT = "ERROR_TIMEOUT";
    public static final String ERROR_FAIL = "ERROR_FAIL";

    private static GoogleManager mInstance;
    private Context mContext;

    private OnServicesListener mOnServicesListener;

    public static GoogleManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new GoogleManager();
        mInstance.mContext = context;
        return mInstance;
    }

    /**
     * Set OnServicesListener
     *
     * @param listener OnServicesListener
     * @return GoogleManager
     */
    public GoogleManager setListener(OnServicesListener listener) {
        this.mOnServicesListener = listener;
        return this;
    }

    /**
     * Google Login
     */
    public void login() {
        Log.d(TAG, "[Google Login]");
        if (mOnServicesListener != null)
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
        if (mOnServicesListener != null)
            throw new NullPointerException("You must setListener");

        Intent intent = new Intent(mContext, GoogleActivity.class);
        intent.putExtra(GoogleActivity.ACTION, GoogleActivity.ACTION_LOGOUT);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void onSuccessLogin(String token) {
        Log.d(TAG, "[onSuccessLogin] token : " + token);
        mOnServicesListener.onSuccessLogin(ServicesManager.SERVICE_GOOGLE, token);
    }

    public void onFailLogin(String message) {
        Log.d(TAG, "[onFailLogin] message : " + message);
        mOnServicesListener.onFailLogin(ServicesManager.SERVICE_GOOGLE, message);
    }

    public void onCancelLogin() {
        Log.d(TAG, "[onCancelLogin]");
        mOnServicesListener.onCancelLogin(ServicesManager.SERVICE_GOOGLE);
    }

    public void onSuccessLogout() {
        Log.d(TAG, "[onSuccessLogout]");
        mOnServicesListener.onSuccessLogout(ServicesManager.SERVICE_GOOGLE);
    }

    public void onFailLogout() {
        Log.d(TAG, "[onFailLogout]");
        mOnServicesListener.onFailLogout(ServicesManager.SERVICE_GOOGLE);
    }
}
