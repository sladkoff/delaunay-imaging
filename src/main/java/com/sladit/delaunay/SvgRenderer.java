package com.sladit.delaunay;

import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.BoundaryBox;
import org.jfree.graphics2d.svg.SVGGraphics2D;

/**
 * {@link org.jdelaunay.delaunay.ConstrainedMesh} to SVG renderer.
 */
public class SvgRenderer extends ImagingRenderer {

    @Override
    public byte[] generateImage() throws DelaunayError {
        if (mesh == null) throw new IllegalStateException("Mesh must be generated first.");
        BoundaryBox boundingBox = mesh.getBoundingBox();
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D((int) boundingBox.getMaxX(), (int) boundingBox.getMaxY());
        mesh.displayObject(svgGraphics2D);
        svgGraphics2D.setShapeRendering("crispEdges");
        svgGraphics2D.setViewBox(0, 0, svgGraphics2D.getWidth(), svgGraphics2D.getHeight());
        svgGraphics2D.setPrintDimensions(false);
        svgGraphics2D.setPreserveAspectRatio("xMidYMid", "slice");

        return svgGraphics2D.getSVGElement().getBytes();
    }
}
