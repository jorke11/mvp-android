package com.jorge.pinedo.mvp.root;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppplicationModule {
    private Application application;

    public AppplicationModule(Application application){
        this.application=application;
    }

    @Provides
    @Singleton
    public Context privideContext(){
        return application;
    }
}
