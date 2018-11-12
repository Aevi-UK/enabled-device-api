package com.aevi.sdk.dms.simulator.di;

import com.aevi.sdk.dms.simulator.app.AppContentProvider;
import com.aevi.sdk.dms.simulator.ui.SimulatorView;
import com.aevi.sdk.dms.simulator.ui.SimulatorModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ComponentBuilder {

    @ContributesAndroidInjector
    abstract AppContentProvider bindContentProvider();

    @ActivityScope
    @ContributesAndroidInjector(modules = SimulatorModule.class)
    abstract SimulatorView bindSimulatorActivity();
}
