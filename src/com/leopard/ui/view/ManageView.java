/**
 * 
 */
package com.leopard.ui.view;

/**
 * @author Duncan
 *
 */

import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.leopard.ui.view.AbstractView;

/*
 * @Author: Duncan Nyakundi
 * @Version: 1.0.0
 * 
 * 
 */
@SuppressWarnings("serial")
public class ManageView extends AbstractView {

	private VerticalLayout layout;

	/*
	 * Styles the User View.
	 */
	public ManageView() {

		setHeight("100%");

		layout = new VerticalLayout();
		setCompositionRoot(layout);
		layout.setMargin(true);
		layout.setHeight("100%");
		layout.addStyleName(Reindeer.LAYOUT_BLACK);
		System.out.println("reportview:>");
		// setStyleName("view");

		// Panel panel = new Panel("Thank you for logging in.");
		// panel.setSizeFull();
		Label l = new Label(
				"<h1 class=\"v-label-h1\" style=\"text-align: center;\">Manage:</h1>  "
						+ "<p style=\"text-align: center;\">Select an option from the dropdown Manage Option on the left side navigator to create or modify:" +
						"<ul> <li>Schools</li> <li>Grades</li><li>Subjects</li><li>Users</li></ul></p>",
				Label.CONTENT_XHTML);
		l.setSizeUndefined();
		l.setStyleName(Reindeer.LABEL_SMALL);
		layout.addComponent(l);
		layout.setComponentAlignment(l, Alignment.TOP_CENTER);

	}

	/*
	 * Close method from AbstractView.
	 * 
	 * @see ui.AbstractView#close()
	 */
	protected void close() throws Exception {

	}

}

