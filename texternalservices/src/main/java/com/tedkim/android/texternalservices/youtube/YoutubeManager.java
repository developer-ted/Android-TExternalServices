package com.tedkim.android.texternalservices.youtube;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tedkim.android.texternalservices.config.ExternalServiceConfig;
import com.tedkim.android.texternalservices.utils.ExternalServiceUtils;

import java.io.File;

/**
 * Youtube Manager
 * Created by Ted
 */

public class YoutubeManager {

    private static final String YOUTUBE_PACKAGE = "com.google.android.youtube";
    private static YoutubeManager mInstance;

    private Activity mActivity;
    private Intent mIntent;

    public static YoutubeManager getInstance(Activity activity) {
        if (mInstance == null)
            mInstance = new YoutubeManager();
        mInstance.mActivity = activity;
        return mInstance;
    }

    // =============================================================================
    // Set share option
    // =============================================================================

    /**
     * Set posting  title
     * @param title title
     * @return YoutubeManager
     */
    public YoutubeManager setTitle(String title) {
        if (mIntent != null)
            mIntent.putExtra(Intent.EXTRA_TITLE, title);
        return this;
    }

    /**
     * Set posting description
     * @param description description
     * @return YoutubeManager
     */
    public YoutubeManager setDescription(String description) {
        if (mIntent != null)
            mIntent.putExtra(Intent.EXTRA_SUBJECT, description);
        return this;
    }

    /**
     * Set posting tag
     * @param tag tag
     * @return YoutubeManager
     */
    public YoutubeManager setTag(String tag) {
        if (mIntent != null)
            mIntent.putExtra(Intent.EXTRA_TEXT, tag);
        return this;
    }

    // =============================================================================
    // Video share
    // =============================================================================
    /**
     * Share video using video path
     *
     * @param videoPath video path
     */
    public YoutubeManager shareVideo(String videoPath) {
        File media = new File(videoPath);
        Uri uri = Uri.fromFile(media);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(YOUTUBE_PACKAGE);
        return this;
    }

    /**
     * Share video
     *
     * @param file video file
     */
    public YoutubeManager shareVideo(File file) {
        Uri uri = Uri.fromFile(file);

        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(YOUTUBE_PACKAGE);
        return this;
    }

    /**
     * Share video using uri
     *
     * @param uri video uri
     */
    public YoutubeManager shareVideo(Uri uri) {
        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setType(ExternalServiceConfig.INTENT_VIDEO_TYPE);
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mIntent.setPackage(YOUTUBE_PACKAGE);
        return this;
    }

    // =============================================================================
    // Share
    // =============================================================================
    /**
     * FIXME not installed check
     * Youtube share
     */
    public void share() {
        if (ExternalServiceUtils.checkInstalled(mActivity, YOUTUBE_PACKAGE))
            mActivity.startActivity(mIntent);

    }
}
