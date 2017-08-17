/**
 * 
 */
package com.leopard.ui.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.delegates.FreeformGrade;
import com.leopard.data.ConnectionHelper;
import com.leopard.ui.ExampleApplication;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

/**
 * @author Duncan
 * 
 */
public class GradeView extends AbstractView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String[] VISIBLE_COLS = { "grade", "remarks" };

	private SQLContainer container = null;

	private Table gradeList = new Table();
	private VerticalLayout editorLayout = new VerticalLayout();
	private Form gradeEditor = new Form();
	private HorizontalLayout bottomLeftCorner = new HorizontalLayout();
	private Button gradeRemovalButton;
	VerticalLayout layout = new VerticalLayout();
	/**
	 * 
	 */
	public GradeView() {
		// TODO Auto-generated constructor stub

		

		// Create the visual components of the view.
		setSizeFull();

		setCompositionRoot(layout);

		layout.setHeight("100%");
		layout.setWidth("100%");
		layout.setSpacing(true);
		layout.setMargin(true);

		ConnectionHelper.initConnectionPool();
		
		initDatabase();
		initContainer();

		initLayout();
		initGradeAddRemoveButtons();
		initGradeList();
		initFilteringControls();
		initFieldFactory();

	}

	@SuppressWarnings("serial")
	private void initFieldFactory() {
        gradeEditor.setFormFieldFactory(new DefaultFieldFactory() {
            @Override
            public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                Field f = super.createField(item, propertyId, uiContext);
                if (f instanceof TextField) {
                    ((TextField) f).setNullRepresentation("");
                }
                if (propertyId.equals("grade")) {
                    f.setRequired(true);
                }
                return f;
            }
        });
    }

    @SuppressWarnings("serial")
	private void initLayout() {
		VerticalSplitPanel splitPanel = new VerticalSplitPanel();
        layout.addComponent(splitPanel);
        VerticalLayout left = new VerticalLayout();
        left.setSizeFull();
        left.addComponent(gradeList);
        gradeList.setCaption("GRADE LIST");
        gradeList.setSizeFull();
        left.setExpandRatio(gradeList, 1);
        splitPanel.addComponent(left);
        splitPanel.addComponent(editorLayout);
        gradeEditor.setSizeFull();
        gradeEditor.getLayout().setMargin(true);
        gradeEditor.setImmediate(false);
        gradeEditor.setValidationVisible(false);
        gradeEditor.setValidationVisibleOnCommit(false);
        editorLayout.addComponent(gradeEditor);
        editorLayout.addComponent(new Button("SAVE",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        
                        try {
//                        	if(gradeEditor.getField(gradeEditor))
                        	gradeEditor.commit();
                            container.commit();
                            editorLayout.setVisible(false);
                        } catch (SQLException e) {
                           showError("Error when saving record!");
                           ExampleApplication.getProject().getMainWindow().showNotification("Invalid Input for:\n *Grade  ", Notification.TYPE_ERROR_MESSAGE);
//                            e.printStackTrace();
                        } catch (EmptyValueException e) {
							// TODO: handle exception
//                        	SQLContainerDemo.getSystemMessages().getInternalErrorMessage()
                        	ExampleApplication.getProject().getMainWindow().showNotification("Invalid Input for:\n *Grade  ", Notification.TYPE_ERROR_MESSAGE);
						}
                    }
                }));
        editorLayout.setVisible(false);
        bottomLeftCorner.setWidth("100%");
        left.addComponent(bottomLeftCorner);
    }

    @SuppressWarnings("serial")
	private void initGradeAddRemoveButtons() {
        // New item button
        bottomLeftCorner.addComponent(new Button("NEW",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        Object id = gradeList.addItem();
                        gradeList.setValue(id);
                    }
                }));

        // Remove item button
        gradeRemovalButton = new Button("DELETE", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                gradeList.removeItem(gradeList.getValue());
                try {
                    container.commit();
                } catch (SQLException e) {
                    showError("Error when removing record!");
                    e.printStackTrace();
                }
                gradeList.select(null);
            }
        });
        gradeRemovalButton.setVisible(false);
        bottomLeftCorner.addComponent(gradeRemovalButton);
    }

    @SuppressWarnings("serial")
	private void initGradeList() {
        gradeList.setContainerDataSource(container);
        gradeList.setVisibleColumns(VISIBLE_COLS);
        gradeList.setSelectable(true);
        gradeList.setImmediate(true);
        gradeList.addListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Object id = gradeList.getValue();
                if (id instanceof Integer) {
                    try {
                        container.rollback();
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                gradeEditor.setItemDataSource(id == null ? null : gradeList
                        .getItem(id));
                editorLayout.setVisible(id != null);
                gradeRemovalButton.setVisible(id != null);
            }
        });
    }

    @SuppressWarnings("serial")
	private void initFilteringControls() {
        for (final String pn : VISIBLE_COLS) {
            final TextField sf = new TextField();
            bottomLeftCorner.addComponent(sf);
            sf.setWidth("100%");
            sf.setInputPrompt(pn);
            sf.setImmediate(true);
            bottomLeftCorner.setExpandRatio(sf, 1);
            sf.addListener(new Property.ValueChangeListener() {
                public void valueChange(ValueChangeEvent event) {
                    container.removeContainerFilters(pn);
                    if (sf.toString().length() > 0 && !pn.equals(sf.toString())) {
                        container.addContainerFilter(pn, sf.toString(), true,
                                false);
                    }
                    ExampleApplication.getProject().getMainWindow().showNotification(
                            "" + container.size() + " matches found");
                }
            });
        }
    }

    

    public void showError(String errorString) {
        ExampleApplication.getProject().getMainWindow().showNotification(errorString,
                Notification.TYPE_ERROR_MESSAGE);
    }
    
    private void initDatabase() {
        try {
            Connection conn = ConnectionHelper.initConnectionPool().reserveConnection();
            Statement statement = conn.createStatement();
            try {
                statement.executeQuery("SELECT * FROM tgrade");
            } catch (SQLException e) {
                // Failed, which means that we should init the database
                ExampleApplication.getProject().getMainWindow().showNotification("Error..\n "+e, Notification.TYPE_ERROR_MESSAGE);
            }
            statement.close();
            conn.commit();
            ConnectionHelper.initConnectionPool().releaseConnection(conn);
        } catch (SQLException e) {
            showError("Could not create people table!");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
	private void initContainer() {
        try {
            FreeformQuery query = new FreeformQuery("SELECT * FROM tgrade",
                    Arrays.asList("ID"), ConnectionHelper.initConnectionPool());
            query.setDelegate(new FreeformGrade());
            container = new SQLContainer(query);
        } catch (SQLException e) {
            showError("Could not create an instance of SQLContainer!");
            e.printStackTrace();
        }
    }

	@Override
	protected void close() throws Exception {
		// TODO Auto-generated method stub

	}

}
