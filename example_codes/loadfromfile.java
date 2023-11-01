import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class loadfromfile extends Application {
    private ImageView imageView = new ImageView();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Processing App");

        VBox root = new VBox(10);

        Button loadImageButton = new Button("Load Image");
        loadImageButton.setOnAction(e -> loadAndDisplayImage(primaryStage));

        root.getChildren().addAll(loadImageButton, imageView);

        Scene scene = new Scene(root, 1000, 800);
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
            imageView.setFitWidth(500);
            imageView.setFitHeight(500);
            imageView.setPreserveRatio(true);
            imageView.setImage(selectedImage);
        }
    }
}
