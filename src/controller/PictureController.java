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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Album;
import model.Picture;
import model.Tag;
import model.User;
/** Implements UI logic on Picture view , handles editing,
 *  viewing Picture, adding /deleting tags and captions
 * @author Christopher Taglieri cat 197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class PictureController {
	/**
	 * displays the picture
	 */
	@FXML ImageView image;
	/**
	 * Triggers creating/editing picture's caption
	 */
	@FXML Button captionBtn;
	/**
	 * Triggers adding new Tag to picture
	 */
	@FXML Button addTagBtn;
	/**
	 * Triggers adding type to picture
	 */
	@FXML Button addTypeBtn;
	/**
	 * Triggers deleting the tag
	 */
	@FXML Button deleteTagBtn;
	/**
	 * TextArea for entering new/modified caption
	 */
	@FXML TextArea captionField;
	/**
	 * field for entering new type for tag
	 */
	@FXML TextField typeField;
	/**
	 * Field where user enters value of tag
	 */
	@FXML TextField tagField;
	/**
	 * Lists all of the types of tags that current user has
	 */
	@FXML ListView<String> typeList;
	/**
	 * Lists all the tags defined on the picture
	 */
	@FXML ListView<Tag> tagList;
	/**
	 * Displays caption of the picture
	 */
	@FXML Label CaptionLbl;
	/**
	 * The picture that is displayed on the page
	 */
	Picture selectedPicture;
	/**
	 * Observable list linked to all the tags of the picture
	 */
	private ObservableList<Tag> obsTags;  
	/** 
	 * Observable list linked to all the types of the tags 
	 * that current user has defined
	 */
	private ObservableList<String> obsTypes;  
	/**
	 * Currently active tab
	 */
	private int currentTab = 0;
	/**
	 * Starts the page
	 * @throws IOException
	 */

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
	
	/**
	 * Changes caption of the picture
	 */
	public void caption() {
		selectedPicture.recaption(captionField.getText());
		CaptionLbl.setText(captionField.getText());
	}
	/**
	 * adding new type to users list of tags and displaying it on the page
	 */
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
	/**
	 * Adding new tag to current picture and displays it on the page
	 */
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
	/**
	 * deletes the tag from the list of picture's tags
	 */
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
