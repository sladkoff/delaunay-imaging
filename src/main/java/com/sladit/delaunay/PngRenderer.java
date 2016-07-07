package com.sladit.delaunay;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.BoundaryBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * {@link org.jdelaunay.delaunay.ConstrainedMesh} to PNG renderer.
 */
class PngRenderer {

    static byte[] generateImage(ConstrainedMesh mesh) throws DelaunayError {
        if (mesh == null) throw new IllegalStateException("Mesh must be generated first");
        BoundaryBox box = mesh.getBoundingBox();
        BufferedImage bi = new BufferedImage((int) box.getMaxX(), (int) box.getMaxY(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bi.createGraphics();
        mesh.displayObject(graphics);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(bi, "PNG", out);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
