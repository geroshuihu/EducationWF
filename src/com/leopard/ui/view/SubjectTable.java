/**
 * 
 */
package com.leopard.ui.view;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.leopard.data.ConnectionHelper;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;

/**
 * @author Duncan
 * 
 */
@SuppressWarnings("serial")
public class SubjectTable extends Table {

	/**
	 * 
	 */
	private String[] visibleCol = { "name", "code" };
	private String[] fields = { "name", "code" };
	IndexedContainer newDataSource = getdata();

	public SubjectTable(final Form sfrm) {
		// TODO Auto-generated constructor stub
		setCaption("SUBJECT LIST:");
		setSizeFull();
		setContainerDataSource(newDataSource);
		setSelectable(true);
		setVisibleColumns(visibleCol);
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
			String sql = "SELECT * FROM tsubject";
			ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Object id = ic.addItem();
				ic.getContainerProperty(id, "name").setValue(
						rs.getString("name"));
				ic.getContainerProperty(id, "code").setValue(
						rs.getString("code"));
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
