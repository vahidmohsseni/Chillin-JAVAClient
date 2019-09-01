package team.koala.chillin.client.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;

public class RealtimeSnapshot extends BaseSnapshot
{
	protected Integer currentCycle;
	protected Float cycleDuration;
	
	// getters
	
	public Integer getCurrentCycle()
	{
		return this.currentCycle;
	}
	
	public Float getCycleDuration()
	{
		return this.cycleDuration;
	}
	
	
	// setters
	
	public void setCurrentCycle(Integer currentCycle)
	{
		this.currentCycle = currentCycle;
	}
	
	public void setCycleDuration(Float cycleDuration)
	{
		this.cycleDuration = cycleDuration;
	}
	
	
	public RealtimeSnapshot()
	{
	}
	
	public static final String nameStatic = "RealtimeSnapshot";
	
	@Override
	public String name() { return "RealtimeSnapshot"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize parents
		s.addAll(b2B(super.serialize()));
		
		// serialize currentCycle
		s.add((byte) ((currentCycle == null) ? 0 : 1));
		if (currentCycle != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(currentCycle).array()));
		}
		
		// serialize cycleDuration
		s.add((byte) ((cycleDuration == null) ? 0 : 1));
		if (cycleDuration != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Float.BYTES).order(ByteOrder.LITTLE_ENDIAN).putFloat(cycleDuration).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize parents
		offset = super.deserialize(s, offset);
		
		// deserialize currentCycle
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			currentCycle = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			currentCycle = null;
		
		// deserialize cycleDuration
		byte tmp1;
		tmp1 = s[offset];
		offset += Byte.BYTES;
		if (tmp1 == 1)
		{
			cycleDuration = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Float.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
			offset += Float.BYTES;
		}
		else
			cycleDuration = null;
		
		return offset;
	}
}
