package com.raksul.userApi.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.sql.*;

@Component
public class BaseDao {
    @Value("${DB_USER}")
    private String dbUser;

    @Value("${DB_PASS}")
    private String dbPassword;

    @Value("${DB_HOST}")
    private String dbHost;

    @Value("${DB_NAME}")
    private String dbName;

    private final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS users";
    private final String CREATE_TABLE_QUERY = "CREATE TABLE users (id bigserial PRIMARY KEY, email VARCHAR ( 255 ) UNIQUE NOT NULL, " +
            "password VARCHAR ( 255 ) NOT NULL,\ttoken VARCHAR ( 50 ), tokenValid boolean, created_at TIMESTAMP NOT NULL, updated_at TIMESTAMP)";
    private Connection con = null;
    private Statement stmt = null;
    private String connectionString = "jdbc:postgresql://";
    private final String pgPort = ":5432/";

    @PostConstruct
    public void init() {
        connectionString += dbHost + pgPort + dbName;
        try {
            openConnection();
            stmt.execute(DROP_TABLE_QUERY);
            stmt.execute(CREATE_TABLE_QUERY);
        } catch (Exception e) {
        }
    }

    private void openConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        if (con == null)
            con = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        stmt = con.createStatement();
    }

    private boolean isValidSql(String sql) {
        return sql != null && sql.length() > 0;
    }

    protected void closeConnection() {
        try {
            if (stmt != null)
                stmt.close();
            if (con != null)
                con.close();
            stmt = null;
            con = null;
        } catch (SQLException e) {}
        finally{stmt = null;con = null;}
    }

    protected ResultSet fetch(String sql) throws Exception {
        if (isValidSql(sql)){
            this.openConnection();
            return this.stmt.executeQuery(sql);
        }
        return null;
    }

    protected boolean update(String sql){
        if (isValidSql(sql)){
            try {
                this.openConnection();
                Integer numberOfUpdatedRows = this.stmt.executeUpdate(sql);
                this.stmt.executeUpdate("commit");
                if (numberOfUpdatedRows > 0)
                    return true;
                return false;
            } catch(Exception e) {return false;}
            finally {this.closeConnection();}
        }
        return false;
    }
}
