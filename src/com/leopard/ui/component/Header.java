package com.leopard.ui.component;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.leopard.ui.controller.LoginHandler;

public class Header extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Header Components
	private final Embedded em = new Embedded("", new ThemeResource(
			"images/wf_logo.png"));
	private final LoginHandler loginhandler = new LoginHandler();

	public Header() {

		setWidth("100%");
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setScrollable(false);
		HorizontalLayout layout = new HorizontalLayout();

		panel.setHeight("140");

		// Create the visual components of the layout.
		layout.addComponent(em);
		layout.setComponentAlignment(em, Alignment.TOP_LEFT);
		layout.setWidth("100%");

		// Adds the login-"box" to the header.
		layout.addComponent(loginhandler);
		layout.setComponentAlignment(loginhandler, Alignment.BOTTOM_RIGHT);
		panel.addComponent(layout);

		layout.setComponentAlignment(loginhandler, Alignment.MIDDLE_RIGHT);
		addComponent(panel);
	}

	/*
	 * Method to change the visuals of the header when a user logs in.
	 */
	public void userLoggedIn() {

		// If for some reason the loginhandler has not been initialized.
		try {
			loginhandler.setSizeFull();
			loginhandler.createLogoutComponents();

		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Method to change the visuals of the header when a user logs out.
	 */
	public void userLoggedOut() {

		// If for some reason the loginhandler has not been initialized.
		try {

			loginhandler.createLoginComponents();

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
