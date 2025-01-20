package ch.srg.dataProvider.integrationlayer.data.remote

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class AspectRatioTest {
    @Test
    fun parse() {
        val aspectRatio = AspectRatio.parse("16:9")

        assertEquals(16, aspectRatio.numerator)
        assertEquals(9, aspectRatio.denominator)
    }

    @Test
    fun `parse denominator 0`() {
        val aspectRatio = AspectRatio.parse("16:0")

        assertEquals(AspectRatio.Infinity, aspectRatio)
    }

    @Test
    fun `parse numerator 0`() {
        val aspectRatio = AspectRatio.parse("0:9")

        assertEquals(AspectRatio.Zero, aspectRatio)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse empty string`() {
        AspectRatio.parse("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse blank string`() {
        AspectRatio.parse("  ")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse invalid string`() {
        AspectRatio.parse("hello")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse string with invalid numerator`() {
        AspectRatio.parse("abc:9")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse string with invalid denominator`() {
        AspectRatio.parse("16:abc")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse string with too many separators`() {
        AspectRatio.parse("1:2:3")
    }

    @Test
    fun `destruction check`() {
        val aspectRatio = AspectRatio(16, 9)
        val (numerator, denominator) = aspectRatio

        assertEquals(aspectRatio.numerator, numerator)
        assertEquals(aspectRatio.denominator, denominator)
    }

    @Test
    fun `equals check`() {
        assertEquals(AspectRatio.Infinity, AspectRatio(1, 0))
        assertEquals(AspectRatio.Zero, AspectRatio(0, 1))
        assertEquals(AspectRatio(16, 9), AspectRatio(16, 9))
        assertNotEquals(AspectRatio(4, 3), AspectRatio(16, 9))
    }

    @Test
    fun `toString format check`() {
        assertEquals("1:0", AspectRatio.Infinity.toString())
        assertEquals("0:1", AspectRatio.Zero.toString())
        assertEquals("16:9", AspectRatio(16, 9).toString())
    }
}
