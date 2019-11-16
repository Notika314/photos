package controller;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class PhotosController {

	@FXML Button logInBtn;
	@FXML Button signUpBtn;
	@FXML Label signUpLbl;
	@FXML Label logInLbl;
	@FXML TextField newUsrName;
	@FXML TextField loginUsrName;
	@FXML PasswordField newUsrPw;
	@FXML PasswordField confirmPw;
	@FXML PasswordField loginPw;	

	
	public void start(Stage mainStage) throws FileNotFoundException, IOException {                		
//		getAllUsers();	
	}
	
	//I'm Keeping this here for now but I feel like it is a relic.
	
//	private void getAllUsers() throws FileNotFoundException, IOException {
//		if (!new File("users.txt").exists()) {
//			return;
//		}
//		Scanner sc = new Scanner(new File("users.txt"));
//		while (sc.hasNextLine()) {
//			String str = sc.nextLine();
//			String split[] = str.split("\t");
//			if (split.length==2) {
//				User u = new User(split[0],split[1]);
////				System.out.println("Creating new user: "+u.userName+" "+ u.password);
//			} 
//		}
//		sc.close();
//	}
	
}
