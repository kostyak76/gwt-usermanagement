package com.logicify.learn.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Event;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 4:35 PM
 */
public class UserAddedEvent extends GwtEvent<UserAddedEventHandler> {

    public static Type<UserAddedEventHandler> TYPE = new Type<UserAddedEventHandler>();

    @Override
    public Type<UserAddedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserAddedEventHandler handler) {
        handler.onUserAdded(this);
    }
}
