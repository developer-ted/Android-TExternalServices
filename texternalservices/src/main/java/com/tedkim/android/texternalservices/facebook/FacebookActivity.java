package com.tedkim.android.texternalservices.facebook;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.tedkim.android.texternalservices.R;
import com.tedkim.android.texternalservices.broadcast.ServicesBroadcastReceiver;
import com.tedkim.android.texternalservices.config.ExternalServiceConfig;

import java.util.Arrays;
import java.util.Set;

/**
 * Facebook Activity
 * Created by Ted
 */
public class FacebookActivity extends AppCompatActivity {

    private static final String TAG = FacebookActivity.class.getSimpleName();

    public static final String ACTION = "ACTION";
    public static final String ACTION_LOGIN = "ACTION_LOGIN";
    public static final String ACTION_LOGOUT = "ACTION_LOGOUT";

    private CallbackManager callbackManager;

    private String[] permissionArray = new String[]{"public_profile", "email"};

    private ServicesBroadcastReceiver mServicesBroadcastReceiver;

    private String mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initFacebook();
    }

    private void initData() {
        Intent intent = getIntent();
        mAction = intent.getStringExtra(ACTION);

        mServicesBroadcastReceiver = new ServicesBroadcastReceiver();
        registerReceiver(mServicesBroadcastReceiver, new IntentFilter());
    }

    private void initFacebook() {
        callbackManager = CallbackManager.Factory.create();

        switch (mAction) {
            case ACTION_LOGIN:
                login();
                break;

            case ACTION_LOGOUT:
                logout();
                break;
        }
    }

    /**
     * Facebook Login
     */
    public void login() {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(this, Arrays.asList(permissionArray[0], permissionArray[1])); //기본정보 반환
        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        successLogin(loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        cancelLogin();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        failLogin(error.toString());
                    }
                });
    }

    /**
     * Facebook Logout
     */
    public void logout() {
        Log.d(TAG, "[logout]");
        // TODO isLogin
//        if (isLogin()) {
        if (LoginManager.getInstance() != null)
            LoginManager.getInstance().logOut();

        successLogout();
//        }
    }

    /**
     * check Login status
     *
     * @return login status / logout status
     */
    public boolean isLogin() {
        boolean isLogin = false;

        if (getAccessToken() != null) {
            isLogin = true;
        }
        Log.d(TAG, "isLogin : " + isLogin);
        return isLogin;
    }


    /**
     * Get facebook access token
     *
     * @return access token
     */
    public AccessToken getAccessToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            return null;
        }

        Set<String> permissionSet = accessToken.getPermissions();
        if (permissionSet != null) {
            for (String aPermissionArray : permissionArray) {
                if (!permissionSet.contains(aPermissionArray)) {
                    // TODO Permission Fail
                    failLogin(ServicesBroadcastReceiver.LOGIN_FAIL);
                    return null;
                }
            }
        }
        return accessToken;
    }

    /**
     * Success login
     *
     * @param token access token
     */
    private void successLogin(String token) {
        Intent intent = new Intent();
        intent.setAction(getString(R.string.intent_action));
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_FACEBOOK);
        intent.putExtra(ServicesBroadcastReceiver.ACTION_TYPE, ServicesBroadcastReceiver.LOGIN_SUCCESS);
        intent.putExtra(ServicesBroadcastReceiver.ACCESS_TOKEN, token);

        destroyActivity(intent);
    }

    /**
     * Cancel login
     */
    private void cancelLogin() {
        Intent intent = new Intent();
        intent.setAction(getString(R.string.intent_action));
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_FACEBOOK);
        intent.putExtra(ServicesBroadcastReceiver.ACTION_TYPE, ServicesBroadcastReceiver.LOGIN_CANCEL);

        destroyActivity(intent);
    }

    /**
     * Fail login
     *
     * @param message fail message
     */
    private void failLogin(String message) {
        Intent intent = new Intent();
        intent.setAction(getString(R.string.intent_action));
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_FACEBOOK);
        intent.putExtra(ServicesBroadcastReceiver.ACTION_TYPE, ServicesBroadcastReceiver.LOGIN_FAIL);
        intent.putExtra(ServicesBroadcastReceiver.ERROR_MESSAGE, message);

        destroyActivity(intent);
    }

    /**
     * Success logout
     */
    private void successLogout() {
        Intent intent = new Intent();
        intent.setAction(getString(R.string.intent_action));
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_FACEBOOK);
        intent.putExtra(ServicesBroadcastReceiver.ACTION_TYPE, ServicesBroadcastReceiver.LOGOUT_SUCCESS);

        destroyActivity(intent);
    }

    /**
     * Activity destroy
     *
     * @param intent intent data
     */
    private void destroyActivity(Intent intent) {
        sendBroadcast(intent);
        unregisterReceiver(mServicesBroadcastReceiver);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult");
        Log.d(TAG, "- requestCode = " + requestCode + "/ resultCode = " + resultCode);

        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
