package main.java.dao;

import java.sql.*;

import main.java.model.*;
import main.java.util.*;

public class LoanDAO {
	public void issueBook(Loan loan) throws SQLException {
		 String query = "INSERT INTO Loan (BookID, MemberID, IssueDate, DueDate) VALUES (?, ?, ?, ?)";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, loan.getBookID());
	            statement.setInt(2, loan.getMemberID());
	            statement.setDate(3, new java.sql.Date(loan.getIssueDate().getTime()));
	            statement.setDate(4, new java.sql.Date(loan.getDueDate().getTime()));
	            statement.executeUpdate();
	        }
	}
	
	  public void returnBook(int loanID, Date returnDate) throws SQLException {
	        String query = "UPDATE Loan SET ReturnDate = ? WHERE LoanID = ?";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setDate(1, new java.sql.Date(returnDate.getTime()));
	            
	            statement.executeUpdate();
	        }
	    }
}
