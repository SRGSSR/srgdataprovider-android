package ch.srg.dataProvider.integrationlayer

import android.net.Uri
import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.image.DefaultImageUrlDecorator
import org.junit.Assert
import org.junit.Test

class TestDefaultImageUrlDecorator {
    private val decorator = DefaultImageUrlDecorator(ilHost = IlHost.PROD)

    @Test
    fun testNonRtsUrl() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        Assert.assertEquals(expected, input.url(decorator, 480))
    }

    @Test
    fun testRtsUrlWithoutImage() {
        val input = ImageUrl("https://ws.rts.ch/asset/image/audio/123")
        val encodedInput = Uri.encode("https://ws.rts.ch/asset/image/audio/123")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        Assert.assertEquals(expected, input.url(decorator, 480))
    }

    @Test
    fun testUrlWithImageOnly() {
        val input = ImageUrl("https://ws.srf.ch/asset/image/audio/123.image")
        val encodedInput = Uri.encode("https://ws.srf.ch/asset/image/audio/123.image")
        val expected = "https://il.srgssr.ch/images/?imageUrl=${encodedInput}&format=webp&width=480"
        Assert.assertEquals(expected, input.url(decorator, 480))
    }

    @Test
    fun testRtsUrlWithImage() {
        val input = ImageUrl("https://ws.rts.ch/asset/image/audio/123.image")
        val expected = "https://ws.rts.ch/asset/image/audio/123.image/scale/width/460"
        Assert.assertEquals(expected, input.url(decorator, 460))
    }



}
