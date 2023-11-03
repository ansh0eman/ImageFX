import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResizingTest2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Resizing Example");

        // Load an image
        Image originalImage = new Image("");

        // Create an ImageView and set the image
        ImageView imageView = new ImageView(originalImage);
        
        // Set the preferred width and height
        double targetWidth = 300; // Desired width
        double targetHeight = 200; // Desired height

        // Adjust the image dimensions while maintaining aspect ratio
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        imageView.setPreserveRatio(true);

        // Display the ImageView in a VBox
        VBox vbox = new VBox(imageView);

        // Set the scene and show the stage
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
