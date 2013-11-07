package com.logicify.learn.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/7/13
 * Time: 4:32 PM
 */
public class AppUtils {
    public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
}
