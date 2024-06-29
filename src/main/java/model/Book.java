package main.java.model;

public class Book {
	private int bookID;
	private String tittle;
	private String author;
	private String publisher;
	private int yearPublish;
	private String isbn;
	private String category;
	private int copiesAvailable;
	
	// Constructor
	public Book() {}
	
	
	public Book(int bookID,  String tittle, String author, String publisher, int yearPublish,  String isbn, String category, int copiesAvailable) {
		this.bookID = bookID;
		this.tittle = tittle;
		this.author = author;
		this.publisher = publisher;
		this.yearPublish = yearPublish;
		this.isbn = isbn;
		this.category = category;
		this.copiesAvailable = copiesAvailable;
	}
	
	public int getBookID() {
		return bookID;
	}
	
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	
	public String getTittle() {
		return tittle;
	}
	
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public int getYearPublish() {
		return yearPublish;
	}
	
	public void  setYearPublish(int yearPublish) {
		this.yearPublish = yearPublish;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setisbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getCopiesAvailable() {
		return copiesAvailable;
	}
	
	public void setCopiesAvailable(int copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
}
