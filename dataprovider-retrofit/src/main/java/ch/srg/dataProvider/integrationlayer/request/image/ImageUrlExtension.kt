package ch.srg.dataProvider.integrationlayer.request.image

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.data.ImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.IlHost

@JvmOverloads
fun ImageUrl.decorated(widthPixels: Int, ilHost: IlHost = IlHost.PROD): String {
    return decorated(ImageUrlDecoratorInstances.getOrCreate(ilHost), widthPixels)
}

@JvmOverloads
fun ImageUrl.decorated(width: ImageWidth, ilHost: IlHost = IlHost.PROD): String {
    return decorated(widthPixels = width.widthPixels, ilHost = ilHost)
}

@JvmOverloads
fun ImageUrl.decorated(imageSize: ImageSize, ilHost: IlHost = IlHost.PROD): String {
    return decorated(width = imageSize.width, ilHost = ilHost)
}

fun ImageUrl.decorated(decorator: ImageUrlDecorator, width: ImageWidth): String {
    return decorated(decorator, width.widthPixels)
}

fun ImageUrl.decorated(decorator: ImageUrlDecorator, imageSize: ImageSize): String {
    return decorated(decorator, imageSize.width)
}

/**
 * Optimization for extensions to reuse DefaultImageUrlDecorator based on IlHost.
 */
private object ImageUrlDecoratorInstances {
    private val instances = mutableMapOf<IlHost, ImageUrlDecorator>()

    fun getOrCreate(ilHost: IlHost): ImageUrlDecorator {
        return instances.getOrPut(ilHost) { DefaultImageUrlDecorator(ilHost) }
    }
}
