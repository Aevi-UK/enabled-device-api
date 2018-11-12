package com.aevi.sdk.dms.simulator.app;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class ViewModelFactory<VMType extends ViewModel> implements ViewModelProvider.Factory {

    private final VMType mViewModel;

    public ViewModelFactory(VMType viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> clazz) {
        return (T)mViewModel;
    }
}
