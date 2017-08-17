package com.leopard.ui.view;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.OptionGroup;

@SuppressWarnings("serial")
public class RegisterView extends AbstractView implements ValueChangeListener {

	private AbsoluteLayout layout = new AbsoluteLayout();
	private OptionGroup op = new OptionGroup();
	private List<String> grp = Arrays.asList(new String[] {"Students'","Teachers'" });
	private RegisterTable rtbl = null;

	public RegisterView() {

		// Create the visual components of the view.
		layout.setMargin(true);
		layout.setWidth("100%");
		layout.setHeight("100%");
//		layout.setSpacing(true);
		
		op = new OptionGroup("Select Attendance group :", grp);
		op.select("Students'");
		op.setImmediate(true);
		op.addListener(this);				
		
		layout.addComponent(op,"top:20px; left:10px");
		layout.addComponent(getrtbl("s"),"top:80px; left:10px");
		setSizeFull();
		setCompositionRoot(layout);
	}

	@Override
	protected void close() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub

		if (event.getProperty().toString().equals("Students'")) {
			layout.removeComponent(rtbl);
			layout.addComponent(getrtbl("s"),"top:80px; left:10px");

		} else {
			layout.removeComponent(rtbl);
			layout.addComponent(getrtbl("t"),"top:80px; left:10px");
		}
	}

	private RegisterTable getrtbl(String grp) {
		// TODO Auto-generated method stub
		rtbl = null;
		if (rtbl == null) {
			rtbl = new RegisterTable(grp);
		}
		return rtbl;
	}

}
