package com.tedkim.android.texternalservices.google;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.tedkim.android.texternalservices.R;
import com.tedkim.android.texternalservices.broadcast.ServicesBroadcastReceiver;

/**
 * Created by Ted
 */
public class GoogleActivity extends AppCompatActivity {

    private static final String TAG = GoogleActivity.class.getSimpleName();

    public static final String ACTION = "ACTION";
    public static final String ACTION_LOGIN = "ACTION_LOGIN";
    public static final String ACTION_LOGOUT = "ACTION_LOGOUT";

    public static final int REQUEST_CODE_GOOGLE_SIGN_IN = 1829;

    private GoogleApiClient mGoogleApiClient;

    private ServicesBroadcastReceiver mServicesBroadcastReceiver;

    private int mSignInTrialCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        mSignInTrialCount = 0;
        Intent intent = getIntent();
        String action = intent.getStringExtra(ACTION);

        mServicesBroadcastReceiver = new ServicesBroadcastReceiver();
        registerReceiver(mServicesBroadcastReceiver, new IntentFilter());

        switch (action) {
            case ACTION_LOGIN:
                login();
                break;

            case ACTION_LOGOUT:
                logout();
                break;
        }
    }

    /**
     * init google
     */
    private void initGoogle() {
        Scope scopePlusLogin = new Scope(Scopes.PLUS_LOGIN);
        Scope scopeEmail = new Scope(Scopes.EMAIL);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(scopePlusLogin, scopeEmail)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(GoogleActivity.this)
                .enableAutoManage(GoogleActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        failLogin(connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * Google login
     */
    private void login() {
        if (mGoogleApiClient == null) {
            Log.d(TAG, "[login] no GoogleApiClient");

            initGoogle();
            retryRequest(true);
        } else {
            if (mGoogleApiClient.isConnected()) {
                Log.d(TAG, "[login] Connected!");

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN);
            } else {
                mGoogleApiClient = null;
                if (mSignInTrialCount < 5) {
                    Log.d(TAG, "- Sign in try again");
                    retryRequest(true);
                } else {
                    Log.d(TAG, "- Sign in failed...");
                    mSignInTrialCount = 0;//초기화
                    failLogin(GoogleManager.ERROR_TIMEOUT);
                }
            }
        }
    }

    /**
     * Google Logout
     */
    public void logout() {
        if (mGoogleApiClient == null) {
            Log.d(TAG, "[logout] no GoogleApiClient");

            initGoogle();
            retryRequest(false);
        } else {
            if (mGoogleApiClient.isConnected()) {
                Log.d(TAG, "[logout] Connected!");

                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    Log.d(TAG, "[Logout] onResult : " + status.getStatusMessage());
                                    mGoogleApiClient.clearDefaultAccountAndReconnect();//재로그인 시 계정리스트 선택하기 위해 기존 정보 값 삭제
                                    successLogout();
                                }
                            }
                    );
                }
            } else {
                mGoogleApiClient = null;
                if (mSignInTrialCount < 5) {
                    Log.d(TAG, "- Sign in try again");
                    retryRequest(false);
                } else {
                    Log.d(TAG, "- Sign in failed...");
                    mSignInTrialCount = 0;//초기화
                    failLogout();
                }
            }
        }
    }

    /**
     * Login/Logout retry
     */
    private void retryRequest(final boolean isLogin) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSignInTrialCount++;
                if (isLogin)
                    login();
                else
                    logout();
            }
        }, 1000);
    }

    /**
     * Success login
     *
     * @param token access token
     */
    private void successLogin(String token) {
        Intent intent = new Intent();
        intent.setAction(getString(R.string.intent_action));
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_GOOGLE);
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
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_GOOGLE);
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
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_GOOGLE);
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
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_GOOGLE);
        intent.putExtra(ServicesBroadcastReceiver.ACTION_TYPE, ServicesBroadcastReceiver.LOGOUT_SUCCESS);

        destroyActivity(intent);
    }

    /**
     * Fail logout
     */
    private void failLogout() {
        Intent intent = new Intent();
        intent.setAction(getString(R.string.intent_action));
        intent.putExtra(ExternalServiceConfig.SERVICE, ExternalServiceConfig.SERVICE_GOOGLE);
        intent.putExtra(ServicesBroadcastReceiver.ACTION_TYPE, ServicesBroadcastReceiver.LOGOUT_FAIL);

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

        switch (requestCode) {
            case REQUEST_CODE_GOOGLE_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);
                } else {
                    cancelLogin();
                }

                break;
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result != null) {
            Log.d(TAG, "[handleSignInResult] result : " + result.isSuccess());
            if (result.isSuccess()) {
                new GetTokenTask(result.getSignInAccount().getEmail()).execute();
            } else {
                failLogin(GoogleManager.ERROR_FAIL);
            }
        } else {
            Log.d(TAG, "[handleSignInResult] fail");
            failLogin(GoogleManager.ERROR_FAIL);
        }
    }

    /**
     * Get token async task
     */
    private class GetTokenTask extends AsyncTask<Void, Void, String> {

        private String accountName;

        private GetTokenTask(String name) {
            this.accountName = name;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                android.accounts.Account account = new android.accounts.Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
                String scopes = "oauth2:" + "https://www.googleapis.com/auth/plus.login";
                return GoogleAuthUtil.getToken(GoogleActivity.this, account, scopes);
            } catch (UserRecoverableAuthException userAuthEx) {
                startActivityForResult(userAuthEx.getIntent(), REQUEST_CODE_GOOGLE_SIGN_IN);
                return null;
            } catch (Exception e) {
                e.printStackTrace(); // Uncomment if needed during debugging.
            }
            return null;
        }

        @Override
        protected void onPostExecute(String info) {
            successLogin(info);
        }
    }
}
