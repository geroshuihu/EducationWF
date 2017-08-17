package com.leopard.ui.view;

/**
 * @author Duncan
 *
 */

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.leopard.data.ConnectionHelper;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;

/**
 * @author Duncan
 * 
 */
@SuppressWarnings("serial")
public class SchoolTable extends Table {

	/**
	 * 
	 */
	private static String[] fields = { "name", "location", "address", "region" };
	private static String[] visibleCols = { "name", "location", "address", "region" };
	IndexedContainer newDataSource = null;

	public SchoolTable() {
		// TODO Auto-generated constructor stub
		setCaption("SCHOOL LIST:");
		setWidth("100%");
		setSizeFull();
		newDataSource = null;
		newDataSource = getdata();
		setContainerDataSource(newDataSource);
		setSelectable(true);
		setVisibleColumns(visibleCols);
		setImmediate(true);
		
	}

	private IndexedContainer getdata() {
		IndexedContainer ic = new IndexedContainer();
		ic.removeAllItems();
		for (String p : fields) {
			ic.addContainerProperty(p, String.class, null);
		}

		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = null;
			String sql = "SELECT * FROM tschool WHERE status=?";
			ps = c.prepareStatement(sql);
			ps.setString(1, "1");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Object id = ic.addItem();
				ic.getContainerProperty(id, "name").setValue(
						rs.getString("name"));
				ic.getContainerProperty(id, "location").setValue(
						rs.getString("location"));
//				System.out.println("trace:"+rs.getString("location"));
				ic.getContainerProperty(id, "address").setValue(
						rs.getString("address"));
				ic.getContainerProperty(id, "region").setValue(
						rs.getString("region"));
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

}

