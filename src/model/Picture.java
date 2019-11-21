package model;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
public class Picture implements Serializable{
	public File file; 
	public String caption;
	Date date;
	String createdAt;
	public boolean locationTagIsSet=false;
	public Album album;
	public User user;
	public ArrayList<Tag> tags;
	public static Picture curr;
	static final long serialVersionUID = 1L;
	
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
		this.date= new Date(file.lastModified());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND,0);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1; // Jan = 0, dec = 11
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); 
		this.createdAt = month+"/"+dayOfMonth+"/"+year;
		this.user = album.user;
		this.tags = new ArrayList<Tag>();
		this.caption = "";
		System.out.println("Date is "+this.date);
		System.out.println("Created at is: "+this.createdAt);
	}
	public Picture(Picture pic, Album album) {
		this.file = pic.file;
		this.caption = pic.caption;
		this.date = pic.date;
		this.createdAt = pic.createdAt;
		this.album = album;
		this.user = pic.user;
		this.tags = new ArrayList<Tag>();
		for (Tag tag : pic.tags) {
			if (tag.type.equals("location")) {
				this.locationTagIsSet = true;
			}
			this.tags.add(new Tag(tag.type, tag.value, this));
		}
	}
	protected boolean tagExists(String type,String value) {
		for (int i=0;i<tags.size();i++) {
			if (tags.get(i).type.toLowerCase().equals(type.toLowerCase()) 
					&& tags.get(i).value.toLowerCase().contentEquals(value.toLowerCase())) return true;
		}
		return false;
	}
	
	public void recaption(String newCaption) {
		this.caption = newCaption;
	}
	
	public void addTag(String type,String value) {
		if (type.equals("location") && locationTagIsSet) {
			for(int i=0;i<this.tags.size();i++) {
				if (this.tags.get(i).type.equals("location")) this.tags.get(i).value = value;
			}	// handle changing location
		}
		if (!tagExists(type,value)) {
			//tags.add(new Tag(type,value));
			if (type.equals("location")) locationTagIsSet = true;
		}/*
		if (!Tag.hasaType(type)) {
			System.out.println("no such tag type exists");
			return;
		}*/
	}
	
	public void deleteTag(String tag) {
		for (int i=0;i<tags.size();i++) {
			if (tags.get(i).equals(tag)) tags.remove(i);
		}
	}
	/*
	public Picture copy() {
		Picture temp = new Picture();
		return temp;
	}
	*/
	/*
	public boolean equals(Object o) {
		if (!( o instanceof Picture)) return false;
		Picture p = (Picture) o;
		if (p.file.equals(this.file)) return true;
		else return false;
	}*/
}
