import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;

public class ImageFilterApp extends Application {

    private Image originalImage;
    private ImageView imageView;

    public void start(Stage primaryStage) {
        originalImage = new Image("file:example.jpg"); // Replace "example.jpg" with your image file

        imageView = new ImageView(originalImage);

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Original", "Grayscale", "Sepia", "Blur");
        filterComboBox.setValue("Original");
        filterComboBox.setOnAction(e -> applyFilter(filterComboBox.getValue()));

        VBox root = new VBox(10, filterComboBox, imageView);
        root.setStyle("-fx-padding: 10px");

        Scene scene = new Scene(root);
        primaryStage.setTitle("Image Filter App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void applyFilter(String filter) {
        switch (filter) {
            case "Grayscale":
                setGrayscaleFilter();
                break;
            case "Sepia":
                setSepiaFilter();
                break;
            case "Blur":
                setBlurFilter();
                break;
            case "Original":
            default:
                clearFilters();
                break;
        }
    }

    private void setGrayscaleFilter() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        imageView.setEffect(colorAdjust);
    }

    private void setSepiaFilter() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSepiaTone(1);
        imageView.setEffect(colorAdjust);
    }

    private void setBlurFilter() {
        GaussianBlur blur = new GaussianBlur(10);
        imageView.setEffect(blur);
    }

    private void clearFilters() {
        imageView.setEffect(null);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
