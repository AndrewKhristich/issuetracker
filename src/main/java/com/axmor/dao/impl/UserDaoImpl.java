package com.axmor.dao.impl;

import com.axmor.dao.UserDao;
import com.axmor.model.User;
import com.axmor.utils.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.axmor.utils.CustomMapper.mapUserFromResultSet;

public class UserDaoImpl implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User findUserByName(String name) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USER WHERE NAME = '" + name + "'";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("FINDING USER FAIL" ,e);
        }
        if (users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public void saveUser(String name, String password) {
        String query = "INSERT INTO USER(NAME, PASSWORD) VALUES('"+name+"', '"+password+"')";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("SAVING USER FAIL" ,e);
        }
    }
}
