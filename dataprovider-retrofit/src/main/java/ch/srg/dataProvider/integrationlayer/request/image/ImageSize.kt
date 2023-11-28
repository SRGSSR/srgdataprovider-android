package ch.srg.dataProvider.integrationlayer.request.image

/**
 * Image size
 * @property width [ImageWidth].
 */
enum class ImageSize(val width: ImageWidth) {
    /**
     * Small [ImageWidth.W320]
     */
    SMALL(ImageWidth.W320),

    /**
     * Medium [ImageWidth.W480]
     */
    MEDIUM(ImageWidth.W480),

    /**
     * Large [ImageWidth.W960]
     */
    LARGE(ImageWidth.W960),
}
