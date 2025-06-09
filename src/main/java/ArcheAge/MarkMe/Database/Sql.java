package ArcheAge.MarkMe.Database;

import ArcheAge.MarkMe.Variables.Record;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Sql {
    private static String jdbcURL = AppConfig.getDbUrl();
    private static String username = AppConfig.getDbUser();
    private static String password = AppConfig.getDbPassword();

    public static void main(String[] args) throws SQLException {

    }
    //Создание даты. Вход: Дата
    public static void addDateForActivities(LocalDate localDate) {
        String date = localDate.toString();
        String insertSQL = """
                INSERT INTO "Activities" (date) VALUES (?::date);
                """;
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement (insertSQL)) {
            connection.setAutoCommit(true);

            statement.setString(1, date);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Добавить запись в ячейку по Боссу, Дате, Json
    public static void addReport(String nameActivities, LocalDate localDate, String json) {
        String date = localDate.toString();
        String insertSQL = String.format("""
                UPDATE "Activities" 
                SET "%s" = ?::json
                WHERE "date" = ?::date;
                """, nameActivities);
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement (insertSQL)) {
            connection.setAutoCommit(true);

            statement.setString(1, json);
            statement.setString(2, date);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*Возвращает boolean, проверка на существование даты*/
    public static boolean existsDate(LocalDate localDate) throws SQLException {
        String date = localDate.toString();
        String sql = """
                SELECT COUNT(*) FROM "Activities" WHERE "date" = ?::date;
                """;

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, date);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //Получить запись по Боссу и Дате
    public static String getReport(String nameBoss, LocalDate localDate) {
        String date = localDate.toString();
        String sql = """
            SELECT "%s" FROM "Activities" WHERE "date" = '%s';
            """.formatted(nameBoss, date);

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }
}
