import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;

//Importing modules for Charts
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.File;

import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.IIOImage;
import javax.imageio.stream.FileImageOutputStream;

import javafx.scene.control.Slider;

public class ImageFX extends Application
{
    private ImageView imageView = new ImageView();
    private ImageView imageView1 = new ImageView();

    private Image selectedImage = null;
    private Image resized;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Processing App");

        VBox root = new VBox(10);

        Button loadImageButton = new Button("Load Image");
        loadImageButton.setOnAction(e -> loadAndDisplayImage(primaryStage));

        imageView1.setFitWidth(300); // Width in pixels
        imageView1.setFitHeight(200); // Height in pixels
        imageView1.setPreserveRatio(true);

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

        Button histoButton = new Button("Histogram");
        histoButton.setOnAction(e -> histogram(selectedImage));


        Button saveImageButton = new Button("Save Image");
        saveImageButton.setOnAction(e -> saveImage(primaryStage, qualitySlider));

        root.getChildren().addAll(loadImageButton, imageView1, filterComboBox, histoButton, qualitySlider, saveImageButton);

        Scene scene = new Scene(root, 800, 600);
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
            resized = new Image(imagePath);
            imageView.setImage(selectedImage);
            imageView1.setImage(resized);
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

    private void histogram(Image img)
    {
        int[][] histogram_data = new int[3][256];

        for(int i[]: histogram_data)
        {
            for(int j: i)
            {
                j = 0;
            }
        }
        
        PixelReader pixelReader = img.getPixelReader();
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
            {

                int pixel = pixelReader.getArgb(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                histogram_data[0][red]++;
                histogram_data[1][green]++;
                histogram_data[2][blue]++;
            }
        }

        CategoryAxis categoryAxis = new CategoryAxis(); // X-axis for categories
        NumberAxis valueAxis = new NumberAxis(); // Y-axis for values
        BarChart<String, Number> histogramChart = new BarChart<>(categoryAxis, valueAxis);
        histogramChart.setTitle("Histogram");
        categoryAxis.setLabel("Intensity Level");
        valueAxis.setLabel("Frequency");

        XYChart.Series<String, Number> redSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> greenSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> blueSeries = new XYChart.Series<>();

        for (int i = 0; i < 256; i++)
        {
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
        imageView1.setEffect(colorAdjust);
    }

    private void setSepiaFilter() {
        SepiaTone sepiaTone = new SepiaTone();
        sepiaTone.setLevel(0.8); // Adjust sepia intensity here
        imageView.setEffect(sepiaTone);
        imageView1.setEffect(sepiaTone);

    }

    private void setBlurFilter() {
        GaussianBlur blur = new GaussianBlur(10);
        imageView.setEffect(blur);
        imageView1.setEffect(blur);

    }

    private void clearFilters() {
        imageView.setEffect(null);
        imageView1.setEffect(null);
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