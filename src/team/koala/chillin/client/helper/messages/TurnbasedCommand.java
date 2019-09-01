package team.koala.chillin.client.helper.messages;

import java.lang.*;
import java.util.*;

public class TurnbasedCommand extends RealtimeCommand
{
	
	// getters
	
	
	// setters
	
	
	public TurnbasedCommand()
	{
	}
	
	public static final String nameStatic = "TurnbasedCommand";
	
	@Override
	public String name() { return "TurnbasedCommand"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize parents
		s.addAll(b2B(super.serialize()));
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize parents
		offset = super.deserialize(s, offset);
		
		return offset;
	}
}
