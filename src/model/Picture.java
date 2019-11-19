package model;
import java.io.*;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class Picture implements Serializable{
	public File file; 
	public String caption;
	private boolean locationTagIsSet=false;
	public Album album;
	public User user;
	public ArrayList<Tag> tags;
	public static Picture curr;
	
	/**
	 * 
	 * @param album
	 * @param file
	 * @param caption
	 * @throws IOException 
	 */
	public Picture(Album album, File file) throws IOException {
		this.file = file;
		this.album = album;
		this.user = album.user;
		this.tags = new ArrayList<Tag>();
		/*
		if (caption.length()<=2000) this.caption = caption;
		else this.caption = caption.substring(0,2000);*/
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
		if (p.file.equals(this.file)) return true;
		else return false;
	}
}
