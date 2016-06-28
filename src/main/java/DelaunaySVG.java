import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.DEdge;
import org.jdelaunay.delaunay.geometries.DPoint;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Leonid on 28.06.2016.
 */
public class DelaunaySVG {

    public static final int DEFAULT_POINTS = 200;
    private static final Random rand = new Random();


    public static String create(BufferedImage image) throws DelaunayError {
        return create(image, DEFAULT_POINTS);
    }

    public static String create(BufferedImage image, int points) throws DelaunayError {
        if (points < 1) {
            throw new IllegalArgumentException("Points integer must be positive.");
        }

        ArrayList<DEdge> toBeAdded = new ArrayList<>();
        for (long i = 0; i < points; i++) {
            DPoint p1 = getRandomDPoint(image.getWidth(), image.getHeight());
            DPoint p2 = getRandomDPoint(image.getWidth(), image.getHeight());
            toBeAdded.add(new DEdge(p1, p2));
        }

        //We perform a first sort to gain time during the insertion.
        Collections.sort(toBeAdded);

        ImageMesh mesh = new ImageMesh(image);
        mesh.setEdges(toBeAdded);
        //We force the integrity of the constraints given as an input.
        mesh.forceConstraintIntegrity();
        //We perform the triangulation
        mesh.processDelaunay();
        return SvgMeshRenderer.generate(mesh);
    }

    private static DPoint getRandomDPoint(int maxX, int maxY) throws DelaunayError {
        double x = rand.nextInt(maxX);
        double y = rand.nextInt(maxY);
        return new DPoint(x, y, 0);
    }

}
