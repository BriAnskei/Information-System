package main.java.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.model.*;
import main.java.util.DatabaseConnection;

public class MemberDAO {
	 public void addMember(Member member) throws SQLException, FileNotFoundException, IOException {
		 String query = "INSERT INTO Member (FirstName, LastName, Address, PhoneNumber, Email, ImageData) VALUES (?, ?, ?, ?, ?, ?)";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, member.getFirstName());
	            pstmt.setString(2, member.getLastName());
	            pstmt.setString(3, member.getAddress());
	            pstmt.setString(4, member.getPhoneNumber());
	            pstmt.setString(5, member.getEmail());
	            pstmt.setBytes(6, member.getImageData());
	            pstmt.executeUpdate();
	        }
	    }
	 
	
	 
	 
	  public List<Member> getAllMembers() throws SQLException {
		  List<Member> members = new ArrayList<>();
	        String query = "SELECT * FROM Member";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query);
	             ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Member member = new Member(resultSet.getInt("MemberID"), resultSet.getString("FirstName"), resultSet.getString("LastName"), resultSet.getString("Address"), resultSet.getString("PhoneNumber"), resultSet.getString("Email"), resultSet.getBytes("ImageData"));
	                members.add(member);
	            }
	        }
	        return members;
	    }
	

	  
	  public List<Member> searchMember(String input) {
		  List<Member> members = new ArrayList<>();
	        String query = "SELECT * FROM member WHERE MemberID = ? OR FirstName = ?";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, input);
	            statement.setString(2, input);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    Member member = new Member(
	                            resultSet.getInt("MemberID"),
	                            resultSet.getString("FirstName"),
	                            resultSet.getString("LastName"),
	                            resultSet.getString("Address"),
	                            resultSet.getString("PhoneNumber"),
	                            resultSet.getString("Email"),
	                            resultSet.getBytes("ImageData")
	                    );
	                    members.add(member);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return members;
	  }
	  
	  
	 
	  public void updateMember(Member member, int memberID) throws SQLException {
	        String query = "UPDATE member SET FirstName = ?, LastName = ?, Address = ?, PhoneNumber = ?, Email = ?, ImageData = ? WHERE MemberID = ?";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	       
	        	
	            statement.setString(1, member.getFirstName());
	            statement.setString(2, member.getLastName());
	            statement.setString(3, member.getAddress());
	            statement.setString(4, member.getPhoneNumber());
	            statement.setString(5, member.getEmail());
	            statement.setBytes(6, member.getImageData());
	            statement.setInt(7, memberID);
	         

	            statement.executeUpdate();
	        }
	    }
	  
	  
	  
	  public int countTotalMember() throws SQLException {
		  int totalMembers = 0;
		  String query = "SELECT COUNT(*) AS total_rows FROM member";
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
	  
	  
	  
	  public void DeleteMemberById(int memberId) throws SQLException {
		  String query = "DELETE FROM member WHERE MemberID = ?";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setInt(1, memberId);
	            statement.executeUpdate();
	        }
	  }
}
