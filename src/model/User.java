package model;
import java.util.*;
import java.io.*;

public class User implements Serializable {
	public static ArrayList<User> users = new ArrayList<User>();
	public String userName;
	public String password;
	public ArrayList<Album> userAlbums;
	public static User curr;
	public static final String storeDir = "data";
	static final long serialVersionUID = 1L;
//	protected ArrayList<Picture> userPictures;
	
	public boolean albumExists(String name) {
		for (int i=0;i<userAlbums.size();i++) {
			if (userAlbums.get(i).albumName.equals(name)) return true;
		}
		return false;
	}
	
	public static boolean userExists(String name) {
		for (int i=0;i<users.size();i++) {
			if (users.size()>0 && users.get(i).userName.equals(name)) return true;
		}
		return false;
	}
	public User(String name, String password) throws IOException {
		if (User.userExists(name)) {
			System.out.println("Choose different username");
			
		} else {
			this.userName = name;
			this.password = password;
			this.userAlbums = new ArrayList<Album>();
//			this.userPictures = new ArrayList<Picture>();
			users.add(this);
		}
	}
	public static User findUser(String name, String password) {
		if (users.size()<1) return null;
		for (int i=0;i<users.size();i++) {
			if (users.get(i).userName.equals(name)&&users.get(i).password.equals(password)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	public static boolean usernameExists(String name) {
		if (users.size()<1) return false;
		for (int i=0;i<users.size();i++) {
			if (users.get(i).userName.contentEquals(name)) {
				return true;
			}
		}
		return false;
	}
	public void createAlbum(String name) {
		if (albumExists(name)) {
			//need to handle the case when album with the name already exists
			System.out.println("Album with such name already exists"); 
			return;
		}
		Album album = new Album(name,this);
		userAlbums.add(album);
	}
	
	public void renameAlbum(Album album, String newName) {
		if (albumExists(newName)) return;	//can't rename if the album with newName already exists
		for (int i=0;i<userAlbums.size();i++) {
			if (userAlbums.get(i).equals(album)) userAlbums.get(i).albumName = newName;
		}
	}
	
	
	public void addPhotoToAlbum(Album album, File file) throws IOException {
		for (int i=0;i<userAlbums.size();i++) {
			if (userAlbums.get(i).equals(album)) {
				Picture picture = new Picture(album,file);
				userAlbums.get(i).addPicture(picture);
			}
		}
	}
	
	public void movePicture(Picture picture, Album newAlbum) {
		if (albumExists(newAlbum.albumName)) {
			picture.album = newAlbum;
			picture.album.removePicture(picture);
			newAlbum.addPicture(picture);
		}
	}
	
	public void copyPicture(Picture picture,Album newAlbum) throws IOException {
		if (albumExists(newAlbum.albumName)) {
			Picture newPicture = new Picture(newAlbum,picture.file);
			newPicture.tags = picture.tags;
			newAlbum.addPicture(newPicture);
		}
	}
	public void deleteAlbum(Album album) {
		for (int i=0;i<userAlbums.size();i++) {
			
			if (userAlbums.get(i).equals(album)) {
//			need to remove every picture before removing an album;		
				userAlbums.remove(i);
			}
		}
	}
	
	public void defineNewTag(String newTag) {
		if (Tag.hasaType(newTag)) return;
		Tag.createNewTagType(newTag);
	}
	
	public boolean equals(Object o) {
		if (!( o instanceof User)) return false;
		User u = (User) o;
		if (u.userName.equals(this.userName)) return true;
		else return false;
	}
	public static void writeUser (User u) throws IOException {
		String storeFile = "data/"+u.userName+".ser"; 
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeFile));
		System.out.println("writing new user to "+ storeFile);
		oos.writeObject(u); 
		oos.close();
		
	}
	
//	public static User readUser(String fileName) throws IOException, ClassNotFoundException {
//		ObjectInputStream ois = new ObjectInputStream(
//				new FileInputStream(storeDir + File.separator + storeFile));
//				GeomApp gapp = (GeomApp)ois.readObject();
//				return gapp; 
//		return new User();
//	}
}
