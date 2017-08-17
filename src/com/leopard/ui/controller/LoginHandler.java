package com.leopard.ui.controller;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Runo;
import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.leopard.data.User;
import com.leopard.data.businesslogic.*;
import com.leopard.ui.ExampleApplication;

@SuppressWarnings("serial")
public class LoginHandler extends VerticalLayout implements Action.Handler {

	// The login "Form".
	private final TextField userField = new TextField();
	private final TextField passwordField = new TextField();
	private final Button submit = new Button("Submit",
			new Button.ClickListener() {
				public void buttonClick(ClickEvent e) {

					login((String) userField.getValue(), (String) passwordField
							.getValue());

				}

			});
	private final Button logoutButton = new Button("Logout",
			new Button.ClickListener() {
				public void buttonClick(ClickEvent e) {

					logout();
				}
			});

	// Authentication method for the project.
	private Authentication auth = new Authentication();

	// User object retrieved from the database.
	private User user;

	// Allow input with the enter button.
	private final Action ACTION_LOGIN = new ShortcutAction("Login ",
			ShortcutAction.KeyCode.ENTER, null);

	/*
	 * Handles the login box on the page.
	 */
	@SuppressWarnings("deprecation")
	public LoginHandler() {

		// Style the login box.
		setMargin(true);
		setWidth("200");
		setHeight("120");

		// Hides the text input for the password text field.
		passwordField.setSecret(true);

		createLoginComponents();

		// Adds keyboard listener
		ExampleApplication.getProject().getMainWindow().addActionHandler(this);
	}

	/*
	 * Try to log in and set credentials.
	 * 
	 * @param loginname login name value retrieved from the form
	 * @param password login password value retrieved from the form
	 */
	private void login(String loginname, String password) {

		// Empty the text boxes.
		userField.setValue("");
		passwordField.setValue("");

		// Try to authenticate the user.
		user = auth.Authenticate(loginname, password);

		if (user != null) {

			// Sets the application user and sends out an event to inform that
			// the user has logged in. UiHandler will receive this event.
			ExampleApplication.getProject().setUser(
					(Object) user.getFirstName() + " ");
			

			// Remove the action handler to the Enter key.
			ExampleApplication.getProject().getMainWindow()
					.removeActionHandler(this);

		} else {

			getApplication().getMainWindow().showNotification(
					"Login Failed, try using demo / demo");

		}

	}

	/*
	 * Inserts the login-"Form" for the user.
	 */
	public void createLoginComponents() {

		// Clears all current components in case the user already logged in.
		removeAllComponents();

		// Adds the "Form" for the login event.
		addComponent(userField);
		setComponentAlignment(userField, Alignment.TOP_RIGHT);
		userField.setInputPrompt("Username");

		addComponent(passwordField);
		setComponentAlignment(passwordField, Alignment.MIDDLE_RIGHT);
		passwordField.setInputPrompt("Password");

		addComponent(submit);
		setComponentAlignment(submit, Alignment.BOTTOM_RIGHT);
		submit.setStyleName(Runo.BUTTON_DEFAULT);

	}

	/*
	 * Creates the login information box and the logout button.
	 */
	public void createLogoutComponents() {

		// User name
		Label welcome = new Label("Welcome, ");
		Label person = new Label(user.getFirstName() + " " + user.getLastName());

		// Clears all current components.
		removeAllComponents();

		VerticalLayout vert = new VerticalLayout();
		vert.setWidth("150");		
		
		vert.addComponent(welcome);
		vert.addComponent(person);
		logoutButton.setHeight(38, UNITS_PIXELS);
		logoutButton.setIcon(new ThemeResource("icon/logout.png"));
		vert.addComponent(logoutButton);
		addComponent(vert);
		setComponentAlignment(vert, Alignment.BOTTOM_RIGHT);

	}

	/*
	 * Used to inform the application that a user has logged off.
	 */
	public void logout() {

		// Sets the current user as NULL and fires an UserChangeEvent.
		ExampleApplication.getProject().setUser(null);
		user = null;

		// Add back the ActionHandler for the Enter key.
		ExampleApplication.getProject().getMainWindow().addActionHandler(this);
	}

	public Action[] getActions(Object target, Object sender) {
		return new Action[] { ACTION_LOGIN };
	}

	public void handleAction(Action action, Object sender, Object target) {
		// Handle only events that originate from Login window

		if (action == ACTION_LOGIN) {
			login((String) userField.getValue(), (String) passwordField
					.getValue());
		}

	}

}
