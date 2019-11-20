package model;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;

public class Search {
	static ArrayList<Picture> searchResult;
	
	static ArrayList<Picture> searchByDateInAllPhotos(Date date) {
		searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
//				if (p.date.equals(date)) searchResult.add(p);
			}
		}
		return searchResult;
	}
}
