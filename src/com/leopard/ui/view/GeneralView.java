/**
 * 
 */
package com.leopard.ui.view;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Duncan
 *
 */
@SuppressWarnings("serial")
public class GeneralView extends AbstractView implements ValueChangeListener{

	/**
	 * 
	 */
	private VerticalLayout layout = new VerticalLayout();
	private GeneralTable gtbl = null;
	
	public GeneralView(String vType) {
	
		ViewDataSource vds = new ViewDataSource(vType);
		// TODO Auto-generated constructor stub
		layout.setMargin(true);
		layout.setWidth("100%");
		layout.setHeight("100%");
		layout.setSpacing(true);
		layout.setCaption(vds.getCaption1());
		
		//add table
		gtbl = new GeneralTable(vType);
		layout.addComponent(gtbl);
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
		
	}

}
