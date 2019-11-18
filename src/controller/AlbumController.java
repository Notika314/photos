package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AlbumController {
	
	@FXML
	ListView listView;

	public void start(Stage mainStage) throws FileNotFoundException,IOException {  
		
		//may need to put this else where
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                    if(mouseEvent.getClickCount() == 2){
                        
                    	//need some stuff to do when two are clicked
                    }
                }
            }});
    }
	
}
