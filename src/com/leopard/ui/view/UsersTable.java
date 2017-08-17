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
public class UsersTable extends Table {

	/**
	 * 
	 */

	private static String[] fields = { "username", "password", "firstname",
			"lastname", "accesslevel" };
	private static String[] visibleCols = { "firstname", "lastname" };
	IndexedContainer newDataSource = getdata();

	public UsersTable() {
		// TODO Auto-generated constructor stub
		setCaption("MANAGE USERS LIST:");
		setSizeFull();
		new Table();
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
			String sql = "SELECT * FROM tusers";
			ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Object id = ic.addItem();
				ic.getContainerProperty(id, "firstname").setValue(
						rs.getString("firstname"));
				ic.getContainerProperty(id, "lastname").setValue(
						rs.getString("lastname"));
				ic.getContainerProperty(id, "username").setValue(
						rs.getString("username"));
				ic.getContainerProperty(id, "password").setValue(
						rs.getString("password"));
				ic.getContainerProperty(id, "accesslevel").setValue(
						rs.getString("accesslevel"));
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
