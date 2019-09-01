package team.koala.chillin.client.helper.parser;

import team.koala.chillin.client.helper.messages.*;
import ks.KSObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


class MessageFactory {

	private static MessageFactory instance = null;

	private Map<String, KSObject> installedMessages;


	private MessageFactory() {
		installedMessages = new HashMap<>();
		loadMessages();
	}

	static MessageFactory getInstance() {
		if (instance == null)
			instance = new MessageFactory();
		return instance;
	}

	private void loadMessages() {
		registerOnMessages(new AgentJoined());
		registerOnMessages(new AgentLeft());
		registerOnMessages(new BaseCommand());
		registerOnMessages(new BaseSnapshot());
		registerOnMessages(new ClientJoined());
		registerOnMessages(new EndGame());
		registerOnMessages(new JoinOfflineGame());
		registerOnMessages(new JoinOnlineGame());
		registerOnMessages(new Message());
		registerOnMessages(new RealtimeCommand());
		registerOnMessages(new RealtimeSnapshot());
		registerOnMessages(new StartGame());
		registerOnMessages(new TurnbasedCommand());
		registerOnMessages(new TurnbasedSnapshot());
	}

	private void registerOnMessages(KSObject obj) {
		installedMessages.put(obj.name(), obj);
	}

	KSObject getMessage(String messageName) {
		try {
			return installedMessages.get(messageName).getClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
