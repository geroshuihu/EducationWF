package com.leopard.ui.component;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Window.Notification;
import com.leopard.ui.ExampleApplication;
import com.leopard.ui.view.GeneralView;
import com.leopard.ui.view.GradeView;
import com.leopard.ui.view.ManageView;
import com.leopard.ui.view.RegisterView;
import com.leopard.ui.view.ReportsView;
import com.leopard.ui.view.SchoolView;
import com.leopard.ui.view.SubjectView;
import com.leopard.ui.view.UsersView;

@SuppressWarnings("serial")
public class Menu extends VerticalLayout implements ValueChangeListener {

	private Tree menuTree;
	private Panel panel;
	private SchoolView schoolView = null;
	private GradeView gradeView = null;
	private SubjectView subjectView = null;
	private UsersView usersView = null;
	private RegisterView registerView = null;
	private GeneralView generalView = null;
	private ReportsView reportsView = null;
	private ManageView manageView = null;

	public Menu() {

		// Visual modifiers of the menu.

		setHeight("100%");
		setMargin(true);

		// Add a tree.
		menuTree = new Tree();
		menuTree.setMultiSelect(false);

		// Instantly receive value update events.
		menuTree.setImmediate(true);

		// Add objects to the tree.
		populate(menuTree);

		panel = new Panel("NAVIGATOR");
		panel.addComponent(menuTree);
		panel.setHeight("100%");

		addComponent(panel);

		menuTree.addListener((ValueChangeListener) this);

	}

	/*
	 * Populates the tree with hard coded data.
	 * 
	 * @param anyTree the tree we want to populate with data.
	 */
	public void populate(Tree anyTree) {
		final Object[][] channels = new Object[][] {
				new Object[] { "HOME" },
				{ "MANAGE", "#SCHOOLS", "#GRADES", "#SUBJECTS", "#USERS" },
				{ "REPORTS", "#REGISTER", "#SUMMARY", "#BOOK ORDERS",
						"#WEEKLY FINANCIAL", "#MARKBOOK" } };//, "REPORTS"

		for (int i = 0; i < channels.length; i++) {
			String temp = (String) (channels[i][0]);
			anyTree.addItem(temp);

			if (channels[i].length == 1) {
				anyTree.setChildrenAllowed(temp, false);

			} else {
				for (int j = 1; j < channels[i].length; j++) {
					String temp1 = (String) (channels[i][j]);
					anyTree.addItem(temp1);
					anyTree.setParent(temp1, temp);
					anyTree.setChildrenAllowed(temp1, false);
				}

			}

		}

	}

	/*
	 * Removes an item from menuTree.
	 */
	public void removeCurrentTreeSelection() {

		if (!menuTree.isRoot(menuTree.getValue())) {

			Object id = menuTree.getParent(menuTree.getValue());
			Object id2 = menuTree.getValue();
			menuTree.select(id);
			menuTree.removeItem(id2);

		}

	}

	public Tree getTree() {
		return menuTree;
	}

	public void valueChange(ValueChangeEvent event) {

		try {
			String clickedView = menuTree.getValue().toString();
			if (clickedView.equals("#SCHOOLS")) {

				ExampleApplication.getProject().getUiHandler().viewList
						.remove(schoolView);
				getSchoolView();
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#SCHOOLS", schoolView);
			} else if (clickedView.equals("#GRADES")) {
				ExampleApplication.getProject().getUiHandler().viewList
						.remove(gradeView);
				getGradeView();
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#GRADES", gradeView);
			} else if (clickedView.equals("#SUBJECTS")) {
				ExampleApplication.getProject().getUiHandler().viewList
						.remove(subjectView);
				getSubjectView();
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#SUBJECTS", subjectView);
			} else if (clickedView.equals("#USERS")) {
				ExampleApplication.getProject().getUiHandler().viewList
						.remove(usersView);
				getUsersView();
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#USERS", usersView);
			} else if (clickedView.equals("#REGISTER")) {
				ExampleApplication.getProject().getUiHandler().viewList
						.remove(registerView);
				getRegisterView();
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#REGISTER", registerView);
			} else if (clickedView.equals("#SUMMARY")) {
				ExampleApplication.getProject().getUiHandler().viewList
						.remove(generalView);
				getGeneralView("a");
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#SUMMARY", generalView);

			} else if (clickedView.equals("#BOOK ORDERS")) {
				ExampleApplication.getProject().getUiHandler().viewList
						.remove(generalView);
				getGeneralView("b");
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#BOOK ORDERS", generalView);

			} else if (clickedView.equals("#WEEKLY FINANCIAL")) {
				ExampleApplication.getProject().getUiHandler().viewList
						.remove(generalView);
				getGeneralView("c");
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#WEEKLY FINANCIAL", generalView);

			} else if (clickedView.equals("#MARKBOOK")) {

				ExampleApplication.getProject().getUiHandler().viewList
						.remove(generalView);
				getGeneralView("d");
				ExampleApplication.getProject().getUiHandler().viewList.put(
						"#MARKBOOK", generalView);

			} else if (clickedView.equals("REPORTS")) {
				System.out.println("REPORTS");
				 ExampleApplication.getProject().getUiHandler().viewList
				 .remove(reportsView);
				 getReportsView();
				 ExampleApplication.getProject().getUiHandler().viewList.put(
				 "REPORTS", reportsView);
			

			}else if (clickedView.equals("MANAGE")){
				 ExampleApplication.getProject().getUiHandler().viewList
				 .remove(manageView);
				 getManageView();
				 ExampleApplication.getProject().getUiHandler().viewList.put(
				 "MANAGE", manageView);
			}
			ExampleApplication.getProject().getUiHandler()
					.switchView(clickedView);

		} catch (NullPointerException e) {
			// TODO: handle exception
			ExampleApplication
					.getProject()
					.getMainWindow()
					.showNotification("Kindly select from the options",
							Notification.TYPE_HUMANIZED_MESSAGE);
		}
	}

	private ManageView getManageView() {
		manageView = null;
		if(manageView==null){
			manageView = new ManageView();
		}
		return manageView;
		// TODO Auto-generated method stub
		
	}

	private GeneralView getGeneralView(String vType) {
		generalView = null;
		if (generalView == null) {
			generalView = new GeneralView(vType);
		}
		return generalView;

	}

	private RegisterView getRegisterView() {
		registerView = null;
		if (registerView == null) {
			registerView = new RegisterView();
		}

		return registerView;

	}

	private ReportsView getReportsView() {
		reportsView = null;
		if (reportsView == null) {
			reportsView = new ReportsView();
		}
		return reportsView;

	}

	private UsersView getUsersView() {
		usersView = null;
		if (usersView == null) {
			usersView = new UsersView();

		}
		return usersView;

	}

	private SubjectView getSubjectView() {
		subjectView = null;
		if (subjectView == null) {
			subjectView = new SubjectView();
		}
		return subjectView;
		// TODO Auto-generated method stub

	}

	private GradeView getGradeView() {
		gradeView = null;
		if (gradeView == null) {
			gradeView = new GradeView();
		}
		return gradeView;
		// TODO Auto-generated method stub

	}

	public SchoolView getSchoolView() {
		// System.out.println("SELECTED:" + clickedView);
		schoolView = null;
		if (schoolView == null) {
			schoolView = new SchoolView();

		}

		return schoolView;
	}
}
