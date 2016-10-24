package com.tedkim.android.texternalservices;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tedkim.android.texternalservices.config.ExternalServiceConfig;

import java.io.File;

/**
 * Common Share manager
 * Created by Ted
 */

public class ShareManager {

    private static ShareManager mInstance;

    private Activity mActivity;
    private Intent mIntent;
    private String mDialogTitle;

    public static ShareManager getInstance(Activity activity) {
        if (mInstance == null)
            mInstance = new ShareManager();
        mInstance.mActivity = activity;
        return mInstance;
    }

    // =============================================================================
    // Video share
    // =============================================================================

    /**
     * Set dialog title
     *
     * @param title title
     * @return ShareManager
     */
    public ShareManager setDialogTitle(String title) {
        mDialogTitle = title;
        return this;
    }

    /**
     * Set posting  title
     *
     * @param title title
     * @return ShareManager
     */
    public ShareManager setTitle(String title) {
        if (mIntent != null)
            mIntent.putExtra(Intent.EXTRA_TITLE, title);
        return this;
    }

    /**
     * Set posting description
     *
     * @param subject description
     * @return ShareManager
     */
    public ShareManager setSubject(String subject) {
        if (mIntent != null)
            mIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        return this;
    }

    /**
     * Set posting tag
     *
     * @param text tag
     * @return ShareManager
     */
    public ShareManager setText(String text) {
        if (mIntent != null)
            mIntent.putExtra(Intent.EXTRA_TEXT, text);
        return this;
    }

    // =============================================================================
    // Text share
    // =============================================================================

    /**
     * share Text
     */
    public ShareManager shareText() {
        mIntent = new Intent(android.content.Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_TEXT_TYPE);
        return this;
    }

    // =============================================================================
    // Image share
    // =============================================================================
    /**
     * Share photo using photo path
     *
     * @param photoPath photo path
     * @return ShareManager
     */
    public ShareManager sharePhoto(String photoPath) {
        File media = new File(photoPath);
        Uri uri = Uri.fromFile(media);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return this;
    }

    /**
     * Share photo using file
     *
     * @param file photo file
     * @return ShareManager
     */
    public ShareManager sharePhoto(File file) {
        Uri uri = Uri.fromFile(file);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return this;
    }

    /**
     * Share photo using uri
     *
     * @param uri photo uri
     * @return ShareManager
     */
    public ShareManager sharePhoto(Uri uri) {
        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return this;
    }

    // =============================================================================
    // Video share
    // =============================================================================
    /**
     * Share video
     *
     * @param videoPath video path
     * @return ShareManager
     */
    public ShareManager shareVideo(String videoPath) {
        File media = new File(videoPath);
        Uri uri = Uri.fromFile(media);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return this;
    }

    /**
     * Share video
     *
     * @param file video file
     * @return ShareManager
     */
    public ShareManager shareVideo(File file) {
        Uri uri = Uri.fromFile(file);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return this;
    }

    /**
     * Share video
     *
     * @param uri video uri
     * @return ShareManager
     */
    public ShareManager shareVideo(Uri uri) {
        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return this;
    }
    
    // =============================================================================
    // Share
    // =============================================================================

    /**
     * Common share
     */
    public void share() {
        if (mIntent == null) {
            throw new NullPointerException("You must share method");
        } else {
            if (mDialogTitle == null)
                throw new NullPointerException("You must setDialogTitle()");
            else
                mActivity.startActivity(Intent.createChooser(mIntent, mDialogTitle));
        }
    }
}
