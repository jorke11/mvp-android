package com.jorge.pinedo.mvp.root;

import android.app.Application;

import com.jorge.pinedo.mvp.login.LoginModule;
import com.jorge.pinedo.mvp.root.DaggerApplicationComponent;

public class App extends Application {
    private  ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .appplicationModule(new AppplicationModule(this))
                .loginModule(new LoginModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
