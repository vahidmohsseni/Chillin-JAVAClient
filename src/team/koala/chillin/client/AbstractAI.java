package team.koala.chillin.client;

import team.koala.chillin.client.helper.messages.BaseSnapshot;

import java.util.*;


public abstract class AbstractAI {

	protected Queue<KSObject> commandSendQueue = null;
	protected Map<String, List<String>> sides;
	protected String mySide;
	protected String otherSide;
	protected Set<String> otherSides;


	public void setCommandSendQueue(Queue<KSObject> q) {
		commandSendQueue = q;
	}

	public void setSides(Map<String, List<String>> sides, String mySide)
	{
		this.sides = sides;
		this.mySide = mySide;
		this.otherSides = new LinkedHashSet<>();
		for (String side : this.sides.keySet())
			if (!side.equals(mySide))
				this.otherSides.add(side);
		this.otherSide = (this.otherSides.size() == 1) ? (String) this.otherSides.iterator().next() : null;
	}

	public abstract void update(BaseSnapshot snapshot);
	public abstract boolean allowedToDecide();
	public void initialize() {}
	public void decide() {}
}
