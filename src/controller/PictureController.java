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
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
	@FXML Label CaptionLbl;
	Picture selectedPicture;
	
	private ObservableList<Tag> obsTags;  
	private ObservableList<String> obsTypes;  
	
	private int currentTab = 0;
	
	public void start() throws IOException {  
		selectedPicture = Picture.curr;
		if (selectedPicture != null) {
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
		if (obsTags != null && obsTags.size() != 0) {
			tagList.getSelectionModel().select(0);
			tagList.getFocusModel().focus(0);
		}
		if (obsTypes.size() != 0) {
			typeList.getSelectionModel().select(0);
			typeList.getFocusModel().focus(0);
		}
		CaptionLbl.setText(selectedPicture.caption);
		captionField.setText(selectedPicture.caption);
//		System.out.println(Picture.curr);
//		System.out.println(Picture.curr.file.getCanonicalPath());
		
		//image = new ImageView();
	    image.setImage(new Image(new FileInputStream(Picture.curr.file)));

	}
	
	
	public void caption() {
		selectedPicture.recaption(captionField.getText());
		CaptionLbl.setText(captionField.getText());
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
}
