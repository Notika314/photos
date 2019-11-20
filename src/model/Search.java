package model;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;

public class Search {
	static ArrayList<Picture> searchResult;
	
	static ArrayList<Picture> searchByDateRangeInAlbum(Album a, Date min,Date max) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			Date picDate = a.pictures.get(i).date;
			if (picDate.compareTo(min)>=0 && picDate.compareTo(max)<=0) Search.searchResult.add(a.pictures.get(i));
		}
		return searchResult;
	}
	
	static ArrayList<Picture> searchByDateRangeInAll(Date min, Date max) {
		searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.date.compareTo(min)>=0 && p.date.compareTo(max)<=0) Search.searchResult.add(p);
			}
		}
		return searchResult;
	}
}
