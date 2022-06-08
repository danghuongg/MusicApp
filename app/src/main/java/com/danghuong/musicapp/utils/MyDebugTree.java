package com.danghuong.musicapp.utils;


import timber.log.Timber;

public class MyDebugTree extends Timber.DebugTree {
    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return String.format("(%s:%s)#%s",
                element.getFileName(),
                element.getLineNumber(),
                element.getMethodName());
    }
}
