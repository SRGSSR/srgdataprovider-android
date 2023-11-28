package ch.srg.dataProvider.integrationlayer.data

/**
 * Image url decorator
 */
interface ImageUrlDecorator {
    /**
     * Decorate [sourceUrl] with [widthPixels].
     *
     * @param sourceUrl The source url.
     * @param widthPixels The width size in pixels.
     * @return decorated url.
     */
    fun decorate(sourceUrl: String, widthPixels: Int): String
}
