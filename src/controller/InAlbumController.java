package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import app.Photos;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Picture;
import model.User;

public class InAlbumController {
	
	@FXML
	TilePane tiles;
	
	@FXML
	Button addBtn;
	
	public void start() throws FileNotFoundException,IOException {  
        ImageView imageView = null;
		for (Picture pic : Album.curr.pictures) {
            imageView = createImage(pic);
            tiles.getChildren().addAll(imageView);
		}
		//may need to put this else where
		
    }

	private ImageView createThumbnails(/* need some parameters maybe */) {
		ImageView imageView = null;
		Image image = null;
		/*
		 * image = new Image(need to find the source image, maybe need to store current
		 * user somewhere, 150, 0, true, true);
		 */
		imageView = new ImageView(image);
		imageView.setFitWidth(150);
		return imageView;
	}

	public void add() throws IOException {
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
	    File file = chooser.showOpenDialog(addBtn.getParent().getScene().getWindow());
	    if (file == null) {
	    	return;
	    }
	    //System.out.println(file.lastModified());
	    Picture temp = new Picture(Album.curr, file);
	    if (!Album.curr.addPicture(temp)) {
	    	System.out.println("File already in album");
	    	return;
	    }
	    ImageView imageView = new ImageView();
	    imageView.setImage(new Image(new FileInputStream(temp.file),150, 0, true, true));
        imageView.setFitWidth(150);
        tiles.getChildren().addAll(imageView);
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
							Photos.root.setCenter(pane);
							temp.start();
	                    }
	                }
	            }});
		}
        return imageView;
	}
	
	public void delete() {
		
	}
}
