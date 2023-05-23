package ch.srg.dataProvider.integrationlayer.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */

public class UserAgentUtils {
    public static String createUserAgent(Context application) {
        String version;
        int verCode;
        try {
            PackageInfo pInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            version = pInfo.versionName;
            verCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
            version = "?";
            verCode = 0;
        }
        return application.getPackageName() + " " + version + "/" + verCode + " (" + Build.MODEL + ")";
    }
}
