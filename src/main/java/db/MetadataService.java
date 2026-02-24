package db;

import java.sql.*;
import java.util.*;

public class MetadataService {

    private final Connection connection;

    public MetadataService(Connection connection) {
        this.connection = connection;
    }

    public List<String> getTables() throws SQLException {
        String sql = """
            SELECT RDB$RELATION_NAME
            FROM RDB$RELATIONS
            WHERE RDB$SYSTEM_FLAG = 0
            AND RDB$VIEW_BLR IS NULL
        """;

        return executeNameQuery(sql);
    }

    public List<String> getViews() throws SQLException {
        String sql = """
            SELECT RDB$RELATION_NAME
            FROM RDB$RELATIONS
            WHERE RDB$VIEW_BLR IS NOT NULL
        """;

        return executeNameQuery(sql);
    }

    public List<String> getProcedures() throws SQLException {
        return executeNameQuery(
            "SELECT RDB$PROCEDURE_NAME FROM RDB$PROCEDURES");
    }

    public List<String> getTriggers() throws SQLException {
        return executeNameQuery(
            "SELECT RDB$TRIGGER_NAME FROM RDB$TRIGGERS WHERE RDB$SYSTEM_FLAG = 0");
    }

    public List<String> getGenerators() throws SQLException {
        return executeNameQuery(
            "SELECT RDB$GENERATOR_NAME FROM RDB$GENERATORS WHERE RDB$SYSTEM_FLAG = 0");
    }

    public List<String> getIndexes() throws SQLException {
        return executeNameQuery(
            "SELECT RDB$INDEX_NAME FROM RDB$INDICES WHERE RDB$SYSTEM_FLAG = 0");
    }

    public List<String> getFunctions() throws SQLException {
        return executeNameQuery(
            "SELECT RDB$FUNCTION_NAME FROM RDB$FUNCTIONS");
    }

    public List<String> getUsers() throws SQLException {
        return executeNameQuery(
            "SELECT SEC$USER_NAME FROM SEC$USERS");
    }

    private List<String> executeNameQuery(String sql) throws SQLException {
        List<String> results = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                results.add(rs.getString(1).trim());
            }
        }
        return results;
    }
}