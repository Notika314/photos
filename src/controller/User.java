package controller;

import java.util.*;
//import controller.Album;
public class User {
	static ArrayList<User> users = new ArrayList<User>();
	ArrayList<Album> albums = new ArrayList<Album>();
	String userName;
	String password;
	ArrayList<Album> userAlbums;
	
	public boolean albumExists(String name) {
		for (int i=0;i<albums.size();i++) {
			if (albums.get(i).albumName.contentEquals(name)) return true;
		}
		return false;
	}
	
	public static boolean userExists(String name) {
		for (int i=0;i<users.size();i++) {
			if (users.size()>0 && users.get(i).userName.contentEquals(name)) return true;
		}
		return false;
	}
	public User(String name, String password) {
		if (User.userExists(name)) {
			System.out.println("Choose different username");
			
		} else {
			this.userName = name;
			this.password = password;
			this.userAlbums = new ArrayList<Album>();
			users.add(this);
		}
	}
	public static User findUser(String name, String password) {
		if (users.size()<1) return null;
		for (int i=0;i<users.size();i++) {
			if (users.get(i).userName.contentEquals(name)&&users.get(i).password.contentEquals(password)) {
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
			System.out.println("Album with such name already exists");
			return;
		}
		Album album = new Album(name,this);
		albums.add(album);
	}
	
	
	public boolean equals(Object o) {
		if (!( o instanceof User)) return false;
		User u = (User) o;
		if (u.userName.equals(this.userName)) return true;
		else return false;
	}
}
