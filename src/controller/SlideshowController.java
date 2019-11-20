package controller;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Picture;

public class SlideshowController {

	@FXML
	ImageView image;
	
	public void start() throws IOException {
	    image.setImage(new Image(new FileInputStream(Picture.curr.file)));

	}
	
	public void returnLast() {
		
	}
	
	public void forward() {
		
	}
	
	public void backward() {
		
	}
	
}
