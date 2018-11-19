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
    @Nullable
    public String getUin() {
        return uin;
    }
}
