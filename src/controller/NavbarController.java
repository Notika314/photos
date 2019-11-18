package controller;

import java.io.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
//import controller.LoginController;

public class NavbarController {
	
	@FXML Button close;
	
	protected PhotosController photosController;


	
	public void closeButton() throws IOException {
		
		for (int i=0;i<User.users.size();i++) {
			User.writeUser(User.users.get(i));
		}
	    Stage stage = (Stage) close.getScene().getWindow();
	    stage.close();
	}
	
	public void setPhotosController(PhotosController photosController) {
		this.photosController = photosController;
	}
}