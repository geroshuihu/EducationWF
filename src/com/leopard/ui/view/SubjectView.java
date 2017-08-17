/**
 * 
 */
package com.leopard.ui.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.delegates.FreeformSubject;
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
public class SubjectView extends AbstractView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String[] VISIBLE_COLS = { "name", "code" };

	private SQLContainer container = null;

	private Table subjectList = new Table();
	private VerticalLayout editorLayout = new VerticalLayout();
	private Form subjectEditor = new Form();
	private HorizontalLayout bottomLeftCorner = new HorizontalLayout();
	private Button subjectRemovalButton;
	VerticalLayout layout = new VerticalLayout();
	/**
	 * 
	 */
	public SubjectView() {
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
        subjectEditor.setFormFieldFactory(new DefaultFieldFactory() {
            @Override
            public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                Field f = super.createField(item, propertyId, uiContext);
                if (f instanceof TextField) {
                    ((TextField) f).setNullRepresentation("");
                }
                if (propertyId.equals("name")||propertyId.equals("code")) {
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
        left.addComponent(subjectList);
        subjectList.setCaption("SUBJECT LIST");
        subjectList.setSizeFull();
        left.setExpandRatio(subjectList, 1);
        splitPanel.addComponent(left);
        splitPanel.addComponent(editorLayout);
        subjectEditor.setSizeFull();
        subjectEditor.getLayout().setMargin(true);
        subjectEditor.setImmediate(false);
        subjectEditor.setValidationVisible(false);
        subjectEditor.setValidationVisibleOnCommit(false);
        editorLayout.addComponent(subjectEditor);
        editorLayout.addComponent(new Button("SAVE",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        
                        try {
//                        	if(subjectEditor.getField(subjectEditor))
                        	subjectEditor.commit();
                            container.commit();
                            editorLayout.setVisible(false);
                        } catch (SQLException e) {
                           showError("Error when saving record!");
                           ExampleApplication.getProject().getMainWindow().showNotification("Invalid Input for:\n *Subject Name  \n *Code ", Notification.TYPE_ERROR_MESSAGE);
//                            e.printStackTrace();
                        } catch (EmptyValueException e) {
							// TODO: handle exception
//                        	SQLContainerDemo.getSystemMessages().getInternalErrorMessage()
                        	ExampleApplication.getProject().getMainWindow().showNotification("Invalid Input for:\n *Subject Name  \n *Code  ", Notification.TYPE_ERROR_MESSAGE);
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
                        Object id = subjectList.addItem();
                        subjectList.setValue(id);
                    }
                }));

        // Remove item button
        subjectRemovalButton = new Button("DELETE", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                subjectList.removeItem(subjectList.getValue());
                try {
                    container.commit();
                } catch (SQLException e) {
                    showError("Error when removing record!");
                    e.printStackTrace();
                }
                subjectList.select(null);
            }
        });
        subjectRemovalButton.setVisible(false);
        bottomLeftCorner.addComponent(subjectRemovalButton);
    }

    @SuppressWarnings("serial")
	private void initGradeList() {
        subjectList.setContainerDataSource(container);
        subjectList.setVisibleColumns(VISIBLE_COLS);
        subjectList.setSelectable(true);
        subjectList.setImmediate(true);
        subjectList.addListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Object id = subjectList.getValue();
                if (id instanceof Integer) {
                    try {
                        container.rollback();
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                subjectEditor.setItemDataSource(id == null ? null : subjectList
                        .getItem(id));
                editorLayout.setVisible(id != null);
                subjectRemovalButton.setVisible(id != null);
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
                statement.executeQuery("SELECT * FROM tsubject");
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
            FreeformQuery query = new FreeformQuery("SELECT * FROM tsubject",
                    Arrays.asList("ID"), ConnectionHelper.initConnectionPool());
            query.setDelegate(new FreeformSubject());
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
