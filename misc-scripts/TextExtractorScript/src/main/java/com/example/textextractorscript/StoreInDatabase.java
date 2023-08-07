package com.example.textextractorscript;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreInDatabase {
    public static void storeTextInDatabase(String pdfName, String textContent) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pdf_database";
        String user = "root";
        String password = "compile1";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO pdf_data (pdf_name, text_content) VALUES (?, ?)")) {

            // Create the table if it does not exist
            preparedStatement.executeUpdate("CREATE TABLE IF NOT EXISTS pdf_data (pdf_name VARCHAR(255), text_content TEXT)");

            // Insert data into the database
            preparedStatement.setString(1, pdfName);
            preparedStatement.setString(2, textContent);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
