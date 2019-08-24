package gameclinet.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class TurnbasedCommand extends RealtimeCommand
{
	
	// getters
	
	
	// setters
	
	
	public TurnbasedCommand()
	{
	}
	
	public static final String NameStatic = "TurnbasedCommand";
	
	@Override
	public String Name() { return "TurnbasedCommand"; }
	
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
