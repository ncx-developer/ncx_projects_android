package com.ncx.dms.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ncx.dms.R;
import com.ncx.dms.core.BaseViewModel;
import com.ncx.dms.core.ViewModelResult;
import com.ncx.dms.remote.ApiService;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.ResultDto;
import com.ncx.dms.remote.dto.UserDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {



    public LoginViewModel(App app) {
        super(app);
    }

    public void login(String username, String password) {

        /*Temp */
        //loginResult.setValue(new LoginResult(new LoggedInUser(UUID.randomUUID(), "admin", "Win Aung")));

        showSpotsDialog();

        app.getApiService().login(username, password).enqueue(new Callback<ResultDto<UserDto>>() {
            @Override
            public void onResponse(Call<ResultDto<UserDto>> call, Response<ResultDto<UserDto>> response) {
                dismissSpotsDialog();
                if(response.isSuccessful()) {
                    ResultDto<UserDto> result = response.body();

                    if (result.isSuccess()) {
                        app.setCurrentUser(result.getResultObject());
                        viewModelResult.setValue(new ViewModelResult(result.getResultObject()));
                    } else {
                        viewModelResult.setValue(new ViewModelResult(result.getMessage()));
                    }
                }else{
                    viewModelResult.setValue(new ViewModelResult(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResultDto<UserDto>> call, Throwable t) {
                dismissSpotsDialog();
                String url = ApiService.URL.replace("http://", "");
                url = url.replace("/","");
                String message = t.getMessage().replace(url, "");
                viewModelResult.setValue(new ViewModelResult(message));
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            formState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            formState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            formState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 4;
    }
}
