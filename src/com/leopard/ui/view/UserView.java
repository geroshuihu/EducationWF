package com.leopard.ui.view;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class UserView extends AbstractView {

	private VerticalLayout layout;

	/*
	 * Styles the User View.
	 */
	public UserView() {

		setHeight("100%");

		layout = new VerticalLayout();
		setCompositionRoot(layout);
		layout.setMargin(true);
		layout.setHeight("100%");
		layout.addStyleName(Reindeer.LAYOUT_BLUE);

//		setStyleName("view");

		// Panel panel = new Panel("Thank you for logging in.");
		// panel.setSizeFull();
		Label l = new Label(
				"<h1 class=\"v-label-h1\" style=\"text-align: center;\">Welcome</h1>  "
						+ "<p style=\"text-align: center;\">Select an option from the left side navigator to begin.</p>",
				Label.CONTENT_XHTML);
		l.setSizeUndefined();
		l.setStyleName(Reindeer.LABEL_SMALL);
		layout.addComponent(l);
		layout.setComponentAlignment(l, Alignment.MIDDLE_CENTER);

		// panel.addComponent(l);

		// layout.addComponent(panel);

	}

	/*
	 * Close method from AbstractView.
	 * 
	 * @see ui.AbstractView#close()
	 */
	protected void close() throws Exception {

	}

}
