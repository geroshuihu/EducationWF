/**
 * 
 */
package com.leopard.ui.view;

import java.awt.geom.Point2D;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vaadin.hezamu.googlemapwidget.GoogleMap;
import org.vaadin.hezamu.googlemapwidget.overlay.BasicMarker;

import com.leopard.data.ConnectionHelper;
import com.leopard.ui.ExampleApplication;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 * @author Duncan
 * 
 */
@SuppressWarnings("serial")
public class RegisterTable extends Table {

	// ID SchoolID SchoolGrade Present Absent Total Longitude Latitude UpdateBy
	// Ticket
	private String[] visibleCol = { "School Name", "SchoolGrade", "Present",
			"Absent", "Total", "UpdateBy",//"Longitude", "Latitude",  "Ticket",
			"View map" };
	private String[] visibleCol1 = { "School Name", "Present", "Absent", "Total",
			"UpdateBy",  "View map" };
	private String[] fields = { "School Name", "SchoolGrade", "Present", "Absent",
			"Total", "UpdateBy", "View map" };
	private String[] fields1 = { "School Name", "Present",
			"Absent", "Total",  "UpdateBy",
			"View map" };
	IndexedContainer newDataSource = null;
	GoogleMap googleMap = null;

	public RegisterTable(String grp) {
		// TODO Auto-generated constructor stub
		setHeight(500, UNITS_PIXELS);
		setCaption("ATTENDANCE REPORT:");
		setWidth("100%");
		
		setSizeFull();

		newDataSource = getdata(grp);

		setContainerDataSource(newDataSource);
		
		setSelectable(true);
		if (grp.equals("s")) {
			setVisibleColumns(visibleCol);
		} else {
			setVisibleColumns(visibleCol1);
		}
		setImmediate(true);

	}

	private IndexedContainer getdata(String grp) {
		IndexedContainer ic = new IndexedContainer();
		ic.removeAllItems();

		Button btn = null;
		if (grp.equals("s")) {
			for (String p : fields) {
				if (p.equals("View map")) {
					ic.addContainerProperty(p, Button.class, null);
					//system.out.println("view: added");

				} else {
					ic.addContainerProperty(p, String.class, null);
				}
			}
			
			Connection c = null;
			try {
				c = ConnectionHelper.getConnection();
				PreparedStatement ps = null;
				String sql = "SELECT s.name, r.SchoolGrade, r.Present, r.Absent, r.Total, r.Longitude, r.Latitude, CONCAT(u.firstname, ' ', u.lastname) AS UpdateBy, r.Ticket FROM tRegister r, tschool s, tusers u WHERE r.SchoolID = s.id AND r.UpdateBy = u.id GROUP BY r.ID";
				ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {

					Object id = ic.addItem();

					ic.getContainerProperty(id, "School Name").setValue(
							rs.getString("s.name"));
					ic.getContainerProperty(id, "SchoolGrade").setValue(
							rs.getString("SchoolGrade"));
					ic.getContainerProperty(id, "Present").setValue(
							rs.getString("Present"));
					ic.getContainerProperty(id, "Absent").setValue(
							rs.getString("Absent"));
					ic.getContainerProperty(id, "Total").setValue(
							rs.getString("Total"));					
					ic.getContainerProperty(id, "UpdateBy").setValue(
							rs.getString("UpdateBy"));
					

					//system.out.println("Trace rtbl:=>" + rs.getString("s.name"));
					// Add button
					btn = null;
					btn = new Button();
					btn.setCaption("View");

					ic.getContainerProperty(id, "View map").setValue(btn);
					btn.setData(rs.getString("Longitude"));
					btn.addListener(new DetailInfoListener(rs
							.getString("Longitude")
							+ ","
							+ rs.getString("Latitude")
							+ ","
							+ rs.getString("s.name")));
				}
			} catch (SQLException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ConnectionHelper.close(c);
			}
		} else {
			for (String p : fields1) {
				if (p.equals("View map")) {
					ic.addContainerProperty(p, Button.class, null);
					//system.out.println("view: added");

				} else {
					ic.addContainerProperty(p, String.class, null);
				}
			}
			
			Connection c = null;
			try {
				c = ConnectionHelper.getConnection();
				PreparedStatement ps = null;
				String sql = "SELECT s.name, r.Present, r.Absent, r.Total, r.Longitude, r.Latitude, CONCAT(u.firstname, ' ', u.lastname) AS UpdateBy, r.Ticket FROM tTeacherReg r, tschool s, tusers u WHERE r.SchoolID = s.id AND u.id = r.UpdateBy GROUP BY r.ID";
				ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Object id = ic.addItem();
					ic.getContainerProperty(id, "School Name").setValue(
							rs.getString("s.name"));
					ic.getContainerProperty(id, "Present").setValue(
							rs.getString("Present"));
					ic.getContainerProperty(id, "Absent").setValue(
							rs.getString("Absent"));
					ic.getContainerProperty(id, "Total").setValue(
							rs.getString("Total"));
					ic.getContainerProperty(id, "UpdateBy").setValue(
							rs.getString("UpdateBy"));
			

					//system.out.println("Trace rtbl:=>" + rs.getString("s.name"));
					// Add button
					btn = null;
					btn = new Button();
					btn.setCaption("View");

					ic.getContainerProperty(id, "View map").setValue(btn);
					btn.setData(rs.getString("Longitude"));
					btn.addListener(new DetailInfoListener(rs
							.getString("Longitude")
							+ ","
							+ rs.getString("Latitude")
							+ ","
							+ rs.getString("s.name")));
				}
			} catch (SQLException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ConnectionHelper.close(c);
			}

		}

		

		// TODO Auto-generated method stub
		return ic;
	}

	private class DetailInfoListener implements Button.ClickListener {
		private final String myData;
		private Window mapWindow;

		public DetailInfoListener(String myData) {
			this.myData = myData;
		}

		public void buttonClick(ClickEvent event) {
			showMap(myData);
		}

		private void showMap(String myData2) {
			// TODO Auto-generated method stub
			mapWindow = new Window();
			mapWindow.setCaption("View Details");
			mapWindow.setModal(true);
			mapWindow.setWidth("50%");

			String[] k = myData.split(",");

			double lat = 0.00;
			double lon = 0.00;

			lat = Double.parseDouble(k[0]);
			lon = Double.parseDouble(k[1]);

			googleMap = new GoogleMap(ExampleApplication.getProject(),
					new Point2D.Double(lat, lon), 13);
			googleMap.setWidth("640px");
			googleMap.setHeight("480px");

			// add a marker
			googleMap.addMarker(new BasicMarker(1L,
					new Point2D.Double(lat, lon), k[2]));

			Label l = null;

			l = null;
			l = new Label();
			l.setValue(k[2]);
			mapWindow.addComponent(l);
			mapWindow.addComponent(googleMap);

			ExampleApplication.getProject().getMainWindow()
					.addWindow(mapWindow);
		}
	}

}
