package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.User;
import main.java.util.DatabaseConnection;

public class UserDAO {

	 public boolean createUser(User user) {
	        String query = "INSERT INTO User (Username, Password, Email) VALUES (?, ?, ?)";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, user.getUsername());
	            statement.setString(2, user.getPassword());
	            statement.setString(3, user.getEmail());

	            int rowsInserted = statement.executeUpdate();
	            return rowsInserted > 0;

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	 
	 public boolean checkUsernameExists(String username) {
	        String query = "SELECT COUNT(*) FROM User WHERE Username = ?";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, username);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    return count > 0;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    public boolean checkEmailExists(String email) {
	        String query = "SELECT COUNT(*) FROM User WHERE Email = ?";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, email);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    return count > 0;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	    public boolean loginValidation(String username, String password) {
	        String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, username);
	            statement.setString(2, password); // Password should be hashed

	            try (ResultSet resultSet = statement.executeQuery()) {
	                return resultSet.next(); // If a record is found, credentials are valid
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public User getUserByUsernameAndPassword(String username, String password) {
	        User user = null;
	        String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, username);
	            statement.setString(2, password);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    user = new User(
	                            resultSet.getInt("UserID"),
	                            resultSet.getString("Username"),
	                            resultSet.getString("Password"),
	                            resultSet.getString("Email")
	                    );
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return user;
	    }
}
