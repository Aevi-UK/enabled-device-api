package com.aevi.sdk.dms.simulator.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aevi.sdk.dms.DmsClient;

import javax.inject.Inject;

import dagger.android.DaggerContentProvider;

public class AppContentProvider extends DaggerContentProvider {

    @Inject protected AppSettings appSettings;

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

    @Override public String getType(@NonNull Uri a) { return null; }
    @Override public Uri insert(@NonNull Uri a, ContentValues b) { return null; }
    @Override public int delete(@NonNull Uri a, String b, String[] c) { return 0; }
    @Override public int update(@NonNull Uri a, ContentValues b, String c, String[] d) { return 0; }
    @Override public Cursor query(@NonNull Uri a, String[] b, String c, String[] d, String e) { return null; }
}
