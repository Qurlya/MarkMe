package ArcheAge.MarkMe.Database;

import ArcheAge.MarkMe.Variables.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitiesSQL {
    private static String jdbcURL = AppConfig.getDbUrl();
    private static String username = AppConfig.getDbUser();
    private static String password = AppConfig.getDbPassword();

    public static void main(String[] args) throws SQLException {
        System.out.println(getActivitiesByDate(LocalDate.now()).get("Левиафан 20:30"));
    }
    public static Map<String, String> getActivitiesByDate(LocalDate localDate) throws SQLException {
        String date = localDate.toString();
        String sql = """
        SELECT * FROM "Activities" WHERE "date" = ?::date
        """;

        Map<String, String> rowMap = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, date);

            try (ResultSet rs = statement.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    rowMap = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = rs.getObject(i);
                        // Преобразуем все значения в строки
                        String stringValue = (value != null) ? value.toString() : null;
                        rowMap.put(metaData.getColumnName(i), stringValue);
                    }
                }
            }
        }
        return rowMap;
    }
}
