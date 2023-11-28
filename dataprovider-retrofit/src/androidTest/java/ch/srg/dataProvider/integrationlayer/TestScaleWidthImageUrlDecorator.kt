package ch.srg.dataProvider.integrationlayer

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.request.image.ImageSize
import ch.srg.dataProvider.integrationlayer.request.image.ImageWidth
import ch.srg.dataProvider.integrationlayer.request.image.ScaleWidthImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.image.url
import ch.srg.dataProvider.integrationlayer.request.image.decorated
import org.junit.Assert.assertEquals
import org.junit.Test

class TestScaleWidthImageUrlDecorator {

    private val decorator = ScaleWidthImageUrlDecorator

    @Test
    fun testScaleWidth() {
        val input = ImageUrl("https://www.data.com/images/images.png")
        val width = 458
        val expected = "https://www.data.com/images/images.png/scale/width/458"
        assertEquals(expected, input.decorated(decorator, width))
    }

    @Test
    fun testScaleWidthImageSize() {
        val input = ImageUrl("https://www.data.com/images/images.png")
        val expected = "https://www.data.com/images/images.png/scale/width/480"
        assertEquals(expected, input.decorated(decorator, ImageSize.MEDIUM))
    }

    @Test
    fun testScaleWidthImageWidth() {
        val input = ImageUrl("https://www.data.com/images/images.png")
        val expected = "https://www.data.com/images/images.png/scale/width/1920"
        assertEquals(expected, input.url(decorator, ImageWidth.W1920))
    }
}
