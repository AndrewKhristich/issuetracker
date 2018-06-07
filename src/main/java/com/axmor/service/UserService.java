package com.axmor.service;

import com.axmor.model.User;

public interface UserService {
    void saveUser(String name, String password);
    User findUser(String name);
}
