package com.jorge.pinedo.mvp.login;


import android.support.annotation.Nullable;

public class LoginActivityPresenter implements LoginActivityMVP.Presenter {

    @Nullable
    private LoginActivityMVP.View view;
    private LoginActivityMVP.Model model;

    public LoginActivityPresenter(LoginActivityMVP.Model model){
        this.model=model;
    }

    @Override
    public void setView(LoginActivityMVP.View view) {
        this.view=view;
    }

    @Override
    public void loginButtonClicked() {
        if(view!=null){
            if(view.getFirtsName().trim().equals("") ||view.getLastName().trim().equals("") ){
                view.showInputError();
            }else{
                model.createUser(view.getFirtsName().trim(),view.getFirtsName().trim());
                view.showUserSaved();
            }
        }
    }

    @Override
    public void getCurrentUser() {
        User user=model.getUser();

        if(user==null){
            if(view!=null){
                view.showUserNotAvailable();
            }
        }else{
            if(view!=null){
                view.setFirstName(user.getFirtsName());
                view.setLastName(user.getLastName());
            }
        }

    }
}
