package features;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageResizer {
    public static Image resizeImage(Image image, int targetWidth, int targetHeight) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        imageView.setPreserveRatio(true); // Maintain the image's aspect ratio

        return imageView.snapshot(null, null);
    }
}

