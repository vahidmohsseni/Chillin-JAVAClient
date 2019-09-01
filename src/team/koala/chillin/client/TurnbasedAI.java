package team.koala.chillin.client;

import team.koala.chillin.client.helper.messages.BaseCommand;
import team.koala.chillin.client.helper.messages.BaseSnapshot;
import team.koala.chillin.client.helper.messages.TurnbasedCommand;
import team.koala.chillin.client.helper.messages.TurnbasedSnapshot;
import ks.KSObject;

import java.util.ArrayList;
import java.util.List;


public class TurnbasedAI<T extends KSObject> extends RealtimeAI<T> {

	protected List<String> turnAllowedSides;


	public TurnbasedAI(T world) {
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
	public void _sendCommand(KSObject command, BaseCommand msg) {
		TurnbasedCommand message;
		if (msg == null)
			message = new TurnbasedCommand();
		else
			message = (TurnbasedCommand) msg;

		super._sendCommand(command, message);
	}
}
