package ch.srg.dataProvider.integrationlayer.request.image

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.data.ImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.IlHost

fun ImageUrl.url(widthPixels: Int, ilHost: IlHost = IlHost.PROD): String {
    return url(DefaultImageUrlDecorator(ilHost), widthPixels)
}

fun ImageUrl.url(width: ImageWidth, ilHost: IlHost = IlHost.PROD): String {
    return url(widthPixels = width.widthPixels, ilHost = ilHost)
}

fun ImageUrl.url(imageSize: ImageSize, ilHost: IlHost = IlHost.PROD): String {
    return url(width = imageSize.width, ilHost = ilHost)
}

fun ImageUrl.url(decorator: ImageUrlDecorator, width: ImageWidth): String {
    return url(decorator, width.widthPixels)
}

fun ImageUrl.url(decorator: ImageUrlDecorator, imageSize: ImageSize): String {
    return url(decorator, imageSize.width)
}
