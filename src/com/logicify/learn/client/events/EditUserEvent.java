package com.logicify.learn.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.logicify.learn.shared.User;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 5:02 PM
 */
public class EditUserEvent extends GwtEvent<EditUserEventHandler> {

    public static Type<EditUserEventHandler> TYPE = new Type<EditUserEventHandler>();

    private User user;

    @Override
    public Type<EditUserEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditUserEventHandler handler) {

        handler.onEditEvent(this, user);
    }

    public EditUserEvent(User user){
        super();
        this.user = user;
    }
}
