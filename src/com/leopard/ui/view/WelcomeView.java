package com.leopard.ui.view;

//import com.itmill.toolkit.ui.*;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class WelcomeView extends AbstractView {

	private VerticalLayout textLayout;

	public WelcomeView() {

		setHeight("100%");

		VerticalLayout layout = new VerticalLayout();
		setCompositionRoot(layout);

		layout.setMargin(false);
		layout.setHeight("100%");

		// Main panel to hold information shown to the unlogged user.
		Panel panel = new Panel(
				"Welcome to the Ministry of Education Web Front (WF).");
		panel.setSizeFull();
		// panel.setStyleName("default-panel");

		textLayout = new VerticalLayout();
		Label lbl4 = new Label("Please login using demo / demo.");

		String[] lb = { "											-> Student and Teacher attendance.",
				"													-> Finance", "													-> Book Order",
				"													-> Markbook", "",
				"The system is used to manage and monitor schools' : ", };
		int i = lb.length;
		while (i > 0) {
			Label lbl2 = new Label(lb[i - 1]);
			textLayout.addComponent(lbl2);
			i--;
		}
		textLayout.addComponent(lbl4);
		textLayout.setWidth("100%");
		textLayout.setHeight("100%");
		textLayout.setSizeFull();
		textLayout.addStyleName(Reindeer.LAYOUT_BLUE);
		panel.setStyleName(Reindeer.LAYOUT_BLUE);
		panel.addComponent(textLayout);

		layout.addComponent(panel);

	}

	/*
	 * Close method from AbstractView.
	 * 
	 * @see ui.AbstractView#close()
	 */
	protected void close() throws Exception {

	}

}
