package model;
import java.util.Date;
import java.util.ArrayList;
/**
 * This class implements the searchfor photos in 
 * an album, or all user's photos by date range, or tags
 * @author Natalia Bryzhatenko nb631
 * @author Christopher Taglieri cat 197
 *
 */
public class Search {
	static ArrayList<Picture> searchResult;
	/**
	 * 
	 * @param a - album where the pictures are searchd
	 * @param min - the earliest date within the range
	 * @param max - the latest date within the range
	 * @return ArrayList of all pictures in Album a withing the Date range
	 */
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
	
	static ArrayList<Picture> searchByTagInAll(String name, String value) {
		searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.tagExists(name,value)) Search.searchResult.add(p);
			}
		}
		return searchResult;
	}
	
	static ArrayList<Picture> searchByTagInAlbum(Album a, String name, String value) {
		searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			Picture p = a.pictures.get(i);
			if (p.tagExists(name,value)) Search.searchResult.add(a.pictures.get(i));
		}
		return searchResult;
	}
	
	static ArrayList<Picture> searchByTagsConjuctionInAll(String name1, String value1,String name2, String value2) {
		searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.tagExists(name1,value1) && p.tagExists(name2,value2)) Search.searchResult.add(p);
			}
		}
		return searchResult;
	}
	
	static ArrayList<Picture> searchByTagsConjunctionInAlbum(Album a, String name1, String value1,String name2, String value2) {
		searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			Picture p = a.pictures.get(i);
			if (p.tagExists(name1,value1) && p.tagExists(name2, value2)) Search.searchResult.add(a.pictures.get(i));
		}
		return searchResult;
	}
	
	static ArrayList<Picture> searchByTagsDisjuctionInAll(String name1, String value1,String name2, String value2) {
		searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.tagExists(name1,value1) || p.tagExists(name2,value2)) Search.searchResult.add(p);
			}
		}
		return searchResult;
	}
	
	static ArrayList<Picture> searchByTagsDisjunctionInAlbum(Album a, String name1, String value1,String name2, String value2) {
		searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			Picture p = a.pictures.get(i);
			if (p.tagExists(name1,value1) || p.tagExists(name2, value2)) Search.searchResult.add(a.pictures.get(i));
		}
		return searchResult;
	}
	
}
