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
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author Duncan
 * 
 */
@SuppressWarnings("serial")
public class GeneralTable extends Table {

	/**
	 * 
	 */

	IndexedContainer ds = null;
	Button btn = new Button();
	GoogleMap googleMap = null;

	public GeneralTable(String vType) {
		// TODO Auto-generated constructor stub

		setHeight(350, UNITS_PIXELS);
		setWidth("100%");
		setSizeFull();
		setSelectable(true);
		setImmediate(true);

		ViewDataSource ds = new ViewDataSource(vType);
		String[][] fields = ds.fields;
		String Caption = ds.getCaption1();
		String[] visibleColumns = ds.getVisibleCols();
		String sql = ds.sql;
		datasource(fields, sql);
		setCaption(Caption);
		setVisibleColumns(visibleColumns);

	}

	private void datasource(String[][] fields, String sql) {
		// TODO Auto-generated method stub
		ds = getDs(fields, sql);
		setContainerDataSource(ds);
	}

	private IndexedContainer getDs(String[][] fields, String sql) {
		IndexedContainer ic = new IndexedContainer();
		ic.removeAllItems();

		for (String[] p : fields) {
			if (p[p.length-1].equals("View map")) {
				
				ic.addContainerProperty(p[0], Button.class, null);
				System.out.println("view: added:"+p[0]);

			} else {
				ic.addContainerProperty(p[0], String.class, null);
			}
		}

		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = null;
			ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Object id = ic.addItem();
				for (String[] p : fields) {
					if (!p[p.length-1].equals("View map")) {
//						System.out.println("track:"+p[0]+"||"+p[1]);
						ic.getContainerProperty(id, p[0]).setValue(
								rs.getString(p[1]));
					}else{
					// Add button
					btn = null;
					btn = new Button();
					btn.setCaption("View");
					System.out.println("track:"+p[0]);
					ic.getContainerProperty(id, p[0]).setValue(btn);
					btn.setData(rs.getString("Longitude"));
					btn.addListener(new DetailInfoListener(rs
							.getString("Longitude")
							+ ","
							+ rs.getString("Latitude")
							+ ","
							+ rs.getString("s.name")));
					}
				}			

			}
		} catch (SQLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionHelper.close(c);
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
