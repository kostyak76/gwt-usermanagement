package com.logicify.learn.client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 4:59 PM
 */
public interface AddUserEventHandler extends EventHandler {

    public void onAddUserEvent(AddUserEvent event);
}
