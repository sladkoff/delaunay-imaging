package com.sladit.delaunay.image;

import com.sladit.delaunay.mesh.DelaunayHelper;
import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class DelaunayImage {

    private final int width;
    private final int height;
    private BufferedImage image;
    private int points = 200;
    private ConstrainedMesh mesh;

    private DelaunayImage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static DelaunayImage random(int width, int height, int points) {
        DelaunayImage delaunayImage = new DelaunayImage(width, height);
        delaunayImage.setPoints(points);
        return delaunayImage;
    }

    public static DelaunayImage from(BufferedImage image) {
        DelaunayImage delaunayImage = new DelaunayImage(image.getWidth(), image.getHeight());
        delaunayImage.setImage(image);
        return delaunayImage;
    }

    public DelaunayImage setPoints(int points) {
        this.points = points;
        return this;
    }

    private void setImage(BufferedImage image) {
        this.image = image;
    }

    public DelaunayImage generateMesh() throws DelaunayError {
        if (image == null) {
            mesh = DelaunayHelper.generateRandomMesh(width, height, points);
        } else {
            mesh = DelaunayHelper.generateImagingMesh(image, points);
        }
        return this;
    }

    public ConstrainedMesh getMesh() {
        return mesh;
    }

    public byte[] render(ImagingFormat format) throws DelaunayError, IOException {
        return DelaunayRenderer.render(mesh, format);
    }

}
