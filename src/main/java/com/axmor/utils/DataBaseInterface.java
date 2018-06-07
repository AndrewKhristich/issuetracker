package com.axmor.utils;

import java.sql.Connection;

public interface DataBaseInterface {
    void createAllTables(String queryPath);
    Connection getConnection();
}
