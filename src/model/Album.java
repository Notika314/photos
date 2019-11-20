package model;
import java.io.*;
import java.util.ArrayList;


public class Album implements Serializable, Comparable<Album> {

	public String albumName;
	public User user;
	public ArrayList<Picture> pictures;
	public static Album curr;
	
	public Album(String albumName, User user) {
		this.albumName = albumName;
		this.user = user;
		this.pictures = new ArrayList<Picture>();
	}
	
	public boolean pictureExists(File file) {
		for (int i=0;i<pictures.size();i++) {
			if (this.pictures.get(i).file.equals(file)) return true;
		}
		return false;
	}
	
	public boolean addPicture(Picture picture) {
//		if (this.pictureExists(picture.file)) {
//			return false;
//		} else {
			pictures.add(picture);
			return true;
//		}
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
	
	public int compareTo(Album other) {
		return this.albumName.compareTo(other.albumName);
	}
	
	public String toString() {
		return this.albumName;
	}
}
