package model;
import java.io.*;
import java.util.ArrayList;

public class Tag implements Serializable {
	public String type;
	public String value;
	static ArrayList<String> tagTypes = new ArrayList<String>();
	public Tag(String type,String value) {
		if (tagTypes.size()==0) {
			tagTypes.add("#");
			tagTypes.add("@");
			tagTypes.add("location");
		}
		this.type = type;
		this.value = value;
	}
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
	
	public boolean equals(Object o) {
		if (!( o instanceof Tag)) return false;
		Tag t = (Tag) o;
		if (t.type.equals(this.type)&&t.value.equals(this.value)) return true;
		else return false;
	}
}
