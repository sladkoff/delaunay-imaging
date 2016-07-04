package com.sladit.delaunay;

import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.BoundaryBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by leonid on 7/4/16.
 */
public class PngRenderer extends ImagingRenderer {

    public PngRenderer(BufferedImage image, int points) throws DelaunayError {
        super(image, points);
    }

    @Override
    public byte[] generate() throws DelaunayError {
        BoundaryBox box = mesh.getBoundingBox();
        BufferedImage bi = new BufferedImage((int) box.getMaxX(), (int) box.getMaxX(), BufferedImage.TYPE_INT_ARGB);
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
