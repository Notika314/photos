package model;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
/**
 * Class that implements logic of how Pictures are created,
 * how new tags and captions for pictures are added and modified
 * @author Christopher Taglieri cat 197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class Picture implements Serializable{
	/**
	 * file where picture is stored on machine
	 */
	public File file; 
	/**
	 * User's description of the picture
	 */
	public String caption;
	/**
	 * Date when picture was last modified
	 */
	public Date date;
	/**
	 * String representation of date
	 */
	public String createdAt;
	/**
	 * boolean value that is true if locationTag of photo is set
	 */
	public boolean locationTagIsSet=false;
	/**
	 * Album in which picture is located
	 */
	public Album album;
	/**
	 * User that created the picture
	 */
	public User user;
	/**
	 * All the tags of the picture
	 */
	public ArrayList<Tag> tags;
	/**
	 * Static field ,currently selected picture
	 */
	public static Picture curr;
	/**
	 * Long integer used for serialization and deserialization purposes
	 */
	static final long serialVersionUID = 1L;
	
	/**
	 * Initializes Picture with given Album and File
	 * @param album Album in which picture is created
	 * @param file File in which picture is located on machine
	 * @param caption Caption of the picture, initialized to empty string
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
	/**
	 * Initializes a Copy of a Picture such that they are not coupled.
	 * @param pic
	 * @param album
	 */
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
	
	/**
	 * Determines if given tag exists
	 * @param type Type of the Tag
	 * @param value Value of the Tag
	 * @return true if tag with given name and value exists,
	 * false otherwise
	 */
	protected boolean tagExists(String type,String value) {
		for (int i=0;i<tags.size();i++) {
			if (tags.get(i).type.toLowerCase().equals(type.toLowerCase()) 
					&& tags.get(i).value.toLowerCase().contentEquals(value.toLowerCase())) return true;
		}
		return false;
	}
	/**
	 * changes caption of the picture
	 * @param newCaption new caption to be set 
	 */
	public void recaption(String newCaption) {
		this.caption = newCaption;
	}
	/**
	 * Adds new tag with given name and value
	 * @param type Name of tag to be added
	 * @param value Value of the tag to be added
	 */
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
	/**
	 * Deletes the tag 
	 * @param tag Tag to be deleted
	 */
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
