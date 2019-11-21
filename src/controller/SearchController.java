package controller;

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
import model.Album;
import model.Picture;
import model.Search;
import model.Tag;
import model.User;

public class SearchController {
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
	TextField createField;
	
	@FXML
	ImageView selectedImage;
	
	Picture selectedPicture;
	
	private ObservableList<Tag> obsTags;  
	private ObservableList<String> obsTypes;  
	private ObservableList<Album> obsAlbums;  
	
	private int currentTab = 0;
	
	public void start() throws FileNotFoundException {
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
		obsAlbums = FXCollections.observableList(User.curr.userAlbums);
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
		for (Picture pic : Search.searchResult) {
            imageView = createImage(pic);
            tiles.getChildren().addAll(imageView);
		}
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
			Search.searchResult.remove(selectedPicture);
			selectedImage = null;
			selectedPicture = null;
			captionField.setText(null);
		}
		else {
			System.out.println("Select a Picture");
		}
	}
	
	public void caption() {
		selectedPicture.caption = captionField.getText();
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
			typeList.getSelectionModel().select(i);
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
		if (selectedPicture == null) {
			System.out.println("Select a photo");
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
		loader.setLocation(getClass().getResource("/view/slideSearch.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
		}
		SlideSearchController temp = loader.getController();
		// Pane pane = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
		Picture.curr = selectedPicture;
		Photos.root.setCenter(pane);
		try {
			temp.start();
		} catch (IOException e) {
		}
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
		for (Picture pic : Search.searchResult) {
			temp.pictures.add(new Picture(pic, temp));
		}
		int i = Collections.binarySearch(obsAlbums, temp);
		i = ~i;
		obsAlbums.add(i, temp);
		albumList.getSelectionModel().select(i);
		clearFields((AnchorPane)createField.getParent());
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
