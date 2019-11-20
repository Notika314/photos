package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.nio.file.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.User;

public class AdminController {
	@FXML ListView<User> allUsers;
	@FXML Button CreateAccount;
	@FXML Label NewUserLbl;
	@FXML TextField newUsrName;
	@FXML PasswordField newUsrPw;
	@FXML PasswordField confirmPw;
	@FXML Button deleteUser;
	
	private ObservableList<User> obsList; 
	public void start() throws FileNotFoundException,IOException { 
		System.out.println("In admin page");
		obsList = FXCollections.observableArrayList(User.users); 
	
		allUsers.setItems(obsList); 
		
		if (obsList.size() != 0) {
			allUsers.getSelectionModel().select(0);
			allUsers.getFocusModel().focus(0);
		}
	}

	public void create() throws IOException {
		System.out.println("Creating new user");
		if (newUsrName.getText() == null || newUsrName.getText() == "" || 
				newUsrPw.getText() == null || newUsrPw.getText() == "" ||
				confirmPw.getText() == null || confirmPw.getText() == "") {
			NewUserLbl.setText("Enter all the fields");
			return;
		}
		String name = newUsrName.getText();
		String password = newUsrPw.getText();
		String passwordConfirm = confirmPw.getText();
		if (!password.equals(passwordConfirm)) {
			NewUserLbl.setStyle("-fx-text-fill: red;");
			NewUserLbl.setText("Passwords don't match");
		} else {
			if (User.usernameExists(name)) {
				NewUserLbl.setStyle("-fx-text-fill: red;");
				NewUserLbl.setText("Choose different username");
			} else {
				User u = new User(name, password);
//				User.users.add(u);
				int i = Collections.binarySearch(obsList, u);
				i = ~i;
				obsList.add(i, u);
				allUsers.getSelectionModel().select(i);
				NewUserLbl.setStyle("-fx-text-fill: green;");
				NewUserLbl.setText("Created account successfully, "+name);
			}
		}
	}
	
	public void deleteUser() throws IOException {
//		System.out.prinltn
		if (obsList.size() == 0) {
			System.out.println("The list is empty");
			return;
		}
		else {
			int i = allUsers.getSelectionModel().getSelectedIndex();
			String name = obsList.get(i).userName;
			obsList.remove(i);
//			User.users.remove(i);
			Files.deleteIfExists(Paths.get("data/users/"+name+".ser")); 
			if (obsList.size() != 0) {
				allUsers.getSelectionModel().select(i);
			}
			else {
				allUsers.getSelectionModel().clearSelection();
			}
		}
	}
	
}
