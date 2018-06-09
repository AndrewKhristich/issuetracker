package com.axmor.utils;

import com.axmor.Main;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSource {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private DataSource() {
    }

    static {
        config.setDriverClassName(DB_DRIVER);
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setMaximumPoolSize(20);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public static void createAllTables(String queryPath){
        StringBuilder query = null;
        try(FileReader createQueryScript = new FileReader(queryPath);
        BufferedReader reader = new BufferedReader(createQueryScript) ) {
             query = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                query.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(String.valueOf(query))) {
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}