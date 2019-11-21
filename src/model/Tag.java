package model;
import java.io.*;
import java.util.ArrayList;
/**
 * Class that implements the logic of creating , comparing 
 * and printing tags
 * @author Christopher Taglieri cat197
 * @author Natalia Bryzhatenko nb631
 *
 */
public class Tag implements Serializable, Comparable<Tag> {
	/**
	 * Type of tag
	 */
	public String type;
	/**
	 * Value of tag
	 */
	public String value;
	/**
	 * Picture that tag is defined on
	 */
	public Picture pic;
	/**
	 * Initializes Tag object with given type, value and picture
	 * @param type Type of tag
	 * @param value Value of tag
	 * @param pic Picture that tag is defined on
	 */
	public Tag(String type,String value, Picture pic) {
		this.type = type;
		this.value = value;
		this.pic = pic;
	}
	/*
	public static boolean hasaType(String aType) {
		for (int i=0;i<Tag.tagTypes.size();i++) {
			if (Tag.tagTypes.get(i).equals(aType)) return true;
		}
		return false;
	}
	public static void createNewTagType(String newType) {
		for (int i=0;i<tagTypes.size();i++) {
			if (tagTypes.get(i).equals(newType)) return;
		}
		tagTypes.add(newType);
	}
	*/
	/**
	 * Overrides equals method of Object class, compares tags by value and type
	 */
	public boolean equals(Object o) {
		if (!( o instanceof Tag)) return false;
		Tag t = (Tag) o;
		if (t.type.toLowerCase().equals(this.type.toLowerCase())
				&& t.value.toLowerCase().equals(this.value.toLowerCase())) return true;
		else return false;
	}
	/**
	 * Implements compareTo method defined in Comparable interface
	 * compares by value if types are equal, otherwise compares by type
	 */
	public int compareTo(Tag o) {
		if (this.type.toLowerCase().compareTo(o.type.toLowerCase()) == 0) {
			return this.value.toLowerCase().compareTo(o.value.toLowerCase());
		}
		else {
			return this.type.toLowerCase().compareTo(o.type.toLowerCase());
		}
	}
	/**
	 * Overwrites toString method of Object class
	 */
	public String toString() {
		return this.type.toUpperCase()+": "+this.value;
	}
}
