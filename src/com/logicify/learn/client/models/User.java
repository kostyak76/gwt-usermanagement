package com.logicify.learn.client.models;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 10/31/13
 * Time: 3:52 PM
 */
public class User extends JavaScriptObject {

    protected User() {
    }

    public final native String getFirstName() /*-{
        return this.firstName;
    }-*/;

    public final native String getLastName() /*-{
        return this.lastName;
    }-*/;

    // returns email
    public final native String getEmail() /*-{
        return this.email;
    }-*/;

    // returns id
    public final native String getId() /*-{
        return this._id + '';
    }-*/;
}
