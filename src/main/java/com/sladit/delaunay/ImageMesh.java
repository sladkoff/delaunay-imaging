package com.sladit.delaunay;

import org.apache.log4j.Logger;
import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.BoundaryBox;
import org.jdelaunay.delaunay.geometries.DPoint;
import org.jdelaunay.delaunay.geometries.DTriangle;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Leonid on 19.03.2016.
 */
public class ImageMesh extends ConstrainedMesh {

    private static final Logger log = Logger.getLogger(ImageMesh.class);

    private final BufferedImage image;

    public ImageMesh(BufferedImage image) {
        super();
        this.image = image;
    }

    @Override
    public void displayObject(Graphics g) throws DelaunayError {
        BoundaryBox boundingBox = getBoundingBox();

        final int xSize = (int) (image.getWidth() * 1.2);
        final int ySize = (int) (image.getHeight() * 1.2);

        final int offsetX = 0;
        final int offsetY = (int) (image.getHeight() * 1.2);

        double scaleX = xSize / (boundingBox.getMaxX() - boundingBox.getMinX());
        double scaleY = ySize / (boundingBox.getMaxY() - boundingBox.getMinY());

        final double minX = boundingBox.getMinX() + image.getWidth() * 0.1;
        final double minY = boundingBox.getMaxY() + image.getHeight() * 0.1;

        // Draw triangles
        if (!this.getTriangleList().isEmpty()) {
            for (DTriangle aTriangle : this.getTriangleList()) {

                BoundaryBox triangleBb = aTriangle.getBoundingBox();

                int triangleMinX, triangleMinY, triangleWidth, triangleHeight;

                triangleMinX = new Double(triangleBb.getMinX()).intValue();
                triangleMinY = new Double(triangleBb.getMinY()).intValue();
                triangleWidth = new Double((triangleBb.getMaxX() - triangleBb.getMinX())).intValue();
                triangleHeight = new Double((triangleBb.getMaxY() - triangleBb.getMinY())).intValue();

                int[] rgb;

                try {
                    rgb = image.getRGB(triangleMinX,
                                       triangleMinY,
                                       triangleWidth,
                                       triangleHeight,
                                       null,
                                       0,
                                       triangleWidth);

                } catch (Exception e) {
                    throw new TriangleColorException("Failed to retrieve triangle color from image",
                                                     e,
                                                     image.getWidth(),
                                                     image.getHeight(),
                                                     triangleWidth,
                                                     triangleHeight,
                                                     triangleMinX,
                                                     triangleMinY);
                }

                long redBucket = 0;
                long greenBucket = 0;
                long blueBucket = 0;

                for (int pixelRgb : rgb) {
                    Color pixelColor = new Color(pixelRgb);
                    redBucket += pixelColor.getRed();
                    greenBucket += pixelColor.getGreen();
                    blueBucket += pixelColor.getBlue();
                }

                Color averageColor;
                try {
                    averageColor = new Color((int) (redBucket / rgb.length),
                                             (int) (greenBucket / rgb.length),
                                             (int) (blueBucket / rgb.length));
                } catch (Exception e) {
                    throw new TriangleColorException("Failed to calculate average triangle color",
                                                     e,
                                                     image.getWidth(),
                                                     image.getHeight(),
                                                     triangleWidth,
                                                     triangleHeight,
                                                     triangleMinX,
                                                     triangleMinY);
                }

                int triangleColor = averageColor.getRGB();
                aTriangle.setProperty(triangleColor);

                aTriangle.displayObject(g, offsetX, offsetY, minX, minY, scaleX, scaleY, false);
            }
        }
    }

    public static class TriangleColorException extends RuntimeException {

        public TriangleColorException(String message, Throwable cause, int imageWidth, int imageHeight,
                                      int triangleWidth, int triangleHeight, int triangleMinX, int triangleMinY) {
            super(message, cause);
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
            this.triangleWidth = triangleWidth;
            this.triangleHeight = triangleHeight;
            this.triangleMinX = triangleMinX;
            this.triangleMinY = triangleMinY;
        }

        private int imageWidth;
        private int imageHeight;
        private int triangleWidth;
        private int triangleHeight;
        private int triangleMinX;
        private int triangleMinY;

        public int getImageWidth() {
            return imageWidth;
        }

        public int getImageHeight() {
            return imageHeight;
        }

        public int getTriangleWidth() {
            return triangleWidth;
        }

        public int getTriangleHeight() {
            return triangleHeight;
        }

        public int getTriangleMinX() {
            return triangleMinX;
        }

        public int getTriangleMinY() {
            return triangleMinY;
        }
    }

}
