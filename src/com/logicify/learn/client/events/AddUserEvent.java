package com.logicify.learn.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 4:59 PM
 */
public class AddUserEvent extends GwtEvent<AddUserEventHandler> {

    public static Type<AddUserEventHandler> TYPE = new Type<AddUserEventHandler>();

    @Override
    public Type<AddUserEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddUserEventHandler handler) {

        handler.onAddUserEvent(this);
    }
}
