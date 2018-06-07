package com.axmor.dao;

import com.axmor.model.User;

public interface UserDao {

    User findUserByName(String name);
    void saveUser(String name, String password);

}
