package ch.srg.dataProvider.integrationlayer.request

import android.net.Uri
import java.io.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 *
 * @property name  designed name
 * @property value hostname of the integration layer url
 */
data class IlHost(
    val name: String,
    val value: String,
) : Serializable {
    val hostUri: Uri
        get() = Uri.parse("https://$value")

    override fun toString(): String {
        return name
    }

    companion object {
        private const val serialVersionUID = 1L

        @JvmField
        val PROD = IlHost("PROD", "il.srgssr.ch")

        @JvmField
        val TEST = IlHost("TEST", "il-test.srgssr.ch")

        @JvmField
        val STAGE = IlHost("STAGE", "il-stage.srgssr.ch")

        @JvmField
        val MMF = IlHost("MMF", "play-mmf.herokuapp.com/android_26CE9E49-9600")

        @JvmField
        val MMF_PUBLIC = IlHost("MMF", "play-mmf.herokuapp.com")

        @JvmField
        val PROD_SAM = IlHost("PROD_SAM", "il.srgssr.ch/sam")

        @JvmField
        val TEST_SAM = IlHost("TEST_SAM", "il-test.srgssr.ch/sam")

        @JvmField
        val STAGE_SAM = IlHost("STAGE_SAM", "il-stage.srgssr.ch/sam")
    }
}
