package com.logicify.learn.client.presenters;

import com.logicify.learn.client.common.Listener;
import com.logicify.learn.client.models.User;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/5/13
 * Time: 10:44 AM
 */
public interface UserListPresenterListener extends Listener {
    //react to AddButton Click event
    public void onAddButtonEvent();

    //react to edit event
    public void onEditEvent(User user);
}
