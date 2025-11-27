package ch.srg.dataProvider.integrationlayer

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.image.DefaultImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.image.ImageSize
import ch.srg.dataProvider.integrationlayer.request.image.ImageWidth
import ch.srg.dataProvider.integrationlayer.request.image.decorated
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestDefaultImageUrlDecorator {

    private val decorator = DefaultImageUrlDecorator(ilHost = IlHost.PROD)

    @Test
    fun testPixelValid() {
        val input = "https://ws.srf.ch/asset/image/audio/123"
        val encodedInput = Uri.encode(input)
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.decorated(decorator, 480))
    }

    @Test
    fun testPixelWidthInvalid() {
        val input = "https://ws.srf.ch/asset/image/audio/123"
        val encodedInput = Uri.encode(input)
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.decorated(decorator, 460))
    }

    @Test
    fun testImageSize() {
        val input = "https://ws.srf.ch/asset/image/audio/123"
        val encodedInput = Uri.encode(input)
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.decorated(decorator, ImageSize.MEDIUM))
    }

    @Test
    fun testImageWidth() {
        val input = "https://ws.srf.ch/asset/image/audio/123"
        val encodedInput = Uri.encode(input)
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.decorated(decorator, ImageWidth.W1920))
    }

    @Test
    fun testOtherIlHost() {
        val input = "https://ws.srf.ch/asset/image/audio/123"
        val encodedInput = Uri.encode(input)
        val expected = "https://il-stage.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.decorated(decorator = DefaultImageUrlDecorator(IlHost.STAGE), width = ImageWidth.W1920))
    }

    @Test
    fun testExtensionImageWidthWithIlHost() {
        val input = "https://ws.srf.ch/asset/image/audio/123"
        val encodedInput = Uri.encode(input)
        val expected = "https://il-stage.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.decorated(ilHost = IlHost.STAGE, width = ImageWidth.W1920))
    }

    @Test
    fun testExtensionImageSizeWithIlHost() {
        val input = "https://ws.srf.ch/asset/image/audio/123"
        val encodedInput = Uri.encode(input)
        val expected = "https://il-test.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.decorated(ilHost = IlHost.TEST, imageSize = ImageSize.MEDIUM))
    }

    @Test
    fun testAllValidatedHostName() {
        val tests = listOf(
            "https://ws.srf.ch/asset/image/audio/123",
            "https://www.srf.ch/asset/image/audio/123",
            "https://ws.debug.srf.ch/asset/image/audio/123",
            "https://ws.rts.ch/asset/image/audio/123",
            "https://ws.rtr.ch/asset/image/audio/123",
            "https://ws.rsi.ch/asset/image/audio/123",
            "https://ws.swissinfo.ch/asset/image/audio/123",
            "https://ws.srgssr.ch/asset/image/audio/123",
            "https://swi-services-ch/asset/image/audio/123",
        )
        for (url in tests) {
            val encodedInput = Uri.encode(url)
            val expected = "https://il-test.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
            assertEquals(expected, url.decorated(ilHost = IlHost.TEST, imageSize = ImageSize.MEDIUM))
        }
    }

    @Test
    fun testInvalidHostNameUrlReturnInputUrl() {
        val input = "https://akamai.playsuisse.ch/asset/image/audio/123"
        assertEquals(input, input.decorated(ilHost = IlHost.TEST, imageSize = ImageSize.MEDIUM))
    }
}
