package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import app.Photos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Album;
import model.Picture;

public class SlideshowController {

	@FXML
	ImageView image;
	@FXML Button returnBtn;
	@FXML Button forwardBtn;
	@FXML Button backwardBtn;
	private int i;
	
	public void start() throws IOException {
	    image.setImage(new Image(new FileInputStream(Picture.curr.file)));
	    i = Album.curr.pictureExists(Picture.curr);
	    if (i == 0) {
	    	backwardBtn.setDisable(true);
	    }
	    else {
	    	backwardBtn.setDisable(false);
	    }
	    if (i == Album.curr.pictures.size()-1) {
	    	forwardBtn.setDisable(true);
	    }
	    else {
	    	forwardBtn.setDisable(false);
	    }
	    
	}
	
	public void returnLast() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/navbar.fxml"));
		try {
			Photos.root.setTop(loader.load());
		} catch (IOException e1) {
		}
		NavbarController cont = loader.getController();
		cont.visHome();
		cont.visLog();
		cont.disHome();
		cont.disLog();
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/inAlbum.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
		}
		InAlbumController temp = loader.getController();
		Photos.root.setCenter(pane);
		try {
			temp.start();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	public void forward() throws IOException {
		Picture.curr = Album.curr.pictures.get(i+1);
	    start();
	}
	
	public void backward() throws IOException {
		Picture.curr = Album.curr.pictures.get(i-1);
		start();
	}
	
}
