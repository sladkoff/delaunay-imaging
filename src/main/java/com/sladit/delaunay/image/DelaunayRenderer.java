package com.sladit.delaunay.image;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.BoundaryBox;
import org.jfree.graphics2d.svg.MeetOrSlice;
import org.jfree.graphics2d.svg.PreserveAspectRatio;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.ViewBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DelaunayRenderer {

    public static byte[] render(ConstrainedMesh mesh, ImagingFormat format) throws DelaunayError, IOException {
        switch (format) {
            case PNG:
                return DelaunayRenderer.toPng(mesh);
            case SVG:
                return DelaunayRenderer.toSvg(mesh);
            default:
                return null;
        }
    }

    private static byte[] toPng(ConstrainedMesh mesh) throws DelaunayError, IOException {
        BoundaryBox box = mesh.getBoundingBox();
        BufferedImage bi = new BufferedImage((int) box.getMaxX(), (int) box.getMaxY(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bi.createGraphics();
        mesh.displayObject(graphics);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "PNG", out);
        return out.toByteArray();

    }

    private static byte[] toSvg(ConstrainedMesh mesh) throws DelaunayError {
        BoundaryBox boundingBox = mesh.getBoundingBox();
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D((int) boundingBox.getMaxX(), (int) boundingBox.getMaxY());
        mesh.displayObject(svgGraphics2D);
        svgGraphics2D.setShapeRendering("crispEdges");
        ViewBox viewBox = new ViewBox(0, 0, svgGraphics2D.getWidth(), svgGraphics2D.getHeight());
        return svgGraphics2D.getSVGElement(null, false, viewBox, PreserveAspectRatio.XMID_YMID, MeetOrSlice.SLICE).getBytes();
    }
}
