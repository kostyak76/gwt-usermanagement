package com.logicify.learn.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.logicify.learn.shared.User;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 5:03 PM
 */
public interface EditUserEventHandler extends EventHandler {

    public void onEditEvent(EditUserEvent event, User user);
}
