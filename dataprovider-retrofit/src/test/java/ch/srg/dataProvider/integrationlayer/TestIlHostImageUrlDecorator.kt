package ch.srg.dataProvider.integrationlayer

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.image.IlHostImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.image.ImageSize
import ch.srg.dataProvider.integrationlayer.request.image.ImageWidth
import ch.srg.dataProvider.integrationlayer.request.image.decorated
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestIlHostImageUrlDecorator {

    private val decorator = IlHostImageUrlDecorator(ilHost = IlHost.PROD)

    @Test
    fun testPixelValid() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.decorated(decorator, 480))
    }

    @Test
    fun testPixelWidthInvalid() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.decorated(decorator, 460))
    }

    @Test
    fun testImageSize() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        assertEquals(expected, input.decorated(decorator, ImageSize.MEDIUM))
    }

    @Test
    fun testImageWidth() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.decorated(decorator, ImageWidth.W1920))
    }

    @Test
    fun testOtherIlHost() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il-stage.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.decorated(decorator = IlHostImageUrlDecorator(IlHost.STAGE), width = ImageWidth.W1920))
    }

    @Test
    fun testExtensionImageWidthWithIlHost() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il-stage.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=1920"
        assertEquals(expected, input.decorated(ilHost = IlHost.STAGE, width = ImageWidth.W1920))
    }

    @Test
    fun testExtensionImageSizeWithIlHost() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
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
            val input = ImageUrl(url)
            val encodedInput = Uri.encode(url)
            val expected = "https://il-test.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
            assertEquals(expected, input.decorated(ilHost = IlHost.TEST, imageSize = ImageSize.MEDIUM))
        }
    }

    @Test
    fun testInvalidHostNameUrlReturnInputUrl() {
        val input = ImageUrl("https://akamai.playsuisse.ch/asset/image/audio/123")
        val expected = input.rawUrl
        assertEquals(expected, input.decorated(ilHost = IlHost.TEST, imageSize = ImageSize.MEDIUM))
    }
}
