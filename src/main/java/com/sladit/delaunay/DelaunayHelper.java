package com.sladit.delaunay;

import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.DEdge;
import org.jdelaunay.delaunay.geometries.DPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by leonid on 6/29/16.
 */
public class DelaunayHelper {
    private static final Random rand = new Random();

    public static DPoint getRandomDPoint(int maxX, int maxY) throws DelaunayError {
        double x = rand.nextInt(maxX);
        double y = rand.nextInt(maxY);
        return new DPoint(x, y, 0);
    }

    public static List<DEdge> generateRandomEdges(int maxWidth, int maxHeight, int points) throws DelaunayError {
        ArrayList<DEdge> toBeAdded = new ArrayList<>();
        for (long i = 0; i < points; i++) {
            DPoint p1 = getRandomDPoint(maxWidth, maxHeight);
            DPoint p2 = getRandomDPoint(maxWidth, maxHeight);
            toBeAdded.add(new DEdge(p1, p2));
        }
        return toBeAdded;
    }

}
