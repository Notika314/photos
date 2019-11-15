package model;
import java.io.*;
import java.util.ArrayList;

public class Picture implements Serializable{
	public String path;
	public String caption;
	public User user;
	ArrayList<String> tags;
	public Picture(User user, String path, String caption) {
		this.user = user;
		this.path = path;
		this.tags = new ArrayList<String>();
		if (caption.length()<=2000) this.caption = caption;
		else this.caption = caption.substring(0,2000);
	}
	private boolean tagExists(String tag) {
		for (int i=0;i<tags.size();i++) {
			if (tags.get(i).contentEquals(tag)) return true;
		}
		return false;
	}
	
	public void recaption(String newCaption) {
		this.caption = newCaption.substring(0,2000);
	}
	
	public void addTag(String tag) {
		if (!tagExists(tag)) tags.add(tag);
	}
	
	public void deletTag(String tag) {
		for (int i=0;i<tags.size();i++) {
			if (tags.get(i).contentEquals(tag)) tags.remove(i);
		}
	}
	
	public boolean equals(Object o) {
		if (!( o instanceof Picture)) return false;
		Picture p = (Picture) o;
		if (p.path.equals(this.path)) return true;
		else return false;
	}
}
