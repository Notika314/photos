package model;
import java.io.*;
import java.util.ArrayList;


public class Album implements Serializable {

	public String albumName;
	public User user;
	public ArrayList<Picture> pictures;
	
	public Album(String albumName, User user) {
		this.albumName = albumName;
		this.user = user;
		this.pictures = new ArrayList<Picture>();
	}
	
	protected boolean pictureExists(String path) {
		for (int i=0;i<pictures.size();i++) {
			if (pictures.get(i).path.equals(path)) return true;
		}
		return false;
	}
	
	public boolean addPicture(Picture picture) {
		if (pictureExists(picture.path)) {
			return false;
		} else {
			pictures.add(picture);
			return true;
		}
	}
	
	public boolean removePicture(Picture picture) {
		for (int i=0;i<pictures.size();i++) {
			if (pictures.get(i).equals(picture) ) {
				pictures.remove(i);
				return true;
			}
		}
//		for (int j=0;j<this.user.userPictures.size();j++) {
//			if (this.user.userPictures.get(j).equals(picture)) {
//				this.user.userPictures.remove(j);
//				return true;
//			}
//		}
		return false;
	}
	
	public boolean equals(Object o) {
		if (!( o instanceof Album)) return false;
		Album a = (Album) o;
		if (a.albumName.equals(this.albumName)) return true;
		else return false;
	}
}
