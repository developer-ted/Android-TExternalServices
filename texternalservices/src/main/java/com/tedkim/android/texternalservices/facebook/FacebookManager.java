package com.tedkim.android.texternalservices.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.tedkim.android.texternalservices.ServicesManager;
import com.tedkim.android.texternalservices.config.ExternalServiceConfig;
import com.tedkim.android.texternalservices.interfaces.OnSnsLoginListener;
import com.tedkim.android.texternalservices.utils.ExternalServiceUtils;

import java.io.File;

/**
 * Facebook Manager
 * Created by Ted
 */
public class FacebookManager {

    private static final String FACEBOOK_PACKAGE = "com.facebook.katana";
    private static final String TAG = FacebookManager.class.getSimpleName();

    private static FacebookManager mInstance;
    private Context mContext;

    private OnSnsLoginListener mOnSnsLoginListener;

    public static FacebookManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new FacebookManager();
        mInstance.mContext = context;
        return mInstance;
    }

    /**
     * Set OnSnsLoginListener
     *
     * @param listener OnSnsLoginListener
     * @return FacebookManager
     */
    public FacebookManager setListener(OnSnsLoginListener listener) {
        this.mOnSnsLoginListener = listener;
        return this;
    }

    // =============================================================================
    // Facebook auth(Login/Logout)
    // =============================================================================

    /**
     * Facebook Login
     */
    public void login() {
        Log.d(TAG, "[Facebook Login]");
        if (mOnSnsLoginListener == null)
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
        if (mOnSnsLoginListener == null)
            throw new NullPointerException("You must setListener");

        Intent intent = new Intent(mContext, FacebookActivity.class);
        intent.putExtra(FacebookActivity.ACTION, FacebookActivity.ACTION_LOGOUT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    // =============================================================================
    // Auth(Login/Logout) callback
    // =============================================================================
    public void onSuccessLogin(String token) {
        Log.d(TAG, "[onSuccessLogin] token : " + token);
        mOnSnsLoginListener.onSuccessLogin(ServicesManager.SERVICE_FACEBOOK, token);
    }

    public void onFailLogin(String message) {
        Log.d(TAG, "[onFailLogin] message : " + message);
        mOnSnsLoginListener.onFailLogin(ServicesManager.SERVICE_FACEBOOK, message);
    }

    public void onCancelLogin() {
        Log.d(TAG, "[onCancelLogin]");
        mOnSnsLoginListener.onCancelLogin(ServicesManager.SERVICE_FACEBOOK);
    }

    public void onSuccessLogout() {
        Log.d(TAG, "[onSuccessLogout]");
        mOnSnsLoginListener.onSuccessLogout(ServicesManager.SERVICE_FACEBOOK);
    }

    // =============================================================================
    // Set share option
    // =============================================================================

    /**
     * Facebook share url
     *
     * @param activity activity
     * @param title    title
     * @param desc     description
     * @param url      share url
     */
    public void shareUrl(Activity activity, String title, String desc, String url) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentUrl(Uri.parse(url))
                .setContentDescription(desc)
                .build();

        ShareDialog.show(activity, content);
    }

    /**
     * Facebook share photo using bitmap image
     *
     * @param activity activity
     * @param bitmap   bitmap
     */
    public static void sharePhoto(Activity activity, Bitmap bitmap) {
        if (ExternalServiceUtils.checkInstalled(activity, FACEBOOK_PACKAGE)) {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            ShareDialog.show(activity, content);
        }
    }

    /**
     * FIXME not installed check
     * Facebook share video using video path
     *
     * @param activity  activity
     * @param videoPath video path
     */
    public static void shareVideo(Activity activity, String videoPath) {
        if (ExternalServiceUtils.checkInstalled(activity, FACEBOOK_PACKAGE)) {
            File media = new File(videoPath);
            Uri uri = Uri.fromFile(media);

            Intent mIntent = new Intent(Intent.ACTION_SEND);
            mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
            mIntent.putExtra(Intent.EXTRA_STREAM, uri);
            mIntent.setPackage(FACEBOOK_PACKAGE);
            activity.startActivity(mIntent);
        }
    }


}