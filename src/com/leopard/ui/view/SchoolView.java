/**
 * 
 */
package com.leopard.ui.view;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.leopard.data.ConnectionHelper;
import com.leopard.ui.ExampleApplication;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * @author Duncan
 * 
 */
public class SchoolView extends AbstractView implements ValueChangeListener,
		ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Form frm = new Form();
	VerticalLayout layout = new VerticalLayout();
	private SchoolTable stbl = null;
	// private static String[] fields = { "name", "location", "address",
	// "region" };
	private TextField txt1 = new TextField("NAME");
	private TextField txt2 = new TextField("LOCATION");
	private TextField txt3 = new TextField("ADDRESS");
	private TextField txt4 = new TextField("REGION");
	private Button btnCancel = new Button("CANCEL");
	private Button btnDelete = new Button("DELETE");
	private Button btnNew = new Button("NEW");
	private Button btnUpdate = new Button("UPDATE");

	private Window newItem;

	public SchoolView() {
		// TODO Auto-generated constructor stub
		
//		VerticalSplitPanel hsp = new VerticalSplitPanel();
		// Add table to the layout
		HorizontalLayout h1 = new HorizontalLayout();
		h1.setWidth("100%");
//		h1.setSpacing(true);
		h1.setMargin(true);
		h1.setSizeFull();
		
		stbl = null;
		stbl = new SchoolTable();
		stbl.addListener(this);
		stbl.setNullSelectionAllowed(true);
		frm.setCaption("School Details Form");
		frm.setWidth("100%");
		frm.setSizeFull();
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		removeForm();
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		layout.setHeight("100%");
		layout.setWidth("50%");
		layout.setMargin(true);
		layout.setSizeFull();
		
		h1.addComponent(stbl);
		h1.addComponent(layout);
		
		h1.setExpandRatio(stbl, 3);
		h1.setExpandRatio(layout, 1);
		
		layout.addComponent(frm);
		layout.addComponent(txt1);
		layout.addComponent(txt2);
		layout.addComponent(txt3);
		layout.addComponent(txt4);

		// add buttons
		HorizontalLayout h = new HorizontalLayout();

		h.addComponent(btnNew);
		h.addComponent(btnUpdate);
		h.addComponent(btnCancel);
		h.addComponent(btnDelete);

		btnNew.addListener((Button.ClickListener) this);
		btnUpdate.addListener((Button.ClickListener) this);
		btnCancel.addListener((Button.ClickListener) this);
		btnDelete.addListener((Button.ClickListener) this);

		layout.addComponent(h);

		// Create the visual components of the view.
		setSizeFull();
		setCompositionRoot(h1);
	}

	@Override
	protected void close() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		if (event.getProperty() == stbl) {
			showForm();
			String name = stbl.getContainerProperty(stbl.getValue(), "name")
					.getValue().toString();
			//system.out.println("NAME:" + name + "\n id:" + stbl.getValue());
			String location = stbl
					.getContainerProperty(stbl.getValue(), "location")
					.getValue().toString();
			String address = stbl
					.getContainerProperty(stbl.getValue(), "address")
					.getValue().toString();
			String region = stbl
					.getContainerProperty(stbl.getValue(), "region").getValue()
					.toString();
			String[] z = { name, location, address, region };
			txt1.setValue(z[0]);
			txt2.setValue(z[1]);
			txt3.setValue(z[2]);
			txt4.setValue(z[3]);

		}
	}

	private void showForm() {
		// TODO Auto-generated method stub
		txt1.setVisible(true);
		txt2.setVisible(true);
		txt3.setVisible(true);
		txt4.setVisible(true);

		btnUpdate.setVisible(true);
		btnCancel.setVisible(true);
		btnDelete.setVisible(true);

		btnNew.setVisible(false);
	}

	private void removeForm() {
		// TODO Auto-generated method stub
		txt1.setVisible(false);
		txt2.setVisible(false);
		txt3.setVisible(false);
		txt4.setVisible(false);

		btnUpdate.setVisible(false);
		btnCancel.setVisible(false);
		btnDelete.setVisible(false);

		btnNew.setVisible(true);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Property src = event.getButton();
		if (src == btnCancel) {
			removeForm();
		} else if (src == btnDelete) {
			deleteRecord();
		} else if (src == btnUpdate) {
			updateRecord();
		} else if (src == btnNew) {
			try {
				showDialog();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void deleteRecord() {
		// TODO Auto-generated method stub
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionHelper.getConnection();
			String sql = "UPDATE tschool SET status=? WHERE id=?";
			ps = c.prepareStatement(sql);
			ps.setString(1, "0");
			ps.setString(2, stbl.getValue().toString());
			ps.executeUpdate();

			ExampleApplication
					.getProject()
					.getMainWindow()
					.showNotification(
							"Successfully deactivated School:",
							((IndexedContainer) stbl.getContainerDataSource())
									.getContainerProperty(stbl.getValue(),
											"name").toString(),
							Notification.TYPE_TRAY_NOTIFICATION);
			((IndexedContainer) stbl.getContainerDataSource()).removeItem(stbl
					.getValue());

			removeForm();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateRecord() {
		// TODO Auto-generated method stub
		String a1 = txt1.getValue().toString();
		String a2 = txt2.getValue().toString();
		String a3 = txt3.getValue().toString();
		String a4 = txt4.getValue().toString();
		String a5 = stbl.getValue().toString();

		// Validate school name
		if (a1.equals("") || a1.length() == 0) {
			ExampleApplication
					.getProject()
					.getMainWindow()
					.showNotification("Invalid", " School name*",
							Notification.TYPE_ERROR_MESSAGE);
		} else {

			Connection c = null;
			try {
				c = ConnectionHelper.getConnection();
				PreparedStatement ps = null;
				String sql = "UPDATE tschool SET name=?, location=?,address=?, region=? WHERE id = ?";
				ps = c.prepareStatement(sql);
				ps.setString(1, a1);
				ps.setString(2, a2);
				ps.setString(3, a3);
				ps.setString(4, a4);
				ps.setString(5, a5);
				long z = ps.executeUpdate();
				if (z == 1) {
					ExampleApplication.getProject().getMainWindow()
							.showNotification("UPDATED SCHOOL:", a1);
					removeForm();

					stbl.getItem(stbl.getValue()).getItemProperty("name")
							.setValue(a1);
					stbl.getItem(stbl.getValue()).getItemProperty("location")
							.setValue(a2);
					stbl.getItem(stbl.getValue()).getItemProperty("address")
							.setValue(a3);
					stbl.getItem(stbl.getValue()).getItemProperty("region")
							.setValue(a4);

					stbl.setValue(stbl.getValue());
					stbl.setCurrentPageFirstItemId(stbl.getValue());
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ConnectionHelper.close(c);
			}
		}
	}

	/*
	 * Close method from AbstractView.
	 * 
	 * @see ui.AbstractView#close()
	 */
	@SuppressWarnings({ "deprecation", "serial" })
	protected void showDialog() throws Exception {
		HorizontalLayout bl = new HorizontalLayout();

		newItem = new Window("Add New School Form");
		newItem.setModal(true);
		newItem.setReadOnly(true);
		newItem.setClosable(true);
		newItem.setWidth("40%");
		VerticalLayout l = (VerticalLayout) newItem.getLayout();

		l.addComponent(new Label("*Ensure school name is valid"));
		frm.setHeight("100%");
		frm.setWidth("100%");
		frm.setImmediate(true);
		frm.setSizeFull();

		l.addComponent(frm);
		
		txt1.setValue("");
		txt2.setValue("");
		txt3.setValue("");
		txt4.setValue("");
		
		l.addComponent(txt1);
		l.addComponent(txt2);
		l.addComponent(txt3);
		l.addComponent(txt4);

		txt1.setVisible(true);
		txt2.setVisible(true);
		txt3.setVisible(true);
		txt4.setVisible(true);

		// Save / Cancel buttons

		Button yes = new Button("Save", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				Connection c = null;
				try {
					if (txt1.getValue().toString().length() != 0) {
						String b1 = txt1.getValue().toString();
						String b2 = txt2.getValue().toString();
						String b3 = txt3.getValue().toString();
						String b4 = txt4.getValue().toString();

						c = ConnectionHelper.getConnection();
						PreparedStatement ps = null;
						String sql = "INSERT INTO tschool (name,location,address,region) VALUES (?,?,?,?)";
						ps = c.prepareStatement(sql);
						ps.setString(1, b1);
						ps.setString(2, b2);
						ps.setString(3, b3);
						ps.setString(4, b4);

						ps.executeUpdate();
						ExampleApplication.getProject().getMainWindow()
								.removeWindow(newItem);

						ExampleApplication
								.getProject()
								.getMainWindow()
								.showNotification("Successfully added : ",
										b1 + " ",
										Notification.TYPE_TRAY_NOTIFICATION);

						// insert item to the container
						// ((IndexedContainer)stbl.getContainerDataSource()).addItemAfter(previousItemId,
						// newItemId);

					} else {
						ExampleApplication
								.getProject()
								.getMainWindow()
								.showNotification("School name ", "is Invalid",
										Notification.TYPE_ERROR_MESSAGE);
					}

				} catch (Exception e) {

					e.printStackTrace();
				} finally {
					ConnectionHelper.close(c);
				}
			}

		});

		Button no = new Button("Cancel", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				ExampleApplication.getProject().getMainWindow()
						.removeWindow(newItem);
			}

		});

		bl.addComponent(yes);
		bl.addComponent(no);
		l.addComponent(bl);
		ExampleApplication.getProject().getMainWindow().addWindow(newItem);

	}
}
