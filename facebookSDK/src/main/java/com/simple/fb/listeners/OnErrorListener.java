package com.simple.fb.listeners;

public interface OnErrorListener {
    void onException(Throwable throwable);

    void onFail(String reason);
}