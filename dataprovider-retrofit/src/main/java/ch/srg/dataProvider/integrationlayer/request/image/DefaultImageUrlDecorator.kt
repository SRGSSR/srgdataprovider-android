package ch.srg.dataProvider.integrationlayer.request.image

import android.net.Uri
import ch.srg.dataProvider.integrationlayer.data.ImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.IlHost

/**
 * Il host image url decorator
 *
 * If the image url isn't supported by [DefaultImageUrlDecorator] the same url is returned.
 *
 * Confluence doc: https://srgssr-ch.atlassian.net/wiki/spaces/SRGPLAY/pages/799082429/Project+-+Image+Service
 *
 * @param ilHost The [IlHost] of the integration layer image service.
 */
class DefaultImageUrlDecorator(ilHost: IlHost) : ImageUrlDecorator {
    private val imageServiceUri = ilHost.hostUri.buildUpon().appendEncodedPath(IMAGES_SEGMENT).build()

    override fun decorate(sourceUrl: String, widthPixels: Int): String {
        // Il image service only support some image url hostnames!
        if (!isImageUrlHostCompatible(sourceUrl)) return sourceUrl
        // Il image service only support a limited image size!
        val imageWidth = ImageWidth.getFromPixels(widthPixels)
        return imageServiceUri.buildUpon()
            .appendQueryParameter(PARAM_IMAGE_URL, sourceUrl)
            .appendQueryParameter(PARAM_FORMAT, FORMAT_WEBP)
            .appendQueryParameter(PARAM_WIDTH, imageWidth.widthPixels.toString())
            .build()
            .toString()
    }

    /**
     * Check that the host of the [imageUrl] is compatible with the il image service.
     *
     * @param imageUrl The image url to decorate.
     */
    fun isImageUrlHostCompatible(imageUrl: String): Boolean {
        return Uri.parse(imageUrl)?.host?.contains(SUPPORTED_HOST_NAME_REGEX) ?: false
    }

    companion object {
        private const val FORMAT_WEBP = "webp" // webp, jpg, png
        private const val IMAGES_SEGMENT = "images/"
        private const val PARAM_IMAGE_URL = "imageUrl"
        private const val PARAM_FORMAT = "format"
        private const val PARAM_WIDTH = "width"

        private val SUPPORTED_HOST_NAME_REGEX = "((rts|srf|rsi|rtr|swissinfo|srgssr)\\.ch)|swi-services-ch".toRegex(RegexOption.IGNORE_CASE)
    }
}
