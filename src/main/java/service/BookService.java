package main.java.service;

import main.java.model.*;
import main.java.util.ImageUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import main.java.dao.*;

public class BookService {
	
	private BookDAO bookDAO;
	
	public BookService() {
		this.bookDAO = new BookDAO();
	}
	
	public void add(String tittle, String author, String publisher, int yearPublish, String isbn, String category, int quanitybook, File selectedFile) throws FileNotFoundException, IOException, SQLException {
		Book book = new Book();
		book.setTittle(tittle);
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setYearPublish(yearPublish);
		book.setisbn(isbn);
		book.setCategory(category);
		book.setCopiesAvailable(quanitybook);
		
		// Initialize selectedFile into inputStream and convert to ByteArray.
		if (selectedFile != null) {
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                byte[] imageBytes = ImageUtil.convertInputStreamToByteArray(fis);
                book.setImageData(imageBytes);
            }
        }
		
		bookDAO.addBook(book);
	}
	
	public boolean checkQuantity(String bookId) {
		try {
			return bookDAO.isBookAvailable(bookId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Book> getBooks() throws SQLException {
		return bookDAO.getAllBooks();
	}
	
	public List<Book> searchedBook(String input) {
		return bookDAO.searchBook(input);
	}
	
	 public Map<Integer, Book> getBooksByIds(Set<Integer> bookIds) {
	        List<Book> books = bookDAO.getBooksByIds(bookIds);
	        return books.stream().collect(Collectors.toMap(Book::getBookID, book -> book));
	    }
	
	public Boolean Update(int bookId, Book book) {
		return bookDAO.updateBook(bookId, book);
	}
	
	public Boolean Delete(int bookId) {
		return bookDAO.deleteBookById(bookId);
	}
	
	public boolean returnBook(int bookId) throws SQLException {
		return bookDAO.updateCopiesAvailable(bookId, 1);
	}
	
	public boolean bookValidation(String bookId) {
		return bookDAO.validateBookExists(bookId);
	}
}
