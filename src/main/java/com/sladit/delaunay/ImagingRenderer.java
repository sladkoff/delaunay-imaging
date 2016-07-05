package com.sladit.delaunay;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;

import java.awt.image.BufferedImage;

/**
 * The abstract ImagingRenderer base class.
 * A renderer is responsible for generating an image output from a {@link ConstrainedMesh}.
 * Subclasses must implement the {@link #generateImage()} method.
 */
public abstract class ImagingRenderer {

    ConstrainedMesh mesh;

    void setMesh(ConstrainedMesh mesh) {
        this.mesh = mesh;
    }

    ConstrainedMesh getMesh() {
        return mesh;
    }

    public ImagingRenderer() {

    }

    public ImagingRenderer(BufferedImage image, int points) throws DelaunayError {
        generateMesh(image, points);
    }

    /**
     * Generates a colored mesh from an image.
     *
     * @param image
     * @param points
     * @throws DelaunayError
     */
    public ImagingRenderer generateMesh(BufferedImage image, int points) throws DelaunayError {
        mesh = DelaunayHelper.generateImagingMesh(image, points);
        return this;
    }

    /**
     * Generates a randomly colored mesh with the given parameters.
     *
     * @param width
     * @param height
     * @param points
     * @throws DelaunayError
     */
    public ImagingRenderer generateMesh(int width, int height, int points) throws DelaunayError {
        mesh = DelaunayHelper.generateRandomMesh(width, height, points);
        return this;
    }

    /**
     * Processes the instance Delaunay mesh into an imaging output format.
     *
     * @return Output file bytes.
     * @throws DelaunayError
     */
    public abstract byte[] generateImage() throws DelaunayError;

    /**
     * Returns a default ImagingRenderer instance for a specified format.
     *
     * @param format The output format.
     * @return
     */
    public static ImagingRenderer getRenderer(ImagingFormat format) {
        switch (format) {
            case SVG:
                return new SvgRenderer();
            case PNG:
                return new PngRenderer();
            default: {
                throw new IllegalArgumentException("Unsupported imaging format");
            }
        }
    }

}
