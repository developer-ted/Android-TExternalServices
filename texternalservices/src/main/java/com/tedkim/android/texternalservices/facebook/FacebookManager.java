package com.tedkim.android.texternalservices.facebook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tedkim.android.texternalservices.ServicesManager;
import com.tedkim.android.texternalservices.interfaces.OnServicesListener;

/**
 * Facebook Manager
 * Created by Ted
 */
public class FacebookManager {

    private static final String TAG = FacebookManager.class.getSimpleName();

    private static FacebookManager mInstance;
    private Context mContext;

    private OnServicesListener mOnServicesListener;

    public static FacebookManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new FacebookManager();
        mInstance.mContext = context;
        return mInstance;
    }

    /**
     * Set OnServicesListener
     *
     * @param listener OnServicesListener
     * @return FacebookManager
     */
    public FacebookManager setListener(OnServicesListener listener) {
        this.mOnServicesListener = listener;
        return this;
    }

    /**
     * Facebook Login
     */
    public void login() {
        Log.d(TAG, "[Facebook Login]");
        if (mOnServicesListener != null)
            throw new NullPointerException("You must setListener");

        Intent intent = new Intent(mContext, FacebookActivity.class);
        intent.putExtra(FacebookActivity.ACTION, FacebookActivity.ACTION_LOGIN);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * Facebook Logout
     */
    public void logout() {
        Log.d(TAG, "[Facebook Logout]");
        if (mOnServicesListener != null)
            throw new NullPointerException("You must setListener");

        Intent intent = new Intent(mContext, FacebookActivity.class);
        intent.putExtra(FacebookActivity.ACTION, FacebookActivity.ACTION_LOGOUT);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void onSuccessLogin(String token) {
        Log.d(TAG, "[onSuccessLogin] token : " + token);
        mOnServicesListener.onSuccessLogin(ServicesManager.SERVICE_FACEBOOK, token);
    }

    public void onFailLogin(String message) {
        Log.d(TAG, "[onFailLogin] message : " + message);
        mOnServicesListener.onFailLogin(ServicesManager.SERVICE_FACEBOOK, message);
    }

    public void onCancelLogin() {
        Log.d(TAG, "[onCancelLogin]");
        mOnServicesListener.onCancelLogin(ServicesManager.SERVICE_FACEBOOK);
    }

    public void onSuccessLogout() {
        Log.d(TAG, "[onSuccessLogout]");
        mOnServicesListener.onSuccessLogout(ServicesManager.SERVICE_FACEBOOK);
    }
}