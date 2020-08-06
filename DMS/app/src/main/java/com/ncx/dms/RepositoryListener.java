package com.ncx.dms;

public interface RepositoryListener {
    void showSpotsDialog();

    void dismissSpotsDialog();

    void showShortToast(String message);

    void showLongToast(String message);
}
