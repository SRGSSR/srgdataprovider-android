package ch.srg.dataProvider.integrationlayer

import ch.srg.dataProvider.integrationlayer.request.image.ImageSize
import ch.srg.dataProvider.integrationlayer.request.image.ImageWidth
import org.junit.Assert
import org.junit.Test

class TestImageSize {

    @Test
    fun testSmallSizeIsW320() {
        Assert.assertEquals(ImageWidth.W320, ImageSize.SMALL.width)
    }

    @Test
    fun testMediumSizeIsW480() {
        Assert.assertEquals(ImageWidth.W480, ImageSize.MEDIUM.width)
    }

    @Test
    fun testLargeSizeIsW960() {
        Assert.assertEquals(ImageWidth.W960, ImageSize.LARGE.width)
    }
}
