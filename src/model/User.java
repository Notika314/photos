package model;
import java.util.*;
import java.io.*;
/**
 * Class that implements logic of how Users are created, modified,
 *  deleted , how tags, pictures and albums are added, modified or deleted by users
 * @author Christopher Taglieri cat 197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class User implements Serializable , Comparable<User>{
	/**
	 * lists that stores all the users that were created (except the ones that 
	 * were deleted)
	 */
	public static ArrayList<User> users = new ArrayList<User>();
	/**
	 * name of the user
	 */
	public String userName;
	/**
	 * password for logging in
	 */
	public String password;
	/**
	 * all albums that were created by user
	 */
	public ArrayList<Album> userAlbums;
	/**
	 * all the types of tags (predefined and user-defined)
	 */
	public ArrayList<String> tagTypes = new ArrayList<String>();
	/**
	 * static field, current user that has logged in 
	 */
	public static User curr;
	/**
	 * folder that has another folders with users and photos
	 */
	public static final String storeDir = "data";
	/**
	 * long integer used for serialization and deserialization purposes
	 */
	static final long serialVersionUID = 1L;
//	protected ArrayList<Picture> userPictures;
	/**
	 * checks whether album with given name exists in user's albums list
	 * @param name - name of the album
	 * @return true if such album exists, false otherwise
	 */
	public boolean albumExists(String name) {
		for (int i=0;i<userAlbums.size();i++) {
			if (userAlbums.get(i).albumName.equals(name)) return true;
		}
		return false;
	}
	/**
	 * static method,checks if user with given name exists
	 * @param name  userName of the user
	 * @return true if such user exists, false otherwise
	 */
	public static boolean userExists(String name) {
		for (int i=0;i<users.size();i++) {
			if (users.size()>0 && users.get(i).userName.equals(name)) return true;
		}
		return false;
	}
	
	/**
	 * Initializes user with given name and password
	 * @param name  userName of the user to be created
	 * @param password  password of the user to be created
	 * @throws IOException
	 */
	public User(String name, String password) throws IOException {
		if (User.userExists(name)) {
			
		} else {
			this.userName = name;
			this.password = password;
			this.userAlbums = new ArrayList<Album>();
			this.tagTypes.add("location");
			this.tagTypes.add("person");
			this.tagTypes.add("hashtag");
//			this.userPictures = new ArrayList<Picture>();
			int i = Collections.binarySearch(users, this);
			i = ~i;
			users.add(i,this);
		}
	}
	
	/**
	 * finds user by userName and password
	 * @param name  userName of the usr to be found
	 * @param password Password of the user to be found
	 * @return True if user with given userName and password is found,
	 * false otherwise
	 */
	public static User findUser(String name, String password) {
		if (users.size()<1) return null;
		for (int i=0;i<users.size();i++) {
			if (users.get(i).userName.toLowerCase().equals(name.toLowerCase())&&users.get(i).password.equals(password)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Removes user with given name from users ArrayList
	 * @param name userName of the user to be deleted from users ArrayList
	 */
	public static void removeUser(String name) {
		for (int i=0;i<User.users.size();i++) {
			if (User.users.get(i).userName.contentEquals(name)) {
				User.users.remove(i);
				return;
			}
		}
	}
	/**
	 * Checks if user with given userName exists
	 * @param name userName of the user
	 * @return true if user with given userName exists, false otherwise
	 */
	public static boolean usernameExists(String name) {
		if (users.size()<1) return false;
		for (int i=0;i<users.size();i++) {
			if (users.get(i).userName.toLowerCase().contentEquals(name.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Creates album with provided albumName
	 * @param name albumName for the album to be created
	 */
	public void createAlbum(String name) {
		if (albumExists(name)) {
			//need to handle the case when album with the name already exists
			System.out.println("Album with such name already exists"); 
			return;
		}
		Album album = new Album(name,this);
		userAlbums.add(album);
	}
	/**
	 * renames album 
	 * @param album Album that needs to be renamed
	 * @param newName the new albumName for the album
	 */
	public void renameAlbum(Album album, String newName) {
		if (albumExists(newName)) return;	//can't rename if the album with newName already exists
		for (int i=0;i<userAlbums.size();i++) {
			if (userAlbums.get(i).equals(album)) userAlbums.get(i).albumName = newName;
		}
	}
	
	/**
	 * Adding photo to album
	 * @param album Album where photo needs to be added
	 * @param file File where photo is stored on machine
	 * @throws IOException
	 */
	public void addPhotoToAlbum(Album album, File file) throws IOException {
		for (int i=0;i<userAlbums.size();i++) {
			if (userAlbums.get(i).equals(album)) {
				Picture picture = new Picture(album,file);
				userAlbums.get(i).addPicture(picture);
			}
		}
	}
	/**
	 * Moves picture from one album to another
	 * @param picture Picture to be moved
	 * @param newAlbum Album where picture is being moved to
	 */
	public void movePicture(Picture picture, Album newAlbum) {
		if (albumExists(newAlbum.albumName)) {
			picture.album = newAlbum;
			picture.album.removePicture(picture);
			newAlbum.addPicture(picture);
		}
	}
	/**
	 * Copying pictures to another album
	 * @param picture Picture to be copied
	 * @param newAlbum Album where picture is being copied to 
	 * @throws IOException
	 */
	public void copyPicture(Picture picture,Album newAlbum) throws IOException {
		if (albumExists(newAlbum.albumName)) {
			Picture newPicture = new Picture(newAlbum,picture.file);
			newPicture.tags = picture.tags;
			newAlbum.addPicture(newPicture);
		}
	}
	/**
	 * Deletes the Album
	 * @param album Album to be deleted
	 */
	public void deleteAlbum(Album album) {
		for (int i=0;i<userAlbums.size();i++) {
			
			if (userAlbums.get(i).equals(album)) {
//			need to remove every picture before removing an album;		
				userAlbums.remove(i);
			}
		}
	}
	
	/*
	public void defineNewTag(String newTag) {
		if (Tag.hasaType(newTag)) return;
		Tag.createNewTagType(newTag);
	}*/
	/**
	 * overrides equals method of Object class, compares by userName
	 */
	public boolean equals(Object o) {
		if (!( o instanceof User)) return false;
		User u = (User) o;
		if (u.userName.toLowerCase().equals(this.userName.toLowerCase())) return true;
		else return false;
	}
	/**
	 * Writes user to a file
	 * @param u
	 * @throws IOException
	 */
	public static void writeUser (User u) throws IOException {
		String storeFile = "data/users/"+u.userName+".ser"; 
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeFile));
		oos.writeObject(u); 
		oos.close();
		
	}
	/**
	 * implements compareTo method from Comparable interface
	 */
	public int compareTo(User other) {
		return this.userName.toLowerCase().compareTo(other.userName.toLowerCase());
	}
	/**
	 * overrides toString method from Object class, prints userName
	 */
	public String toString() {
		return this.userName;
	}
	

}
