package model;
import java.io.*;
import java.util.ArrayList;


public class Album implements Serializable, Comparable<Album> {

	public String albumName;
	public User user;
	public ArrayList<Picture> pictures;
	public static Album curr;
	public Picture earliestPic;
	public Picture latestPic;
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
			if (pictures.size()==0) {
				earliestPic = picture;
				latestPic = picture;
			} else {
				boolean earliest = true;
				boolean latest = true;
				for (int i=0;i<pictures.size();i++) {
					if (pictures.get(i).date.compareTo(picture.date)<0) earliest = false;
					if (pictures.get(i).date.compareTo(picture.date)>0) latest = false;
				}
				if (earliest) this.earliestPic = picture;
				if (latest) this.latestPic = picture;
			}
			pictures.add(picture);
			return true;
//		}
	}
	
	public void updateEarliestPic() {
		Picture e = this.pictures.get(0);
		for (int i=0;i<this.pictures.size();i++) {
			if (this.pictures.get(i).date.compareTo(e.date)<0) {
				e = this.pictures.get(i);
			}
		}
		this.earliestPic = e;
	}
	
	public void updateLatestPic() {
		Picture l = this.pictures.get(0);
		for (int i=0;i<this.pictures.size();i++) {
			if (this.pictures.get(i).date.compareTo(l.date)>0) {
				l = this.pictures.get(i);
			}
		}
		this.latestPic = l;
	}
	
	public boolean removePicture(Picture picture) {
		for (int i=0;i<pictures.size();i++) {
			if (pictures.get(i).equals(picture) ) {
				pictures.remove(i);
				if (this.earliestPic.equals(picture)) {
					updateEarliestPic();
				}
				if (this.latestPic.equals(picture)) {
					updateLatestPic();
				}
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
		return this.albumName.toLowerCase().compareTo(other.albumName.toLowerCase());
	}
	
	public String toString() {
		return this.albumName;
	}
}
