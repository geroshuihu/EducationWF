package com.leopard.ui.controller;

import java.util.Calendar;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
import com.leopard.data.businesslogic.Channel;
import com.leopard.ui.ExampleApplication;

public class ChannelController {

	private Channel channelData = new Channel();

	/*
	 * Writes an item to the channel. Insert input to database/send message to
	 * server if real implementation starts.
	 * 
	 * @param sentMessage The message the user wishes to display
	 * 
	 * @param Table The table which holds the channel chat
	 */
	public void writeToChannel(Object sentMessage, Table channelTable) {

		String message = (String) sentMessage;

		// Spam-prevention: If the string is empty, do nothing.
		if (message.equals("")) {
			return;
		}

		Calendar date = Calendar.getInstance();

		// Add timestamp to message.
		String timestamp = pad(date.get(Calendar.HOUR_OF_DAY)) + ":"
				+ pad(date.get(Calendar.MINUTE)) + ":"
				+ pad(date.get(Calendar.SECOND));

		// Add username to message.
		// Change if implementing channel access (op/voice/half-op).
		String username = "< "
				+ (String) ExampleApplication.getProject().getUser() + "> ";

		message = username + message;

		int lastId = Integer.valueOf(channelTable.lastItemId().toString());
		lastId++;
		
		Item item = channelTable.addItem(lastId);

		item.getItemProperty("timestamp").setValue(timestamp);
		item.getItemProperty("log").setValue(message);
		

	}

	/*
	 * Inserts leading zeros if needed.
	 * 
	 * @param timestamp integer of the hour/minute/second
	 */
	private String pad(int timestamp) {
		if (timestamp < 10)
			return "0" + timestamp;
		else
			return "" + timestamp;

	}

	/*
	 * Retrieves the log for the channel and returns it in a table.
	 * 
	 * @param channel name of the channel of the logs wanted
	 */
	public IndexedContainer getChannelLog(String channel) {

		// If an error occurred and we don't have a channel name returned null
		// (channel log not found), break the operation.
		if (channel == null)
			return null;

		// Get the logs from the database.
		List<String[]> channelRows = channelData.getChannelLog(channel);

		// Insert the logs in a table.
		IndexedContainer channelLogContainer = new IndexedContainer();
		channelLogContainer.addContainerProperty("timestamp", String.class,
				null);
		channelLogContainer.addContainerProperty("log", String.class, null);

		int dummyId = 0;

		// For each line in the log, add a container item.
		for (String[] row : channelRows) {

			Item item = channelLogContainer.addItem(dummyId);

			item.getItemProperty("timestamp").setValue(row[0]);
			item.getItemProperty("log").setValue(row[1]);
			dummyId++;

		}

		return channelLogContainer;
	}

}
