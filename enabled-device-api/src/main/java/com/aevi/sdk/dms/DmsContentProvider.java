package com.aevi.sdk.dms;

import android.app.admin.DevicePolicyManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileNotFoundException;

public abstract class DmsContentProvider extends ContentProvider {

    private static final String LOGS_PATH = "/logs";

    public abstract ParcelFileDescriptor openLogs(Uri uri, String mode) throws FileNotFoundException;

    @Override
    public final boolean onCreate() {
        return true;
    }

    @Override
    public final ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        String callingPackage = getCallingPackage();
        if (getContext().getPackageName().equals(callingPackage) || devicePolicyManager.isDeviceOwnerApp(callingPackage)) {
            if (LOGS_PATH.equals(uri.getPath())) {
                return openLogs(uri, mode);
            } else {
                throw new FileNotFoundException("Not found: " + uri.getPath());
            }
        } else {
            throw new SecurityException("Caller must be the device owner");
        }
    }

    @Override
    public final Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public final String getType(Uri uri) {
        return "application/octet-stream";
    }

    @Override
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public final int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public final int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }


}
