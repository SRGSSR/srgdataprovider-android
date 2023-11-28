package ch.srg.dataProvider.integrationlayer

import ch.srg.dataProvider.integrationlayer.request.image.ImageWidth
import org.junit.Assert.assertEquals
import org.junit.Test

class TestImageWidth {

    @Test
    fun testGetFromPixelMatchingWidth() {
        assertEquals(ImageWidth.W240, ImageWidth.getFromPixels(240))
        assertEquals(ImageWidth.W320, ImageWidth.getFromPixels(320))
        assertEquals(ImageWidth.W480, ImageWidth.getFromPixels(480))
        assertEquals(ImageWidth.W960, ImageWidth.getFromPixels(960))
        assertEquals(ImageWidth.W1920, ImageWidth.getFromPixels(1920))
    }

    @Test
    fun testClosestUnder240Pixels() {
        assertEquals(ImageWidth.W240, ImageWidth.getFromPixels(0))
        assertEquals(ImageWidth.W240, ImageWidth.getFromPixels(239))
    }

    @Test
    fun testClosestGreaterThan1920Pixels() {
        assertEquals(ImageWidth.W1920, ImageWidth.getFromPixels(1921))
        assertEquals(ImageWidth.W1920, ImageWidth.getFromPixels(4000))
    }

    @Test
    fun testClosestW240() {
        assertEquals(ImageWidth.W240, ImageWidth.getFromPixels(239))
        assertEquals(ImageWidth.W240, ImageWidth.getFromPixels(240))
        assertEquals(ImageWidth.W240, ImageWidth.getFromPixels(241))
    }

    @Test
    fun testClosestW320() {
        assertEquals(ImageWidth.W320, ImageWidth.getFromPixels(319))
        assertEquals(ImageWidth.W320, ImageWidth.getFromPixels(320))
        assertEquals(ImageWidth.W320, ImageWidth.getFromPixels(321))
    }

    @Test
    fun testClosestW480() {
        assertEquals(ImageWidth.W480, ImageWidth.getFromPixels(479))
        assertEquals(ImageWidth.W480, ImageWidth.getFromPixels(480))
        assertEquals(ImageWidth.W480, ImageWidth.getFromPixels(481))
    }

    @Test
    fun testClosestW960() {
        assertEquals(ImageWidth.W960, ImageWidth.getFromPixels(959))
        assertEquals(ImageWidth.W960, ImageWidth.getFromPixels(960))
        assertEquals(ImageWidth.W960, ImageWidth.getFromPixels(961))
    }

    @Test
    fun testClosestW1920() {
        assertEquals(ImageWidth.W1920, ImageWidth.getFromPixels(1919))
        assertEquals(ImageWidth.W1920, ImageWidth.getFromPixels(1920))
        assertEquals(ImageWidth.W1920, ImageWidth.getFromPixels(1921))
    }

    @Test
    fun testClosestBetween240_320() {
        assertEquals(ImageWidth.W240, ImageWidth.getFromPixels(279))
        assertEquals(ImageWidth.W320, ImageWidth.getFromPixels(280)) // Middle
        assertEquals(ImageWidth.W320, ImageWidth.getFromPixels(281))
    }

    @Test
    fun testClosestBetween320_480() {
        assertEquals(ImageWidth.W320, ImageWidth.getFromPixels(399))
        assertEquals(ImageWidth.W480, ImageWidth.getFromPixels(400)) // Middle
        assertEquals(ImageWidth.W480, ImageWidth.getFromPixels(401))
    }

    @Test
    fun testClosestBetween480_960() {
        assertEquals(ImageWidth.W480, ImageWidth.getFromPixels(719))
        assertEquals(ImageWidth.W960, ImageWidth.getFromPixels(720)) // Middle
        assertEquals(ImageWidth.W960, ImageWidth.getFromPixels(721))
    }

    @Test
    fun testClosestBetween960_1920() {
        assertEquals(ImageWidth.W960, ImageWidth.getFromPixels(1439))
        assertEquals(ImageWidth.W1920, ImageWidth.getFromPixels(1440)) // Middle
        assertEquals(ImageWidth.W1920, ImageWidth.getFromPixels(1441))
    }

    @Test
    fun testNegativeWidthPixels() {
        assertEquals(ImageWidth.W240, ImageWidth.getFromPixels(-100))
    }

}
