package com.logicify.learn.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created with IntelliJ IDEA.
 * @author : kostyak
 * Date: 11/4/13
 * Time: 12:28 PM
 */

/**
 * abstract class to instantiate AsyncCallback with json data received
 * is typical for that project
 */
public abstract class AsyncCallbackJSON implements AsyncCallback<String>{
    @Override
    public void onFailure(Throwable caught) {
        // implement error handler for better user experience
        Window.alert("Error while fetching data...\n"+ caught.getMessage());
    }

    @Override
    public void onSuccess(String result) {
        // parse result as json representations
        JSONObject jsonObject = JSONParser.parseStrict(result).isObject();
        doStuffWithObject(jsonObject);
    }

    /**
     * define to fetch a correct data
     * @param obj define concrete operations with parsed JSONObject
     */
    public abstract void doStuffWithObject(JSONObject obj);
}
