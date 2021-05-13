/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.aevi.sdk.dms.simulator.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.aevi.sdk.dms.Account;
import com.aevi.sdk.dms.DmsClient;
import com.aevi.sdk.dms.simulator.app.AppSchedulers;
import com.aevi.sdk.dms.simulator.app.AppSettings;
import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import javax.inject.Inject;

public class SimulatorViewModel extends ViewModel {

    final MutableLiveData<String> errorEvent = new MutableLiveData<>();
    final MutableLiveData<String> serialUpdateEvent = new MutableLiveData<>();
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
                .subscribe(dmsInfo -> {
                    serialUpdateEvent.setValue(dmsInfo.getSerial());
                    uinUpdateEvent.setValue(dmsInfo.getUin());
                }, error -> errorEvent.setValue(error.getMessage())));
    }

    private void subscribeToAccount() {
        disposables.add(dmsClient.loggedInAccount()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.main())
                .subscribe(
                        account -> accountRoleEvent.setValue(account.getRole().ordinal()),
                        error -> errorEvent.setValue(error.getMessage())));
    }

    void save(@Nullable String serial, @Nullable String uin, int roleIndex) {
        disposables.add(Observable.fromCallable(() -> appSettings.setSerial(serial) && appSettings.setUin(uin) && appSettings.setAccount(Account.Role.values()[roleIndex]))
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
