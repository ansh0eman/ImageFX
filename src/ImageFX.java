import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.control.Slider;
import javafx.geometry.Pos;

//Importing modules for Charts
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.File;

import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.IIOImage;
import javax.imageio.stream.FileImageOutputStream;

public class TEST2 extends Application {
    private ImageView imageView = new ImageView();
    private Image selectedImage = null;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("ImageFX");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#e5e5e5;"); // Red to Blue gradient
        
        VBox vbox = new VBox();
        VBox leftBox = new VBox(10);

        Label title = new Label("ImageFX");
        title.setStyle("-fx-text-fill: linear-gradient(to left, #FF0000, #0000FF);-fx-font-family: \"Times New Roman\";-fx-font-size: 40px; -fx-font-weight: bold; ");
        // title.setStyle("");
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setMinHeight(50);

        Button loadImageButton = new Button("Load Image");
        loadImageButton.setOnAction(e -> loadAndDisplayImage(primaryStage));
        loadImageButton.setStyle("-fx-background-radius: 30; -fx-font-size: 16;");

        imageView.setFitWidth(500);
        imageView.setFitHeight(375);
        imageView.setPreserveRatio(true);

        Slider qualitySlider = new Slider(0, 1, 0.7);
        qualitySlider.setBlockIncrement(0.1);
        qualitySlider.setShowTickLabels(true);
        qualitySlider.setShowTickMarks(true);
        qualitySlider.setMajorTickUnit(0.1);
        qualitySlider.setMinorTickCount(0);

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Original", "Grayscale", "Sepia", "Blur");
        filterComboBox.setValue("Original");
        filterComboBox.setOnAction(e -> applyFilter(filterComboBox.getValue()));
        filterComboBox.setStyle("-fx-background-radius: 30; -fx-font-size: 16;");


        Button histoButton = new Button("Histogram");
        histoButton.setOnAction(e -> histogram(selectedImage));
        histoButton.setStyle("-fx-background-radius: 30; -fx-font-size: 16;");

        Button saveImageButton = new Button("Save Image");
        saveImageButton.setOnAction(e -> saveImage(primaryStage, qualitySlider));
        saveImageButton.setStyle("-fx-background-radius: 30; -fx-font-size: 16;");

        leftBox.getChildren().addAll(loadImageButton, imageView, filterComboBox, histoButton, qualitySlider, saveImageButton);
        leftBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(titleBox, leftBox);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 660);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadAndDisplayImage(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(ownerWindow);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            selectedImage = new Image(imagePath);
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

    private void histogram(Image img) {
        int[][] histogram_data = new int[3][256];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 256; j++) {
                histogram_data[i][j] = 0;
            }
        }

        PixelReader pixelReader = img.getPixelReader();
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = pixelReader.getArgb(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                histogram_data[0][red]++;
                histogram_data[1][green]++;
                histogram_data[2][blue]++;
            }
        }

        CategoryAxis categoryAxis = new CategoryAxis();
        NumberAxis valueAxis = new NumberAxis();
        BarChart<String, Number> histogramChart = new BarChart<>(categoryAxis, valueAxis);
        histogramChart.setTitle("Histogram");
        categoryAxis.setLabel("Intensity Level");
        valueAxis.setLabel("Frequency");

        XYChart.Series<String, Number> redSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> greenSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> blueSeries = new XYChart.Series<>();

        for (int i = 0; i < 256; i++) {
            redSeries.getData().add(new XYChart.Data<>(Integer.toString(i), histogram_data[0][i]));
            greenSeries.getData().add(new XYChart.Data<>(Integer.toString(i), histogram_data[1][i]));
            blueSeries.getData().add(new XYChart.Data<>(Integer.toString(i), histogram_data[2][i]));
        }

        redSeries.setName("Red Channel");
        greenSeries.setName("Green Channel");
        blueSeries.setName("Blue Channel");

        histogramChart.getData().addAll(redSeries, greenSeries, blueSeries);

        Stage histogramStage = new Stage();
        histogramStage.setTitle("Histogram");
        histogramStage.setScene(new Scene(histogramChart, 600, 400));
        histogramStage.show();
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

    private void saveImage(Window ownerWindow, Slider qualitySlider) {
        if (selectedImage != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.png"));

            File selectedFile = fileChooser.showSaveDialog((Stage) ownerWindow);

            if (selectedFile != null) {
                try {
                    int width = (int) selectedImage.getWidth();
                    int height = (int) selectedImage.getHeight();

                    WritableImage writableImage = new WritableImage(width, height);
                    imageView.snapshot(null, writableImage);

                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

                    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
                    if (writers.hasNext()) {
                        ImageWriter writer = writers.next();
                        ImageWriteParam writeParam = writer.getDefaultWriteParam();
                        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

                        double compressionQuality = qualitySlider.getValue();
                        writeParam.setCompressionQuality((float) compressionQuality);

                        FileImageOutputStream output = new FileImageOutputStream(selectedFile);
                        writer.setOutput(output);
                        writer.write(null, new IIOImage(bufferedImage, null, null), writeParam);
                        writer.dispose();

                        System.out.println("Image saved to: " + selectedFile.getAbsolutePath());
                    } else {
                        System.out.println("No suitable image writer found.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No image to save.");
            }
        }
    }
}
