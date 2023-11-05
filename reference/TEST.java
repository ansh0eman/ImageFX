import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class TEST extends Application {
    private ImageView imageView = new ImageView();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image FX App");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #aec6cf, #c175ff);"); // Red to Blue gradient
        // Title
        Label title = new Label("Image FX");
        title.setStyle("-fx-font-size: 24; -fx-padding: 10 0 10 10;");
        BorderPane.setAlignment(title, javafx.geometry.Pos.CENTER);
        root.setTop(title);
        title.setStyle("-fx-font-size: 24; -fx-padding: 10 20 10 20;"); // Adjust the padding values for the left and right sides

        // Options for filters
        VBox options = new VBox(10);
        options.setStyle("-fx-padding: 20;");
        options.setAlignment(javafx.geometry.Pos.CENTER);

        // File chooser
        FileChooser fileChooser = new FileChooser();

        // Load Image button
        Button loadImageButton = new Button("Load Image");
        loadImageButton.setStyle("-fx-background-color: gray; -fx-background-radius: 20; -fx-font-size: 16; -fx-text-fill: white; -fx-border-color: gray; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 0, 0, 0, 1);");
        loadImageButton.setOnAction(e -> loadAndDisplayImage(primaryStage, fileChooser));

        // Imageview Dimensions to fit screen
        imageView.setFitWidth(300); // Width in pixels
        imageView.setFitHeight(200); // Height in pixels
        imageView.setPreserveRatio(true);

        // Container for image display
        HBox imageBox = new HBox(20);
        imageBox.setAlignment(javafx.geometry.Pos.CENTER);
        imageBox.getChildren().add(imageView);

        // Dropdown box for filters
        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Original", "Grayscale", "Sepia", "Blur");
        filterComboBox.setValue("Original");
        filterComboBox.setOnAction(e -> applyFilter(filterComboBox.getValue()));

        options.getChildren().addAll(loadImageButton, filterComboBox);

        // Place components in the BorderPane
        root.setCenter(imageBox);
        root.setBottom(options);

        // Set the scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Load and display the selected image
    private void loadAndDisplayImage(Stage primaryStage, FileChooser fileChooser) {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image selectedImage = new Image(imagePath);
            imageView.setImage(selectedImage);
        }
    }

    // Apply different image filters
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

    // Set the grayscale filter
    private void setGrayscaleFilter() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        imageView.setEffect(colorAdjust);
    }

    // Set the sepia filter
    private void setSepiaFilter() {
        SepiaTone sepiaTone = new SepiaTone();
        sepiaTone.setLevel(0.8);
        imageView.setEffect(sepiaTone);
    }

    // Set the blur filter
    private void setBlurFilter() {
        GaussianBlur blur = new GaussianBlur(10);
        imageView.setEffect(blur);
    }

    // Clear all filters
    private void clearFilters() {
        imageView.setEffect(null);
    }
}
