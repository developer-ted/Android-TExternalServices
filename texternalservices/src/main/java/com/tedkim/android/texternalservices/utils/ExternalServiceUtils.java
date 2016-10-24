package com.tedkim.android.texternalservices.utils;

import android.content.Context;

/**
 * Util class
 * Created by Ted
 */

public class ExternalServiceUtils {

    /**
     * Make sure the service is installed
     * @param context context
     * @param packageName package name
     * @return installed / not installed
     */
    public static boolean checkInstalled(Context context, String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }

}
