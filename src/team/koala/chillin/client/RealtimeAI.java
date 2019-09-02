package team.koala.chillin.client;

import team.koala.chillin.client.helper.messages.BaseCommand;
import team.koala.chillin.client.helper.messages.BaseSnapshot;
import team.koala.chillin.client.helper.messages.RealtimeCommand;
import team.koala.chillin.client.helper.messages.RealtimeSnapshot;


public class RealtimeAI<TWorld, TCommand> extends BaseAI<TWorld, TCommand> {

	protected Integer currentCycle;
	protected Float cycleDuration;


	public RealtimeAI(TWorld world) {
		super(world);
	}

	@Override
	public void update(BaseSnapshot snapshot) {
		super.update(snapshot);
		RealtimeSnapshot x = (RealtimeSnapshot) snapshot;
		this.currentCycle = x.getCurrentCycle();
		this.cycleDuration = x.getCycleDuration();
	}

	@Override
	public boolean allowedToDecide() {
		return true;
	}

	@Override
	protected void _sendCommand(TCommand command, BaseCommand msg) {
		RealtimeCommand message;
		if (msg == null)
			message = new RealtimeCommand();
		else
			message = (RealtimeCommand) msg;

		message.setCycle(this.currentCycle);
		super._sendCommand(command, message);
	}
}
