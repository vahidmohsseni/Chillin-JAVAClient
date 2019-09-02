package team.koala.chillin.client;

import team.koala.chillin.client.helper.messages.BaseCommand;
import team.koala.chillin.client.helper.messages.BaseSnapshot;
import team.koala.chillin.client.helper.parser.Parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseAI<TWorld, TCommand> extends AbstractAI {

	protected TWorld world;


	BaseAI(TWorld world) {
		this.world = world;
	}

	@Override
	public void update(BaseSnapshot snapshot) {
		// Deserialize world
		try {
			Method method = this.world.getClass().getMethod("deserialize", byte[].class);
			method.invoke(this.world, Parser.getBytes(snapshot.getWorldPayload()));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException |
				 IllegalArgumentException | InvocationTargetException e) {}
	}

	@Override
	public boolean allowedToDecide() {
		return true;
	}


	protected void _sendCommand(TCommand command, BaseCommand msg) {
		BaseCommand message;
		if (msg == null)
			message = new BaseCommand();
		else
			message = (BaseCommand) msg;

		// Store command's name
		try {
			Method method = command.getClass().getMethod("name");
			String name = (String) method.invoke(command);
			message.setType(name);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException |
				 IllegalArgumentException | InvocationTargetException e) {}

		// Store command's serialization data
		try {
			Method method = command.getClass().getMethod("serialize");
			byte[] data = (byte[]) method.invoke(command);
			message.setPayload(Parser.getString(data));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException |
				 IllegalArgumentException | InvocationTargetException e) {}

		commandSendQueue.add(message);
	}

	protected void _sendCommand(TCommand command) {
		_sendCommand(command, null);
	}

	public void sendCommand(TCommand command) {
		if (allowedToDecide())
			_sendCommand(command);
	}
}
