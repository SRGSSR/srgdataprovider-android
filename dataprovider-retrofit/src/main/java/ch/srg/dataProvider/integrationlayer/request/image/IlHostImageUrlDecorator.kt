package ch.srg.dataProvider.integrationlayer.request.image

import android.net.Uri
import ch.srg.dataProvider.integrationlayer.data.ImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.IlHost

/**
 * Il host image url decorator
 *
 * @param ilHost The [IlHost] of the integration layer image service.
 */
class IlHostImageUrlDecorator(ilHost: IlHost) : ImageUrlDecorator {
    private val imageServiceUri: Uri

    init {
        imageServiceUri = ilHost.hostUri.buildUpon().appendEncodedPath(IMAGES_SEGMENT).build()
    }

    override fun decorate(source: String, size: Int): String {
        // Il image service only support a limited image size!
        val imageWidth = ImageWidth.getFromPixels(size)
        return imageServiceUri.buildUpon()
            .appendQueryParameter(PARAM_IMAGE_URL, source)
            .appendQueryParameter(PARAM_FORMAT, FORMAT_WEBP)
            .appendQueryParameter(PARAM_WIDTH, imageWidth.widthPixels.toString())
            .build()
            .toString()
    }

    companion object {
        private const val FORMAT_WEBP = "webp" // webp, jpg, png
        private const val IMAGES_SEGMENT = "images/"
        private const val PARAM_IMAGE_URL = "imageUrl"
        private const val PARAM_FORMAT = "format"
        private const val PARAM_WIDTH = "width"
    }
}
