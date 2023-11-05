import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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

public class ImageFX extends Application {
    private ImageView imageView = new ImageView();
    private Image currentImage = null;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Processing App");

        VBox root = new VBox(10);

        Button loadImageButton = new Button("Load Image");
        loadImageButton.setOnAction(e -> loadAndDisplayImage(primaryStage));

        Slider qualitySlider = new Slider(0, 1, 0.7);
        qualitySlider.setBlockIncrement(0.1);
        qualitySlider.setShowTickLabels(true);
        qualitySlider.setShowTickMarks(true);
        qualitySlider.setMajorTickUnit(0.1);
        qualitySlider.setMinorTickCount(0);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(loadImageButton, qualitySlider);

        Button saveImageButton = new Button("Save Image");
        saveImageButton.setOnAction(e -> saveImage(primaryStage, qualitySlider));

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Original", "Grayscale", "Sepia", "Blur");
        filterComboBox.setValue("Original");
        filterComboBox.setOnAction(e -> applyFilter(filterComboBox.getValue()));

        imageView.setPreserveRatio(true);

        ScrollPane scrollPane = new ScrollPane(imageView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        root.getChildren().addAll(buttonBox, scrollPane, filterComboBox, saveImageButton);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadAndDisplayImage(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(ownerWindow);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            currentImage = new Image(imagePath);
            imageView.setImage(currentImage);
            imageView.setFitWidth(currentImage.getWidth());
            imageView.setFitHeight(currentImage.getHeight());
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

    private void saveImage(Window ownerWindow, Slider qualitySlider) {
        if (currentImage != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.png"));

            File selectedFile = fileChooser.showSaveDialog((Stage) ownerWindow);

            if (selectedFile != null) {
                try {
                    int width = (int) currentImage.getWidth();
                    int height = (int) currentImage.getHeight();

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
