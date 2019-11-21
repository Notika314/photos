package controller;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import app.Photos;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
public class LoginController {

//	@FXML protected NavbarController navbarController;
	
	@FXML Button logInBtn;
	@FXML Button signUpBtn;
	@FXML Label signUpLbl;
	@FXML Label logInLbl;
	@FXML TextField newUsrName;
	@FXML TextField loginUsrName;
	@FXML PasswordField newUsrPw;
	@FXML PasswordField confirmPw;
	@FXML PasswordField logInPw;
	
	protected PhotosController photosController;

	
	
	
	public void start(Stage mainStage) throws FileNotFoundException,IOException {  
//		navbarController.setLoginController(this);
		getAllUsersFromFile();	
	}
	
	
	
	private void getAllUsersFromFile() throws FileNotFoundException , IOException {
		if (!new File("users.txt").exists()) {
			return;
		}
		Scanner sc = new Scanner(new File("users.txt"));
		while (sc.hasNextLine()) {
			String str = sc.nextLine();
			String split[] = str.split("\t");
			User u;
			if (split.length==2) {
				u = new User(split[0],split[1]);
				System.out.println("Creating new user: "+u.userName+" "+ u.password);
			} 
		}
		sc.close();
	}
	
	public void login() throws IOException {
		System.out.println("in login");
		if (loginUsrName.getText().toLowerCase().equals("admin")) {
			if (logInPw.getText().equals("admin")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/navbar.fxml"));
				Photos.root.setTop(loader.load());
				NavbarController cont = loader.getController();
				cont.visHome();
				cont.visLog();
				cont.disLog();
				loader = new FXMLLoader(); 
				loader.setLocation(getClass().getResource("/view/admin.fxml"));
				Pane pane = loader.load();
//				Pane pane = FXMLLoader.load(getClass().getResource("/view/admin.fxml"));
				AdminController ac = loader.getController();
				Photos.root.setCenter(pane);
				ac.start();
				return;
			}
			else {
				logInLbl.setStyle("-fx-text-fill: red;");
				logInLbl.setText("Incorrect username or password");
				return;
			}
		}
		User user = User.findUser(loginUsrName.getText(),logInPw.getText());
		if (user==null) {
			logInLbl.setStyle("-fx-text-fill: red;");
			logInLbl.setText("Incorrect username or password");
		} else {
			logInLbl.setStyle("-fx-text-fill: green;");
			logInLbl.setText("Successful login");
			System.out.println("successful login");
			User.curr = user;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/navbar.fxml"));
			Photos.root.setTop(loader.load());
			NavbarController cont = loader.getController();
			cont.visHome();
			cont.visLog();
			cont.disLog();
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/album.fxml"));
			Pane pane = loader.load();
			AlbumController temp = loader.getController();
			Photos.root.setCenter(pane);
			temp.start();
		}
	}

	private void errorUpdate(String str) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(str);
		alert.showAndWait();
	}
	
	public void signUp() throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/view/signup.fxml"));
		Photos.root.setCenter(pane);
	}

}