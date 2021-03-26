package com.jorge.pinedo.mvp.root;


import com.jorge.pinedo.mvp.http.TwitchModule;
import com.jorge.pinedo.mvp.login.LoginActivity;
import com.jorge.pinedo.mvp.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {   AppplicationModule.class,LoginModule.class,TwitchModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity target);
}
