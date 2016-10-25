package com.tedkim.android.texternalservices.instagram;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tedkim.android.texternalservices.config.ExternalServiceConfig;
import com.tedkim.android.texternalservices.utils.ExternalServiceUtils;

import java.io.File;

/**
 * Instagram Manger
 * Created by Ted
 */

public class InstagramManager {

    private static final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private static InstagramManager mInstance;

    private Activity mActivity;
    private Intent mIntent;

    public static InstagramManager getInstance(Activity activity) {
        if (mInstance == null)
            mInstance = new InstagramManager();
        mInstance.mActivity = activity;
        return mInstance;
    }

    // =============================================================================
    // Photo share
    // =============================================================================
    /**
     * Share photo using photo path
     *
     * @param photoPath photo path
     */
    public void sharePhoto(String photoPath) {
        File media = new File(photoPath);
        Uri uri = Uri.fromFile(media);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(INSTAGRAM_PACKAGE);
        share();
    }

    /**
     * Share photo using file
     *
     * @param file photo file
     */
    public void sharePhoto(File file) {
        Uri uri = Uri.fromFile(file);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(INSTAGRAM_PACKAGE);
        share();
    }

    /**
     * Share photo using uri
     *
     * @param uri photo uri
     */
    public void sharePhoto(Uri uri) {
        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(INSTAGRAM_PACKAGE);
        share();
    }

    // =============================================================================
    // Video share
    // =============================================================================
    /**
     * Share video
     *
     * @param videoPath video path
     */
    public void shareVideo(String videoPath) {
        File media = new File(videoPath);
        Uri uri = Uri.fromFile(media);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(INSTAGRAM_PACKAGE);
        share();
    }

    /**
     * Share video
     *
     * @param file video file
     */
    public void shareVideo(File file) {
        Uri uri = Uri.fromFile(file);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(INSTAGRAM_PACKAGE);
        share();
    }

    /**
     * Share video
     *
     * @param uri video uri
     */
    public void shareVideo(Uri uri) {
        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(INSTAGRAM_PACKAGE);
        share();
    }

    // =============================================================================
    // Share
    // =============================================================================
    /**
     * FIXME not installed check
     * Instagram share
     */
    private void share() {
        if (ExternalServiceUtils.checkInstalled(mActivity, INSTAGRAM_PACKAGE))
            mActivity.startActivity(mIntent);
    }


}
