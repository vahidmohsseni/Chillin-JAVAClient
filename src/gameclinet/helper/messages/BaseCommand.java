package gameclinet.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class BaseCommand extends KSObject
{
	protected String type;
	protected String payload;
	
	// getters
	
	public String getType()
	{
		return this.type;
	}
	
	public String getPayload()
	{
		return this.payload;
	}
	
	
	// setters
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setPayload(String payload)
	{
		this.payload = payload;
	}
	
	
	public BaseCommand()
	{
	}
	
	public static final String nameStatic = "BaseCommand";
	
	@Override
	public String name() { return "BaseCommand"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize type
		s.add((byte) ((type == null) ? 0 : 1));
		if (type != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(type.length()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			s.addAll(b2B(type.getBytes(Charset.forName("ISO-8859-1"))));
		}
		
		// serialize payload
		s.add((byte) ((payload == null) ? 0 : 1));
		if (payload != null)
		{
			List<Byte> tmp1 = new ArrayList<>();
			tmp1.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(payload.length()).array()));
			while (tmp1.size() > 0 && tmp1.get(tmp1.size() - 1) == 0)
				tmp1.remove(tmp1.size() - 1);
			s.add((byte) tmp1.size());
			s.addAll(tmp1);
			
			s.addAll(b2B(payload.getBytes(Charset.forName("ISO-8859-1"))));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize type
		byte tmp2;
		tmp2 = s[offset];
		offset += Byte.BYTES;
		if (tmp2 == 1)
		{
			byte tmp3;
			tmp3 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp4 = Arrays.copyOfRange(s, offset, offset + tmp3);
			offset += tmp3;
			int tmp5;
			tmp5 = ByteBuffer.wrap(Arrays.copyOfRange(tmp4, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			type = new String(s, offset, tmp5, Charset.forName("ISO-8859-1"));
			offset += tmp5;
		}
		else
			type = null;
		
		// deserialize payload
		byte tmp6;
		tmp6 = s[offset];
		offset += Byte.BYTES;
		if (tmp6 == 1)
		{
			byte tmp7;
			tmp7 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp8 = Arrays.copyOfRange(s, offset, offset + tmp7);
			offset += tmp7;
			int tmp9;
			tmp9 = ByteBuffer.wrap(Arrays.copyOfRange(tmp8, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			payload = new String(s, offset, tmp9, Charset.forName("ISO-8859-1"));
			offset += tmp9;
		}
		else
			payload = null;
		
		return offset;
	}
}
