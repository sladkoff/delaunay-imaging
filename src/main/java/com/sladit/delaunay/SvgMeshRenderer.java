package com.sladit.delaunay;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.BoundaryBox;
import org.jfree.graphics2d.svg.SVGGraphics2D;

/**
 * Created by Leonid on 19.03.2016.
 */
public class SvgMeshRenderer {

    public static String generate(ConstrainedMesh mesh) throws DelaunayError {
        BoundaryBox boundingBox = mesh.getBoundingBox();
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D((int) boundingBox.getMaxX(), (int) boundingBox.getMaxY());
        mesh.displayObject(svgGraphics2D);
        svgGraphics2D.setShapeRendering("crispEdges");
        return svgGraphics2D.getSVGElement();
    }

}
