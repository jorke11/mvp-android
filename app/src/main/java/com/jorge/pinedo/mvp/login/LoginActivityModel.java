package com.jorge.pinedo.mvp.login;

public class LoginActivityModel implements LoginActivityMVP.Model {

    private LoginRepository repository;

    public LoginActivityModel(LoginRepository repository){
        this.repository = repository;
    }

    @Override
    public void createUser(String firstName, String lastName) {
        //login de business, existe, no existe,validadiones, transformaciones
        repository.saveUser(new User(firstName,lastName));
    }

    @Override
    public User getUser() {
        //login de business, existe, no existe,validadiones, transformaciones
        return repository.getUser();
    }
}
