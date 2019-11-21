package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
//import java.nio.file.*;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.User;
/**
 * Controller for Admin page, where admin user can create ,
 * list or delete accounts
 * @author Christopher Taglieri cat197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class AdminController {
	/**
	 * lists all users that were created so far
	 */
	@FXML ListView<User> allUsers;
	/**
	 * button that triggers the method that creates new user
	 */
	@FXML Button CreateAccount;
	/**
	 * Shows updates about success/failure of user deletion
	 */
	@FXML Label DeleteUserLbl;
	/**
	 * Shows updates about success/failure of account creation
	 */
	@FXML Label NewUserLbl;
	/**
	 * textfield where user can enter new user name while creating account
	 */
	@FXML TextField newUsrName;
	/**
	 * textfield where user can enter new user's password
	 */
	@FXML PasswordField newUsrPw;
	/**
	 * textfeild for confirming the password
	 */
	@FXML PasswordField confirmPw;
	/**
	 * button that triggers account's deletion
	 */
	@FXML Button deleteUser;
	/**
	 * Observable list that displays all existing users
	 */
	private ObservableList<User> obsList; 
	/**
	 * Starts the page, displays all users  
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void start() throws FileNotFoundException,IOException { 
		obsList = FXCollections.observableArrayList(User.users); 
	
		allUsers.setItems(obsList); 
		
		if (obsList.size() != 0) {
			allUsers.getSelectionModel().select(0);
			allUsers.getFocusModel().focus(0);
		}
	}
	/**
	 * Creates new user if all the fields are filled correctly
	 * @throws IOException
	 */
	public void create() throws IOException {
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
				DeleteUserLbl.setText("");
			} else if (name.contentEquals("")|| password.contentEquals("")) {
				NewUserLbl.setStyle("-fx-text-fill: red;");
				NewUserLbl.setText("Username or password can't be empty");
				DeleteUserLbl.setText("");
			}	else {
				User u = new User(name, password);
//				User.users.add(u);
				int i = Collections.binarySearch(obsList, u);
				i = ~i;
				obsList.add(i, u);
				allUsers.getSelectionModel().select(i);
				NewUserLbl.setStyle("-fx-text-fill: green;");
				DeleteUserLbl.setText("");
				NewUserLbl.setText("Created account successfully, "+name);
				clearFields((AnchorPane)newUsrName.getParent());
			}
		}
	}
	/**
	 * deletes selected user
	 * @throws IOException
	 */
	public void deleteUser() throws IOException {
		if (obsList.size() == 0) {
			return;
		}
		else {
			int i = allUsers.getSelectionModel().getSelectedIndex();
			String name = obsList.get(i).userName;
			User.removeUser(name);
			obsList.remove(i);
//			Files.deleteIfExists(Paths.get("data/users/"+name+".ser")); 
			File file = new File("data/users/"+name+".ser");
    		if(file.delete()){
    			DeleteUserLbl.setStyle("-fx-text-fill: green;");
				DeleteUserLbl.setText("Account was deleted successfully");
				NewUserLbl.setText("");
    		}else{
				DeleteUserLbl.setText("");
				NewUserLbl.setText("");
    		}
    	   
			if (obsList.size() != 0) {
				allUsers.getSelectionModel().select(i);
			}
			else {
				allUsers.getSelectionModel().clearSelection();
			}
		}
	}
	
	/**
	 * helper method, clears fields to be ready for future input
	 * @param pane
	 */
	private void clearFields(AnchorPane pane) {
		for (Node node : pane.getChildren()) {
			if (node instanceof TextField) {
				((TextField)node).setText(null);
			}
			if (node instanceof Text) {
				((Text)node).setText(null);
			}
		}
	}
	
	private void errorUpdate(String str) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(str);
		alert.showAndWait();
	}
}
