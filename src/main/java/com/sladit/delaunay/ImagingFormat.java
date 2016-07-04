package com.sladit.delaunay;

import java.awt.*;

/**
 * Supported image formats that can be generated .
 */
public enum ImagingFormat {
    SVG("svg"),
    PNG("png");

    private final String format;

    ImagingFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }

    public static ImagingFormat getImagingFormat(String value) {
        for(ImagingFormat v : values())
            if(v.toString().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }

}
