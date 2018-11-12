package com.aevi.sdk.dms.simulator.app;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface AppSchedulers {

    default Scheduler main() {
        return AndroidSchedulers.mainThread();
    }

    default Scheduler io() {
        return Schedulers.io();
    }
}
