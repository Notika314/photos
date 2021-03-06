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
import model.Search;

/**
 * Controller for SlideSearch page, where user view slideshow when in search ,
 * list or delete accounts
 * @author Christopher Taglieri cat197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class SlideSearchController {

	@FXML
	ImageView image;
	@FXML Button returnBtn;
	@FXML Button forwardBtn;
	@FXML Button backwardBtn;
	
	/**
	 * Tracker for how far along we are.
	 */
	private int i;
	
	/**
	 * Start of the SlideShow controller.
	 * @throws IOException
	 */
	public void start() throws IOException {
	    image.setImage(new Image(new FileInputStream(Picture.curr.file)));
	    for (i = 0; i < Search.searchResult.size(); i++) {
	    	if (Search.searchResult.get(i).equals(Picture.curr)) {
	    		break;
	    	}
	    }
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
	
	/**
	 * Goes back to the previous window.
	 */
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
		loader.setLocation(getClass().getResource("/view/search.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
		}
		SearchController temp = loader.getController();
		Photos.root.setCenter(pane);
		try {
			temp.start();
		} catch (FileNotFoundException e) {
		} 
	}
	
	/**
	 * Advances forward one degree.
	 * @throws IOException
	 */
	public void forward() throws IOException {
		Picture.curr = Search.searchResult.get(i+1);
	    start();
	}
	
	/**
	 * Goes backwards one degree.
	 * @throws IOException
	 */
	public void backward() throws IOException {
		Picture.curr = Search.searchResult.get(i-1);
		start();
	}
	
}
