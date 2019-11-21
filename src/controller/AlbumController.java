package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
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
	/**
	 * Field for start date
	 */
	@FXML
	DatePicker startField;
	/**
	 * field for end date
	 */
	@FXML
	DatePicker endField;
	/**
	 * field for type one
	 */
	@FXML
	TextField typeField1;
	/**
	 * field for type two
	 */
	@FXML
	TextField typeField2;
	/**
	 * field for value A
	 */
	@FXML
	TextField valueFieldA;
	/**
	 * field for value B
	 */
	@FXML
	TextField valueFieldB;
	/**
	 * toggles 'and'
	 */
	@FXML
	ToggleButton andBtn;
	/**
	 * toggles 'or'
	 */
	@FXML
	ToggleButton orBtn;
	/**
	 * triggers search by tags
	 */
	@FXML
	Button searchTagBtn;
	/**
	 * triggers search by times
	 */
	@FXML
	Button searchTimesBtn;
	/**
	 * List of tag types A used for search
	 */
	@FXML
	ListView<String> typeListA;
	/**
	 * List of tag types B
	 */
	@FXML
	ListView<String> typeListB;
	@FXML
	Text numPhotos;
	@FXML
	Text earlyDate;
	@FXML
	Text lateDate;

	/**
	 * value used for conjuction search
	 */
	private boolean and;
	/**
	 * value used for disjunction search
	 */
	private boolean or;
	/**
	 * Observable list containing to all tags of typeA type
	 */
	private ObservableList<String> obsTypeA;  
	/**
	 * Observable list containing all tags of type B
	 */
	private ObservableList<String> obsTypeB;  
	/**
	 * Observble list of albums
	 */
	private ObservableList<Album> obsList;  

	/**
	 * starts the controller,sets all the events listeners
	 * @throws FileNotFoundException throws exception if file not found
	 * @throws IOException throws IOException
	 */
	public void start() throws FileNotFoundException,IOException {  
		obsList = FXCollections.observableList(User.curr.userAlbums); 
		obsTypeA = FXCollections.observableList(User.curr.tagTypes);
		obsTypeB = FXCollections.observableList(User.curr.tagTypes);

		listView.setItems(obsList); 
		typeListA.setItems(obsTypeA);
		typeListB.setItems(obsTypeB);


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
	
	private void details() {
		if (obsList.size() == 0) {
			return;
		}
		Album item = listView.getSelectionModel().getSelectedItem();
		numPhotos.setText(item.pictures.size()+"");
		earlyDate.setText(item.getEarliest().date+"");
		lateDate.setText(item.getLatest().date+"");
	}
	/**
	 * Triggers creation of the new album 
	 */
	public void create() {
		if (createField.getText() == null || createField.getText().equals("")) {
			errorUpdate("Please Input a Name");
			return;
		}
		if (User.curr.albumExists(createField.getText())) {
			errorUpdate("Duplicate Album Exists!");
			return;
		}
		Album temp = new Album(createField.getText(), User.curr);
		int i = Collections.binarySearch(obsList, temp);
		i = ~i;
		obsList.add(i, temp);
		listView.getSelectionModel().select(i);
		clearFields((AnchorPane)createField.getParent());
	}
	/**
	 * Triggers changing the selected album's name
	 */
	public void rename() {
		if (renameField.getText() == null || renameField.getText().equals("")) {
			errorUpdate("Please Input a Name");
			return;
		}
		if (User.curr.albumExists(renameField.getText())) {
			errorUpdate("Duplicate Album Exists!");
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
	/**
	 * Triggers deletion of the selected album
	 */
	public void delete() {
		if (obsList.size() == 0) {
			errorUpdate("The list is empty");
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
	/**
	 * Searches pictures by time among all user's pictures
	 * @throws FileNotFoundException throws exception if file not found
	 * @throws IOException throws IOException
	 */
	public void searchTimeAll() throws FileNotFoundException, IOException {
		if (obsList.size() == 0) {
			errorUpdate("The list is empty");
			return;
		}
		/*
		if ((startField.getText() == null || startField.getText() == "" || startField.getText().trim().length() == 0)||
				(endField.getText() == null || endField.getText() == "" || endField.getText().trim().length() == 0)) {
			System.out.println("Input valid start/end date");
			return;
		} */
		if (startField.getValue() == null || endField.getValue() == null) {
			errorUpdate("Input valid start/end date");
			return;
		}
		if (startField.getValue().compareTo(endField.getValue()) > 0) {
			errorUpdate("Start must come before end");
			return;
		}
		Search.searchByDateRangeInAll(Date.valueOf(startField.getValue()), Date.valueOf(endField.getValue()));
		swapToSearch();
	}
	/**
	 * Searches pictures by time in the album
	 * @throws FileNotFoundException throws exception if file not found
	 * @throws IOException throws IOException
	 */
	public void searchTimeAlbum() throws FileNotFoundException, IOException {
		if (obsList.size() == 0) {
			errorUpdate("The list is empty");
			return;
		}
		/*
		if ((startField.getText() == null || startField.getText() == "" || startField.getText().trim().length() == 0)||
				(endField.getText() == null || endField.getText() == "" || endField.getText().trim().length() == 0)) {
			System.out.println("Input valid start/end date");
			return;
		} */
		if (startField.getValue() == null || endField.getValue() == null) {
			errorUpdate("Input valid start/end date");
			return;
		}
		if (startField.getValue().compareTo(endField.getValue()) > 0) {
			errorUpdate("Start must come before end");
			return;
		}
		Search.searchByDateRangeInAlbum(listView.getSelectionModel().getSelectedItem(),Date.valueOf(startField.getValue()), Date.valueOf(endField.getValue()));
		swapToSearch();
	}
	/**
	 * Searches pictures by tags in all user's photos
	 * @throws FileNotFoundException throws exception if file not found
	 * @throws IOException throws IOException
	 */
	public void searchTagAll() throws FileNotFoundException, IOException {
		if (empty(valueFieldA.getText())) {
			errorUpdate("Input First Tag");
			return;
		}
		if (!or && !and && !empty(valueFieldB.getText())) {
			errorUpdate("Select either AND OR");
			return;
		}
		if ((or || and) && empty(valueFieldB.getText())){
			errorUpdate("Input Second Tag");
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
	/**
	 * Searches pictures In album by tag
	 * @throws FileNotFoundException throws exception if file not found
	 * @throws IOException throws IOEXception
	 */
	public void searchTagAlbum() throws FileNotFoundException, IOException {
		if (empty(valueFieldA.getText())) {
			errorUpdate("Input First Tag");
			return;
		}
		if (!or && !and && !empty(valueFieldB.getText())) {
			errorUpdate("Select either AND OR");
			return;
		}
		if ((or || and) && empty(valueFieldB.getText())){
			errorUpdate("Input Second Tag");
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
	/**
	 * selects or button, disables and button
	 */
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
	/**
	 * selects and button, disables or button
	 */
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
	/**
	 * helper method, swaps to earch
	 * @throws FileNotFoundException throws exception when file not found
	 * @throws IOException throws IOException
	 */
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
	/**
	 * helper method 
	 * @param s determines if field is empty
	 * @return true if field is empty
	 */
	private boolean empty(String s) {
		if (s == null || s.equals("") || s.trim().length() == 0) {
			return true;
		}
		return false;
	}
	/**
	 * helper method, clears fields 
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
