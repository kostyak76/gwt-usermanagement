package com.logicify.learn.client.models;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.logicify.learn.client.models.User;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 10/31/13
 * Time: 3:51 PM
 */
public class UserList<T> extends JavaScriptObject implements IsSerializable {

    protected UserList() {
    }

    public final native int length() /*-{
        return this.length;
    }-*/;

    public final native T get(int i) /*-{
        return this[i];
    }-*/;

    public static native UserList<User> convertAndGive(JavaScriptObject jso) /*-{
        return jso.data;
    }-*/;

    public final native void remove(int i) /*-{
        this.splice(i,1);
    }-*/;
}
