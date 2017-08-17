/**
 * 
 */
package com.leopard.ui.view;

/**
 * @author Duncan
 * 
 */
public class ViewDataSource {

	/**
	 * 
	 */
	String Caption1 = null;
	String[] visibleCols = null;
	String[][] fields = null;
	String sql = null;

	String[] Caption = { "SUMMARY", "BOOK ORDERS", "WEEKLY FINANCIAL",
			"MARKBOOK" };
	// ~~~~~~~~~~~~~~~~~~~Summary~~~~~~~~~~~~~~~~~~~~~~//
	String sql_summary = "SELECT s1.SchoolName, s1.Region, s1.Location, s1.Address, s1.Date_visit, CONCAT(u.firstname, ' ',u.lastname) AS Update_By FROM `tSchoolInput` s1, `tusers` u WHERE s1.Update_By = u.id GROUP BY s1.ID ORDER BY s1.ID DESC";
	String[] visibleCol_summary = {"SchoolName", "Region",
			"Location", "Address", "Date_visit", "Update_By" };
	String[][] fields_summary = { { "SchoolName", "s1.SchoolName" }, { "Region", "s1.Region" },
			{ "Location", "s1.Location" }, { "Address", "s1.Address" },
			{ "Date_visit", "s1.Date_visit" }, { "Update_By", "Update_By" } };
	// ~~~~~~~~~~~~~~~~~~~BOOK ORDERS~~~~~~~~~~~~~~~~~~~~~~//
	//
	String[] visibleCol_orders = { "School Name", "Grade", "Subject", "Title",
			"Quantity", "UpdateBy", "RegDate", "View map" };
	String sql_orders = "SELECT o.id, s. NAME, o.Grade, o. SUBJECT, o.Title, o.Quantity, o.RegDate, CONCAT(u.firstname, ' ', u.lastname)AS UpdateBy, o.Longitude, o.Latitude,o.Ticket FROM `tOrders` o, `tschool` s, `tusers` u WHERE s.id = o.SchoolNo AND o.userid = u.id GROUP BY o.ID ORDER BY o.ID DESC";
	String[][] fields_orders = { { "School Name", "s.name" },
			{ "Grade", "o.Grade" }, { "Subject", "o.Subject" },
			{ "Title", "o.Title" }, { "Quantity", "o.Quantity" },
			{ "RegDate", "o.RegDate" }, { "UpdateBy", "UpdateBy" },
			{ "View map", "View map" } };

	// ~~~~~~~~~~~~~~~~~~~Finance~~~~~~~~~~~~~~~~~~~~~~//
	String[] visibleCol_finance = { "SchoolName", " Income", " Expenditure",
			" Recon", " UpdateBy", " RegDate",
			"View map" };
	String sql_finance = "SELECT s.name, f.Income, f.Expenditure, f.Recon, CONCAT(u.firstname, ' ', u.lastname) AS UpdateBy, f.RegDate,f.Longitude, f.Latitude, f.Ticket FROM tFinancial f, tschool s, tusers u WHERE s.id = f.SchoolID AND u.id = f.UpdateBy GROUP BY f.ID ORDER BY f.ID DESC";
	String[][] fields_finance = { { "SchoolName", "s.name" },
			{ " Income", "f.Income" }, { " Expenditure", "f.Expenditure" },
			{ " Recon", "f.Recon" }, { " UpdateBy", "UpdateBy" },
			{ " RegDate", "f.RegDate" }, { "View map", "View map" } };

	// ~~~~~~~~~~~~~~~~~~~MarkBook~~~~~~~~~~~~~~~~~~~~~~//
//	String sqls = "SELECT s.name,m.Grade,m.Subject,m.Name,"
//			+ "m.Total,m.Mark,m.RegDate,m.Longitude,m.Latitude,"
//			+ "m.userid,m.Ticket FROM `tMarkBook` m, tschool s "
//			+ "WHERE  m.SchoolNo=s.id " + "GROUP BY m.ID ORDER BY m.ID";
	String sqls = "SELECT s.name, m.Grade, m.Subject, m.Name AS StudentName, m.Total, m.Mark, m.RegDate, m.Longitude, m.Latitude, CONCAT(u.firstname, ' ', u.lastname) AS UpdateBy, m.Ticket FROM `tMarkBook` m, tschool s, tusers u WHERE m.SchoolNo = s.id AND u.id = m.userid GROUP BY m.ID ORDER BY m.ID DESC";
	
	String[] visibleColumns = { "SchoolName", "Grade", "Subject", "StudentName",
			"Total", "Mark", "RegDate", // "Longitude", "Latitude","Ticket",
			"UpdateBy", "View map" };

	String[][] field = { { "SchoolName", "s.name" }, { "Grade", "m.Grade" },
			{ "Subject", "m.Subject" }, { "StudentName", "StudentName" },
			{ "Total", "m.Total" }, { "Mark", "m.Mark" },
			{ "RegDate", "m.RegDate" },{ "UpdateBy", "UpdateBy" }, { "View map", "View map" } };

	public ViewDataSource(String vType) {
		// TODO Auto-generated constructor stub
		switch (vType) {
		case "a":
			Caption1 = Caption[0];
			visibleCols = visibleCol_summary;
			fields =fields_summary;
			sql = sql_summary;
			break;

		case "b":
			Caption1 = Caption[1];
			visibleCols = visibleCol_orders;
			fields = fields_orders;
			sql = sql_orders;
			break;
		case "c":
			Caption1 = Caption[2];
			visibleCols = visibleCol_finance;
			fields = fields_finance;
			sql = sql_finance;
			break;
		case "d":
			Caption1 = Caption[3];
			visibleCols = visibleColumns;
			fields = getField();
			sql = getSqls();
			break;

		}

	}

	public String getCaption1() {
		return Caption1;
	}

	public void setCaption1(String caption1) {
		Caption1 = caption1;
	}

	public String[] getVisibleCols() {
		return visibleCols;
	}

	public void setVisibleCols(String[] visibleCols) {
		this.visibleCols = visibleCols;
	}

	public String[] getVisibleColumns() {
		return visibleColumns;
	}

	public void setVisibleColumns(String[] visibleColumns) {
		this.visibleColumns = visibleColumns;
	}

	public String[][] getField() {
		return field;
	}

	public void setField(String[][] field) {
		this.field = field;
	}

	public String getSqls() {
		return sqls;
	}

	public void setSqls(String sqls) {
		this.sqls = sqls;
	}

}
