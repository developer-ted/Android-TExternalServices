package com.tedkim.android.texternalservices.line;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tedkim.android.texternalservices.config.ExternalServiceConfig;
import com.tedkim.android.texternalservices.utils.ExternalServiceUtils;

import java.io.File;

/**
 * Created by Ted
 */

public class LineManager {

    private static final String LINE_PACKAGE = "jp.naver.line.android";

    private static LineManager mInstance;

    private Activity mActivity;
    private Intent mIntent;

    public static LineManager getInstance(Activity activity) {
        if (mInstance == null)
            mInstance = new LineManager();
        mInstance.mActivity = activity;
        return mInstance;
    }

    // =============================================================================
    // Text share
    // =============================================================================
    /**
     * share Text
     */
    public void shareText(String text) {
        mIntent = new Intent(android.content.Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_TEXT_TYPE);
        mIntent.putExtra(Intent.EXTRA_TEXT, text);
        mIntent.setPackage(LINE_PACKAGE);
        share();
    }


    // =============================================================================
    // Image share
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
        mIntent.setPackage(LINE_PACKAGE);
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
        mIntent.setPackage(LINE_PACKAGE);
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
        mIntent.setPackage(LINE_PACKAGE);
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
        mIntent.setPackage(LINE_PACKAGE);
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
        mIntent.setPackage(LINE_PACKAGE);
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
        mIntent.setPackage(LINE_PACKAGE);
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
        if (ExternalServiceUtils.checkInstalled(mActivity, LINE_PACKAGE))
            mActivity.startActivity(mIntent);
    }

}
