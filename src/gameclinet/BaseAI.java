package gameclinet;

import gameclinet.helper.messages.BaseCommand;
import gameclinet.helper.messages.BaseSnapshot;
import gameclinet.helper.parser.Parser;

import ks.KSObject;


import java.util.*;
import ks.KSObject;


public class BaseAI<T extends KSObject> extends AbstractAI {

	protected T world;


	BaseAI(T world) {
		this.world = world;
	}

	@Override
	public void update(BaseSnapshot snapshot) {
		this.world.deserialize(Parser.getBytes(snapshot.getWorldPayload()));
	}

	@Override
	public boolean allowedToDecide() {
		return true;
	}


	protected void _sendCommand(KSObject command, BaseCommand msg) {
		BaseCommand message;
		if (msg == null)
			message = new BaseCommand();
		else
			message = (BaseCommand) msg;

		message.setType(command.name());
		message.setPayload(Parser.getString(command.serialize()));
		commandSendQueue.add(message);
	}

	protected void _sendCommand(KSObject command) {
		_sendCommand(command, null);
	}

	public void sendCommand(KSObject command) {
		if (allowedToDecide())
			_sendCommand(command);
	}
}
