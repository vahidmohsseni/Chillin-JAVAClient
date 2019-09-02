package team.koala.chillin.client.helper.messages;

import team.koala.chillin.client.KSObject;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

public class BaseSnapshot extends KSObject
{
	protected String worldPayload;
	
	// getters
	
	public String getWorldPayload()
	{
		return this.worldPayload;
	}
	
	
	// setters
	
	public void setWorldPayload(String worldPayload)
	{
		this.worldPayload = worldPayload;
	}
	
	
	public BaseSnapshot()
	{
	}
	
	public static final String nameStatic = "BaseSnapshot";
	
	@Override
	public String name() { return "BaseSnapshot"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize worldPayload
		s.add((byte) ((worldPayload == null) ? 0 : 1));
		if (worldPayload != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(worldPayload.length()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			s.addAll(b2B(worldPayload.getBytes(Charset.forName("ISO-8859-1"))));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize worldPayload
		byte tmp1;
		tmp1 = s[offset];
		offset += Byte.BYTES;
		if (tmp1 == 1)
		{
			byte tmp2;
			tmp2 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp3 = Arrays.copyOfRange(s, offset, offset + tmp2);
			offset += tmp2;
			int tmp4;
			tmp4 = ByteBuffer.wrap(Arrays.copyOfRange(tmp3, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			worldPayload = new String(s, offset, tmp4, Charset.forName("ISO-8859-1"));
			offset += tmp4;
		}
		else
			worldPayload = null;
		
		return offset;
	}
}
