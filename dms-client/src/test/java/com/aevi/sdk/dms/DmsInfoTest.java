package com.aevi.sdk.dms;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public final class DmsInfoTest {

    @Test
    public void getUIN() {
        // Given
        String givenUin = "the_uin";
        DmsInfo dmsInfo = new DmsInfo(givenUin);

        // When
        String returnedUin = dmsInfo.getUin();

        // Then
        Assert.assertEquals(returnedUin, givenUin);
    }

    @Test
    public void getNullUIN() {
        // Given
        DmsInfo dmsInfo = new DmsInfo(null);

        // When
        String returnedUin = dmsInfo.getUin();

        // Then
        Assert.assertNull(returnedUin);
    }
}
