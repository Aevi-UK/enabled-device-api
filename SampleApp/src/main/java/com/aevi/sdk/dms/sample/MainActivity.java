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

package com.aevi.sdk.dms.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aevi.sdk.dms.DmsClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.serial_field) protected TextView serialField;
    @BindView(R.id.uin_field) protected TextView uinField;
    @BindView(R.id.role_field) protected TextView roleField;
    @BindView(R.id.error_field) protected TextView errorField;

    private DmsClient dmsClient;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dmsClient = new DmsClient(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (disposables != null) disposables.dispose();
    }

    @Override
    protected void onResume() {
        super.onResume();

        disposables = new CompositeDisposable();
        subscribeToDmsInfo();
        subscribeToDmsAccount();
    }

    private void subscribeToDmsInfo() {
        disposables.add(dmsClient
            .info()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(dmsInfo -> {
                onData(serialField, dmsInfo.getSerial());
                onData(uinField, dmsInfo.getUin());
            }, this::onError));
    }

    private void subscribeToDmsAccount() {
        disposables.add(dmsClient
            .loggedInAccount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                account -> onData(roleField, account.getRole().name()),
                this::onError));
    }

    private void onData(TextView textView, String data) {
        errorField.setVisibility(View.GONE);
        textView.setText(data);
    }

    private void onError(Throwable error) {
        if (isUnknownUriError(error)) {
            errorField.setText(R.string.content_provider_error);
        } else {
            errorField.setText(getString(R.string.generic_error, error.getMessage()));
        }

        errorField.setVisibility(View.VISIBLE);
    }

    private boolean isUnknownUriError(Throwable error) {
        return (error instanceof IllegalArgumentException &&
                error.getMessage().startsWith("Unknown URI"));
    }
}
