package com.sladit.delaunay;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;

import java.awt.image.BufferedImage;

/**
 * Created by leonid on 7/4/16.
 */
public abstract class ImagingRenderer {
    protected ConstrainedMesh mesh;

    public ImagingRenderer(BufferedImage image, int points) throws DelaunayError {
        mesh = DelaunayHelper.generateImagingMesh(image, points);
    }

    public abstract byte[] generate() throws DelaunayError;

    public static ImagingRenderer getRenderer(ImagingFormat format, BufferedImage image, int points) {
        try {
            switch (format) {
                case SVG:
                    return new SvgRenderer(image, points);
                case PNG:
                    return new PngRenderer(image, points);
                default: {
                    throw new IllegalArgumentException("Unsupported imaging format");
                }
            }
        } catch (DelaunayError delaunayError) {
            delaunayError.printStackTrace();
            return null;
        }
    }

}
