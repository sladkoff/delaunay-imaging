package com.sladit.delaunay;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.DEdge;
import org.jdelaunay.delaunay.geometries.DPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Helper Class for generating jdelaunay meshes.
 */
public class DelaunayHelper {

    private static final Random rand = new Random();

    /**
     * Initializes a random 2D DPoint within specified bounds.
     *
     * @param maxX
     * @param maxY
     * @return Random DPoint
     * @throws DelaunayError
     */
    static DPoint getRandomDPoint(int maxX, int maxY) throws DelaunayError {
        double x = rand.nextInt(maxX);
        double y = rand.nextInt(maxY);
        return new DPoint(x, y, 0);
    }

    /**
     * Initializes a List of a specified amount of random DEdges within specified bounds.
     *
     * @param maxWidth
     * @param maxHeight
     * @param edges     Number of edges to create.
     * @return List of DEdge
     * @throws DelaunayError
     */
    static List<DEdge> generateRandomEdges(int maxWidth, int maxHeight, int edges) throws DelaunayError {
        ArrayList<DEdge> toBeAdded = new ArrayList<>();
        for (long i = 0; i < edges; i++) {
            DPoint p1 = getRandomDPoint(maxWidth, maxHeight);
            DPoint p2 = getRandomDPoint(maxWidth, maxHeight);
            toBeAdded.add(new DEdge(p1, p2));
        }
        return toBeAdded;
    }

    /**
     * Creates an {@link ImagingMesh} from an image with the specified number of initial edges.
     *
     * @param image
     * @param edges
     * @return
     * @throws DelaunayError
     */
    public static ImagingMesh generateImagingMesh(BufferedImage image, int edges) throws DelaunayError {
        if (edges < 1) {
            throw new IllegalArgumentException("Edges parameter must be positive.");
        }

        List<DEdge> toBeAdded = generateRandomEdges(image.getWidth(), image.getHeight(), edges);

        //We perform a first sort to gain time during the insertion.
        Collections.sort(toBeAdded);

        ImagingMesh mesh = new ImagingMesh(image);
        mesh.setEdges(toBeAdded);
        //We force the integrity of the constraints given as an input.
        mesh.forceConstraintIntegrity();
        //We perform the triangulation
        mesh.processDelaunay();
        return mesh;
    }

    /**
     * Creates a random {@link ImagingMesh} with the specified dimensions and number of initial edges.
     * The color of the mesh is generated randomly from a dummy buffered image.
     *
     * @param width
     * @param height
     * @param edges
     * @return
     * @throws DelaunayError
     */
    public static ConstrainedMesh generateRandomMesh(int width, int height, int edges) throws DelaunayError {
        if (width < 1 | height < 1 | edges < 1) {
            throw new IllegalArgumentException("Edges parameter must be positive.");
        }

        List<DEdge> toBeAdded = generateRandomEdges(width, height, edges);

        //We perform a first sort to gain time during the insertion.
        Collections.sort(toBeAdded);

        // Generate a random image to feed the imaging mesh.
        BufferedImage dummyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int x = 0, y = 0;
        while (y < dummyImage.getHeight()) {
            int randHeight = rand.nextInt(100);
            if (y + randHeight > dummyImage.getHeight()) { randHeight = dummyImage.getHeight() - y; }
            while (x < dummyImage.getWidth()) {
                int randWidth = rand.nextInt(100);
                if (x + randWidth > dummyImage.getWidth()) {
                    randWidth = dummyImage.getWidth() - x;
                }
                int[] rgbs = new int[randWidth * randHeight];
                int randomRgb = getRandomColor().getRGB();
                for (int r = 0; r < rgbs.length; r++) {
                    rgbs[r] = randomRgb;
                }
                dummyImage.setRGB(x, y, randWidth, randHeight, rgbs, 0, randWidth);
                x += randWidth;
            }
            x = 0;
            y += randHeight;
        }

        ConstrainedMesh mesh = new ImagingMesh(dummyImage);
        mesh.setEdges(toBeAdded);
        //We force the integrity of the constraints given as an input.
        mesh.forceConstraintIntegrity();
        //We perform the triangulation
        mesh.processDelaunay();
        return mesh;
    }

    /**
     * Generates a random blue-greenish color.
     *
     * @return Color
     */
    private static Color getRandomColor() {
        return new Color(rand.nextInt(51), rand.nextInt(201), rand.nextInt(201));
    }

}
