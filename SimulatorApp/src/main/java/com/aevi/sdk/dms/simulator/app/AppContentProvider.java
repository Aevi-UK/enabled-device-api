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

package com.aevi.sdk.dms.simulator.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.aevi.sdk.dms.DmsClient;
import dagger.android.DaggerContentProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import javax.inject.Inject;

public class AppContentProvider extends DaggerContentProvider {

    @Inject
    protected AppSettings appSettings;

    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        switch (method) {
            case DmsClient.SDK_DMS_INFO_METHOD:
                return serialiseUin();
            case DmsClient.SDK_LOGGED_IN_ACCOUNT_METHOD:
                return serialiseAccount();
            default:
                return super.call(method, arg, extras);
        }
    }

    private Bundle serialiseUin() {
        Bundle bundle = new Bundle();
        bundle.putString(DmsClient.FIELD_SERIAL, appSettings.getSerial());
        bundle.putString(DmsClient.FIELD_UIN, appSettings.getUin());
        return bundle;
    }

    private Bundle serialiseAccount() {
        Bundle bundle = new Bundle();
        bundle.putString(DmsClient.FIELD_ACCOUNT_ID, appSettings.getAccountId());
        bundle.putString(DmsClient.FIELD_ACCOUNT_NAME, appSettings.getAccountName());
        bundle.putString(DmsClient.FIELD_ACCOUNT_ROLE, appSettings.getAccountRole().name());
        return bundle;
    }

    @Override
    public String getType(@NonNull Uri a) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri a, ContentValues b) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri a, String b, String[] c) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri a, ContentValues b, String c, String[] d) {
        return 0;
    }

    @Override
    public Cursor query(@NonNull Uri a, String[] b, String c, String[] d, String e) {
        return null;
    }
}
