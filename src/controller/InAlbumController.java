package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class InAlbumController {
	
	@FXML
	TilePane tiles;
	
	public void start() throws FileNotFoundException,IOException {  
		ImageView imageView;
		while (true) {
            imageView = createThumbnails();
            tiles.getChildren().addAll(imageView);
            break;
		}
		//may need to put this else where
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                    if(mouseEvent.getClickCount() == 2){
                        
                    	//need some stuff to do when two are clicked
                    }
                }
            }});
    }
		
		private ImageView createThumbnails(/*need some parameters maybe*/) {
	        ImageView imageView = null;
	        Image image = null;
	        /*image = new Image(need to find the source image, maybe need to store current user somewhere, 150, 0, true, true);*/
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
	        return imageView;
	    }
	

}
