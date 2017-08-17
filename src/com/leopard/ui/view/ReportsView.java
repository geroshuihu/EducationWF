package com.leopard.ui.view;

//import org.vaadin.vaadinvisualizations.PieChart;

import java.awt.Color;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.Reindeer;
import com.leopard.data.ConnectionHelper;
import com.leopard.ui.ExampleApplication;
import com.leopard.ui.view.AbstractView;

/*
 * @Author: Duncan Nyakundi
 * @Version: 1.0.0
 * 
 * 
 */
@SuppressWarnings("serial")
public class ReportsView extends AbstractView implements ValueChangeListener,
		ClickListener {

	private VerticalLayout layout;
	Timeline timeline = null;
	String sql = "SELECT f.ID, s. NAME, f.Income, f.Expenditure, f.RegDate FROM `tFinancial` f, `tschool` s WHERE f.SchoolID = s.id AND s.name LIKE ?";
	Connection c = null;
	ComboBox cb = null;
	IndexedContainer ds = null;
	String sql_listschool = "SELECT * FROM `tschool`";
	String ss = "Makgoka High School";
	Container.Indexed graphDS = null, graphDS1 = null;
	IndexedContainer container = null;
	HorizontalLayout vl = new HorizontalLayout();
	Table tbl = null;
	private Button btn = new Button();
	private Window subWindow;

	public ReportsView() {

		setHeight("100%");

		layout = new VerticalLayout();
		setCompositionRoot(layout);
		layout.setMargin(true);
		layout.setHeight("100%");
		layout.addStyleName(Reindeer.LAYOUT_BLACK);

		Label l = new Label(
				"<h1 class=\"v-label-h1\" style=\"text-align: center;\">Report Summary</h1>  "
						+ "<p style=\"text-align: center;\">Select an option from the Reports Dropdown option on left side navigator to view details.</p>",
				Label.CONTENT_XHTML);
		l.setSizeUndefined();
		l.setStyleName(Reindeer.LABEL_SMALL);
		layout.addComponent(l);// , "top:5px; center:50%");
		// layout.setComponentAlignment(l, Alignment.TOP_CENTER);

		// Add Button
		btn.setCaption("View Financial Summary");
		btn.addListener((Button.ClickListener) this);
		layout.addComponent(btn);

		// Add subwindow
		subWindow = new Window("Financial Summary");
		subWindow.setModal(true);
		subWindow.setWidth("90%");
		subWindow.setHeight("90%");
		subWindow.setClosable(true);

		// Add Financial report

		cb = new ComboBox("Select School");
		cb.setWidth("350px");
		cb.setImmediate(true);
		cb.addListener(this);
		cb.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);

		populatecb();

		// Create the timeline component
		createTimeline(ss);

		vl.setSizeUndefined();
		vl.addComponent(timeline);
		vl.addComponent(tbl);
		subWindow.addComponent(cb);
		subWindow.addComponent(vl);

		// Add Bargraph
		// PieChart pc = new PieChart();
		// pc.setSizeFull();
		// pc.setCaption("Student Attendance at : -> Alphedale High School");
		//
		// pc.add("Absent", 18);
		// pc.add("Present", 2);
		// pc.setOption("width", 400);
		// pc.setOption("height", 350);
		// pc.setOption("is3D", true);
		//
		// layout.addComponent(pc,"top:90px; left:10px");
		// layout.setComponentAlignment(l, Alignment.MIDDLE_CENTER);

	}

	/*
	 * Close method from AbstractView.
	 * 
	 * @see ui.AbstractView#close()
	 */
	protected void close() throws Exception {

	}

	private Timeline createTimeline(String ss2) {
		// TODO Auto-generated method stub
		// timeline = null;
		timeline = null;
		if (timeline == null) {
			timeline = new Timeline();
			timeline.setCaption("Finance timeline for " + ss);
			System.out.println("Finance timeline for " + ss);
			timeline.setWidth("700px");
			timeline.setHeight("400px");

			// Add some zoom levels
			timeline.addZoomLevel("Day", 86400000L);
			timeline.addZoomLevel("Week", 7 * 86400000L);
			timeline.addZoomLevel("Month", 2629743830L);
			timeline.addZoomLevel("Year", 12 * 2629743830L);

			graphDS = createContainer("f.Income", ss);
			graphDS1 = createContainer("f.Expenditure", ss);

			// Add the container
			timeline.addGraphDataSource(graphDS, "DATE",// Timeline.PropertyId.TIMESTAMP,
					Timeline.PropertyId.VALUE);
			timeline.setGraphGridColor(Color.BLUE);
			timeline.setGraphOutlineColor(graphDS, Color.ORANGE);
			timeline.setGraphShadowsEnabled(true);
			timeline.setGraphLegend(graphDS, "Income");

			// Add the container
			timeline.addGraphDataSource(graphDS1, "DATE",// Timeline.PropertyId.TIMESTAMP,
					Timeline.PropertyId.VALUE);

			timeline.setGraphOutlineColor(graphDS1, Color.BLUE);
			timeline.setGraphShadowsEnabled(true);
			timeline.setGraphLegend(graphDS1, "Expenditure");
		}
		return timeline;
	}

	private void populatecb() {

		cb.removeAllItems();
		Connection cc = null;
		try {
			cc = ConnectionHelper.getConnection();
			PreparedStatement ps = null;
			ps = cc.prepareStatement(sql_listschool);
			ResultSet rs = null;
			rs = ps.executeQuery();
			while (rs.next()) {
				cb.addItem(rs.getString("name").toUpperCase());

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionHelper.close(cc);
		}

	}

	/**
	 * Creates a graph container with random data points
	 */
	private Container.Indexed createContainer(String valu, String ss) {

		// Create the container
		container = null;
		container = new IndexedContainer();
		container.addContainerProperty("DATE",// Timeline.PropertyId.TIMESTAMP,
				Date.class, null);
		container.addContainerProperty(Timeline.PropertyId.VALUE, Float.class,
				0);
		// Lets add a month of random data
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);

		// create table
		tbl = new Table();
		tbl.addContainerProperty("INCOME", String.class, null);
		tbl.addContainerProperty("EXPENDITURE", String.class, null);
		tbl.addContainerProperty("REG. DATE", String.class, null);
		tbl.setSelectable(true);
		tbl.setImmediate(true);

		// Query financial data
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = null;
			ps = c.prepareStatement(sql);
			ps.setString(1, "%" + ss.toUpperCase() + "%");
			ResultSet rs = ps.executeQuery();
			System.out.println("sh:> " + ss);
			while (rs.next()) {
				System.out.println("INCOME:=>" + rs.getString("f.Income")
						+ " \t EXP:=>" + rs.getString("f.Expenditure")
						+ "\t Date:>" + rs.getString("f.RegDate"));
				Item item = container.addItem(cal.getTime());
				String z = rs.getString("f.RegDate");
				z = z.replace('-', '/');
				String dateString = z;
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd hh:mm:ss");
				Date convertedDate = null;
				try {
					convertedDate = dateFormat.parse(dateString);
					// System.out.println("Converted string to date : "
					// + convertedDate);
					item.getItemProperty("DATE").setValue(convertedDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				item.getItemProperty(Timeline.PropertyId.VALUE).setValue(
						rs.getString(valu));

				// Populate financial table
				tbl.addItem(
						new Object[] { rs.getString("f.Income"),
								rs.getString("f.Expenditure"),
								rs.getString("f.RegDate") },
						new Integer(rs.getString("f.ID")));
				cal.add(Calendar.MONTH, 1);
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

		return container;
	}

	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		Property src = event.getProperty();
		if (src == cb) {
			ExampleApplication
					.getProject()
					.getMainWindow()
					.showNotification("School selected :", cb.getValue() + " ",
							Notification.TYPE_TRAY_NOTIFICATION);
			ss = cb.getValue().toString();

			vl.removeAllComponents();
			createTimeline(ss);
			vl.addComponent(timeline);
			vl.addComponent(tbl);
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Property srcbutton = event.getButton();
		if (srcbutton == btn) {
			System.out.println("clicked");
			ExampleApplication.getProject().getMainWindow().addWindow(subWindow);
		}
	}

}
