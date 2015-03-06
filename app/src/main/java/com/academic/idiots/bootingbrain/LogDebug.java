package com.academic.idiots.bootingbrain;

import android.util.Log;

/**
 * Created by root on 1/25/15.
 */
public class LogDebug {
    private String className;
    public LogDebug(String className){
        this.className = className;
    }
    public void logDebug(String message){
        Log.d(className, message);
    }
}
