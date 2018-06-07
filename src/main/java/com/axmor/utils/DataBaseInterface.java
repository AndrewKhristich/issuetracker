package com.axmor.utils;

import java.sql.Connection;

public interface DataBaseInterface {
    public void createAllTables(String queryPath);
    public Connection getConnection();
}
