package com.aevi.sdk.dms.simulator.ui;

import android.content.Context;

import com.aevi.sdk.dms.DmsClient;
import com.aevi.sdk.dms.simulator.app.ViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class SimulatorModule {

    @Provides
    DmsClient provideDmsClient(Context context) {
        return new DmsClient(context);
    }

    @Provides
    static ViewModelFactory<SimulatorViewModel> provideViewModelFactory(SimulatorViewModel viewModel) {
        return new ViewModelFactory<>(viewModel);
    }
}
