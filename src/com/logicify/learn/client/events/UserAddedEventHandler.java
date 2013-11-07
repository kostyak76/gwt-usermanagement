package com.logicify.learn.client.events;


import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 4:39 PM
 */
public interface UserAddedEventHandler extends EventHandler {

    public void onUserAdded(UserAddedEvent event);
}
