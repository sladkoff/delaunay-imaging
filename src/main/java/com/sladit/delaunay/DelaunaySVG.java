package com.sladit.delaunay;

import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.DEdge;
import org.jdelaunay.delaunay.geometries.DPoint;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.sladit.delaunay.DelaunayHelper.generateRandomEdges;
import static com.sladit.delaunay.DelaunayHelper.getRandomDPoint;

/**
 * Created by Leonid on 28.06.2016.
 */
public class DelaunaySVG {

    public static final int DEFAULT_POINTS = 200;

    public static String create(BufferedImage image) throws DelaunayError {
        return create(image, DEFAULT_POINTS);
    }

    public static String create(BufferedImage image, int points) throws DelaunayError {
        if (points < 1) {
            throw new IllegalArgumentException("Points integer must be positive.");
        }

        List<DEdge> toBeAdded = generateRandomEdges(image.getWidth(), image.getHeight(), points);

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

}
