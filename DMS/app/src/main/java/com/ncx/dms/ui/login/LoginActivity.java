package com.ncx.dms.ui.login;


import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ncx.dms.R;
import com.ncx.dms.core.BaseActivity;
import com.ncx.dms.core.ViewModelResult;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.McSparePart;
import com.ncx.dms.remote.dto.UserDto;

import java.util.List;

import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar loadingProgressBar;

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setFullScreen();
        hideActionBar();
        //setLandscape();
        setKeepScreenOn();

        setContentView(R.layout.activity_login);

        /*loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory(this))
                .get(LoginViewModel.class);*/

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LoginViewModel((App)getApplication());
            }
        };
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);

        loginViewModel.setCallActivityMethod(this);

        loginViewModel.getFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getViewModelResult().observe(this, new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult viewModelResult) {
                if (viewModelResult == null) {
                    return;
                }

                if (!commonHelper.isNullOrEmpty(viewModelResult.getErrorMessage())) {
                    showLoginFailed(viewModelResult.getErrorMessage());
                }else if (viewModelResult.getResultObject() != null) {
                    updateUiWithUser(((UserDto) viewModelResult.getResultObject()).getFullName());
                    callMainActivity();
                    finish();
                }
            }
        });

        super.onCreate(savedInstanceState);
    }

    private void updateUiWithUser(String message) {
        String welcome = getString(R.string.welcome) + message;
        // TODO : initiate successful logged in experience
        showLongToast(welcome);
    }

    private void showLoginFailed(String errorString) {
        showLongToast(errorString);
    }

    @Override
    public void apiResult(int requestId, Response result) {

    }

    @Override
    public void apiError(int requestId, String result) {

    }

    @Override
    public void initService() {

    }

    @Override
    public void findViewById() {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        loadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
               /*app.getApiService().getAvailableFrameNo("NCX01").enqueue(new Callback<ResultDto<List<String>>>() {
                   @Override
                   public void onResponse(Call<ResultDto<List<String>>> call, Response<ResultDto<List<String>>> response) {
                       if (response.isSuccessful()){
                           ResultDto<List<String>> result = response.body();
                       }
                   }

                   @Override
                   public void onFailure(Call<ResultDto<List<String>>> call, Throwable t) {

                   }
               });*/
               /*app.getApiService().warrantyRegister(new WarrantyRegistrationRequest()).enqueue(new Callback<ResultDto>() {
                   @Override
                   public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                       ResultDto res = response.body();
                   }

                   @Override
                   public void onFailure(Call<ResultDto> call, Throwable t) {

                   }
               });*/
            }
        });
    }

    @Override
    public void initFillData() {

    }

    @Override
    public UserDto getCurrentUser() {
        return null;
    }

    @Override
    public List<DealerDto> getCurrentDealers() {
        return null;
    }

    @Override
    public List<McSparePart> getMcSpareParts() {
        return null;
    }
}
