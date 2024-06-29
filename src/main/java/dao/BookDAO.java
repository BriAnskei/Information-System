package main.java.dao;

import java.sql.*;

import main.java.model.*;
import main.java.util.DatabaseConnection;

public class BookDAO {
	public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO Book (Title, Author, Publisher, YearPublished, ISBN, Category, CopiesAvailable) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTittle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYearPublish());
            statement.setString(5, book.getIsbn());
            statement.setString(6, book.getCategory());
            statement.setInt(7, book.getCopiesAvailable());
            statement.executeUpdate();
        }
	}
}
