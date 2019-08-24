package gameclinet.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class RealtimeCommand extends BaseCommand
{
	protected Integer cycle;
	
	// getters
	
	public Integer getCycle()
	{
		return this.cycle;
	}
	
	
	// setters
	
	public void setCycle(Integer cycle)
	{
		this.cycle = cycle;
	}
	
	
	public RealtimeCommand()
	{
	}
	
	public static final String NameStatic = "RealtimeCommand";
	
	@Override
	public String Name() { return "RealtimeCommand"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize parents
		s.addAll(b2B(super.serialize()));
		
		// serialize cycle
		s.add((byte) ((cycle == null) ? 0 : 1));
		if (cycle != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cycle).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize parents
		offset = super.deserialize(s, offset);
		
		// deserialize cycle
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			cycle = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cycle = null;
		
		return offset;
	}
}
