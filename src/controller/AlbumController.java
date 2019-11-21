package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.User;
/**
 * Controller for Album page, where all albums are listed, can be created,
 * renamed, deleted, and searched by date range ,or tags
 * @author Christopher Taglieri cat197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class AlbumController {
	/**
	 * Lists all user's albums
	 */
	@FXML
	ListView<Album> listView;
	/**
	 * field for entering new album's name
	 */
	@FXML
	TextField createField;
	/**
	 * field for changing album's name
	 */
	@FXML
	TextField renameField;
	
	private ObservableList<Album> obsList;  

	
	public void start() throws FileNotFoundException,IOException {  
		obsList = FXCollections.observableList(User.curr.userAlbums); 
		listView.setItems(obsList); 
		System.out.println(obsList);
		
		for (int i = 0; i < obsList.size(); i++) {
			System.out.println(obsList.get(i));
		}

		//may need to put this else where
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/navbar.fxml"));
						try {
							Photos.root.setTop(loader.load());
						} catch (IOException e1) {
						}
						NavbarController cont = loader.getController();
						cont.visHome();
						cont.visLog();
						cont.disHome();
						cont.disLog();
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/inAlbum.fxml"));
						Pane pane = null;
						try {
							pane = loader.load();
						} catch (IOException e) {
						}
						InAlbumController temp = loader.getController();
						// Pane pane = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
						Album.curr = listView.getSelectionModel().getSelectedItem();
						Photos.root.setCenter(pane);
						try {
							temp.start();
						} catch (FileNotFoundException e) {
						} catch (IOException e) {
						}
					}
				}
			}
		});
		if (obsList.size() != 0) {
			listView.getSelectionModel().select(0);
			listView.getFocusModel().focus(0);
			details();
			renameField.setText(listView.getSelectionModel().getSelectedItem().albumName);
		}
		listView.getSelectionModel().selectedIndexProperty().
    	addListener(obs -> details()); 
		listView.getSelectionModel().selectedIndexProperty().
    	addListener(obs -> 
    	{	if (listView.getSelectionModel().getSelectedItem() != null) 
    			{renameField.setText(listView.getSelectionModel().getSelectedItem().albumName);}}); 
    }
	
	public void details() {
		//We have to set the various album traits here.
	}
	
	public void create() {
		if (createField.getText() == null || createField.getText() == "") {
			System.out.println("Flag Some Error");
			return;
		}
		if (User.curr.albumExists(createField.getText())) {
			System.out.println("Flag Duplicate");
			return;
		}
		Album temp = new Album(createField.getText(), User.curr);
		int i = Collections.binarySearch(obsList, temp);
		i = ~i;
		obsList.add(i, temp);
		listView.getSelectionModel().select(i);
		clearFields((AnchorPane)createField.getParent());
	}
	
	public void rename() {
		if (renameField.getText() == null || renameField.getText() == "") {
			System.out.println("Flag Some Error");
			return;
		}
		if (User.curr.albumExists(renameField.getText())) {
			System.out.println("Flag Duplicate");
			return;
		}
		Album temp = new Album(renameField.getText(), User.curr);
		Album del = listView.getSelectionModel().getSelectedItem();
		listView.getSelectionModel().clearSelection();
		User.curr.deleteAlbum(del);

		int i = Collections.binarySearch(obsList, temp);
		i = ~i;
		if (i == 0) {
			listView.getSelectionModel().select(i);
			obsList.add(i, temp);
			listView.getSelectionModel().select(i);
		}
		else {
			obsList.add(i, temp);
			listView.getSelectionModel().select(i);
		}
	}
	
	public void delete() {
		if (obsList.size() == 0) {
			System.out.println("The list is empty");
			return;
		}
		else {
			int i = listView.getSelectionModel().getSelectedIndex();
			obsList.remove(i);
			if (obsList.size() != 0) {
				listView.getSelectionModel().select(i);
			}
			else {
				listView.getSelectionModel().clearSelection();
				//clearFields((AnchorPane)detailsName.getParent());
				clearFields((AnchorPane)renameField.getParent());
			}
		}
	}
	
	public void searchTime() {
		
	}
	
	public void searchTag() {
		
	}
	
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
}
