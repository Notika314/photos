package model;
import java.io.*;
import java.util.ArrayList;

public class Picture implements Serializable{
	public String path;
	public String caption;
	private boolean locationTagIsSet=false;
	public Album album;
	public User user;
	ArrayList<Tag> tags;
	public Picture(Album album, String path, String caption) {
		this.path = path;
		this.album = album;
		this.user = album.user;
		this.tags = new ArrayList<Tag>();
		if (caption.length()<=2000) this.caption = caption;
		else this.caption = caption.substring(0,2000);
	}
	private boolean tagExists(String type,String value) {
		for (int i=0;i<tags.size();i++) {
			if (tags.get(i).type.equals(type) && tags.get(i).value.contentEquals(value)) return true;
		}
		return false;
	}
	
	public void recaption(String newCaption) {
		this.caption = newCaption.substring(0,2000);
	}
	
	public void addTag(String type,String value) {
		if (type.equals("location") && locationTagIsSet) {
			for(int i=0;i<this.tags.size();i++) {
				if (this.tags.get(i).type.equals("location")) this.tags.get(i).value = value;
			}	// handle changing location
		}
		if (!tagExists(type,value)) {
			tags.add(new Tag(type,value));
			if (type.equals("location")) locationTagIsSet = true;
		}
		if (!Tag.hasaType(type)) {
			System.out.println("no such tag type exists");
			return;
		}
	}
	
	public void deleteTag(String tag) {
		for (int i=0;i<tags.size();i++) {
			if (tags.get(i).equals(tag)) tags.remove(i);
		}
	}
	
	public boolean equals(Object o) {
		if (!( o instanceof Picture)) return false;
		Picture p = (Picture) o;
		if (p.path.equals(this.path)) return true;
		else return false;
	}
}
