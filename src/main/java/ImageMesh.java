import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.BoundaryBox;
import org.jdelaunay.delaunay.geometries.DTriangle;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Leonid on 19.03.2016.
 */
public class ImageMesh extends ConstrainedMesh {

    private final BufferedImage image;

    public ImageMesh(BufferedImage image) {
        super();
        this.image = image;
    }

    public void render(Graphics g) throws DelaunayError {
        BoundaryBox boundingBox = getBoundingBox();

        final int xSize = (int) (image.getWidth() * 1.2);
        final int ySize = (int) (image.getHeight() * 1.2);

        final int offsetX = 0;
        final int offsetY = (int) (image.getHeight() * 1.2);

        final double scaleX = xSize / (boundingBox.getMaxX() - boundingBox.getMinX());
        final double scaleY = ySize / (boundingBox.getMaxY() - boundingBox.getMinY());

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
                triangleHeight = new Double(triangleBb.getMaxY() - triangleBb.getMinY()).intValue();

                int[] rgb;

                try {
                    rgb = image.getRGB(triangleMinX, triangleMinY, triangleWidth, triangleHeight, null, 0, triangleWidth);
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    aioobe.printStackTrace();
                    rgb = new int[0];
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

                Color averageColor = new Color((int) (redBucket / rgb.length), (int) (greenBucket / rgb.length), (int) (blueBucket / rgb.length));

                int triangleColor = averageColor.getRGB();
                aTriangle.setProperty(triangleColor);

                aTriangle.displayObject(g, offsetX, offsetY, minX, minY, scaleX, scaleY);
            }
        }
    }

}
