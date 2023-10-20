package ch.srg.dataProvider.integrationlayer.request.image

import android.net.Uri
import ch.srg.dataProvider.integrationlayer.data.ImageUrlDecorator

/**
 * Scale width image url decorator
 *
 * @constructor Create empty Scale width image url decorator
 */
object ScaleWidthImageUrlDecorator : ImageUrlDecorator {
    private const val Scale = "scale"
    private const val Width = "width"

    override fun decorate(source: String, size: Int): String {
        return Uri.parse(source).buildUpon()
            .appendPath(Scale)
            .appendPath(Width)
            .appendPath(size.toString())
            .build()
            .toString()
    }
}
