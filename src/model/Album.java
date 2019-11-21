package model;
import java.io.*;
import java.util.ArrayList;

/**
 * Class that implements logic of how Albums are created, modified,
 *  deleted , as well as how pictures are added/deleted/updated within the album
 * @author Natalia Bryzhatenko
 * @author Christopher Taglieri cat197
 *
 */
public class Album implements Serializable, Comparable<Album> {
	/**
	 * Name of the album
	 */
	public String albumName;
	/**
	 * user to which the album belongs
	 */
	public User user;
	/**
	 * Arraylist that stores all the pictures in the album
	 */
	public ArrayList<Picture> pictures;
	/**
	 * currently selected album
	 */
	public static Album curr;
	/**
	 * picture in the album that has the earliest date field
	 */
	private Picture earliestPic;
	/**
	 * picture in the album that has the latest date field
	 */
	private Picture latestPic;
	/**
	 * long integer used for serialization
	 */
	static final long serialVersionUID = 1L;
	/**
	 * Initializes Album with given albumName and user
	 * @param albumName name of the album
	 * @param user owner of the album
	 */
	public Album(String albumName, User user) {
		this.albumName = albumName;
		this.user = user;
		this.pictures = new ArrayList<Picture>();
	}
	/**
	 * Determines whether picture exists
	 * @param file The file that picture have as a field
	 * @return true if picture exists in the album, false otherwise
	 */
	public boolean pictureExists(File file) {
		for (int i=0;i<pictures.size();i++) {
			if (this.pictures.get(i).file.equals(file)) return true;
		}
		return false;
	}
	/**
	 * getter method
	 * @return earliest created picture in the album
	 */
	public Picture getEarliest() {
		return this.earliestPic;
	}
	/**
	 * getter method
	 * @return latest created picture in the album
	 */
	public Picture getLatest() {
		return this.latestPic;
	}
	/**
	 * adding picture to album
	 * @param picture picture that user attempts to add
	 * @return true on success, false otherwise
	 */
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
					if (pictures.get(i).date.compareTo(picture.date)<0) {
						earliest = false;
						break;
					}
					if (pictures.get(i).date.compareTo(picture.date)>0) {
						latest = false;
						break;
					}
				}
				if (earliest) this.earliestPic = picture;
				if (latest) this.latestPic = picture;
			}
			pictures.add(picture);
			return true;
//		}
	}
	/**
	 * helper method
	 * updates earliestPic field
	 */
	public void updateEarliestPic() {
		Picture e = this.pictures.get(0);
		for (int i=1;i<this.pictures.size();i++) {
			if (this.pictures.get(i).date.compareTo(e.date)<0) {
				e = this.pictures.get(i);
			}
		}
		this.earliestPic = e;
	}
	/**
	 * helper method, updates latestPicture field
	 */
	public void updateLatestPic() {
		Picture l = this.pictures.get(0);
		for (int i=1;i<this.pictures.size();i++) {
			if (this.pictures.get(i).date.compareTo(l.date)>0) {
				l = this.pictures.get(i);
			}
		}
		this.latestPic = l;
	}
	/**
	 * removes picture from album
	 * @param picture Picture that user attempts to remove
	 * @return true on success, false otherwise
	 */
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
	/**
	 * overrides equals method, compares albums by the names
	 */
	public boolean equals(Object o) {
		if (!( o instanceof Album)) return false;
		Album a = (Album) o;
		if (a.albumName.equals(this.albumName)) return true;
		else return false;
	}
	/**
	 * implements compareTo method from Comparable interface
	 */
	public int compareTo(Album other) {
		return this.albumName.toLowerCase().compareTo(other.albumName.toLowerCase());
	}
	/**
	 * overrides toString method, returns the name of the album
	 */
	public String toString() {
		return this.albumName;
	}
}
