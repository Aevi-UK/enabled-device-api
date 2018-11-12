package com.aevi.sdk.dms.simulator.di;

import android.app.Application;
import android.content.Context;

import com.aevi.sdk.dms.simulator.app.AppSchedulers;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract Context provideContext(Application application);

    @Provides
    static AppSchedulers providesAppSchedulers() {
        return new AppSchedulers(){};
    }
}
