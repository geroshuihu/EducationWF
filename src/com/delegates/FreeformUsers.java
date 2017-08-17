/**
 * 
 */
package com.delegates;

/**
 * @author Duncan
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.vaadin.addon.sqlcontainer.RowItem;
import com.vaadin.addon.sqlcontainer.TemporaryRowId;
import com.vaadin.addon.sqlcontainer.Util;
import com.vaadin.addon.sqlcontainer.query.FreeformStatementDelegate;
import com.vaadin.addon.sqlcontainer.query.OrderBy;
import com.vaadin.addon.sqlcontainer.query.generator.StatementHelper;
import com.vaadin.addon.sqlcontainer.query.generator.filter.QueryBuilder;
import com.vaadin.data.Container.Filter;

@SuppressWarnings("serial")
public class FreeformUsers implements FreeformStatementDelegate {

	private List<Filter> filters;
	private List<OrderBy> orderBys;
	private String sql = "SELECT * FROM tusers ";
	private String sql_count = "SELECT COUNT(*) FROM tusers";
	private String sql_where = "SELECT * FROM tusers WHERE id = ?";
	private String sql_delete = "DELETE FROM tusers WHERE id = ?";

	@Deprecated
	public String getQueryString(int offset, int limit)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Use getQueryStatement method.");
	}

	public StatementHelper getQueryStatement(int offset, int limit)
			throws UnsupportedOperationException {
		StatementHelper sh = new StatementHelper();
		StringBuffer query = new StringBuffer(sql);
		if (filters != null) {
			query.append(QueryBuilder.getWhereStringForFilters(filters, sh));
		}
		query.append(getOrderByString());
		if (offset != 0 || limit != 0) {
			query.append(" LIMIT ").append(limit);
			query.append(" OFFSET ").append(offset);
		}
		sh.setQueryString(query.toString());
		return sh;
	}

	private String getOrderByString() {
		StringBuffer orderBuffer = new StringBuffer("");
		if (orderBys != null && !orderBys.isEmpty()) {
			orderBuffer.append(" ORDER BY ");
			OrderBy lastOrderBy = orderBys.get(orderBys.size() - 1);
			for (OrderBy orderBy : orderBys) {
				orderBuffer.append(Util.escapeSQL(orderBy.getColumn()));
				if (orderBy.isAscending()) {
					orderBuffer.append(" ASC");
				} else {
					orderBuffer.append(" DESC");
				}
				if (orderBy != lastOrderBy) {
					orderBuffer.append(", ");
				}
			}
		}
		return orderBuffer.toString();
	}

	@Deprecated
	public String getCountQuery() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Use getCountStatement method.");
	}

	public StatementHelper getCountStatement()
			throws UnsupportedOperationException {
		StatementHelper sh = new StatementHelper();
		StringBuffer query = new StringBuffer(sql_count);
		if (filters != null) {
			query.append(QueryBuilder.getWhereStringForFilters(filters, sh));
		}
		sh.setQueryString(query.toString());
		return sh;
	}

	public void setFilters(List<Filter> filters)
			throws UnsupportedOperationException {
		this.filters = filters;
	}

	public void setOrderBy(List<OrderBy> orderBys)
			throws UnsupportedOperationException {
		this.orderBys = orderBys;
	}

	public int storeRow(Connection conn, RowItem row) throws SQLException {
		PreparedStatement statement = null;
		if (row.getId() instanceof TemporaryRowId) {
			statement = conn
					.prepareStatement("INSERT INTO tusers VALUES(DEFAULT,?,?,?,?,?)");
			setRowValues(statement, row);
		} else {
			statement = conn
					.prepareStatement("UPDATE tusers SET username=?,password=?,firstname=?,lastname=?,	accesslevel=? WHERE id = ?");

			setRowValues(statement, row);
			statement.setLong(6, (Long) row.getItemProperty("id").getValue());
		}

		int retval = statement.executeUpdate();
		statement.close();
		return retval;
	}

	private void setRowValues(PreparedStatement statement, RowItem row)
			throws SQLException {
		statement.setString(1, (String) row.getItemProperty("username")
				.getValue());
		statement.setString(2, (String) row.getItemProperty("password")
				.getValue());

		statement.setString(3, (String) row.getItemProperty("firstname")
				.getValue());
		statement.setString(4, (String) row.getItemProperty("lastname")
				.getValue());
		statement.setString(5, (String) row.getItemProperty("accesslevel")
				.getValue());
	}

	public boolean removeRow(Connection conn, RowItem row)
			throws UnsupportedOperationException, SQLException {
		PreparedStatement statement = conn.prepareStatement(sql_delete);
		statement.setInt(1, (Integer) row.getItemProperty("id").getValue());
		int rowsChanged = statement.executeUpdate();
		statement.close();
		return rowsChanged == 1;
	}

	@Deprecated
	public String getContainsRowQueryString(Object... keys)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"Please use getContainsRowQueryStatement method.");
	}

	public StatementHelper getContainsRowQueryStatement(Object... keys)
			throws UnsupportedOperationException {
		StatementHelper sh = new StatementHelper();
		StringBuffer query = new StringBuffer(sql_where);
		sh.addParameterValue(keys[0]);
		sh.setQueryString(query.toString());
		return sh;
	}
}
