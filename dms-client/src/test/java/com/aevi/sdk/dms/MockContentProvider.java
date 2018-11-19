package com.aevi.sdk.dms;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class MockContentProvider extends ContentProvider {

    static final String AUTHORITY = "com.aevi.dms.info";

    private Bundle dmsInfo;
    private Bundle account;

    public void setInfo(Bundle dmsInfo) {
        this.dmsInfo = dmsInfo;
    }

    public void setAccount(Bundle account) {
        this.account = account;
    }

    @Override
    public Bundle call(@NonNull String method, String arg, Bundle extras) {
        switch (method) {
            case DmsClient.SDK_DMS_INFO_METHOD:
                return dmsInfo;
            case DmsClient.SDK_LOGGED_IN_ACCOUNT_METHOD:
                return account;
            default:
                throw new IllegalArgumentException("Unrecognised method '" + method + "'");
        }
    }

    @Override public boolean onCreate() { return true; }
    @Override public String getType(@NonNull Uri a) { return null; }
    @Override public Uri insert(@NonNull Uri a, ContentValues b) { return null; }
    @Override public int delete(@NonNull Uri a, String b, String[] c) { return 0; }
    @Override public int update(@NonNull Uri a, ContentValues b, String c, String[] d) { return 0; }
    @Override public Cursor query(@NonNull Uri a, String[] b, String c, String[] d, String e) { return null; }
}
