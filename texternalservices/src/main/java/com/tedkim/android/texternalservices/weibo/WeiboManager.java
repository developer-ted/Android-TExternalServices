package com.tedkim.android.texternalservices.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tedkim.android.texternalservices.config.ExternalServiceConfig;
import com.tedkim.android.texternalservices.utils.ExternalServiceUtils;

import java.io.File;

/**
 * Weibo Manger
 * Created by Ted
 */

public class WeiboManager {

    private static final String WEIBO_URL = "https://weibo.com/";
    private static final String WEIBO_PACKAGE = "com.sina.weibo";

    public static void openApp(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_TEXT_TYPE);
        intent.setPackage(WEIBO_PACKAGE);
        activity.startActivity(intent);
    }

    /**
     * Check weibo app installed
     *
     * @param context context
     * @return installed
     */
    public static boolean checkInstalledApp(Context context) {
        return ExternalServiceUtils.checkInstalled(context, WEIBO_PACKAGE);
    }

    /**
     * Open weibo web site
     *
     * @param context context
     */
    public static void openWebSite(Context context) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WEIBO_URL)));
    }

    // =============================================================================
    // Photo share
    // =============================================================================
    /**
     * Share photo using photo path
     *
     * @param photoPath photo path
     */
    public static void sharePhoto(Activity activity, String photoPath) {
        File media = new File(photoPath);
        Uri uri = Uri.fromFile(media);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TITLE, "title");
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.setPackage(WEIBO_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share photo using file
     *
     * @param file photo file
     */
    public static void sharePhoto(Activity activity, File file) {
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(WEIBO_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share photo using uri
     *
     * @param uri photo uri
     */
    public static void sharePhoto(Activity activity, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(WEIBO_PACKAGE);
        share(activity, intent);
    }
    // =============================================================================
    // Photo share
    // =============================================================================
    /**
     * Share photo using photo path with message
     *
     * @param photoPath photo path
     */
    public static void sharePhotoMessage(Activity activity, String photoPath, String message) {
        File media = new File(photoPath);
        Uri uri = Uri.fromFile(media);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setPackage(WEIBO_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share photo using file with message
     *
     * @param file photo file
     */
    public static void sharePhoto(Activity activity, File file, String message) {
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setPackage(WEIBO_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share photo using uri with message
     *
     * @param uri photo uri
     */
    public static void sharePhoto(Activity activity, Uri uri, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setPackage(WEIBO_PACKAGE);
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
    public static void shareVideo(Activity activity, String videoPath) {
        File media = new File(videoPath);
        Uri uri = Uri.fromFile(media);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(WEIBO_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share video
     *
     * @param file video file
     */
    public static void shareVideo(Activity activity, File file) {
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(WEIBO_PACKAGE);
        share(activity, intent);
    }

    /**
     * Share video
     *
     * @param uri video uri
     */
    public static void shareVideo(Activity activity, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage(WEIBO_PACKAGE);
        share(activity, intent);
    }

    // =============================================================================
    // Share
    // =============================================================================
    /**
     * FIXME not installed check
     * Instagram share
     */
    private static void share(Activity activity, Intent intent) {
        if (ExternalServiceUtils.checkInstalled(activity, WEIBO_PACKAGE))
            activity.startActivity(intent);
    }


}
