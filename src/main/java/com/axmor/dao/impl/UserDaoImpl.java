package com.axmor.dao.impl;

import com.axmor.dao.UserDao;
import com.axmor.model.User;
import com.axmor.utils.DataBaseInterface;
import com.axmor.utils.DataBaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.axmor.Main.logger;

import static com.axmor.utils.CustomMapper.mapUserFromResultSet;

public class UserDaoImpl implements UserDao {

    private DataBaseInterface dataBaseUtil;

    public UserDaoImpl(DataBaseInterface dataBaseUtil) {
        this.dataBaseUtil = dataBaseUtil;
    }

    @Override
    public User findUserByName(String name) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataBaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USER WHERE NAME = '" + name + "'");
            while (resultSet.next()) {
                users.add(mapUserFromResultSet(resultSet));
            }
            logger.info("SQL : SELECT * FROM USER WHERE NAME = '" + name + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public void saveUser(String name, String password) {
        try (Connection connection = dataBaseUtil.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO USER(NAME, PASSWORD) VALUES('"+name+"', '"+password+"')");
            logger.info("SQL : INSERT INTO USER(NAME, PASSWORD) VALUES('"+name+"', '"+password+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
