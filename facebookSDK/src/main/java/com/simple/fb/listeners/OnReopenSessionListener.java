package com.simple.fb.listeners;

import com.simple.fb.Permission;

public interface OnReopenSessionListener {
    void onSuccess();

    void onNotAcceptingPermissions(Permission.Type type);
}
