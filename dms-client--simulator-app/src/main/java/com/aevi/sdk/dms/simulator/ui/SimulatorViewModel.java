package com.aevi.sdk.dms.simulator.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.aevi.sdk.dms.Account;
import com.aevi.sdk.dms.DmsClient;
import com.aevi.sdk.dms.simulator.app.AppSchedulers;
import com.aevi.sdk.dms.simulator.app.AppSettings;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SimulatorViewModel extends ViewModel {

    final MutableLiveData<String> errorEvent = new MutableLiveData<>();
    final MutableLiveData<String> uinUpdateEvent = new MutableLiveData<>();
    final MutableLiveData<Integer> accountRoleEvent = new MutableLiveData<>();
    final MutableLiveData<Boolean> settingsSavedEvent = new MutableLiveData<>();

    private final DmsClient dmsClient;
    private final AppSettings appSettings;
    private final AppSchedulers schedulers;

    private CompositeDisposable disposables;

    @Inject
    SimulatorViewModel(DmsClient dmsClient, AppSettings appSettings, AppSchedulers schedulers) {
        this.dmsClient = dmsClient;
        this.appSettings = appSettings;
        this.schedulers = schedulers;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposables != null) disposables.dispose();
    }

    public void start() {
        disposables = new CompositeDisposable();
        subscribeToDmsInfo();
        subscribeToAccount();
    }

    private void subscribeToDmsInfo() {
        disposables.add(dmsClient.info()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe(
                dmsInfo -> uinUpdateEvent.setValue(dmsInfo.getUin()),
                error -> errorEvent.setValue(error.getMessage())));
    }

    private void subscribeToAccount() {
        disposables.add(dmsClient.loggedInAccount()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe(
                account -> accountRoleEvent.setValue(account.getRole().ordinal()),
                error -> errorEvent.setValue(error.getMessage())));
    }

    void save(@Nullable String uin, int roleIndex) {
        disposables.add(Observable.fromCallable(() -> appSettings.setUin(uin) && appSettings.setAccount(Account.Role.values()[roleIndex]))
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe(
                settingsSavedEvent::setValue,
                error -> {
                    Timber.e(error);
                    settingsSavedEvent.setValue(false);
                }));
    }
}
