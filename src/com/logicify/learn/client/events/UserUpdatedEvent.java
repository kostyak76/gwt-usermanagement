package com.logicify.learn.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 4:49 PM
 */
public class UserUpdatedEvent extends GwtEvent<UserUpdatedEventHandler> {

    public static Type<UserUpdatedEventHandler> TYPE = new Type<UserUpdatedEventHandler>();

    @Override
    public Type<UserUpdatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserUpdatedEventHandler handler) {
        handler.onUserUpdated(this);
    }
}
