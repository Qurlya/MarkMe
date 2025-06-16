package ArcheAge.MarkMe.Database;

import ArcheAge.MarkMe.Ctrl.Helpyshka.JsonLink;
import ArcheAge.MarkMe.Variables.Record;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sql {
    private static String jdbcURL = AppConfig.getDbUrl();
    private static String username = AppConfig.getDbUser();
    private static String password = AppConfig.getDbPassword();

    public static void main(String[] args) throws SQLException {
        List<Record> records = getNeedCheckMarks();
        for (Record record: records){
            System.out.println(JsonLink.write(record));
        }
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
    //Возвращает массив Записей с непроверенными днями
    public static List<Record> getNeedCheckMarks() {
        List<Record> needCheckMarks = new ArrayList<>();
        String sql = """
                SELECT\s
                    "date" AS activity_date,
                    'АГЛ 3:20' AS boss_name,
                    "АГЛ 3:20" AS json_value
                FROM "Activities"
                WHERE ("АГЛ 3:20"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'АГЛ 7:20' AS boss_name,
                    "АГЛ 7:20" AS json_value
                FROM "Activities"
                WHERE ("АГЛ 7:20"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Кошка 10:00' AS boss_name,
                    "Кошка 10:00" AS json_value
                FROM "Activities"
                WHERE ("Кошка 10:00"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Марля утро' AS boss_name,
                    "Марля утро" AS json_value
                FROM "Activities"
                WHERE ("Марля утро"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Морф утро' AS boss_name,
                    "Морф утро" AS json_value
                FROM "Activities"
                WHERE ("Морф утро"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'АГЛ 11:20' AS boss_name,
                    "АГЛ 11:20" AS json_value
                FROM "Activities"
                WHERE ("АГЛ 11:20"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'АГЛ 15:20' AS boss_name,
                    "АГЛ 15:20" AS json_value
                FROM "Activities"
                WHERE ("АГЛ 15:20"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'АГЛ 19:20' AS boss_name,
                    "АГЛ 19:20" AS json_value
                FROM "Activities"
                WHERE ("АГЛ 19:20"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Кракен 19:30' AS boss_name,
                    "Кракен 19:30" AS json_value
                FROM "Activities"
                WHERE ("Кракен 19:30"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Калидис 20:30' AS boss_name,
                    "Калидис 20:30" AS json_value
                FROM "Activities"
                WHERE ("Калидис 20:30"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Кошка 22:00' AS boss_name,
                    "Кошка 22:00" AS json_value
                FROM "Activities"
                WHERE ("Кошка 22:00"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Марля вечер' AS boss_name,
                    "Марля вечер" AS json_value
                FROM "Activities"
                WHERE ("Марля вечер"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Морф вечер' AS boss_name,
                    "Морф вечер" AS json_value
                FROM "Activities"
                WHERE ("Морф вечер"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'АГЛ 23:20' AS boss_name,
                    "АГЛ 23:20" AS json_value
                FROM "Activities"
                WHERE ("АГЛ 23:20"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Ксанатос 19:30' AS boss_name,
                    "Ксанатос 19:30" AS json_value
                FROM "Activities"
                WHERE ("Ксанатос 19:30"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Анталлон 21:30' AS boss_name,
                    "Анталлон 21:30" AS json_value
                FROM "Activities"
                WHERE ("Анталлон 21:30"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Левиафан 20:30' AS boss_name,
                    "Левиафан 20:30" AS json_value
                FROM "Activities"
                WHERE ("Левиафан 20:30"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Фесаникс 18:30' AS boss_name,
                    "Фесаникс 18:30" AS json_value
                FROM "Activities"
                WHERE ("Фесаникс 18:30"->>'needCheck')::boolean = true
                
                UNION ALL
                
                SELECT\s
                    "date" AS activity_date,
                    'Анталлон 19:50' AS boss_name,
                    "Анталлон 19:50" AS json_value
                FROM "Activities"
                WHERE ("Анталлон 19:50"->>'needCheck')::boolean = true;
""";

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Record temp = JsonLink.read(rs.getString("json_value"));
                temp.setDateBoss(rs.getString(1));
                temp.setNameBoss(rs.getString(2));
                needCheckMarks.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return needCheckMarks;
    }
}
