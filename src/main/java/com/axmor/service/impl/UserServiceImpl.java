package com.axmor.service.impl;

import com.axmor.dao.UserDao;
import com.axmor.exception.UserAlreadyExistException;
import com.axmor.model.User;
import com.axmor.service.UserService;

public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUser(String name, String password) {
        if (userDao.findUserByName(name)!=null){
            throw new UserAlreadyExistException("User already exists");
        }
        userDao.saveUser(name,password);
    }

    @Override
    public User findUser(String name) {
        User userByName = userDao.findUserByName(name);
        return userByName;
    }
}
