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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.User;

public class AlbumController {
	
	@FXML
	ListView<Album> listView;
	
	@FXML
	TextField createField;
	
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

		listView.getSelectionModel().selectedIndexProperty().
    	addListener(obs -> details()); 
    }
	
	public void details() {
		
	}
	
	public void create() {
		if (createField.getText() == null || createField.getText() == "") {
			System.out.println("Flag Some Error");
		}
		if (User.curr.albumExists(createField.getText())) {
			System.out.println("Flag Duplicate");
		}
		Album temp = new Album(createField.getText(), User.curr);
		int i = Collections.binarySearch(obsList, temp);
		i = ~i;
		obsList.add(i, temp);
		listView.getSelectionModel().select(i);

	}
	
	public void rename() {
		
	}
	
	public void delete() {
		
	}
}
