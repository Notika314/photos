package controller;

import java.util.*;

public class User {
	static ArrayList<User> users = new ArrayList<User>();
	String userName;
	String password;
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
	
	public boolean equals(Object o) {
		if (!( o instanceof User)) return false;
		User u = (User) o;
		if (u.userName.equals(this.userName)) return true;
		else return false;
	}
}
