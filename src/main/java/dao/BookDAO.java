package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.java.model.*;
import main.java.util.DatabaseConnection;

public class BookDAO {
	public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO book (Title, Author, Publisher, YearPublished, ISBN, Category, CopiesAvailable, ImageData) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTittle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYearPublish());
            statement.setString(5, book.getIsbn());
            statement.setString(6, book.getCategory());
            statement.setInt(7, book.getCopiesAvailable());
            statement.setBytes(8, book.getImageData());
            statement.executeUpdate();
        }
	}
	
	public List<Book> getAllBooks() throws SQLException {
		  List<Book> books = new ArrayList<>();
	        String query = "SELECT * FROM book";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query);
	             ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Book book = new Book(resultSet.getInt("BookID"), resultSet.getString("Title"), resultSet.getString("Author"), resultSet.getString("Publisher"), resultSet.getInt("YearPublished"), resultSet.getString("ISBN"), resultSet.getString("Category"), resultSet.getInt("CopiesAvailable"), resultSet.getBytes("ImageData"));
	                books.add(book);
	            }
	        }
	        return books;
	}
	
	public boolean deductBookQuantity(int bookId) throws SQLException {
		String query = "UPDATE book SET CopiesAvailable = CopiesAvailable - 1 WHERE BookID = ? AND CopiesAvailable > 0";
		try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)){
				statement.setInt(1, bookId);
				boolean isUpdated = statement.executeUpdate() > 0;
				if(!isUpdated) System.out.println("Error updating quantities");
				return isUpdated;		
		}
	}
	
	public boolean isBookAvailable(String bookID) throws SQLException {
	    String query = "SELECT CopiesAvailable FROM book WHERE BookID = ?";
	    try (Connection connection = DatabaseConnection.getConnection();
	    	PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, bookID);
	        ResultSet rs = statement.executeQuery();
	        return rs.next() && rs.getInt("CopiesAvailable") > 0;
	    }
	}
	
	public List<Book> searchBook(String input) {
		List<Book> books = new ArrayList<>();
		String query = "SELECT * FROM book WHERE BookID = ? OR Title = ?";
		 try (Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query))  {
			 statement.setString(1, input);
			 statement.setString(2, input);
			 try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                 Book book = extractBook(resultSet);
	                 books.add(book);
	                }
	            }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return books;
	}
	
	  public static Book extractBook(ResultSet resultSet) throws SQLException {
	        return new Book(
	                resultSet.getInt("BookID"),
	                resultSet.getString("Title"),
	                resultSet.getString("Author"),
	                resultSet.getString("Publisher"),
	                resultSet.getInt("YearPublished"),
	                resultSet.getString("ISBN"),
	                resultSet.getString("Category"),
	                resultSet.getInt("CopiesAvailable"),
	                resultSet.getBytes("ImageData")
	        );
	    }
	
	   public List<Book> getBooksByIds(Set<Integer> bookIds) {
	        List<Book> books = new ArrayList<>();
	        String query = "SELECT * FROM book WHERE BookID IN (" +
	                bookIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            int index = 1;
	            for (Integer id : bookIds) {
	                statement.setInt(index++, id);
	            }

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    books.add(extractBook(resultSet));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return books;
	    }

	 public int countTotalBooks() throws SQLException {
		  int totalBooks = 0;
		  String query = "SELECT COUNT(*) AS total_rows FROM book";
		  try (Connection connection = DatabaseConnection.getConnection();
		       PreparedStatement statement = connection.prepareStatement(query); 
			  ResultSet resultSet = statement.executeQuery()) {
				  if(resultSet.next()) {
					  totalBooks = resultSet.getInt("total_rows"); 
			  }
			  
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  return totalBooks;
	  }
	 
	 public List<Book> getLatesBooks() throws SQLException {
		 List<Book> books = new ArrayList<>();
		 String query = "SELECT * FROM book ORDER BY BookID DESC LIMIT 5";
		  try (Connection connection = DatabaseConnection.getConnection();
		       PreparedStatement statement = connection.prepareStatement(query); 
			  ResultSet resultSet = statement.executeQuery()) {
			  while(resultSet.next()) {
				  int bookId = resultSet.getInt("BookID");
	                String title = resultSet.getString("Title");
	                String author = resultSet.getString("Author");
	                String publisher = resultSet.getString("Publisher");
	                int yearPublished = resultSet.getInt("YearPublished");
	                String isbn = resultSet.getString("ISBN");
	                String category = resultSet.getString("Category");
	                int copiesAvailable = resultSet.getInt("CopiesAvailable");
	                byte[] imageData = resultSet.getBytes("ImageData");

	                // Create a Book object and add it to the list
	                Book book = new Book(bookId, title, author, publisher, yearPublished, isbn, category, copiesAvailable, imageData);
	                books.add(book);
			  }
		  } catch (SQLException e) {
	            e.printStackTrace();
	      }
		  return books;
	 }
	 
	 public boolean updateBook(int bookId, Book book) {
		 System.out.println("Boodao: "  + book);
		    String query = "UPDATE book SET Title = ?, Author = ?, Publisher = ?, YearPublished = ?, ISBN = ?, Category = ?, CopiesAvailable = ?, ImageData = ? WHERE BookID = ?";
		    try (Connection connection = DatabaseConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setString(1, book.getTittle());
		        statement.setString(2, book.getAuthor());
		        statement.setString(3, book.getPublisher());
		        statement.setInt(4, book.getYearPublish());
		        statement.setString(5, book.getIsbn());
		        statement.setString(6, book.getCategory());
		        statement.setInt(7, book.getCopiesAvailable());
		        statement.setBytes(8, book.getImageData());
		        statement.setInt(9, bookId);
		        
		        int rowsUpdated = statement.executeUpdate();
		        return rowsUpdated > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	 public boolean deleteBookById(int bookId) {
		    String query = "DELETE FROM book WHERE BookID = ?";
		    try (Connection connection = DatabaseConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setInt(1, bookId);
		        int rowsDeleted = statement.executeUpdate();
		        return rowsDeleted > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	   public boolean updateCopiesAvailable(int bookId, int quantity) throws SQLException {
	        String query = "UPDATE book SET CopiesAvailable = CopiesAvailable + ? WHERE BookID = ?";
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	             
	            statement.setInt(1, quantity);
	            statement.setInt(2, bookId);
	            
	            return statement.executeUpdate() > 0;
	        }
	   }
	   
	   public boolean validateBookExists(String input) {
	        String query = "SELECT COUNT(*) FROM book WHERE BookID = ?";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, input);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    System.out.println("Book " + input + (count > 0));
	                    return count > 0;  // Returns true if the book exists, otherwise false
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
}
