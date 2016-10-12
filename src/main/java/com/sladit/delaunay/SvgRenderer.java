package com.sladit.delaunay;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.BoundaryBox;
import org.jfree.graphics2d.svg.MeetOrSlice;
import org.jfree.graphics2d.svg.PreserveAspectRatio;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.ViewBox;

/**
 * {@link org.jdelaunay.delaunay.ConstrainedMesh} to SVG renderer.
 */
class SvgRenderer {

    public static byte[] generateImage(ConstrainedMesh mesh) throws DelaunayError {
        if (mesh == null) throw new IllegalStateException("Mesh must be generated first.");
        BoundaryBox boundingBox = mesh.getBoundingBox();
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D((int) boundingBox.getMaxX(), (int) boundingBox.getMaxY());
        mesh.displayObject(svgGraphics2D);
        svgGraphics2D.setShapeRendering("crispEdges");
        ViewBox viewBox = new ViewBox(0, 0, svgGraphics2D.getWidth(), svgGraphics2D.getHeight());
        return svgGraphics2D.getSVGElement(null, false, viewBox, PreserveAspectRatio.XMID_YMID, MeetOrSlice.SLICE).getBytes();
    }
}
