package com.aevi.sdk.dms;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class DmsClientTest {

    @Test
    public void clientInfoNoContentResolver() {
        // Given
        DmsClient client = new DmsClient(RuntimeEnvironment.systemContext);

        // When
        TestObserver<DmsInfo> observer = client.info().test();

        // Then
        Assert.assertEquals(observer.errorCount(), 1);
        observer.assertErrorMessage("No DMS information found");
        observer.assertNoValues();
    }

    @Test
    public void validClientInfo() {
        // Given
        String givenUin = "the_uin";
        DmsClient client = new DmsClient(RuntimeEnvironment.systemContext);
        MockContentProvider contentProvider = mockContentProvider();
        contentProvider.setInfo(mockDmsInfo(givenUin));

        // When
        TestObserver<DmsInfo> observer = client.info().test();

        // Then
        observer.assertNoErrors();
        observer.assertValueCount(1);
        DmsInfo receivedDmsInfo = observer.values().get(0);
        Assert.assertEquals(receivedDmsInfo.getUin(), givenUin);
    }

    @Test
    public void nullClientInfoUin() {
        // Given
        DmsClient client = new DmsClient(RuntimeEnvironment.systemContext);
        MockContentProvider contentProvider = mockContentProvider();
        contentProvider.setInfo(mockDmsInfo(null));

        // When
        TestObserver<DmsInfo> observer = client.info().test();

        // Then
        observer.assertNoErrors();
        observer.assertValueCount(1);
        DmsInfo receivedDmsInfo = observer.values().get(0);
        Assert.assertNull(receivedDmsInfo.getUin());
    }

    @Test
    public void accountNoContentResolver() {
        // Given
        DmsClient client = new DmsClient(RuntimeEnvironment.systemContext);

        // When
        TestObserver<Account> observer = client.loggedInAccount().test();

        // Then
        observer.assertNoErrors();
        observer.assertNoValues();
    }

    @Test
    public void validAccount() {
        // Given
        String givenId = "the_id";
        String givenName = "the_name";
        Account.Role givenRole = Account.Role.MANAGER;
        DmsClient client = new DmsClient(RuntimeEnvironment.systemContext);
        MockContentProvider contentProvider = mockContentProvider();
        contentProvider.setAccount(mockAccount(givenId, givenName, givenRole));

        // When
        TestObserver<Account> observer = client.loggedInAccount().test();

        // Then
        observer.assertNoErrors();
        observer.assertValueCount(1);
        Account receivedAccount = observer.values().get(0);
        Assert.assertEquals(receivedAccount.getId(), givenId);
        Assert.assertEquals(receivedAccount.getName(), givenName);
        Assert.assertEquals(receivedAccount.getRole(), givenRole);
    }

    @Test
    public void accountChanged() {
        // Given
        DmsClient client = new DmsClient(RuntimeEnvironment.systemContext);
        MockContentProvider contentProvider = mockContentProvider();
        contentProvider.setAccount(mockAccount("id_1", "name_1", Account.Role.OPERATOR));
        TestObserver<Account> observer = client.loggedInAccount().test();

        // When
        contentProvider.setAccount(mockAccount("id_2", "name_2", Account.Role.TECHNICIAN));
        RuntimeEnvironment.systemContext.sendBroadcast(new Intent(DmsClient.ACTION_SESSION_CHANGE));

        // Then
        observer.assertNoErrors();
        observer.assertValueCount(2);

        Account firstReceivedAccount = observer.values().get(0);
        Assert.assertEquals(firstReceivedAccount.getId(), "id_1");
        Assert.assertEquals(firstReceivedAccount.getName(), "name_1");
        Assert.assertEquals(firstReceivedAccount.getRole(), Account.Role.OPERATOR);

        Account secondReceivedAccount = observer.values().get(1);
        Assert.assertEquals(secondReceivedAccount.getId(), "id_2");
        Assert.assertEquals(secondReceivedAccount.getName(), "name_2");
        Assert.assertEquals(secondReceivedAccount.getRole(), Account.Role.TECHNICIAN);
    }

    @Test
    public void nullAccount() {
        // Given
        DmsClient client = new DmsClient(RuntimeEnvironment.systemContext);
        MockContentProvider contentProvider = mockContentProvider();
        contentProvider.setAccount(null);

        // When
        TestObserver<Account> observer = client.loggedInAccount().test();

        // Then
        observer.assertNoErrors();
        observer.assertNoValues();
    }

    private MockContentProvider mockContentProvider() {
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.authority = MockContentProvider.AUTHORITY;
        return Robolectric.buildContentProvider(MockContentProvider.class)
            .create(providerInfo)
            .get();
    }

    private Bundle mockDmsInfo(@Nullable String uin) {
        Bundle bundle = new Bundle();
        bundle.putString(DmsClient.FIELD_UIN, uin);
        return bundle;
    }

    private Bundle mockAccount(@NonNull String id, @NonNull String name, @NonNull Account.Role role) {
        Bundle bundle = new Bundle();
        bundle.putString(DmsClient.FIELD_ACCOUNT_ID, id);
        bundle.putString(DmsClient.FIELD_ACCOUNT_NAME, name);
        bundle.putString(DmsClient.FIELD_ACCOUNT_ROLE, role.name());
        return bundle;
    }
}
