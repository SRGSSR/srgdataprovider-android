package ch.srg.dataProvider.integrationlayer.request;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.IntDef;

import javax.inject.Inject;

import ch.srg.dataProvider.integrationlayer.SRGUrlFactory;
import ch.srg.dataProvider.integrationlayer.data.IlImage;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.ConfiguredScope;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@ConfiguredScope
public final class ImageProvider {
    @IntDef({SIZE_SMALL, SIZE_MEDIUM, SIZE_LARGE})
    public @interface ImageSize {
    }

    public static final int SIZE_SMALL = 0;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_LARGE = 2;

    private final Uri srgImageServiceUri;

    @Inject
    public ImageProvider(SRGUrlFactory factory) {
        srgImageServiceUri = factory.getHostUri().buildUpon().appendEncodedPath("images/").build();

    }

    public Uri decorateImageWithSize(IlImage image, @ImageSize int size) {
        return decorateImageWithSize(image, getImageSize(size));
    }

    public Uri decorateImageWithSize(IlImage image, IlImage.Size size) {
        return decorateImageWithSizeInPixel(image, size.value);
    }

    /**
     * @param widthInPixels 160,240,320,480,640,960,1280,1920
     */
    private Uri decorateImageWithSizeInPixel(IlImage image, int widthInPixels) {
        return decorateImageUrlWithSizeInPixel(image.getUrl(), image.getScaling(), widthInPixels);
    }

    public Uri decorateImageUrlWithSize(String imageUrl, IlImage.Scaling scaling, @ImageSize int size) {
        return decorateImageUrlWithSize(imageUrl, scaling, getImageSize(size));
    }

    public Uri decorateImageUrlWithSize(String imageUrl, @ImageSize int size) {
        return decorateImageUrlWithSize(imageUrl, IlImage.Scaling.Default, getImageSize(size));
    }

    public Uri decorateImageUrlWithSize(String imageUrl, IlImage.Scaling scaling, IlImage.Size size) {
        return decorateImageUrlWithSizeInPixel(imageUrl, scaling, size.value);
    }

    /**
     * When using {@link IlImage.Scaling#PreserveAspectRatio} the Integration layer image service is used
     * When using {@link IlImage.Scaling#Default} just append /scale/width/widthInPixels to the imageUrl.
     *
     * @param widthInPixels 160,240,320,480,640,960,1280,1920
     */
    public Uri decorateImageUrlWithSizeInPixel(String imageUrl, IlImage.Scaling scaling, int widthInPixels) {
        if (TextUtils.isEmpty(imageUrl)) {
            return null;
        }
        switch (scaling) {
            case PreserveAspectRatio:
                return createImageServiceUrl(imageUrl, widthInPixels);
            case Default:
            default:
                return scaledImageUrl(imageUrl, widthInPixels);
        }
    }

    private Uri createImageServiceUrl(String imageUrl, int width) {
        return srgImageServiceUri.buildUpon()
                .appendQueryParameter("imageUrl", imageUrl)
                .appendQueryParameter("format", FORMAT_WEBP)
                .appendQueryParameter("width", Integer.toString(width)).build();
    }

    private Uri.Builder addScaleWith(Uri.Builder uri, int width) {
        return uri.appendPath(Scale).appendPath(Width).appendPath(Integer.toString(width));
    }

    private Uri scaledImageUrl(String url, int width) {
        return addScaleWith(Uri.parse(url).buildUpon(), width).build();
    }


    private IlImage.Size getImageSize(@ImageSize int size) {
        return DIMENSIONS_PX[size];
    }

    /**
     * Dimension to use for each size depending of screen density
     * (SRGImageSizeSmall) : @(SRGImageWidth320),
     * (SRGImageSizeMedium) : @(SRGImageWidth480),
     * (SRGImageSizeLarge) : @(SRGImageWidth960)
     */
    private static final IlImage.Size[] DIMENSIONS_PX = {IlImage.Size.w320, IlImage.Size.w480, IlImage.Size.w960};

    private static final String Scale = "scale";
    private static final String Width = "width";
    private static final String FORMAT_JPG = "jpg";
    private static final String FORMAT_PNG = "png";
    private static final String FORMAT_WEBP = "webp";
}
