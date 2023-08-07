package org.example;
import java.sql.*;

public class Connector {
    public static void main() {

        try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dogs");

            while (rs.next()) {
                System.out.println(rs.getInt("animal_id") + " " + rs.getString("animal_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        view.start();

        System.out.println("Creating table in given database...");


    }
}
