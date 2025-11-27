package ch.srg.dataProvider.integrationlayer.request.image

import ch.srg.dataProvider.integrationlayer.data.ImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.IlHost

@JvmOverloads
fun String.decorated(widthPixels: Int, ilHost: IlHost = IlHost.PROD): String {
    return decorated(ImageUrlDecoratorInstances.getOrCreate(ilHost), widthPixels)
}

@JvmOverloads
fun String.decorated(width: ImageWidth, ilHost: IlHost = IlHost.PROD): String {
    return decorated(widthPixels = width.widthPixels, ilHost = ilHost)
}

@JvmOverloads
fun String.decorated(imageSize: ImageSize, ilHost: IlHost = IlHost.PROD): String {
    return decorated(width = imageSize.width, ilHost = ilHost)
}

fun String.decorated(decorator: ImageUrlDecorator, widthPixels: Int): String {
    return decorator.decorate(this, widthPixels)
}

fun String.decorated(decorator: ImageUrlDecorator, width: ImageWidth): String {
    return decorated(decorator, width.widthPixels)
}

fun String.decorated(decorator: ImageUrlDecorator, imageSize: ImageSize): String {
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
