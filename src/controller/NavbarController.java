package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

//import controller.LoginController;

public class NavbarController {
	
	@FXML Button close;
	
	protected PhotosController photosController;


	
	public void closeButton() {
	    Stage stage = (Stage) close.getScene().getWindow();
	    stage.close();
	}
	
	public void setPhotosController(PhotosController photosController) {
		this.photosController = photosController;
	}
}