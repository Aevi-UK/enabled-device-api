package com.aevi.sdk.dms.simulator.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.TextView;

import com.aevi.sdk.dms.simulator.R;
import com.aevi.sdk.dms.simulator.app.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class SimulatorView extends DaggerAppCompatActivity {

    @BindView(R.id.uin_field) protected TextView uinField;
    @BindView(R.id.role_spinner) protected Spinner roleSpinner;

    @Inject protected ViewModelFactory<SimulatorViewModel> mVmFactory;

    private SimulatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initialiseView();
        viewModel.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                onSaveClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialiseView() {
        viewModel = ViewModelProviders.of(this, mVmFactory).get(SimulatorViewModel.class);
        viewModel.errorEvent.observe(this, this::onMessage);
        viewModel.uinUpdateEvent.observe(this, this::onNewUin);
        viewModel.accountRoleEvent.observe(this, this::onNewAccount);
        viewModel.settingsSavedEvent.observe(this, this::onSettingsSaved);
    }

    private void onSaveClicked() {
        hideKeyboard();
        String uin = uinField.getText().toString();
        int roleIndex = roleSpinner.getSelectedItemPosition();
        viewModel.save(uin, roleIndex);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(uinField.getWindowToken(), 0);
        uinField.clearFocus();
    }

    private void onMessage(@NonNull String message) {
        Snackbar.make(uinField, message, Snackbar.LENGTH_LONG).show();
    }

    private void onSettingsSaved(boolean success) {
        onMessage(getString(success  ? R.string.save_success : R.string.save_fail));
    }

    private void onNewUin(@Nullable String uin) {
        uinField.setText(uin);
    }

    private void onNewAccount(int roleIndex) {
        roleSpinner.setSelection(roleIndex);
    }
}