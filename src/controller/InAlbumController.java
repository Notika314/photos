package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Picture;
import model.Tag;
import model.User;
/**
 * Controller for InAlbum page, handles the functionality
 * of creating, deleting pictures within the album, 
 * adding/editing/removing tags and captions for picture,
 * triggering slideshow
 * @author Christopher Taglieri cat197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class InAlbumController {
	/**
	 * thumbnails for each pictures
	 */
	@FXML
	TilePane tiles;
	/**
	 * triggers adding the picture
	 */
	@FXML
	Button addBtn;
	/**
	 * triggers deletion of the picture
	 */
	@FXML 
	Button deleteBtn;
	/**
	 * Field where user enter picture/s caption
	 */
	@FXML
	TextField captionField;
	/**
	 * triggers adding new tag type
	 */
	@FXML
	Button addTypeBtn;
	/**
	 * triggers adding new tag for picture
	 */
	@FXML 
	Button addTagBtn;
	/**
	 * triggers tag's deletion
	 */
	@FXML 
	Button deleteTagBtn;
	/**
	 * textfield where user enters tag type
	 */
	@FXML
	TextField typeField;
	/**
	 * textfield where user enters tag value
	 */
	@FXML
	TextField tagField;
	/**
	 * list of all tags
	 */
	@FXML
	ListView<Tag> tagList;
	/**
	 * list of all tag types
	 */
	@FXML
	ListView<String> typeList;
	/**
	 * list of albums
	 */
	@FXML
	ListView<Album> albumList;
	/**
	 * tabs
	 */
	@FXML
	TabPane tabs;
	/**
	 * shows selected image
	 */
	@FXML
	ImageView selectedImage;
	/**
	 * selected picture
	 */
	Picture selectedPicture = null;
	/**
	 * observable list of tags
	 */
	private ObservableList<Tag> obsTags; 
	/**
	 * observable list of tag types
	 */
	private ObservableList<String> obsTypes;  
	/**
	 * observable list of user's albums
	 */
	private ObservableList<Album> obsAlbums;  
	/**
	 * currently selected tab
	 */
	private int currentTab = 0;

	 /**
	  * starts the controller
	  * @throws FileNotFoundException throws exception if file not found
	  * @throws IOException throws IOException
	  */
	public void start() throws FileNotFoundException,IOException { 
		if (selectedPicture != null) {
			obsTags = FXCollections.observableList(selectedPicture.tags);
			tagList.setItems(obsTags);
		}
		else {
			obsTags = null;
			tagList.setItems(null);
		}
		obsTypes = FXCollections.observableList(User.curr.tagTypes);
		typeList.setItems(obsTypes);
		obsAlbums = FXCollections.observableArrayList(User.curr.userAlbums);
		albumList.setItems(obsAlbums);
		if (obsTags != null && obsTags.size() != 0) {
			tagList.getSelectionModel().select(0);
			tagList.getFocusModel().focus(0);
		}
		if (obsTypes.size() != 0) {
			typeList.getSelectionModel().select(0);
			typeList.getFocusModel().focus(0);
		}
		if (obsAlbums.size() != 0) {
			albumList.getSelectionModel().select(0);
			albumList.getFocusModel().focus(0);
		}
        ImageView imageView = null;
		for (Picture pic : Album.curr.pictures) {
            imageView = createImage(pic);
            tiles.getChildren().addAll(imageView);
		}
		//may need to put this else where
		
    }
	/**
	 * handles adding new picture to album
	 * @throws IOException throws IOException
	 */
	public void add() throws IOException {
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
	    File file = chooser.showOpenDialog(addBtn.getParent().getScene().getWindow());
	    if (file == null) {
	    	errorUpdate("Please Give a File");
	    	return;
	    }
	    String f = file.getCanonicalPath();
	    String ext = "";
	    int i = f.lastIndexOf('.');
	    if (i > 0) {
	    	ext = f.substring(i+1);
	    }
	    if (!ext.equals("jpg") || ext.equals("png")) {
	    	errorUpdate("Insert jpg or png file");
	    	return;
	    }
	    System.out.println(file.lastModified());
	    Picture temp = new Picture(Album.curr, file);
	    if (!Album.curr.addPicture(temp)) {
	    	errorUpdate("File already in album");
	    	return;
	    }
	    ImageView imageView = createImage(temp);
        tiles.getChildren().addAll(imageView);


	}
	/**
	 * 
	 * @param picture displays the picture
	 * @return object of ImageView
	 * @throws FileNotFoundException throws exception if file not found
	 */
	public ImageView createImage(Picture picture) throws FileNotFoundException {
		ImageView imageView = null;
		Image image = new Image(new FileInputStream(picture.file),150, 0, true, true);
        imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        
        if (imageView != null) {
			imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent mouseEvent) {
					selectedImage = (ImageView)mouseEvent.getSource();
					selectedPicture = picture;
					updateCaption();
					System.out.println(selectedPicture);
					System.out.println(selectedPicture.tags);
					obsTags = FXCollections.observableList(selectedPicture.tags);
					tagList.setItems(obsTags);
	                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

	                    if(mouseEvent.getClickCount() == 2){
	                        
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
							loader.setLocation(getClass().getResource("/view/picture.fxml"));
							Pane pane = null;
							try {
								pane = loader.load();
							} catch (IOException e) {
							}
							PictureController temp = loader.getController();
							Picture.curr = picture;
							Photos.root.setCenter(pane);
							try {
								temp.start();
							} catch (IOException e) {
							}
	                    }
	                }
	            }});
		}
        return imageView;
	}
	/**
	 * deletes selected picture from album
	 */
	public void delete() {
		if (selectedImage != null) {
            tiles.getChildren().remove(selectedImage);
			Album.curr.pictures.remove(selectedPicture);
			selectedImage = null;
			selectedPicture = null;
			captionField.setText(null);
		}
		else {
			errorUpdate("Select a Picture");
		}
	}
	/**
	 * changes picture's caption
	 */
	public void caption() {
		selectedPicture.caption = captionField.getText();
//		selectedPicture.recaption(captionField.getText());
	}
	/**
	 * updates caption on the page
	 */
	public void updateCaption() {
		captionField.setText(selectedPicture.caption);
	}
	/**
	 * adds new type to all tag types of the user
	 */
	public void addType() {
		int i = Collections.binarySearch(User.curr.tagTypes, typeField.getText(), String::compareToIgnoreCase);
		if (i >= 0) {
			errorUpdate("Type Already Exits");
		}
		else {
			i = ~i;
			obsTypes.add(i, typeField.getText());
			typeList.getSelectionModel().select(i);
		}
	}
	/**
	 * adds new tag to selected picture
	 */
	public void addTag() {
		if (selectedPicture == null) {
			errorUpdate("Select a photo");
			return;
		}
		if (tagField.getText() == null || tagField.getText().equals("") || tagField.getText().trim().length() == 0) {
			errorUpdate("Input valid value");
			return;
		}
		if (typeList.getSelectionModel().getSelectedItem().equals("location")) {
			if (selectedPicture.locationTagIsSet) {
				errorUpdate("Location is Unique!");
				return;
			}
			selectedPicture.locationTagIsSet = true;
		}
		Tag temp = new Tag(typeList.getSelectionModel().getSelectedItem(),tagField.getText(),selectedPicture);
		int i = Collections.binarySearch(obsTags, temp);
		if (i >= 0) {
			errorUpdate("Tag Already exits");
			return;
		}
		i = ~i;
		obsTags.add(i, temp);
		tagList.getSelectionModel().select(i);
			
		
	}
	/**
	 * delete tag from picture's tag
	 */
	public void deleteTag() {
		if (obsTags.size() == 0) {
			errorUpdate("The list is empty");
			return;
		}
		if (selectedPicture == null) {
			errorUpdate("Select a photo");
			return;
		}
		else {
			int i = tagList.getSelectionModel().getSelectedIndex();
			if (obsTags.get(i).type.equals("location")) {
				selectedPicture.locationTagIsSet = false;
			}
			obsTags.remove(i);
			if (obsTags.size() != 0) {
				tagList.getSelectionModel().select(i);
			}
			else {
				tagList.getSelectionModel().clearSelection();
			}
			clearFields((AnchorPane)deleteTagBtn.getParent());

		}
	}
	/**
	 * moves picture to different album
	 */
	public void moveTo() {
		if (selectedPicture == null) {
			errorUpdate("Select a photo");
			return;
		}
		if (albumList.getSelectionModel().getSelectedItem().albumName.equals(Album.curr.albumName)) {
			errorUpdate("Selected different album");
			return;
		}
		albumList.getSelectionModel().getSelectedItem().addPicture(selectedPicture);
		delete();
	}
	/**
	 * copies picture to different album
	 */
	public void copyTo() {
		if (selectedPicture == null) {
			errorUpdate("Select a photo");
			return;
		}
		if (albumList.getSelectionModel().getSelectedItem().albumName.equals(Album.curr.albumName)) {
			errorUpdate("Selected different album");
			return;
		}
		albumList.getSelectionModel().getSelectedItem().addPicture(selectedPicture);
	}
	/**
	 * starts slideshow
	 */
	public void slideShow() {
		if (selectedPicture == null) {
			errorUpdate("Select a photo");
			tabs.getSelectionModel().clearAndSelect(currentTab);
			return;
		}
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
		loader.setLocation(getClass().getResource("/view/slideshow.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
		}
		SlideshowController temp = loader.getController();
		Picture.curr = selectedPicture;
		Photos.root.setCenter(pane);
		try {
			temp.start();
		} catch (IOException e) {
		}
	}
	/**
	 * chooses tab)
	 */
	public void tab0() {
		currentTab = 0;
	}
	/**
	 * chooses tab 1
	 */
	public void tab1() {
		currentTab = 1;
	}
	/**
	 * chooses tab 2
	 */
	public void tab2() {
		currentTab = 2;
	}
	/**
	 * helper method, clears fields
	 * @param pane , parent of the fields to be cleared
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
	
	/**
	 * Updates various errors
	 * @param str message
	 */
	private void errorUpdate(String str) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(str);
		alert.showAndWait();
	}
}
