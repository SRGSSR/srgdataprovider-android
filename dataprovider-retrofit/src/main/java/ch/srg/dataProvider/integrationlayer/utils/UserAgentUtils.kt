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
    private const val UNKNOWN_VERSION = "?"

    fun createUserAgent(application: Context): String {
        var version: String
        var verCode: Long
        try {
            val pInfo = application.packageManager.getPackageInfo(application.packageName, 0)

            @Suppress("DEPRECATION")
            val versionCode = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) pInfo.longVersionCode else pInfo.versionCode.toLong()
            version = pInfo.versionName ?: UNKNOWN_VERSION
            verCode = versionCode
        } catch (ignored: PackageManager.NameNotFoundException) {
            version = UNKNOWN_VERSION
            verCode = 0
        }
        return application.packageName + " " + version + "/" + verCode + " (" + Build.MODEL + ")"
    }
}
