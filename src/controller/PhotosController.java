package controller;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
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
/**
 * Main controller
 * @author Christopher Taglieri cat197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class PhotosController {
	/**
	 * button for loggin in
	 */
	@FXML Button logInBtn;
	/**
	 * button for signing up
	 */
	@FXML Button signUpBtn;
	/**
	 * label for status of signing up
	 */
	@FXML Label signUpLbl;
	/**
	 * label for status is logging in
	 */
	@FXML Label logInLbl;
	/**
	 * field for user to enter new user's name
	 */
	@FXML TextField newUsrName;
	/**
	 * field for user to enter returning user's name
	 */
	@FXML TextField loginUsrName;
	/**
	 * field to enter new user's password
	 */
	@FXML PasswordField newUsrPw;
	/**
	 * field to enter confirmation password
	 */
	@FXML PasswordField confirmPw;
	/**
	 * field to enter password when loggin in
	 */
	@FXML PasswordField loginPw;	
	/**
	 * navbar Controller
	 */
	@FXML protected NavbarController navbarController;
	/**
	 * loginController
	 */
	@FXML protected LoginController loginController;
	/**
	 * signup Controller
	 */
	@FXML protected SignUpController signupController;

	/**
	 * starting controller
	 * @param mainStage mainStage
	 * @throws FileNotFoundException thrown when file not found
	 * @throws IOException throws IOException
	 * @throws ClassNotFoundException throws in case of serialization issues
	 */
	public void start(Stage mainStage) throws FileNotFoundException, IOException , ClassNotFoundException{ 
		getAllUsers();	
		navbarController.setPhotosController(this);
	}
	/**
	 * reads all users from files
	 * @throws FileNotFoundException thrown when file not found
	 * @throws IOException throws IOException
	 * @throws ClassNotFoundException throws in case of serialization issues
	 */
	public void getAllUsers() throws FileNotFoundException, IOException,  ClassNotFoundException {
		File dir = new File("data/users/");
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("ser");
			}
		});
		for (int i=0;i<files.length;i++) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(files[i]));
			User user = (User)ois.readObject();
			System.out.println("Reading user "+ user.userName +", with password "+user.password +" from a file .");
			ois.close();
			int j = Collections.binarySearch(User.users, user);
			j = ~j;
			User.users.add(j,user);
		}
		System.out.println("All current users are: :");
		for (int i=0;i<User.users.size();i++) {
			System.out.println(User.users.get(i).userName+", "+ User.users.get(i).password);
		}
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
