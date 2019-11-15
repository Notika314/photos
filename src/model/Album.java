package model;
import java.io.*;
import java.util.ArrayList;


public class Album implements Serializable {

	public String albumName;
	User user;
	ArrayList<Picture> pictures;
	
	public Album(String albumName, User user) {
		this.albumName = albumName;
		this.user = user;
		this.pictures = new ArrayList<Picture>();
	}
	
	private boolean pictureExists(String path) {
		for (int i=0;i<pictures.size();i++) {
			if (pictures.get(i).path.equals(path)) return true;
		}
		return false;
	}
	
	public boolean addPicture(String path, String caption) {
		if (pictureExists(path)) {
			return false;
		} else {
			Picture picture = new Picture(this.user,path,caption);
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
		return false;
	}
	
	public boolean equals(Object o) {
		if (!( o instanceof Album)) return false;
		Album a = (Album) o;
		if (a.albumName.equals(this.albumName)) return true;
		else return false;
	}
}
