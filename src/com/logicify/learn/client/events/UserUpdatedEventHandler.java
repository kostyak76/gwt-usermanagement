package com.logicify.learn.client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 4:50 PM
 */
public interface UserUpdatedEventHandler extends EventHandler {

    public void onUserUpdated(UserUpdatedEvent event);
}
