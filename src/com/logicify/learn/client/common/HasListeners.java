package com.logicify.learn.client.common;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/5/13
 * Time: 10:26 AM
 */
public class HasListeners<T extends Listener> {
    ArrayList<T> listeners = new ArrayList<T>();

    public void addListener(T listener) {
        listeners.add(listener);
    }

    // notify listeners
    protected void notifyListeners(NotifyListenersCallback callback) {
        for (T listener : listeners) {
            callback.onCallback(listener);
        }
    }
}