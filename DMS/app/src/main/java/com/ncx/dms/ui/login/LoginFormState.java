package com.ncx.dms.ui.login;

import androidx.annotation.Nullable;

import com.ncx.dms.core.BaseFormState;

/**
 * Data validation state of the login form.
 */
class LoginFormState extends BaseFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
}
