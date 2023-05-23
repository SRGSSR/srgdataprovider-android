package ch.srg.dataProvider.integrationlayer;

import junit.framework.Assert;

import org.junit.Test;

import ch.srg.dataProvider.integrationlayer.utils.IlUrn;

/**
 * Created by StahliJ on 14.12.2017.
 */

public class TestIlUrn {

    @Test(expected = NullPointerException.class)
    public void createFromNullUrn() {
        String urn = null;
        new IlUrn(urn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFromEmptyUrn() {
        String urn = "";
        new IlUrn(urn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFromRandomStringUrn() {
        String urn = "urn:hellowI'mNotValid!";
        new IlUrn(urn);
    }

    @Test
    public void createFromValidUrn() {
        String urn = "urn:rts:video:123456";
        IlUrn ilUrn = new IlUrn(urn);
        Assert.assertEquals("video", ilUrn.getAssetType());
        Assert.assertEquals("rts", ilUrn.getBu());
        Assert.assertEquals("123456", ilUrn.getId());
    }

    @Test
    public void testIsAudioValideUrn() {
        Assert.assertFalse(IlUrn.isAudio("urn:rts:video:123456"));
        Assert.assertTrue(IlUrn.isAudio("urn:rts:audio:123456"));
        Assert.assertFalse(IlUrn.isAudio("urn:a:b:12345"));
    }

    @Test
    public void testIsAudioEmptyUrn() {
        Assert.assertFalse(IlUrn.isAudio(""));
    }

    @Test
    public void testIsAudioNullUrn() {
        Assert.assertFalse(IlUrn.isAudio(null));
    }


    @Test
    public void testIsVideoValideUrn() {
        Assert.assertTrue(IlUrn.isVideo("urn:rts:video:123456"));
        Assert.assertFalse(IlUrn.isVideo("urn:rts:audio:123456"));
        Assert.assertFalse(IlUrn.isVideo("urn:a:b:12345"));
    }

    @Test
    public void testIsVideoEmptyUrn() {
        Assert.assertFalse(IlUrn.isVideo(""));
    }

    @Test
    public void testIsVideoNullUrn() {
        Assert.assertFalse(IlUrn.isVideo(null));
    }

    @Test
    public void testGetId() {
        final String id = "123456";
        String urn = "urn:rts:video:" + id;
        final String resultId = IlUrn.getId(urn);
        Assert.assertEquals(id, resultId);
    }

    @Test
    public void testGetIdEmptyUrn() {
        final String resultId = IlUrn.getId("");
        Assert.assertEquals("", resultId);
    }

    @Test
    public void testGetIdNullUrn() {
        final String resultId = IlUrn.getId(null);
        Assert.assertEquals(null, resultId);
    }

    @Test
    public void testGetIdInvalidUrn() {
        String urn = "NotValidUrn";
        final String resultId = IlUrn.getId(urn);
        Assert.assertEquals(urn, resultId);
    }

    @Test
    public void testGetAssetTypeValidUrn() {
        final String assetType = IlUrn.ASSET_SHOW;
        String urn = "urn:rts:" + assetType + ":12345";
        Assert.assertEquals(assetType, IlUrn.getAssetType(urn));
    }

    @Test
    public void testGetAssetTypeInvalidUrn() {
        final String assetType = IlUrn.ASSET_AUDIO;
        String urn = "urn:" + assetType + " not valid urn";
        Assert.assertEquals(IlUrn.ASSET_VIDEO, IlUrn.getAssetType(urn));
    }

    @Test
    public void testGetAssetTypeEmptyUrn() {
        Assert.assertEquals(IlUrn.ASSET_VIDEO, IlUrn.getAssetType(""));
    }

    @Test
    public void testGetAssetTypeNullUrn() {
        Assert.assertEquals(IlUrn.ASSET_VIDEO, IlUrn.getAssetType(null));
    }

    @Test
    public void testSwissTXTUrn() {
        String urn = "urn:swisstxt:video:srf:353878";
        IlUrn ilUrn = new IlUrn(urn);
        Assert.assertEquals(ilUrn.getAssetType(), "video");
        Assert.assertEquals(ilUrn.getBu(), "srf");
        Assert.assertEquals(ilUrn.getId(), "353878");
    }

    @Test
    public void testIsUrnNullUrn() {
        Assert.assertFalse(IlUrn.isUrn(null));
    }

    @Test
    public void testIsUrnEmptyUrn() {
        Assert.assertFalse(IlUrn.isUrn(""));
    }

    @Test
    public void testIsUrnInvalidUrn() {
        Assert.assertFalse(IlUrn.isUrn(" "));
        Assert.assertFalse(IlUrn.isUrn("Toto"));
        Assert.assertFalse(IlUrn.isUrn("foo:bar:bar:122345"));
        Assert.assertFalse(IlUrn.isUrn("urn:foo:bar:12345"));
        Assert.assertFalse(IlUrn.isUrn("urn:rts:foo:12345"));
        Assert.assertFalse(IlUrn.isUrn("urn:swisstxt:video:123456"));
    }

    @Test
    public void testIsUrnValidUrn() {
        Assert.assertTrue(IlUrn.isUrn("urn:srf:video:12345"));
        Assert.assertTrue(IlUrn.isUrn("urn:rts:video:12345"));
        Assert.assertTrue(IlUrn.isUrn("urn:rtr:video:12345"));
        Assert.assertTrue(IlUrn.isUrn("urn:swi:video:12345"));
        Assert.assertTrue(IlUrn.isUrn("urn:rsi:video:12345"));
        Assert.assertTrue(IlUrn.isUrn("urn:srf:audio:12345"));
        Assert.assertTrue(IlUrn.isUrn("urn:swisstxt:video:srf:4567"));
    }

}
