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
        mOnSnsLoginListener.onSuccessLogin(ExternalServiceConfig.SERVICE_FACEBOOK, token);
    }

    public void onFailLogin(String message) {
        Log.d(TAG, "[onFailLogin] message : " + message);
        mOnSnsLoginListener.onFailLogin(ExternalServiceConfig.SERVICE_FACEBOOK, message);
    }

    public void onCancelLogin() {
        Log.d(TAG, "[onCancelLogin]");
        mOnSnsLoginListener.onCancelLogin(ExternalServiceConfig.SERVICE_FACEBOOK);
    }

    public void onSuccessLogout() {
        Log.d(TAG, "[onSuccessLogout]");
        mOnSnsLoginListener.onSuccessLogout(ExternalServiceConfig.SERVICE_FACEBOOK);
    }

    // =============================================================================
    // Share Link
    // =============================================================================
    /**
     * Facebook share url
     *
     * @param activity activity
     * @param title    title
     * @param desc     description
     * @param url      share url
     */
    public static void shareLink(Activity activity, String title, String desc, String url) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentUrl(Uri.parse(url))
                .setContentDescription(desc)
                .build();

        ShareDialog.show(activity, content);
    }

    /**
     * Facebook share url and image
     *
     * @param activity activity
     * @param imageUrl link image url
     * @param title    link title
     * @param desc     link description
     * @param url      share url
     */
    public static void shareLink(Activity activity, String imageUrl, String title, String desc, String url) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentUrl(Uri.parse(url))
                .setContentDescription(desc)
                .setImageUrl(Uri.parse(imageUrl))
                .build();

        ShareDialog.show(activity, content);
    }

    // =============================================================================
    // Share photo
    // =============================================================================

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
//                    .setShareHashtag(new ShareHashtag.Builder()
//                            .setHashtag("#Charis #HICHARIS")
//                            .build())
                    .build();
            ShareDialog.show(activity, content);
        }
    }

    // =============================================================================
    // Share video
    // =============================================================================

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

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TITLE, "title");
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
            intent.putExtra(Intent.EXTRA_TEXT, "text");
            intent.setPackage(FACEBOOK_PACKAGE);
            activity.startActivity(intent);
        }
    }



}