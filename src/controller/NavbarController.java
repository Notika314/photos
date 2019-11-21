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
/**
 * Controller for Navigation bar, with buttons common between all pages 
 * @author Christopher Taglieri cat197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class NavbarController {
	/**
	 * closes the application
	 */
	@FXML Button close;
	/**
	 * returns to main page
	 */
	@FXML Button homeBtn;
	/**
	 * logs out
	 */
	@FXML Button logOutBtn;
	/**
	 * photos Controller
	 */
	@FXML protected PhotosController photosController;


	/**
	 * exits the application, saves all the changes
	 * @throws IOException throws IOException
	 */
	public void closeButton() throws IOException {
		/*
		System.out.println("All users are: ");
		for (int i=0;i<User.users.size();i++) {
			System.out.println(User.users.get(i));
		}
		*/
		for (int i=0;i<User.users.size();i++) {
			User.writeUser(User.users.get(i));
		}
	    Stage stage = (Stage) close.getScene().getWindow();
	    stage.close();
	}
	/**
	 * changes visibility of home button
	 */
	public void visHome() {
		if (homeBtn.isVisible()) {
			homeBtn.setVisible(false);
		}
		else {
			homeBtn.setVisible(true);
		}
	}
	/**
	 * changes the status of logOut button
	 */
	public void visLog() {
		if (logOutBtn.isVisible()) {
			logOutBtn.setVisible(false);
		}
		else {
			logOutBtn.setVisible(true);
		}
	}
	/**
	 * toggles homeButton between setDisable false and true
	 */
	public void disHome() {
		if (homeBtn.isDisabled()) {
			homeBtn.setDisable(false);
		}
		else {
			homeBtn.setDisable(true);
		}
	}
	/**
	 * toggles logout button between disable false and true
	 */
	public void disLog() {
		if (logOutBtn.isDisabled()) {
			logOutBtn.setDisable(false);
		}
		else {
			logOutBtn.setDisable(true);
		}
	}
	/**
	 * triggers logging out
	 * @throws IOException throws IOException
	 */
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
	/**
	 * returns to home page
	 * @throws IOException throws IOException
	 */
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
	/**
	 * set photos Controller
	 * @param photosController controller to be set
	 */
	public void setPhotosController(PhotosController photosController) {
		this.photosController = photosController;
	}
}