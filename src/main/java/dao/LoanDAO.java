package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.model.*;
import main.java.service.*;
import main.java.util.*;

public class LoanDAO {
	public boolean issueBook(Loan loan) throws SQLException {
		BookDAO bookDAO = new BookDAO();
		 String query = "INSERT INTO Loan (BookID, MemberID, IssueDate, DueDate) VALUES (?, ?, ?, ?)";

		  boolean isSuccesFul = bookDAO.deductBookQuantity(loan.getBookID());
		  if(!isSuccesFul) {
			  return false;
		  }
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, loan.getBookID());
	            statement.setInt(2, loan.getMemberID());
	            statement.setDate(3, loan.getIssueDate() != null ? new java.sql.Date(loan.getIssueDate().getTime()) : null);
	            statement.setDate(4, loan.getDueDate() != null ? new java.sql.Date(loan.getDueDate().getTime()) : null);
	            
	            boolean isInserted = statement.executeUpdate() > 0;  
	            return isInserted;
	        }   
	}
	
	 public boolean returnBook(Loan loan) throws SQLException {
	        String query = "UPDATE loan SET ReturnDate = ? WHERE LoanID = ?";
	        BookService service = new BookService();
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	             
	            statement.setDate(1, new java.sql.Date(loan.getReturnDate().getTime()));
	            statement.setInt(2, loan.getLoanID());
	            
	            boolean loanUpdated = statement.executeUpdate() > 0;
	            boolean bookUpdated = service.returnBook(loan.getBookID());
	            
	            return loanUpdated && bookUpdated;
	        }
	    }
	 
	 // Get all the returned loans.
	 public List<Loan> getLoansWithReturnedDate() {
	        List<Loan> loans = new ArrayList<>();
	        String query = "SELECT * FROM Loan WHERE ReturnDate IS NOT NULL";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query);
	             ResultSet resultSet = statement.executeQuery()) {

	            while (resultSet.next()) {
	                Loan loan = new Loan(
	                        resultSet.getInt("LoanID"),
	                        resultSet.getInt("BookID"),
	                        resultSet.getInt("MemberID"),
	                        resultSet.getDate("IssueDate"),
	                        resultSet.getDate("DueDate"),
	                        resultSet.getDate("ReturnDate")
	                );
	                loans.add(loan);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return loans;
	    }
	
	  // Get all books that are not returned.
	  public List<Loan> getAllLoans() throws SQLException {
	        String query = "SELECT * FROM loan WHERE ReturnDate IS NULL";
	  
	        List<Loan> loans = new ArrayList<>();

	        try (Connection connection = DatabaseConnection.getConnection();
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {

	            while (resultSet.next()) {
	                Loan loan = new Loan();
	                loan.setLoanID(resultSet.getInt("LoanID"));;
	                loan.setBookID(resultSet.getInt("BookID"));
	                loan.setMemberID(resultSet.getInt("MemberID"));
	                loan.setIssueDate(resultSet.getDate("IssueDate"));
	                loan.setDueDate(resultSet.getDate("DueDate"));
	                loan.setReturnDate(resultSet.getDate("ReturnDate"));

	                loans.add(loan);
	            }
	        }

	        return loans;
	    }
	  
	 
	  public Loan getLoanById(String loanId) throws SQLException {
	        String query = "SELECT * FROM loan WHERE loanId = ?";
	        Loan loan = null;

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, loanId);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    loan = new Loan();
	                    loan.setLoanID(resultSet.getInt("LoanID"));
	                    loan.setMemberID(resultSet.getInt("MemberID"));
	                    loan.setBookID(resultSet.getInt("BookID"));
	                    loan.setIssueDate(resultSet.getDate("IssueDate"));
	                    loan.setDueDate(resultSet.getDate("DueDate"));
	                    loan.setReturnDate(resultSet.getDate("ReturnDate"));
	                }
	            }
	        }

	        return loan;
	    }
	 
	  // User circulation table
//	   public List<Book> getAllIssuedBooksByMemberId(int memberId) {
//	        List<Book> books = new ArrayList<>();
//	        String query = "SELECT b.BookID, b.Title, b.Author, b.Publisher, b.YearPublished, b.ISBN, b.Category, b.CopiesAvailable, b.ImageData " +
//	                       "FROM loan l " +
//	                       "JOIN book b ON l.BookID = b.BookID " +
//	                       "WHERE l.MemberID = ? AND l.ReturnDate IS NULL";
//
//	        try (Connection connection = DatabaseConnection.getConnection();
//	             PreparedStatement statement = connection.prepareStatement(query)) {
//
//	            // Bind parameters
//	            statement.setInt(1, memberId);
//
//	            // Execute query and process results
//	            try (ResultSet resultSet = statement.executeQuery()) {
//	                while (resultSet.next()) {
//	                    Book book = mapResultSetToBook(resultSet);
//	                    books.add(book);
//	                }
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            // Handle exceptions properly based on your application's needs
//	        }
//	        return books;
//	    }
//	    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
//	        return new Book(
//	            resultSet.getInt("BookID"),
//	            resultSet.getString("Title"),
//	            resultSet.getString("Author"),
//	            resultSet.getString("Publisher"),
//	            resultSet.getInt("YearPublished"),
//	            resultSet.getString("ISBN"),
//	            resultSet.getString("Category"),
//	            resultSet.getInt("CopiesAvailable"),
//	            resultSet.getBytes("ImageData")
//	        );
//	    }
	    
	    // Check issued book that are not returned
	    public int countIssuedBooksByMemberId(int memberId) {
	        int count = 0;
	        String query = "SELECT COUNT(*) FROM loan WHERE MemberID = ? AND ReturnDate IS NULL";
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            
	            // Bind parameters
	            statement.setInt(1, memberId);
	            
	            // Execute query
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    count = resultSet.getInt(1);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle exceptions properly based on your application's needs
	        }
	        
	        return count;
	    }
	  
	    // Return the unreturned books by member iD.
	  public List<Loan> getLoansByMemberId(int memberId) {
	        List<Loan> loans = new ArrayList<>();
	        String query = "SELECT * FROM loan WHERE MemberID = ? AND ReturnDate IS NULL";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            
	            statement.setInt(1, memberId);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    Loan loan = mapResultSetToLoan(resultSet);
	                    loans.add(loan);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle exceptions properly based on your application's needs
	        }

	        return loans;
	    }
	    private Loan mapResultSetToLoan(ResultSet resultSet) throws SQLException {
	        return new Loan(
	            resultSet.getInt("LoanID"),
	            resultSet.getInt("BookID"),
	            resultSet.getInt("MemberID"),
	            resultSet.getDate("IssueDate"),
	            resultSet.getDate("DueDate"),
	            resultSet.getDate("ReturnDate")
	        );
	    }
	  
	  // View for admin only
	  public int countTotalLoans() throws SQLException {
		  int totalMembers = 0;
		  String query = "SELECT COUNT(*) AS total_rows FROM loan";
		  try (Connection connection = DatabaseConnection.getConnection();
		       PreparedStatement statement = connection.prepareStatement(query); 
			  ResultSet resultSet = statement.executeQuery()) {
				  if(resultSet.next()) {
					  totalMembers = resultSet.getInt("total_rows"); 
			  }
			  
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  return totalMembers;
	  }
	  
	  public List<Loan> getLoansByMemberIdWithReturnDate(int memberId) {
	        List<Loan> loans = new ArrayList<>();
	        String query = "SELECT * FROM Loan WHERE MemberID = ? AND ReturnDate IS NOT NULL";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setInt(1, memberId);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    Loan loan =  mapResultSetToLoan(resultSet);
	                    loans.add(loan);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return loans;
	    }
	  
	  
}
