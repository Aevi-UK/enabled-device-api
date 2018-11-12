package com.aevi.sdk.dms;

import io.reactivex.annotations.NonNull;

/**
 * Account which can be used to log into a device
 */
public final class Account {

    public enum Role {
        OPERATOR,
        MANAGER,
        TECHNICIAN
    }

    private final String id;
    private final String name;
    private final Role role;

    Account(@NonNull String id, @NonNull String name, @NonNull Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    /**
     * @return the unique identifier of this account
     */
    public @NonNull String getId() {
        return id;
    }

    /**
     * @return the display name of this account
     */
    public @NonNull String getName() {
        return name;
    }

    /**
     * @return the current role of this account. See {@link Role}.
     */
    public @NonNull Role getRole() {
        return role;
    }
}
