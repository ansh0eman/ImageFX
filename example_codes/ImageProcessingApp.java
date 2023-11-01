import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;

import features.ImageCompression;
import features.ImageFilter;
import features.ImageResizer;

public class ImageProcessingApp extends Application {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Processing App");

        // Load an initial image
        File initialFile = new File("https://images.unsplash.com/photo-1682685797406-97f364419b4a?auto=format&fit=crop&q=80&w=2070&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        Image image = new Image(initialFile.toURI().toString());

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);

        HBox hbox = new HBox(10);
        hbox.getChildren().add(imageView);

        Button filterButton = new Button("Apply Filters");
        Button compressButton = new Button("Compress Image");
        Button resizeButton = new Button("Resize Image");

        filterButton.setOnAction(e -> {
            // Apply filter (Grayscale in this example)
            Image filteredImage = ImageFilter.applyFilter(image, "Grayscale");
            imageView.setImage(filteredImage);
        });

        compressButton.setOnAction(e -> {
            // Compress the image and replace the displayed image with the compressed version
            Image compressedImage = ImageCompression.compressImage(image, 0.5f);
            imageView.setImage(compressedImage);
        });

        resizeButton.setOnAction(e -> {
            // Resize the image and replace the displayed image with the resized version
            Image resizedImage = ImageResizer.resizeImage(image, 200, 150);
            imageView.setImage(resizedImage);
        });

        hbox.getChildren().addAll(filterButton, compressButton, resizeButton);

        Scene scene = new Scene(hbox, 800, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
