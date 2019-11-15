package model;
import java.io.*;
import java.util.ArrayList;

public class Picture implements Serializable{
	public String path;
	public User user;
	ArrayList<String> tags;
	public Picture(User user, String path) {
		this.user = user;
		this.path = path;
		this.tags = new ArrayList<String>();
	}
	
	public boolean equals(Object o) {
		if (!( o instanceof Picture)) return false;
		Picture p = (Picture) o;
		if (p.path.equals(this.path)) return true;
		else return false;
	}
}
