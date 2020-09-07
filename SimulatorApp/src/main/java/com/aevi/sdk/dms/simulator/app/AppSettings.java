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

import android.content.Context;
import android.content.SharedPreferences;

import com.aevi.sdk.dms.Account;
import com.aevi.sdk.dms.simulator.R;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

@Singleton
@SuppressWarnings("ApplySHaredPref")
public class AppSettings {

    private static final String KEY_SERIAL = "keySerial";
    private static final String KEY_UIN = "keyUin";

    private static final String KEY_ACCOUNT_ID = "keyAccountId";
    private static final String KEY_ACCOUNT_NAME = "keyAccountName";
    private static final String KEY_ACCOUNT_ROLE = "keyAccountRole";

    private final String accountName;
    private final SharedPreferences sharedPreferences;

    @Inject
    AppSettings(Context context) {
        accountName = context.getString(R.string.account_name);
        sharedPreferences = context.getSharedPreferences(AppSettings.class.getSimpleName(), Context.MODE_PRIVATE);

        if (getAccountId() == null) setAccount(Account.Role.OPERATOR);
    }

    @Nullable
    String getSerial() {
        return sharedPreferences.getString(KEY_SERIAL, "123456ABCDEF");
    }

    public boolean setSerial(@Nullable String serial) {
        return sharedPreferences.edit()
                .putString(KEY_SERIAL, serial)
                .commit();
    }

    @Nullable
    String getUin() {
        return sharedPreferences.getString(KEY_UIN, "A1B2C3D4E5F6");
    }

    public boolean setUin(@Nullable String uin) {
        return sharedPreferences.edit()
            .putString(KEY_UIN, uin)
            .commit();
    }

    @NonNull
    String getAccountId() {
        return sharedPreferences.getString(KEY_ACCOUNT_ID, null);
    }

    @NonNull
    String getAccountName() {
        return sharedPreferences.getString(KEY_ACCOUNT_NAME, null);
    }

    @NonNull
    Account.Role getAccountRole() {
        return Account.Role.valueOf(sharedPreferences.getString(KEY_ACCOUNT_ROLE, null));
    }

    public boolean setAccount(@NonNull Account.Role role) {
        return sharedPreferences.edit()
            .putString(KEY_ACCOUNT_ID, lookupAccountId(role))
            .putString(KEY_ACCOUNT_NAME, accountName)
            .putString(KEY_ACCOUNT_ROLE, role.name())
            .commit();
    }

    private String lookupAccountId(Account.Role role) {
        switch (role) {
            case OPERATOR:
                return UUID.fromString("05f16711-c36e-4ac5-a90f-94d3df3ca010").toString();
            case MANAGER:
                return UUID.fromString("35606c72-b564-46e2-8598-af99252796d0").toString();
            case TECHNICIAN:
                return UUID.fromString("31eb2d33-8546-4e61-be0c-4756e18aa6ab").toString();
            default:
                throw new IllegalStateException("Unrecognised role: " + role.name());
        }
    }
}
