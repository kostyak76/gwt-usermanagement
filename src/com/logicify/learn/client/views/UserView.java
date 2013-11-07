package com.logicify.learn.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
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
    DivElement firstNameError;

    @UiField
    TextBox lastName;

    @UiField
    DivElement lastNameError;

    @UiField
    TextBox email;

    @UiField
    DivElement emailError;

    @UiField
    Button okButton;

    @UiField
    Button updateButton;

    @UiField
    Button clearButton;

    @UiHandler("clearButton")
    void clearMethod(ClickEvent event) {
        clearFormData();
    }

    @UiHandler("firstName")
    void changeFirstName(KeyUpEvent event) {
        if (checkFirstName()) {
            firstNameError.addClassName("lg-hidden");
        } else {
            firstNameError.removeClassName("lg-hidden");
        }
    }

    @UiHandler("lastName")
    void changeLastName(KeyUpEvent event) {
        if (checkLastName()) {
            lastNameError.addClassName("lg-hidden");
        } else {
            lastNameError.removeClassName("lg-hidden");
        }
    }

    @UiHandler("email")
    void changeEmail(KeyUpEvent event) {
        if (checkEmail()) {
            emailError.addClassName("lg-hidden");
        } else {
            emailError.removeClassName("lg-hidden");
        }
    }

    public UserView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        //init default state
        viewForAdd();
        errorDefaultState();
    }

    private void errorDefaultState(){
        firstNameError.addClassName("lg-hidden");
        lastNameError.addClassName("lg-hidden");
        emailError.addClassName("lg-hidden");
    }

    /**
     * sets empty form field
     */
    private void clearFormData() {
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
        errorDefaultState();
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

    private void _stateHelper(Boolean isAddState) {
        okButton.setVisible(isAddState);
        clearButton.setVisible(isAddState);
        updateButton.setVisible(!isAddState);
    }

    /**
     * check if data valid
     *
     * @return result of checks
     */
    @Override
    public boolean isDataValid() {

        boolean response = true;

        //check firstName
        if (!checkFirstName()) {
            response = false;
        }

        //check lastName
        if (!checkLastName()) {
            response = false;
        }

        //check email
        if (!checkEmail()) {
            response = false;
        }

        return response;
    }

    private boolean checkFirstName() {
        int min = 1;
        int max = 10;
        String value = firstName.getText();
        return isLengthBetween(min, max, value);
    }

    private boolean checkLastName() {
        int min = 1;
        int max = 20;
        String value = lastName.getText();
        return isLengthBetween(min, max, value);
    }

    private boolean checkEmail() {
        String value = email.getValue();
        return isValidEmail(value);
    }

    private boolean isLengthBetween(int min, int max, String valueForCheck) {
        int currentLength = valueForCheck.length();

        if (currentLength >= min && currentLength <= max) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {

        RegExp regExp = RegExp.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$", "i");
        return regExp.test(email);
    }
}