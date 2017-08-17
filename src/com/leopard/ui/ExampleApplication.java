package com.leopard.ui;

import com.vaadin.Application;
import com.vaadin.ui.*;

import com.vaadin.service.ApplicationContext.TransactionListener;


@SuppressWarnings("serial")
public class ExampleApplication extends Application implements TransactionListener {

	protected static ThreadLocal<ExampleApplication> thisApplication = new ThreadLocal<ExampleApplication>();

	public UiHandler ui;
	
	/*
	 *  Set ThreadLocal application.
	 *  @param ExampleApplication t	
	 */
	public static void setProject(ExampleApplication t) {
		thisApplication.set(t);
	}

	/*
	 *  Get ThreadLocal application.
	 */
	public static ExampleApplication getProject() {
		return thisApplication.get();
	}

	@Override
	public void init() {

		// sets the current application to ThreadLocal.
		setProject(this);

		// Creates the Main Window and then hands over all UI work to the
		// UiHandler
		setMainWindow(new Window("EDUCATION WF"));

//		setTheme("reindeer");
		setTheme("runo");

		ui = new UiHandler(getMainWindow());

		// Adds a TransactionListener for this class.
		getContext().addTransactionListener(this);

		// Register user change listener for UiHandler.
		addListener(ui);

	}

	/*
	 * Helper to return the uiHandler attached to a unique application.
	 */
	public UiHandler getUiHandler() {
		return thisApplication.get().ui;
	}

	/*
	 * For ThreadLocal pattern.
	 */
	public void transactionStart(Application application, Object transactionData) {
		// Same WebApplicationContext (session) may contain multiple different
		// Toolkit applications, here we are only interested of
		// TrainingApplication related transaction events.
		if (application == ExampleApplication.this) {
			thisApplication.set(this);
		}
	}

	/**
	 * For ThreadLocal pattern, remove application reference
	 */
	public void transactionEnd(Application application, Object transactionData) {
		if (application == ExampleApplication.this) {
			thisApplication.set(null);
		}
	}
}

