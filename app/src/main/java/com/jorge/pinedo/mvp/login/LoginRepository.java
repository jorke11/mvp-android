package com.jorge.pinedo.mvp.login;

public interface LoginRepository {

    void saveUser(User user);

    User getUser();
}
