package team.koala.chillin.client.helper.messages;

import team.koala.chillin.client.KSObject;

import java.lang.*;
import java.util.*;
import java.nio.*;

public class StartGame extends KSObject
{
	protected Integer startTime;
	
	// getters
	
	public Integer getStartTime()
	{
		return this.startTime;
	}
	
	
	// setters
	
	public void setStartTime(Integer startTime)
	{
		this.startTime = startTime;
	}
	
	
	public StartGame()
	{
	}
	
	public static final String nameStatic = "StartGame";
	
	@Override
	public String name() { return "StartGame"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize startTime
		s.add((byte) ((startTime == null) ? 0 : 1));
		if (startTime != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(startTime).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize startTime
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			startTime = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			startTime = null;
		
		return offset;
	}
}
