package controller;
import javafx.scene.Parent;
import controller.MainController;
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
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	
	public void login(ActionEvent e) throws IOException {
		System.out.println("in login");
		if (loginUsrName.getText().equals("admin")) {
			if (logInPw.getText().equals("password")) {
				Pane pane = FXMLLoader.load(getClass().getResource("/view/admin.fxml"));
				Photos.root.setCenter(pane);
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
			Pane pane = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
			Photos.root.setCenter(pane);
			/*
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/main.fxml"));
			Parent root = loader.load(); 
			MainController mainController = loader.getController();
			mainController.start(primaryStage);
			Scene scene = new Scene(root, 700, 550);
			primaryStage.initStyle(StageStyle.UNDECORATED);
	        primaryStage.setScene(scene);
	        root.requestFocus();
			primaryStage.show();
			*/ 
		}
	}
		
	public void signupPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		System.out.println("b is "+b);
	}
	
	public void signUp() throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/view/signup.fxml"));
		Photos.root.setCenter(pane);
	}

}