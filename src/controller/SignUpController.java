package controller;

import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import app.Photos;
import javafx.event.ActionEvent;
import model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.PasswordField;
import javafx.stage.StageStyle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Stage;
import java.io.*;
/**
 * Controller for Signup page, handles user creating the account
 * @author Christopher Taglieri cat197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class SignUpController {

//	@FXML protected NavbarController navbarController;
	/**
	 * triggers loggin in
	 */
	@FXML Button logInBtn;
	/**
	 * triggers creating new account
	 */
	@FXML Button signUpBtn;
	/**
	 * displays information about success/failure of signing up
	 */
	@FXML Label signUpLbl;
	/**
	 * displays information about success/failure of loggin in
	 */
	@FXML Label logInLbl;
	@FXML TextField newUsrName;
	@FXML TextField loginUsrName;
	@FXML PasswordField newUsrPw;
	@FXML PasswordField confirmPw;
	@FXML PasswordField logInPw;
	
	@FXML protected PhotosController photosController;

	
	
	
	/**
	 * start of controller
	 * @param mainStage main
	 * @throws FileNotFoundException error
	 * @throws IOException error
	 */
	public void start(Stage mainStage) throws FileNotFoundException,IOException {  
//		navbarController.setLoginController(this);
		getAllUsersFromFile();	
	}
	
	/**
	 * gets users from file
	 * @throws FileNotFoundException error
	 * @throws IOException error
	 */
	private void getAllUsersFromFile() throws FileNotFoundException,IOException {
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
	
	/**
	 * Signs user up
	 * @throws IOException error
	 */
	public void signup() throws IOException {
		String name = newUsrName.getText();
		String password = newUsrPw.getText();
		String passwordConfirm = confirmPw.getText();
		if (!password.equals(passwordConfirm)) {
			signUpLbl.setStyle("-fx-text-fill: red;");
			signUpLbl.setText("Passwords don't match");
		} else {
			if (User.usernameExists(name)) {
				signUpLbl.setStyle("-fx-text-fill: red;");
				signUpLbl.setText("Choose different username");
			} else {
				User u = new User(name, password);
				signUpLbl.setStyle("-fx-text-fill: green;");
				signUpLbl.setText("Created account successfully, "+name);
				returnLogin();
			}
		}
	}
	
	/**
	 * takes us back to login
	 * @throws IOException error
	 */
	public void returnLogin() throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		System.out.println(pane);
		Photos.root.setCenter(pane);

	}
	
	/**
	 * Signs user up
	 * @param e event
	 */
	public void signupPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		System.out.println("b is "+b);
	}
	

	
}