package com.sladit.delaunay;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;

import java.awt.image.BufferedImage;

/**
 * Created by leonid on 7/4/16.
 */
public abstract class ImagingRenderer {
    protected ConstrainedMesh mesh;

    /**
     * Initialize a new ImageRenderer with a BufferedImage.
     *
     * @param image Triangles of this mesh will be colored from this image.
     * @param points Number of points for Delaunay calculation.
     * @throws DelaunayError
     */
    public ImagingRenderer(BufferedImage image, int points) throws DelaunayError {
        mesh = DelaunayHelper.generateImagingMesh(image, points);
    }

    /**
     * Processes the instance Delaunay mesh into an imaging output format.
     *
     * @return Output file bytes.
     * @throws DelaunayError
     */
    public abstract byte[] generate() throws DelaunayError;

    /**
     * Returns a default ImagingRenderer instance for a specified format.
     * @param format The output format.
     * @param image The image for initialization.
     * @param points The number of points.
     * @return
     */
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
