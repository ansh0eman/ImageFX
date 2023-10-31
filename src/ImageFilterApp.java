import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;

public class ImageFilterApp extends Application {

    private Image originalImage;
    private ImageView imageView;

    public void start(Stage primaryStage) {
        originalImage = new Image("https://images.unsplash.com/photo-1698527692282-fc5d8ab13771?auto=format&fit=crop&q=80&w=1887&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"); // Replace "example.jpg" with your image file

        imageView = new ImageView(originalImage);

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Original", "Grayscale", "Sepia", "Blur");
        filterComboBox.setValue("Original");
        filterComboBox.setOnAction(e -> applyFilter(filterComboBox.getValue()));

        VBox root = new VBox(10, filterComboBox, imageView);
        root.setStyle("-fx-padding: 10px");

        Scene scene = new Scene(root);
        primaryStage.setTitle("Image Filters App");
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
        SepiaTone sepiaTone = new SepiaTone();
        sepiaTone.setLevel(0.8); // Adjust sepia intensity here
        imageView.setEffect(sepiaTone);
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
