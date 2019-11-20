package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import model.Picture;

public class PictureController {
	@FXML ImageView image;
	
	public void start() throws IOException {  
		
		System.out.println(Picture.curr);
		System.out.println(Picture.curr.file.getCanonicalPath());
		
		//image = new ImageView();
	    image.setImage(new Image(new FileInputStream(Picture.curr.file)));

	}
	
	
	public void caption() {
		
	}
	
	public void addType() {
		
	}
	
	public void addTag() {
		
	}
	
	public void deleteTag() {
		
	}
}
