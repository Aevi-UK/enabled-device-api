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

package com.aevi.sdk.dms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposables;

/**
 * Client class to interact with the device management service API
 */
public final class DmsClient {

    public static final String SDK_DMS_INFO_METHOD = "dmsInfo";
    public static final String SDK_LOGGED_IN_ACCOUNT_METHOD = "loggedInAccount";

    public static final String FIELD_UIN = "uin";

    public static final String FIELD_ACCOUNT_ID = "id";
    public static final String FIELD_ACCOUNT_NAME = "name";
    public static final String FIELD_ACCOUNT_ROLE = "role";

    static final String ACTION_SESSION_CHANGE = "com.aevi.SESSION_CHANGE";

    private static final Uri SDK_CONTENT_URI = Uri.parse("content://com.aevi.dms.info");

    private abstract class RxBroadcastReceiver<T> extends BroadcastReceiver implements ObservableOnSubscribe<T> {

        private final String method;
        private final IntentFilter intentFilter;
        private ObservableEmitter<T> observableEmitter;

        RxBroadcastReceiver(String method, IntentFilter intentFilter) {
            this.method = method;
            this.intentFilter = intentFilter;
        }

        protected abstract T deserialize(Bundle bundle);

        @Override
        public void subscribe(ObservableEmitter<T> observableEmitter) {
            this.observableEmitter = observableEmitter;
            context.registerReceiver(this, intentFilter);
            observableEmitter.setDisposable(Disposables.fromRunnable(() ->
                context.unregisterReceiver(RxBroadcastReceiver.this)
            ));
            onReceive(context, null);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            T value = deserialize(context.getContentResolver().call(SDK_CONTENT_URI, method, null, null));
            if (value != null) {
                observableEmitter.onNext(value);
            }
        }
    }

    private final Context context;

    public DmsClient(Context context) {
        this.context = context;
    }

    /**
     * Retrieve information about the current device management service configuration
     * @return an instance of {@link DmsInfo}
     */
    @NonNull
    public Single<DmsInfo> info() {
        return Single.create(emitter -> {
            try {
                Bundle bundle = context.getContentResolver().call(SDK_CONTENT_URI, SDK_DMS_INFO_METHOD, null, null);

                if (isEmpty(bundle)) {
                    throw new IllegalStateException("No DMS information found");
                } else {
                    emitter.onSuccess(new DmsInfo(bundle.getString(FIELD_UIN)));
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    /**
     * Watch for logged in {@link Account} changes. If an account is already logged in at the time of the call, the observable will be
     * notified straight away and subsequently following any other changes. Please note no notification will be issued on logout.
     * @return an observable to watch for logged in {@link Account} change
     */
    @NonNull
    public Observable<Account> loggedInAccount() {
        return Observable.create(new RxBroadcastReceiver<Account>(SDK_LOGGED_IN_ACCOUNT_METHOD, new IntentFilter(ACTION_SESSION_CHANGE)) {
            @Override
            protected Account deserialize(Bundle bundle) {
                if (isEmpty(bundle)) {
                    return null;
                } else {
                    Account.Role role = Account.Role.valueOf(bundle.getString(FIELD_ACCOUNT_ROLE));
                    return new Account(bundle.getString(FIELD_ACCOUNT_ID), bundle.getString(FIELD_ACCOUNT_NAME), role);
                }
            }
        });
    }

    private static boolean isEmpty(Bundle bundle) {
        return bundle == null || bundle.isEmpty();
    }
}
