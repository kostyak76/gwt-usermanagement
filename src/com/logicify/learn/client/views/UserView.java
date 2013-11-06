package com.logicify.learn.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.logicify.learn.shared.User;
import com.logicify.learn.client.presenters.UserPresenter;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/4/13
 * Time: 6:41 PM
 */
public class UserView extends Composite implements UserPresenter.View {

    interface UserUiBinder extends UiBinder<Widget, UserView> {
    }

    private static UserUiBinder ourUiBinder = GWT.create(UserUiBinder.class);

    @UiField
    TextBox firstName;

    @UiField
    TextBox lastName;

    @UiField
    TextBox email;

    @UiField
    Button okButton;

    @UiField
    Button updateButton;

    @UiField
    Button clearButton;

    @UiHandler("clearButton")
    void clearMethod(MouseMoveEvent event){
        clearFormData();
    }

    public UserView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        //init default state
        viewForAdd();
    }

    /**
     * sets empty form field
     */
    private void clearFormData(){
        firstName.setText("");
        lastName.setText("");
        email.setText("");
    }

    @Override
    public Button getOkButton() {
        return okButton;
    }

    @Override
    public Widget getAsWidget() {
        return this;
    }

    @Override
    public Button getUpdateButton() {
        return updateButton;
    }

    @Override
    public String getFormData() {
        // select al variable and create formString
        String formString;
        formString = "firstName=" + firstName.getText() + "&lastName=" + lastName.getText() + "&email=" + email.getText();
        return formString;
    }

    @Override
    public void loadUSer(User user) {
        //load user
        firstName.setText(user.firstName);
        lastName.setText(user.lastName);
        email.setText(user.email);
    }

    @Override
    public void viewForAdd() {
        //hide buttons
        _stateHelper(true);
        clearFormData();
    }

    @Override
    public void viewForUpdate() {
        //hide buttons
        _stateHelper(false);
    }

    private void _stateHelper(Boolean isAddState){
        okButton.setVisible(isAddState);
        clearButton.setVisible(isAddState);
        updateButton.setVisible(!isAddState);
    }
}