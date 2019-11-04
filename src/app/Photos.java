package app;

import java.io.File; 
import java.io.IOException;

import controller.PhotosController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Photos extends Application {

	private double xOffset = 0;
    private double yOffset = 0;
	
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		GridPane root = loader.load();
		
		PhotosController listController = loader.getController();
		listController.start(primaryStage);
		Scene scene = new Scene(root, 500, 450);
		// Enables us to drag the program window around.
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
