package ArcheAge.MarkMe.Database;

import ArcheAge.MarkMe.Variables.JsonLink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sql {
    private static String jdbcURL = AppConfig.getDbUrl();
    private static String username = AppConfig.getDbUser();
    private static String password = AppConfig.getDbPassword();

    public static void main(String[] args) {
    }
    public static void addActivities(String tableName){
        String insertSQL = String.format("""
                INSERT INTO "%S" (id) VALUES (-1),(-2),(-3),(-4);
                """, tableName);
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement (insertSQL)) {
            connection.setAutoCommit(true);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
