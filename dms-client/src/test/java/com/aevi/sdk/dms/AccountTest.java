package com.aevi.sdk.dms;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public final class AccountTest {

    @Test
    public void getId() {
        // Given
        String givenId = "the_id";
        Account account = new Account(givenId, null, null);

        // When
        String returnedId = account.getId();
        String returnedName = account.getName();
        Account.Role returnedRole = account.getRole();

        // Then
        Assert.assertEquals(returnedId, givenId);
        Assert.assertNull(returnedName);
        Assert.assertNull(returnedRole);
    }

    @Test
    public void getName() {
        // Given
        String givenName = "the_name";
        Account account = new Account(null, givenName, null);

        // When
        String returnedId = account.getId();
        String returnedName = account.getName();
        Account.Role returnedRole = account.getRole();

        // Then
        Assert.assertEquals(returnedName, givenName);
        Assert.assertNull(returnedId);
        Assert.assertNull(returnedRole);
    }

    @Test
    public void getRole() {
        // Given
        Account.Role givenRole = Account.Role.OPERATOR;
        Account account = new Account(null, null, givenRole);

        // When
        String returnedId = account.getId();
        String returnedName = account.getName();
        Account.Role returnedRole = account.getRole();

        // Then
        Assert.assertEquals(returnedRole, givenRole);
        Assert.assertNull(returnedId);
        Assert.assertNull(returnedName);
    }
}
