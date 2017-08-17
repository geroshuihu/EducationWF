package com.leopard.ui;

import java.util.HashMap;

import com.vaadin.Application.UserChangeEvent;
import com.vaadin.Application.UserChangeListener;
import com.vaadin.ui.*;
import com.leopard.ui.component.Header;
import com.leopard.ui.component.Menu;
import com.leopard.ui.view.AbstractView;
import com.leopard.ui.view.GeneralView;
import com.leopard.ui.view.GradeView;
import com.leopard.ui.view.ManageView;
import com.leopard.ui.view.RegisterView;
import com.leopard.ui.view.ReportsView;
import com.leopard.ui.view.SchoolView;
import com.leopard.ui.view.SubjectView;
import com.leopard.ui.view.UserView;
import com.leopard.ui.view.UsersView;
import com.leopard.ui.view.WelcomeView;

@SuppressWarnings("serial")
public class UiHandler extends VerticalLayout implements UserChangeListener {

	/**
	 * 
	 */
	
	// Portions of the application that exist at all times.
	private Header header;
	private WelcomeView defaultView;
	private SchoolView schoolView;
	private GradeView gradeView;
	private SubjectView subjectView;
	private UsersView usersView;
	private RegisterView registerView;
	private GeneralView generalView;
	private ReportsView reportsView;
	private ManageView manageView;
	
	// The views that are shown to logged users.
	private UserView userView;


	// Components shown to logged users.
	private Menu menu;
	@SuppressWarnings("deprecation")
	private SplitPanel menusplit;

	// Used to keep track of the current main view.

	public HashMap<String, AbstractView> viewList = new HashMap<String, AbstractView>();
	

	/*
	 * Constructor of the handler. Creates and styles all default components.
	 * 
	 * @param window main window of the application
	 */
	@SuppressWarnings("deprecation")
	public UiHandler(Window window) {

		String vType = "a";
		header = new Header();
		defaultView = new WelcomeView();
		schoolView = null;
		schoolView = new SchoolView();
		gradeView = new GradeView();
		subjectView = new SubjectView();
		usersView = new UsersView();
		registerView = new RegisterView();
		generalView = new GeneralView(vType);
		reportsView = new ReportsView();
		manageView = new ManageView();
		
		setSizeFull();
		setMargin(true);

		// Add the main components for the UI.

		addComponent(header);
		addComponent(defaultView);

		setComponentAlignment(header, Alignment.TOP_CENTER);
		setComponentAlignment(defaultView, Alignment.TOP_CENTER);

		setExpandRatio(defaultView, 1);
		this.setSpacing(true);
		window.setLayout(this);
		
	}

	/*
	 * All the UI logic in the event that a user logs in.
	 */
	@SuppressWarnings("deprecation")
	public void userLoggedIn() {

		// initialization of the user specific portions of the UI and
		// add a split panel to differentiate between menu and main view.
		userView = new UserView();
		menu = new Menu();
		schoolView = new SchoolView();
		gradeView = new GradeView();
		subjectView = new SubjectView();
		usersView = new UsersView();
		registerView = new RegisterView();
		generalView = new GeneralView("b");
		reportsView = new ReportsView();
		manageView = new ManageView();
		
		// Hard code adding of the user specific views to the view list.
		
		viewList.put("HOME", userView);
		viewList.put("#SCHOOLS", schoolView);
		viewList.put("#GRADES", gradeView);
		viewList.put("#SUBJECTS", subjectView);
		viewList.put("#USERS", usersView);
		viewList.put("#REGISTER", registerView);
		viewList.put("#SUMMARY", generalView);
		viewList.put("REPORTS", reportsView);
		viewList.put("MANAGE", manageView);
		
		menusplit = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);

		// Adds the splitbar to the layout.
		addComponent(menusplit, 1);

		// Visual modifiers of the splitpanel.
		setExpandRatio(menusplit, 1);
		menusplit.setSplitPosition(200, SplitPanel.UNITS_PIXELS);

		// Remove the default view and show the user specifc one.
		removeComponent(defaultView);
		addComponent(userView);

		// Set the menu on the left side of the split.
		menusplit.setFirstComponent(menu);

		// Set the Welcome View on the right side.
		setMainView(userView);

		// Inform the different parts of the UI that the user has logged in.
		header.userLoggedIn();
		

		ExampleApplication.getProject().getMainWindow().showNotification(
				"Login Successful");

	}

	/*
	 * Reverts back to the default view and removes all the user specific
	 * content.
	 */
	public void userLoggedOut() {

		// If components exist.
		try {
			// Remove the split panel including menu and userView.
			removeComponent(menusplit);

			// Add the default view.
			addComponent(defaultView, 1);
			setExpandRatio(defaultView, 1);

			// Tell the other UI components that the user logged off.
			header.userLoggedOut();

		} catch (Exception e) {

		}
	}

	/*
	 * Updates the main view when the user clicks an item in the menu.
	 * 
	 * @param event Event
	 */
	public void switchView(String viewName) {
		setMainView(viewList.get(viewName));
	}

	/*
	 * Helper method for setting the main view.
	 * 
	 * @param c View that we want to display
	 */
	public void setMainView(AbstractView c) {

		menusplit.setSecondComponent(c);

	}

	/*
	 * Closes the view and removes it from the menu.
	 */
	public void closeCurrentView() throws Exception {

		// Remove the view from the view list.
		viewList.remove(menu.getTree().getValue().toString());
		setMainView(userView);
		menu.removeCurrentTreeSelection();

	}

	public void applicationUserChanged(UserChangeEvent event) {


				if (event.getNewUser() == null) {
					userLoggedOut();
				} else {
					userLoggedIn();
				}

	}

}
