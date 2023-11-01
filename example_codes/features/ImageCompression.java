package features;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCompression {
    public static Image compressImage(Image image, float quality) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);

        File output = new File("compressed_image.jpg");
        try {
            ImageIO.write(bImage, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Image(output.toURI().toString());
    }
}
