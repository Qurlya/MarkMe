package ArcheAge.MarkMe.Database;

import ArcheAge.MarkMe.Ctrl.Helpyshka.JsonLink;
import ArcheAge.MarkMe.Variables.Record;

import java.sql.*;
import java.util.List;

public class Memders {
    private static String jdbcURL = AppConfig.getDbUrl();
    private static String username = AppConfig.getDbUser();
    private static String password = AppConfig.getDbPassword();

    public static void main(String[] args) throws SQLException {
        System.out.println(getIdFromNickname("Курля"));
        System.out.println(getNicknameFromId(17));
    }
    public static int getIdFromNickname(String nickname) throws SQLException {
        String sql = """
                SELECT id FROM "members" WHERE nickname = ?;
                """;
        int id = 0;
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nickname);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public static String getNicknameFromId(int id) throws SQLException {
        String sql = """
                SELECT nickname FROM "members" WHERE id = ?;
                """;
        String nickname = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    nickname = rs.getString("nickname");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nickname;
    }
}
