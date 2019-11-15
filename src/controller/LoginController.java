package controller;
import javafx.scene.Parent;
import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Stage;
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
	
	
	
	public void start(Stage mainStage) throws FileNotFoundException {  
//		navbarController.setLoginController(this);
		getAllUsersFromFile();	
	}
	
	private void getAllUsersFromFile() throws FileNotFoundException {
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
	
	public void login(ActionEvent e) throws Exception {
		System.out.println("in login");
		User user = User.findUser(loginUsrName.getText(),logInPw.getText());
		if (user==null) {
			logInLbl.setStyle("-fx-text-fill: red;");
			logInLbl.setText("Incorrect username or password");
		} else {
			logInLbl.setStyle("-fx-text-fill: green;");
			logInLbl.setText("Successful login");
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
		}
	}
	
	public void signup(ActionEvent e) {
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
			}
		}
	}
	
	
	public void signupPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		System.out.println("b is "+b);
	}
	
}