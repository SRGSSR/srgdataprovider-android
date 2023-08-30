package ch.srg.dataProvider.integrationlayer

import android.net.Uri
import ch.srg.dataProvider.integrationlayer.request.IlHost

/**
 * SRG Url factory
 *
 * @param ilHost IlHost to create service urls.
 */
class SRGUrlFactory(ilHost: IlHost) {
    val hostUri: Uri = ilHost.hostUri
    val ilBaseUrl: Uri = Uri.parse("${ilHost.hostUri}/integrationlayer/")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SRGUrlFactory

        if (hostUri != other.hostUri) return false
        if (ilBaseUrl != other.ilBaseUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hostUri.hashCode()
        result = 31 * result + ilBaseUrl.hashCode()
        return result
    }

    override fun toString(): String {
        return "SRGUrlFactory(hostUri=$hostUri, ilBaseUrl=$ilBaseUrl)"
    }
}
