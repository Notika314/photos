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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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

public class InAlbumController {
	
	@FXML
	TilePane tiles;
	@FXML
	Button addBtn;
	@FXML 
	Button deleteBtn;
	@FXML
	TextField captionField;
	@FXML
	Button addTypeBtn;
	@FXML 
	Button addTagBtn;
	@FXML 
	Button deleteTagBtn;
	@FXML
	TextField typeField;
	@FXML
	TextField tagField;
	@FXML
	ListView<Tag> tagList;
	@FXML
	ListView<String> typeList;
	@FXML
	ListView<Album> albumList;
	@FXML
	TabPane tabs;
	
	@FXML
	ImageView selectedImage;
	
	Picture selectedPicture;
	
	private ObservableList<Tag> obsTags;  
	private ObservableList<String> obsTypes;  
	private ObservableList<Album> obsAlbums;  
	
	private int currentTab = 0;

	 
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

	public void add() throws IOException {
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
	    File file = chooser.showOpenDialog(addBtn.getParent().getScene().getWindow());
	    if (file == null) {
	    	return;
	    }
	    System.out.println(file.lastModified());
	    Picture temp = new Picture(Album.curr, file);
	    if (!Album.curr.addPicture(temp)) {
	    	System.out.println("File already in album");
	    	return;
	    }
	    ImageView imageView = createImage(temp);
        tiles.getChildren().addAll(imageView);

	    /*
	    ImageView imageView = new ImageView();
	    imageView.setImage(new Image(new FileInputStream(temp.file),150, 0, true, true));
        imageView.setFitWidth(150);
        tiles.getChildren().addAll(imageView);*/
	}
	
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
					System.out.println("tester");
					selectedImage = (ImageView)mouseEvent.getSource();
					selectedPicture = picture;
					updateCaption();
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
							// Pane pane = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
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
	
	public void delete() {
		if (selectedImage != null) {
            tiles.getChildren().remove(selectedImage);
			Album.curr.removePicture(selectedPicture);
			selectedImage = null;
			selectedPicture = null;
			captionField.setText(null);
		}
		else {
			System.out.println("Select a Picture");
		}
	}
	
	public void caption() {
		selectedPicture.recaption(captionField.getText());
	}
	
	public void updateCaption() {
		captionField.setText(selectedPicture.caption);
	}
	
	public void addType() {
		int i = Collections.binarySearch(User.curr.tagTypes, typeField.getText(), String::compareToIgnoreCase);
		if (i >= 0) {
			System.out.println("Type Already Exits");
		}
		else {
			i = ~i;
			obsTypes.add(i, typeField.getText());
		}
	}
	
	public void addTag() {
		if (selectedPicture == null) {
			System.out.println("Select a photo");
			return;
		}
		if (tagField.getText() == null || tagField.getText() == "" || tagField.getText().trim().length() == 0) {
			System.out.println("Input valid value");
			return;
		}
		if (typeList.getSelectionModel().getSelectedItem().equals("location")) {
			if (selectedPicture.locationTagIsSet) {
				System.out.println("Location is Unique!");
				return;
			}
			selectedPicture.locationTagIsSet = true;
		}
		Tag temp = new Tag(typeList.getSelectionModel().getSelectedItem(),tagField.getText(),selectedPicture);
		int i = Collections.binarySearch(obsTags, temp);
		if (i >= 0) {
			System.out.println("Tag Already exits");
			return;
		}
		i = ~i;
		obsTags.add(i, temp);
		tagList.getSelectionModel().select(i);
			
		
	}
	
	public void deleteTag() {
		if (obsTags.size() == 0) {
			System.out.println("The list is empty");
			return;
		}
		else {
			int i = tagList.getSelectionModel().getSelectedIndex();
			obsTags.remove(i);
			if (obsTags.size() != 0) {
				tagList.getSelectionModel().select(i);
			}
			else {
				tagList.getSelectionModel().clearSelection();
				//clearFields((AnchorPane)detailsName.getParent());
			}
		}
	}
	
	public void moveTo() {
		if (selectedPicture == null) {
			System.out.println("Select a photo");
			return;
		}
		if (albumList.getSelectionModel().getSelectedItem().albumName.equals(Album.curr.albumName)) {
			System.out.println("Selected different album");
			return;
		}
		albumList.getSelectionModel().getSelectedItem().addPicture(selectedPicture);
		delete();
	}
	
	public void copyTo() {
		if (selectedPicture == null) {
			System.out.println("Select a photo");
			return;
		}
		if (albumList.getSelectionModel().getSelectedItem().albumName.equals(Album.curr.albumName)) {
			System.out.println("Selected different album");
			return;
		}
		albumList.getSelectionModel().getSelectedItem().addPicture(selectedPicture);
	}
	
	public void slideShow() {
		if (selectedPicture == null) {
			System.out.println("Select a photo");
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
		// Pane pane = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
		Picture.curr = selectedPicture;
		Photos.root.setCenter(pane);
		try {
			temp.start();
		} catch (IOException e) {
		}
	}
	
	public void tab0() {
		currentTab = 0;
	}
	
	public void tab1() {
		currentTab = 1;
	}
	public void tab2() {
		currentTab = 2;
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
