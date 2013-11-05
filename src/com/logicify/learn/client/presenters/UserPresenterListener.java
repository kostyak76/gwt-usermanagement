package com.logicify.learn.client.presenters;

import com.logicify.learn.client.common.Listener;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/5/13
 * Time: 9:44 AM
 */
public interface UserPresenterListener extends Listener {
    /**
     * run after user added successfully
     */
    public void onAddButtonEvent();

    /**
     * run after user updated successfully
     */
    public void onUpdatedEvent();
}
