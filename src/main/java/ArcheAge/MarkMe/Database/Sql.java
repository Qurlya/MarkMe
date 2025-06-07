package ArcheAge.MarkMe.Database;

import ArcheAge.MarkMe.Variables.JsonLink;
import ArcheAge.MarkMe.Variables.Record;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Sql {
    private static String jdbcURL = AppConfig.getDbUrl();
    private static String username = AppConfig.getDbUser();
    private static String password = AppConfig.getDbPassword();

    public static void main(String[] args) throws SQLException {
        /*addDateForActivities();*/
        Record record = new Record();
        record.setId(List.of(14, 15, 16));
        record.setLink("Ссылка");
        record.setPVP(true);
        record.setNote("Примечание");
        record.setBossTime("18-30");
        addJsonActivities("Кракен 19:30", LocalDate.of(2025, 6,5), JsonLink.write(record));
    }
    /*Создание даты. Вход: Дата*/
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
    public static void addJsonActivities(String nameActivities, LocalDate localDate, String json) {
        /*String date = localDate.toString();*/
        String date = "2025-05-06";
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
    /*Вовращает boolean, проверка на существование даты*/
    public static boolean existsDate(String value) throws SQLException {
        String sql = """
                SELECT COUNT(*) FROM "Activities" WHERE "date" = ?::date;
                """;

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, value);
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
}
