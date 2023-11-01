package features;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageFilter {

    public static Image applyGrayscaleFilter(Image image) {
        ImageView imageView = new ImageView(image);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1.0); // Applying grayscale by removing saturation
        imageView.setEffect(colorAdjust);
        return imageView.snapshot(null, null);
    }

    public static Image applySepiaFilter(Image image) {
        ImageView imageView = new ImageView(image);
        SepiaTone sepiaTone = new SepiaTone();
        sepiaTone.setLevel(0.8); // Adjusting the sepia level
        imageView.setEffect(sepiaTone);
        return imageView.snapshot(null, null);
    }

    public static Image applyBlurFilter(Image image) {
        ImageView imageView = new ImageView(image);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10); // Set the blur radius
        imageView.setEffect(gaussianBlur);
        return imageView.snapshot(null, null);
    }

    public static Image applyFilter(Image image, String filterType) {
        switch (filterType) {
            case "Grayscale":
                return applyGrayscaleFilter(image);
            case "Sepia":
                return applySepiaFilter(image);
            case "Blur":
                return applyBlurFilter(image);
            default:
                return image; // Return the original image if the filter is not recognized
        }
    }
}
