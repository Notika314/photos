package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import model.Album;
import model.Picture;
import model.Tag;
import model.User;

public class PictureController {
	@FXML ImageView image;
	@FXML Button captionBtn;
	@FXML Button addTagBtn;
	@FXML Button addTypeBtn;
	@FXML TextField captionField;
	@FXML TextField typeField;
	@FXML TextField tagField;
	@FXML ListView<String> typeList;
	@FXML ListView<Tag> tagList;
	Picture selectedPicture;
	
	private ObservableList<Tag> obsTags;  
	private ObservableList<String> obsTypes;  
	
	public void start() throws IOException {  
		selectedPicture = Picture.curr;
		if (selectedPicture != null) {
			System.out.println("The tags of the picture are:");
			for (int i=0;i<selectedPicture.tags.size();i++) {
				System.out.println(selectedPicture.tags.get(i));
			}
			
			obsTags = FXCollections.observableList(selectedPicture.tags);
			tagList.setItems(obsTags);
		}
		else {
			System.out.println("SelectedPicure is null");
			obsTags = null;
			tagList.setItems(null);
		}
		obsTypes = FXCollections.observableList(User.curr.tagTypes);
		typeList.setItems(obsTypes);
//		System.out.println(Picture.curr);
//		System.out.println(Picture.curr.file.getCanonicalPath());
		
		//image = new ImageView();
	    image.setImage(new Image(new FileInputStream(Picture.curr.file)));

	}
	
	
	public void caption() {
		
	}
	
	public void addType() {
		
	}
	
	public void addTag() {
		
	}
	
	public void deleteTag() {
		
	}
}
