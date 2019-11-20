package model;
import java.io.*;
import java.util.ArrayList;

public class Tag implements Serializable, Comparable<Tag> {
	public String type;
	public String value;
	public Picture pic;
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
	
	public boolean equals(Object o) {
		if (!( o instanceof Tag)) return false;
		Tag t = (Tag) o;
		if (t.type.toLowerCase().equals(this.type.toLowerCase())
				&& t.value.toLowerCase().equals(this.value.toLowerCase())) return true;
		else return false;
	}

	public int compareTo(Tag o) {
		if (this.type.toLowerCase().compareTo(o.type.toLowerCase()) == 0) {
			return this.value.toLowerCase().compareTo(o.value.toLowerCase());
		}
		else {
			return this.type.toLowerCase().compareTo(o.type.toLowerCase());
		}
	}
	
	public String toString() {
		return this.type.toUpperCase()+": "+this.value;
	}
}
