package com.tedkim.android.texternalservices.qq;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tedkim.android.texternalservices.config.ExternalServiceConfig;
import com.tedkim.android.texternalservices.utils.ExternalServiceUtils;

import java.io.File;

/**
 * Wechat Manger
 * Created by Ted
 */

public class QQManager {

    private static final String QQ_PACKAGE = "com.tencent.mobileqq";

    public static void openApp(Activity activity) {
        if (ExternalServiceUtils.checkInstalled(activity, QQ_PACKAGE)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType(ExternalServiceConfig.INTENT_TEXT_TYPE);
            intent.setPackage(QQ_PACKAGE);
            activity.startActivity(intent);
        } else {
//            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL)));
        }
    }

    // =============================================================================
    // Photo share
    // =============================================================================
    /**
     * Share photo using photo path
     *
     * @param photoPath photo path
     */
    public void sharePhoto(Activity activity, String photoPath) {
        File media = new File(photoPath);
        Uri uri = Uri.fromFile(media);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TITLE, "title");
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "text");
        intent.setPackage(QQ_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share photo using file
     *
     * @param file photo file
     */
    public void sharePhoto(Activity activity, File file) {
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(QQ_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share photo using uri
     *
     * @param uri photo uri
     */
    public void sharePhoto(Activity activity, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(QQ_PACKAGE);
        share(activity, intent);
    }

    // =============================================================================
    // Video share
    // =============================================================================
    /**
     * Share video
     *
     * @param videoPath video path
     */
    public void shareVideo(Activity activity, String videoPath) {
        File media = new File(videoPath);
        Uri uri = Uri.fromFile(media);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(QQ_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share video
     *
     * @param file video file
     */
    public void shareVideo(Activity activity, File file) {
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(QQ_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share video
     *
     * @param uri video uri
     */
    public void shareVideo(Activity activity, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(QQ_PACKAGE);
        share(activity, intent);
    }

    // =============================================================================
    // Share
    // =============================================================================
    /**
     * FIXME not installed check
     * Instagram share
     */
    private void share(Activity activity, Intent intent) {
        if (ExternalServiceUtils.checkInstalled(activity, QQ_PACKAGE))
            activity.startActivity(intent);
    }


}
