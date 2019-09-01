package team.koala.chillin.client.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class AgentLeft extends KSObject
{
	protected String sideName;
	protected String agentName;
	
	// getters
	
	public String getSideName()
	{
		return this.sideName;
	}
	
	public String getAgentName()
	{
		return this.agentName;
	}
	
	
	// setters
	
	public void setSideName(String sideName)
	{
		this.sideName = sideName;
	}
	
	public void setAgentName(String agentName)
	{
		this.agentName = agentName;
	}
	
	
	public AgentLeft()
	{
	}
	
	public static final String nameStatic = "AgentLeft";
	
	@Override
	public String name() { return "AgentLeft"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize sideName
		s.add((byte) ((sideName == null) ? 0 : 1));
		if (sideName != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(sideName.length()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			s.addAll(b2B(sideName.getBytes(Charset.forName("ISO-8859-1"))));
		}
		
		// serialize agentName
		s.add((byte) ((agentName == null) ? 0 : 1));
		if (agentName != null)
		{
			List<Byte> tmp1 = new ArrayList<>();
			tmp1.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(agentName.length()).array()));
			while (tmp1.size() > 0 && tmp1.get(tmp1.size() - 1) == 0)
				tmp1.remove(tmp1.size() - 1);
			s.add((byte) tmp1.size());
			s.addAll(tmp1);
			
			s.addAll(b2B(agentName.getBytes(Charset.forName("ISO-8859-1"))));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize sideName
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
			
			sideName = new String(s, offset, tmp5, Charset.forName("ISO-8859-1"));
			offset += tmp5;
		}
		else
			sideName = null;
		
		// deserialize agentName
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
			
			agentName = new String(s, offset, tmp9, Charset.forName("ISO-8859-1"));
			offset += tmp9;
		}
		else
			agentName = null;
		
		return offset;
	}
}
