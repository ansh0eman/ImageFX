import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.PixelReader;

import java.io.File;

public class histogram extends Application {
    private ImageView imageView = new ImageView();
    private Button displayHistogramButton = new Button("Display Histogram");
    private Stage histogramStage;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Processing App");

        VBox root = new VBox(10);

        Button loadImageButton = new Button("Load Image");
        loadImageButton.setOnAction(e -> loadAndDisplayImage(primaryStage));

        imageView.setFitWidth(300); // Width in pixels
        imageView.setFitHeight(200); // Height in pixels
        imageView.setPreserveRatio(true);

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Original", "Grayscale", "Sepia", "Blur");
        filterComboBox.setValue("Original");
        filterComboBox.setOnAction(e -> applyFilter(filterComboBox.getValue()));

        // Add the "Display Histogram" button and set its action
        displayHistogramButton.setOnAction(e -> displayHistogram());

        HBox controls = new HBox(10);
        controls.getChildren().addAll(loadImageButton, filterComboBox, displayHistogramButton);

        root.getChildren().addAll(controls, imageView);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadAndDisplayImage(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(ownerWindow);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image selectedImage = new Image(imagePath);
            imageView.setImage(selectedImage);
        }
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

    private void displayHistogram() {
        if (imageView.getImage() != null) {
            // Calculate the histogram data
            Image image = imageView.getImage();
            int[] histogram = calculateHistogram(image);

            // Create a new stage for displaying the histogram
            if (histogramStage == null) {
                histogramStage = new Stage();
                histogramStage.setTitle("Histogram");
                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                BarChart<String, Number> histogramChart = new BarChart<>(xAxis, yAxis);

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Histogram");

                for (int i = 0; i < histogram.length; i++) {
                    series.getData().add(new XYChart.Data<>(String.valueOf(i), histogram[i]));
                }

                histogramChart.getData().add(series);

                Scene histogramScene = new Scene(histogramChart, 400, 300);
                histogramStage.setScene(histogramScene);
            }

            // Show the histogram stage
            histogramStage.show();
        }
    }

    private int[] calculateHistogram(Image image) {
        int[] histogram = new int[256]; // Assuming 256 bins for grayscale values

        // Calculate the histogram data here

        return histogram;
    }
}
