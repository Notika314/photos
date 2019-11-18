package app;

import java.io.File; 
import java.io.IOException;
import controller.LoginController;
import controller.PhotosController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import controller.LoginController;
public class Photos extends Application {

	private double xOffset = 0;
    private double yOffset = 0;
    
    public static BorderPane root;
	
	@Override
	public void start(Stage primaryStage) 
	throws IOException , ClassNotFoundException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/photos.fxml"));
		root = loader.load();
		Pane bar = (Pane)root.getChildren().get(0);		
		PhotosController photosController = loader.getController();
		photosController.start(primaryStage);
		Scene scene = new Scene(root, 1440, 900);
		// Enables us to drag the program window around.
		bar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        bar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        // Removes the default file bar.
		primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        root.requestFocus();
		primaryStage.show(); 
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
