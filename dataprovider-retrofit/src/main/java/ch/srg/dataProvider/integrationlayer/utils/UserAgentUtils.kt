package ch.srg.dataProvider.integrationlayer.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
object UserAgentUtils {

    fun createUserAgent(application: Context): String {
        var version: String
        var verCode: Int
        try {
            val pInfo = application.packageManager.getPackageInfo(application.packageName, 0)
            version = pInfo.versionName
            verCode = pInfo.versionCode
        } catch (ignored: PackageManager.NameNotFoundException) {
            version = "?"
            verCode = 0
        }
        return application.packageName + " " + version + "/" + verCode + " (" + Build.MODEL + ")"
    }
}
