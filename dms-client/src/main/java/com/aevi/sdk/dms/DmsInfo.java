package com.aevi.sdk.dms;

import io.reactivex.annotations.Nullable;

/**
 * Device management service configuration
 */
public final class DmsInfo {

    private final String uin;

    DmsInfo(@Nullable String uin) {
        this.uin = uin;
    }

    /**
     * @return the unique identifier number of this device. This number isn't attached to a physical device but is
     * unique amongst the list of active devices. // TODO - Could we explain this as an "installation ID"?
     */
    public @Nullable String getUin() {
        return uin;
    }
}
