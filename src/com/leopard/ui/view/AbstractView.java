package com.leopard.ui.view;

import com.vaadin.ui.CustomComponent;



@SuppressWarnings("serial")
public abstract class AbstractView extends CustomComponent {

	protected abstract void close() throws Exception;

}

