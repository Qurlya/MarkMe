package ArcheAge.MarkMe.Database;

import ArcheAge.MarkMe.Variables.JsonLink;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pg {

    private static String jdbcURL = AppConfig.getDbUrl();
    private static String username = AppConfig.getDbUser();
    private static String password = AppConfig.getDbPassword();

    public static void main(String[] args) {
    }
    /*Регистрация, добавляет пользователя в БД*/
    public static void addMembers(String nickname, String vk, String way, String pin) {
        String insertSQL = "INSERT INTO members (nickname, vk, way, user_pin) VALUES (?, ?, ?, ?);";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement (insertSQL)) {
            connection.setAutoCommit(true);
            statement.setString(1, nickname);
            statement.setString(2, vk);
            statement.setString(3, way);
            statement.setString(4, pin);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addTest(Integer nickname, String json) {
        String insertSQL = "INSERT INTO test (name, ttt) VALUES (2, ?::json);";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement (insertSQL)) {
            connection.setAutoCommit(true);
            statement.setString(1, json);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*Логин, проверка на правильность логина и пароля*/
    public static boolean checkMembers(String nickname, String pin) {
        String insertSQL = "SELECT * FROM members WHERE nickname = ? AND user_pin = ?;";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password); PreparedStatement statement = connection.prepareStatement (insertSQL)) {

            statement.setString(1, nickname);
            statement.setString(2, pin);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*Регистрация, аккаунт с таким ником уже существует*/
    public static boolean checkOnceMembers(String nickname) {
        String insertSQL = "SELECT * FROM members WHERE nickname = ?;";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password); PreparedStatement statement = connection.prepareStatement (insertSQL)) {
            statement.setString(1, nickname);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*Удаляет пользователя по имени*/
    public static void delMembers(String nickname) {
        String insertSQL = "DELETE FROM members WHERE nickname = ?;";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password); PreparedStatement statement = connection.prepareStatement (insertSQL)) {
            statement.setString(1, nickname);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*Создает таблицу по имени и дню*/
    public static void createDay(String name, String day) throws SQLException {
        /*String sql = String.format(Variables.Variables().get(day), name);

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);

        }
        addConstRows(name);*/
    }
    /*Проверяет, существует ли таблица*/
    public static boolean isTableExists(String tableName) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            DatabaseMetaData meta = connection.getMetaData();
            try (ResultSet tables = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
                return tables.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*Возвращает название столбцов*/
    public static List<String> getColumns(String tableName){
        if (tableName == null || tableName.trim().isEmpty()) {
            return Collections.emptyList();
        }

        List<String> columns = new ArrayList<>();

        String sql = """
            SELECT column_name
            FROM information_schema.columns
            WHERE table_schema = 'public' AND table_name = ?
            """;

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tableName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    columns.add(rs.getString("column_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columns;
    }
    /*Добавляет системные строки
    * -1 Примечание
    * -2 Время
    * -3 Ссылку
    * -4 ПВП*/
    public static void addConstRows(String tableName){
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
    /*Добавляет системные строки в таблицу*/
    public static void updateSistemActivities(String tableName, String activity, String note, String timeBoss, String link, String isPVP) {
        String updateSQL = String.format("""
                UPDATE "%S"
                SET "%S" = CASE
                WHEN id = -1 THEN ?
                WHEN id = -2 THEN ?
                WHEN id = -3 THEN ?
                WHEN id = -4 THEN ?
                ELSE "%S"
                END
                WHERE id IN (-1, -2, -3, -4);
                """, tableName, activity, activity);
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement (updateSQL)) {
            connection.setAutoCommit(true);
            statement.setString(1, note);
            statement.setString(2, timeBoss);
            statement.setString(3, link);
            statement.setString(4, isPVP);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**/

}
