package team.koala.chillin.client;

import team.koala.chillin.client.helper.messages.BaseCommand;
import team.koala.chillin.client.helper.messages.BaseSnapshot;
import team.koala.chillin.client.helper.messages.TurnbasedCommand;
import team.koala.chillin.client.helper.messages.TurnbasedSnapshot;

import java.util.ArrayList;
import java.util.List;


public class TurnbasedAI<TWorld, TCommand> extends RealtimeAI<TWorld, TCommand> {

	protected List<String> turnAllowedSides;


	public TurnbasedAI(TWorld world) {
		super(world);
		turnAllowedSides = new ArrayList<>();
	}

	@Override
	public void update(BaseSnapshot snapshot) {
		super.update(snapshot);
		TurnbasedSnapshot x = (TurnbasedSnapshot) snapshot;
		this.turnAllowedSides = x.getTurnAllowedSides();
	}


	@Override
	public boolean allowedToDecide() {
		return this.turnAllowedSides.contains(this.mySide);
	}

	@Override
	public void _sendCommand(TCommand command, BaseCommand msg) {
		TurnbasedCommand message;
		if (msg == null)
			message = new TurnbasedCommand();
		else
			message = (TurnbasedCommand) msg;

		super._sendCommand(command, message);
	}
}
