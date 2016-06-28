import org.jdelaunay.delaunay.error.DelaunayError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Leonid on 19.03.2016.
 */
public class Main {

    public static void main(String[] args) throws DelaunayError {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(args[0]));
        } catch (Exception e) {
            System.out.println("Image not found or not specified");
            return;
        }

        String svg = DelaunaySVG.create(image);
        System.out.println(svg);
    }
}
