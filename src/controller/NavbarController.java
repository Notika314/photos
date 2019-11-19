package controller;

import java.io.*;

import app.Photos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
//import controller.LoginController;

public class NavbarController {
	
	@FXML Button close;
	@FXML Button homeBtn;
	@FXML Button logOutBtn;
	
	@FXML protected PhotosController photosController;


	
	public void closeButton() throws IOException {
		
		for (int i=0;i<User.users.size();i++) {
			User.writeUser(User.users.get(i));
		}
	    Stage stage = (Stage) close.getScene().getWindow();
	    stage.close();
	}
	
	public void visHome() {
		if (homeBtn.isVisible()) {
			homeBtn.setVisible(false);
		}
		else {
			homeBtn.setVisible(true);
		}
	}
	
	public void visLog() {
		if (logOutBtn.isVisible()) {
			logOutBtn.setVisible(false);
		}
		else {
			logOutBtn.setVisible(true);
		}
	}
	
	public void disHome() {
		if (homeBtn.isDisabled()) {
			homeBtn.setDisable(false);
		}
		else {
			homeBtn.setDisable(true);
		}
	}
	
	public void disLog() {
		if (logOutBtn.isDisabled()) {
			logOutBtn.setDisable(false);
		}
		else {
			logOutBtn.setDisable(true);
		}
	}
	
	public void logOut() throws IOException {
		homeBtn.setDisable(true);
		logOutBtn.setDisable(true);
		visHome();
		visLog();
		for (int i=0;i<User.users.size();i++) {
			User.writeUser(User.users.get(i));
		}
		Pane pane = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		Photos.root.setCenter(pane);
	}
	
	public void home() throws IOException {
		homeBtn.setDisable(true);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/album.fxml"));
		Pane pane = loader.load();
		AlbumController temp = loader.getController();
		//Pane pane = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
		Photos.root.setCenter(pane);
		temp.start();
	}
	
	public void setPhotosController(PhotosController photosController) {
		this.photosController = photosController;
	}
}