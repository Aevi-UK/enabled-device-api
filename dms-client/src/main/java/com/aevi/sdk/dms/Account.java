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
    @NonNull
    public String getId() {
        return id;
    }

    /**
     * @return the display name of this account
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * @return the current role of this account. See {@link Role}.
     */
    @NonNull
    public Role getRole() {
        return role;
    }
}
