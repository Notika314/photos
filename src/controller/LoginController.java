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
<<<<<<< HEAD
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
=======
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
>>>>>>> f79d15a3da0ccca4df103a05465bb287250ec75f
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
<<<<<<< HEAD
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/main.fxml"));
						Parent root = loader.load(); 
						MainController mainController = loader.getController();
						mainController.start(primaryStage);
						Scene scene = new Scene(root, 500, 450);
				        
						primaryStage.initStyle(StageStyle.UNDECORATED);
				        primaryStage.setScene(scene);
				        root.requestFocus();
						primaryStage.show(); 
=======
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/main.fxml"));
			Parent root = loader.load(); 
			MainController mainController = loader.getController();
			mainController.start(primaryStage);
			Scene scene = new Scene(root, 500, 450);
			// Enables us to drag the program window around.
			
	        
	        // Removes the default file bar.
			primaryStage.initStyle(StageStyle.UNDECORATED);
	        primaryStage.setScene(scene);
	        root.requestFocus();
			primaryStage.show(); 
>>>>>>> f79d15a3da0ccca4df103a05465bb287250ec75f
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
				signUpLbl.setText("Welcome, "+name);
			}
		}
	}
	
	
	public void signupPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		System.out.println("b is "+b);
	}
	
}