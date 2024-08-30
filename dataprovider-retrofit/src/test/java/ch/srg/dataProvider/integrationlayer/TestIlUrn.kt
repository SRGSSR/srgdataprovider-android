package ch.srg.dataProvider.integrationlayer

import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.srg.dataProvider.integrationlayer.utils.IlUrn
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestIlUrn {

    @Test(expected = IllegalArgumentException::class)
    fun createFromEmptyUrn() {
        val urn = ""
        IlUrn(urn)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createFromRandomStringUrn() {
        val urn = "urn:hellowI'mNotValid!"
        IlUrn(urn)
    }

    @Test
    fun createFromValidUrn() {
        val urn = "urn:rts:video:123456"
        val ilUrn = IlUrn(urn)
        Assert.assertEquals("video", ilUrn.assetType)
        Assert.assertEquals("rts", ilUrn.bu)
        Assert.assertEquals("123456", ilUrn.id)
    }

    @Test
    fun testIsAudioValidUrn() {
        Assert.assertFalse(IlUrn.isAudio("urn:rts:video:123456"))
        Assert.assertTrue(IlUrn.isAudio("urn:rts:audio:123456"))
        Assert.assertFalse(IlUrn.isAudio("urn:a:b:12345"))
    }

    @Test
    fun testIsAudioEmptyUrn() {
        Assert.assertFalse(IlUrn.isAudio(""))
    }

    @Test
    fun testIsAudioNullUrn() {
        Assert.assertFalse(IlUrn.isAudio(null))
    }

    @Test
    fun testIsVideoValidUrn() {
        Assert.assertTrue(IlUrn.isVideo("urn:rts:video:123456"))
        Assert.assertFalse(IlUrn.isVideo("urn:rts:audio:123456"))
        Assert.assertFalse(IlUrn.isVideo("urn:a:b:12345"))
    }

    @Test
    fun testIsVideoEmptyUrn() {
        Assert.assertFalse(IlUrn.isVideo(""))
    }

    @Test
    fun testIsVideoNullUrn() {
        Assert.assertFalse(IlUrn.isVideo(null))
    }

    @Test
    fun testGetId() {
        val id = "123456"
        val urn = "urn:rts:video:$id"
        val resultId = IlUrn.getId(urn)
        Assert.assertEquals(id, resultId)
    }

    @Test
    fun testGetIdEmptyUrn() {
        val resultId = IlUrn.getId("")
        Assert.assertEquals("", resultId)
    }

    @Test
    fun testGetIdNullUrn() {
        val resultId = IlUrn.getId(null)
        Assert.assertEquals(null, resultId)
    }

    @Test
    fun testGetIdInvalidUrn() {
        val urn = "NotValidUrn"
        val resultId = IlUrn.getId(urn)
        Assert.assertEquals(urn, resultId)
    }

    @Test
    fun testGetAssetTypeValidUrn() {
        val assetType = IlUrn.ASSET_SHOW
        val urn = "urn:rts:$assetType:12345"
        Assert.assertEquals(assetType, IlUrn.getAssetType(urn))
    }

    @Test
    fun testGetAssetTypeInvalidUrn() {
        val assetType = IlUrn.ASSET_AUDIO
        val urn = "urn:$assetType not valid urn"
        Assert.assertEquals(IlUrn.ASSET_VIDEO, IlUrn.getAssetType(urn))
    }

    @Test
    fun testGetAssetTypeEmptyUrn() {
        Assert.assertEquals(IlUrn.ASSET_VIDEO, IlUrn.getAssetType(""))
    }

    @Test
    fun testGetAssetTypeNullUrn() {
        Assert.assertEquals(IlUrn.ASSET_VIDEO, IlUrn.getAssetType(null))
    }

    @Test
    fun testSwissTXTUrn() {
        val urn = "urn:swisstxt:video:srf:353878"
        val ilUrn = IlUrn(urn)
        Assert.assertEquals(ilUrn.assetType, "video")
        Assert.assertEquals(ilUrn.bu, "srf")
        Assert.assertEquals(ilUrn.id, "353878")
    }

    @Test
    fun testIsUrnNullUrn() {
        Assert.assertFalse(IlUrn.isUrn(null))
    }

    @Test
    fun testIsUrnEmptyUrn() {
        Assert.assertFalse(IlUrn.isUrn(""))
    }

    @Test
    fun testIsUrnInvalidUrn() {
        Assert.assertFalse(IlUrn.isUrn(" "))
        Assert.assertFalse(IlUrn.isUrn("Toto"))
        Assert.assertFalse(IlUrn.isUrn("foo:bar:bar:122345"))
        Assert.assertFalse(IlUrn.isUrn("urn:foo:bar:12345"))
        Assert.assertFalse(IlUrn.isUrn("urn:rts:foo:12345"))
        Assert.assertFalse(IlUrn.isUrn("urn:swisstxt:video:123456"))
    }

    @Test
    fun testIsUrnValidUrn() {
        Assert.assertTrue(IlUrn.isUrn("urn:srf:video:12345"))
        Assert.assertTrue(IlUrn.isUrn("urn:rts:video:12345"))
        Assert.assertTrue(IlUrn.isUrn("urn:rtr:video:12345"))
        Assert.assertTrue(IlUrn.isUrn("urn:swi:video:12345"))
        Assert.assertTrue(IlUrn.isUrn("urn:rsi:video:12345"))
        Assert.assertTrue(IlUrn.isUrn("urn:srf:audio:12345"))
        Assert.assertTrue(IlUrn.isUrn("urn:swisstxt:video:srf:4567"))
    }
}
