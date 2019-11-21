package model;
import java.util.Date;
import java.util.ArrayList;
import java.util.function.Predicate;
/**
 * This class implements the search for photos in 
 * an album, or all user's photos by date range, or tags
 * @author Natalia Bryzhatenko nb631
 * @author Christopher Taglieri cat 197
 *
 */
public class Search {
	public static ArrayList<Picture> searchResult;

	
	/**
	 * 
	 * @param a - album where the pictures are searched
	 * @param startDate - the earliest date within the range
	 * @param endDate - the latest date within the range
	 * @return ArrayList of all pictures in Album within the Date range
	 */
	public static ArrayList<Picture> searchByDateRangeInAlbum(Album a, Date startDate,Date endDate) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			Date picDate = a.pictures.get(i).date;
			if (picDate.compareTo(startDate)>=0 && picDate.compareTo(endDate)<=0) Search.searchResult.add(a.pictures.get(i));
		}
		return Search.searchResult;
	}
	/**
	 * 
	 * @param startDate - the earliest date within the range
	 * @param endDate - the latest date within the range
	 * @return ArrayList of all user's pictures within the Date range
	 */
	public static ArrayList<Picture> searchByDateRangeInAll(Date startDate, Date endDate) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.date.compareTo(startDate)>=0 && p.date.compareTo(endDate)<=0) Search.searchResult.add(p);
			}
		}
		return Search.searchResult;
	}
	/**
	 * 
	 * @param name - name of the tag search 
	 * @param value - value of the tag searched
	 * @return ArrayList of all user's pictures that have the tag with given name and value
	 */
	public static ArrayList<Picture> searchByTagInAll(String name, String value) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.tagExists(name,value)) Search.searchResult.add(p);
			}
		}
		return Search.searchResult;
	}
	/**
	 * 
	 * @param a - name of the album
	 * @param name - name of the tag search 
	 * @param value - value of the tag searched
	 * @return ArrayList of all pictures in Album a that have the tag with given name and value
	 */
	public static ArrayList<Picture> searchByTagInAlbum(Album a, String name, String value) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			Picture p = a.pictures.get(i);
			if (p.tagExists(name,value)) Search.searchResult.add(a.pictures.get(i));
		}
		return Search.searchResult;
	}
	/**
	 * 
	 * @param name1 - first name of the tag searched
	 * @param value1 - first value of the tag searched
	 * @param name2 - second name of the tag search 
	 * @param value2 - second value of the tag searched
	 * @return ArrayList of all user's pictures that have the both tags with given names and values
	 */
	public static ArrayList<Picture> searchByTagsConjunctionInAll(String name1, String value1,String name2, String value2) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.tagExists(name1,value1) && p.tagExists(name2,value2)) Search.searchResult.add(p);
			}
		}
		return Search.searchResult;
	}
	/**
	 * 
	 * @param a - name of the album
	 * @param name1 - first name of the tag searched
	 * @param value1 - first value of the tag searched
	 * @param name2 - second name of the tag search 
	 * @param value2 - second value of the tag searched
	 * @return ArrayList of all user's pictures that have the both tags with given names and values
	 
	 */
	public static ArrayList<Picture> searchByTagsConjunctionInAlbum(Album a, String name1, String value1,String name2, String value2) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			Picture p = a.pictures.get(i);
			if (p.tagExists(name1,value1) && p.tagExists(name2, value2)) Search.searchResult.add(a.pictures.get(i));
		}
		return Search.searchResult;
	}
	/**
	 * 
	 * @param name1 - first name of the tag searched
	 * @param value1 - first value of the tag searched
	 * @param name2 - second name of the tag search 
	 * @param value2 - second value of the tag searched
	 * @return ArrayList of all user's pictures that have at least one of the tags with given names and values
	 
	 */
	public static ArrayList<Picture> searchByTagsDisjuctionInAll(String name1, String value1,String name2, String value2) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<User.curr.userAlbums.size();i++) {
			Album a = User.curr.userAlbums.get(i);
			for (int j=0;j<a.pictures.size();j++) {
				Picture p = a.pictures.get(j);
				if (p.tagExists(name1,value1) || p.tagExists(name2,value2)) Search.searchResult.add(p);
			}
		}
		return Search.searchResult;
	}
	/**
	 * 
	 * @param a - name of the album
	 * @param name1 - first name of the tag searched
	 * @param value1 - first value of the tag searched
	 * @param name2 - second name of the tag search 
	 * @param value2 - second value of the tag searched
	 * @return ArrayList of all user's pictures that have at least one of the tags with given names and values
	 
	 */
	public static ArrayList<Picture> searchByTagsDisjunctionInAlbum(Album a, String name1, String value1,String name2, String value2) {
		Search.searchResult = new ArrayList<Picture>();
		for (int i=0;i<a.pictures.size();i++) {
			Picture p = a.pictures.get(i);
			if (p.tagExists(name1,value1) || p.tagExists(name2, value2)) Search.searchResult.add(a.pictures.get(i));
		}
		return Search.searchResult;
	}
	
	
}
