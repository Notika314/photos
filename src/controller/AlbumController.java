package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.stream.Collectors;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Search;
import model.User;

public class AlbumController {
	
	@FXML
	ListView<Album> listView;
	
	@FXML
	TextField createField;
	@FXML
	TextField renameField;
	
	@FXML
	DatePicker startField;
	
	@FXML
	DatePicker endField;
	
	@FXML
	TextField typeField1;
	
	@FXML
	TextField typeField2;
	
	@FXML
	TextField valueFieldA;
	
	@FXML
	TextField valueFieldB;
	
	@FXML
	ToggleButton andBtn;
	
	@FXML
	ToggleButton orBtn;
	
	@FXML
	Button searchTagBtn;
	
	@FXML
	Button searchTimesBtn;
	
	@FXML
	ListView<String> typeListA;
	
	@FXML
	ListView<String> typeListB;

	private boolean and;
	private boolean or;
	private ObservableList<String> obsTypeA;  
	private ObservableList<String> obsTypeB;  
	private ObservableList<Album> obsList;  


	public void start() throws FileNotFoundException,IOException {  
		obsList = FXCollections.observableList(User.curr.userAlbums); 
		obsTypeA = FXCollections.observableList(User.curr.tagTypes);
		obsTypeB = FXCollections.observableList(User.curr.tagTypes);

		listView.setItems(obsList); 
		typeListA.setItems(obsTypeA);
		typeListB.setItems(obsTypeB);

		
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
		if (createField.getText() == null || createField.getText().equals("")) {
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
		if (renameField.getText() == null || renameField.getText().equals("")) {
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
	
	public void searchTimeAll() throws FileNotFoundException, IOException {
		if (obsList.size() == 0) {
			System.out.println("The list is empty");
			return;
		}
		/*
		if ((startField.getText() == null || startField.getText() == "" || startField.getText().trim().length() == 0)||
				(endField.getText() == null || endField.getText() == "" || endField.getText().trim().length() == 0)) {
			System.out.println("Input valid start/end date");
			return;
		} */
		if (startField.getValue() == null || endField.getValue() == null) {
			System.out.println("Input valid start/end date");
			return;
		}
		if (startField.getValue().compareTo(endField.getValue()) > 0) {
			System.out.println("Start must come before end");
			return;
		}
		Search.searchByDateRangeInAll(Date.valueOf(startField.getValue()), Date.valueOf(endField.getValue()));
		swapToSearch();
	}
	public void searchTimeAlbum() throws FileNotFoundException, IOException {
		if (obsList.size() == 0) {
			System.out.println("The list is empty");
			return;
		}
		/*
		if ((startField.getText() == null || startField.getText() == "" || startField.getText().trim().length() == 0)||
				(endField.getText() == null || endField.getText() == "" || endField.getText().trim().length() == 0)) {
			System.out.println("Input valid start/end date");
			return;
		} */
		if (startField.getValue() == null || endField.getValue() == null) {
			System.out.println("Input valid start/end date");
			return;
		}
		if (startField.getValue().compareTo(endField.getValue()) > 0) {
			System.out.println("Start must come before end");
			return;
		}
		Search.searchByDateRangeInAlbum(listView.getSelectionModel().getSelectedItem(),Date.valueOf(startField.getValue()), Date.valueOf(endField.getValue()));
		swapToSearch();
	}
	
	public void searchTagAll() throws FileNotFoundException, IOException {
		if (empty(valueFieldA.getText())) {
			System.out.println("Input First Tag");
			return;
		}
		if (!or && !and && !empty(valueFieldB.getText())) {
			System.out.println("Select either AND OR");
			return;
		}
		if ((or || and) && empty(valueFieldB.getText())){
			System.out.println("Input Second Tag");
			return;
		}
		if (empty(valueFieldB.getText())) {
			Search.searchByTagInAll(typeListA.getSelectionModel().getSelectedItem(),valueFieldA.getText());
		}
		else {
			if (and) {
				Search.searchByTagsConjunctionInAll(typeListA.getSelectionModel().getSelectedItem(), valueFieldA.getText(),
						typeListB.getSelectionModel().getSelectedItem(), valueFieldB.getText());
			}
			else {
				Search.searchByTagsDisjuctionInAll(typeListA.getSelectionModel().getSelectedItem(), valueFieldA.getText(),
						typeListB.getSelectionModel().getSelectedItem(), valueFieldB.getText());
			}
		}
		swapToSearch();
	}
	
	public void searchTagAlbum() throws FileNotFoundException, IOException {
		if (empty(valueFieldA.getText())) {
			System.out.println("Input First Tag");
			return;
		}
		if (!or && !and && !empty(valueFieldB.getText())) {
			System.out.println("Select either AND OR");
			return;
		}
		if ((or || and) && empty(valueFieldB.getText())){
			System.out.println("Input Second Tag");
			return;
		}
		if (empty(valueFieldB.getText())) {
			Search.searchByTagInAll(typeListA.getSelectionModel().getSelectedItem(),valueFieldA.getText());
		}
		else {
			if (and) {
				Search.searchByTagsConjunctionInAll(typeListA.getSelectionModel().getSelectedItem(), valueFieldA.getText(),
						typeListB.getSelectionModel().getSelectedItem(), valueFieldB.getText());
			}
			else {
				Search.searchByTagsDisjuctionInAll(typeListA.getSelectionModel().getSelectedItem(), valueFieldA.getText(),
						typeListB.getSelectionModel().getSelectedItem(), valueFieldB.getText());
			}
		}
		swapToSearch();
	}
	
	public void or() {
		andBtn.setSelected(false);
		if (or) {
			or = false;
		}
		else {
			or = true;
		}
		and = false;
	}
	
	public void and() {
		orBtn.setSelected(false);
		if (and) {
			and = false;
		}
		else {
			and = true;
		}
		or = false;
	}
	
	private void swapToSearch() throws FileNotFoundException, IOException {
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
		loader.setLocation(getClass().getResource("/view/search.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
		}
		SearchController temp = loader.getController();
		// Pane pane = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
		Album.curr = listView.getSelectionModel().getSelectedItem();
		Photos.root.setCenter(pane);
		temp.start();
	}
	
	private boolean empty(String s) {
		if (s == null || s.equals("") || s.trim().length() == 0) {
			return true;
		}
		return false;
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
