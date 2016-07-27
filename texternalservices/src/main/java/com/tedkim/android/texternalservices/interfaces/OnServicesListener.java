package com.tedkim.android.texternalservices.interfaces;

/**
 * Created by Ted
 */
public class OnServicesListener {

    public void onSuccessLogin(String service, String token) {}
    public void onFailLogin(String service, String message) {}
    public void onCancelLogin(String service) {}
    public void onSuccessLogout(String service) {}
    public void onFailLogout(String service) {}

}
