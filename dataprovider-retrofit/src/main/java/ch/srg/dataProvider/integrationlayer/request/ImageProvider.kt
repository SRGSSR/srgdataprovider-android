package ch.srg.dataProvider.integrationlayer.request

import android.net.Uri
import android.text.TextUtils
import androidx.annotation.IntDef
import ch.srg.dataProvider.integrationlayer.SRGUrlFactory
import ch.srg.dataProvider.integrationlayer.data.IlImage

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
class ImageProvider(factory: SRGUrlFactory) {
    @IntDef(SIZE_SMALL, SIZE_MEDIUM, SIZE_LARGE)
    annotation class ImageSize

    private val srgImageServiceUri: Uri

    init {
        srgImageServiceUri = factory.hostUri.buildUpon().appendEncodedPath("images/").build()
    }

    fun decorateImageWithSize(image: IlImage, @ImageSize size: Int): Uri? {
        return decorateImageWithSize(image, getImageSize(size))
    }

    fun decorateImageWithSize(image: IlImage, size: IlImage.Size): Uri? {
        return decorateImageWithSizeInPixel(image, size.sizePixels)
    }

    /**
     * @param widthInPixels 160,240,320,480,640,960,1280,1920
     */
    private fun decorateImageWithSizeInPixel(image: IlImage, widthInPixels: Int): Uri? {
        return decorateImageUrlWithSizeInPixel(image.url, widthInPixels)
    }

    fun decorateImageUrlWithSize(imageUrl: String, @ImageSize size: Int): Uri? {
        return decorateImageUrlWithSize(imageUrl, getImageSize(size))
    }

    fun decorateImageUrlWithSize(imageUrl: String, size: IlImage.Size): Uri? {
        return decorateImageUrlWithSizeInPixel(imageUrl, size.sizePixels)
    }

    /**
     * Fixme https://github.com/SRGSSR/srgdataprovider-apple/issues/47 once RTS image service is well connected to Il Play image service.
     *
     * @param widthInPixels 160,240,320,480,640,960,1280,1920
     */
    private fun decorateImageUrlWithSizeInPixel(imageUrl: String, widthInPixels: Int): Uri? {
        return if (TextUtils.isEmpty(imageUrl)) {
            null
        } else {
            if (imageUrl.contains("rts.ch") && imageUrl.contains(".image")) {
                return createBusinessUnitImageServiceUrl(imageUrl, widthInPixels)
            } else {
                return createPlaySrgImageServiceUrl(imageUrl, widthInPixels)
            }
        }
    }

    private fun createPlaySrgImageServiceUrl(imageUrl: String?, width: Int): Uri {
        return srgImageServiceUri.buildUpon()
            .appendQueryParameter("imageUrl", imageUrl)
            .appendQueryParameter("format", FORMAT_WEBP)
            .appendQueryParameter("width", width.toString())
            .build()
    }

    private fun addScaleWith(uri: Uri.Builder, width: Int): Uri.Builder {
        return uri.appendPath(Scale).appendPath(Width).appendPath(width.toString())
    }

    private fun createBusinessUnitImageServiceUrl(url: String?, width: Int): Uri {
        return addScaleWith(Uri.parse(url).buildUpon(), width).build()
    }

    private fun getImageSize(@ImageSize size: Int): IlImage.Size {
        return DIMENSIONS_PX[size]
    }

    companion object {
        const val SIZE_SMALL = 0
        const val SIZE_MEDIUM = 1
        const val SIZE_LARGE = 2

        /**
         * Dimension to use for each size depending of screen density
         * (SRGImageSizeSmall) : @(SRGImageWidth320),
         * (SRGImageSizeMedium) : @(SRGImageWidth480),
         * (SRGImageSizeLarge) : @(SRGImageWidth960)
         */
        private val DIMENSIONS_PX = arrayOf(IlImage.Size.W320, IlImage.Size.W480, IlImage.Size.W960)
        private const val Scale = "scale"
        private const val Width = "width"
        private const val FORMAT_WEBP = "webp" // webp, jpg, png
    }
}
