package ch.srg.dataProvider.integrationlayer

import android.net.Uri
import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.image.IlHostImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.image.ImageSize
import ch.srg.dataProvider.integrationlayer.request.image.ImageWidth
import ch.srg.dataProvider.integrationlayer.request.image.url
import org.junit.Assert.assertEquals
import org.junit.Test

class TestIlHostImageUrlDecorator {

    private val decorator = IlHostImageUrlDecorator(ilHost = IlHost.PROD)

    @Test
    fun testPixelValid() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.url(decorator, 480))
    }

    @Test
    fun testPixelWidthInvalid() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.url(decorator, 460))
    }

    @Test
    fun testImageSize() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.url(decorator, ImageSize.MEDIUM))
    }

    @Test
    fun testImageWidth() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.url(decorator, ImageWidth.W1920))
    }

    @Test
    fun testOtherIlHost() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il-stage.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.url(decorator = IlHostImageUrlDecorator(IlHost.STAGE), width = ImageWidth.W1920))
    }

    @Test
    fun testExtensionImageWidthWithIlHost() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il-stage.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.url(ilHost = IlHost.STAGE, width = ImageWidth.W1920))
    }

    @Test
    fun testExtensionImageSizeWithIlHost() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il-test.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.url(ilHost = IlHost.TEST, imageSize = ImageSize.MEDIUM))
    }
}
