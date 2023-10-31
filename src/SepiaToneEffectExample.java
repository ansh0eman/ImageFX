import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.effect.SepiaTone; 
import javafx.scene.image.Image; 
import javafx.scene.image.ImageView; 
import javafx.stage.Stage;  

public class SepiaToneEffectExample extends Application { 
   @Override 
   public void start(Stage stage) {       
      //Creating an image 
      Image image = new Image("https://images.unsplash.com/photo-1698527692282-fc5d8ab13771?auto=format&fit=crop&q=80&w=1887&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"); 
       
      //Setting the image view 
      ImageView imageView = new ImageView(image); 
      
      //Setting the position of the image  
      imageView.setX(150); 
      imageView.setY(0);
      
      //setting the fit height and width of the image view 
      imageView.setFitHeight(300); 
      imageView.setFitWidth(400); 
      
      //Setting the preserve ratio of the image view 
      imageView.setPreserveRatio(true);    
       
      //Instanting the SepiaTone class 
      SepiaTone sepiaTone = new SepiaTone(); 
      
      //Setting the level of the effect 
      sepiaTone.setLevel(0.8); 
      
      //Applying SepiaTone effect to the image 
      imageView.setEffect(sepiaTone);      
         
      //Creating a Group object  
      Group root = new Group(imageView);   
               
      //Creating a scene object 
      Scene scene = new Scene(root, 600, 300);  
      
      //Setting title to the Stage 
      stage.setTitle("Sepia tone effect example"); 
         
      //Adding scene to the stage 
      stage.setScene(scene); 
         
      //Displaying the contents of the stage 
      stage.show();         
   } 
   public static void main(String args[]){ 
      launch(args); 
   } 
}