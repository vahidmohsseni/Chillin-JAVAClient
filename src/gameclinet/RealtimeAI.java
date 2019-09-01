package gameclinet;

import gameclinet.helper.messages.*;
import ks.KSObject;


public class RealtimeAI<T extends KSObject> extends BaseAI<T> {

	protected Integer currentCycle;
	protected Float cycleDuration;


	public RealtimeAI(T world) {
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
	protected void _sendCommand(KSObject command, BaseCommand msg) {
		RealtimeCommand  message;
		if (msg == null)
			message = new RealtimeCommand();
		else
			message = (RealtimeCommand) msg;

		message.setCycle(this.currentCycle);
		super._sendCommand(command, message);
	}
}
