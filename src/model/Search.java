package model;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;

public class Search {
	static ArrayList<Picture> searchResult;
	
	static ArrayList<Picture> searchByDateInAllPhotos(Date date) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.date.equals(date)) Search.searchResult.add(p);
			}
		}
		return searchResult;
	}
	
	static ArrayList<Picture> searchByDateInAnAlbum(Album a, Date date) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			if (a.pictures.get(i).date.equals(date)) Search.searchResult.add(a.pictures.get(i));
		}
		return searchResult;
	}
	
}
