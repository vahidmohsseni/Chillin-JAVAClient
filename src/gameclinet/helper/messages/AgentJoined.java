package gameclinet.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class AgentJoined extends KSObject
{
	protected String sideName;
	protected String agentName;
	protected String teamNickname;
	
	// getters
	
	public String getSideName()
	{
		return this.sideName;
	}
	
	public String getAgentName()
	{
		return this.agentName;
	}
	
	public String getTeamNickname()
	{
		return this.teamNickname;
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
	
	public void setTeamNickname(String teamNickname)
	{
		this.teamNickname = teamNickname;
	}
	
	
	public AgentJoined()
	{
	}
	
	public static final String nameStatic = "AgentJoined";
	
	@Override
	public String name() { return "AgentJoined"; }
	
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
		
		// serialize teamNickname
		s.add((byte) ((teamNickname == null) ? 0 : 1));
		if (teamNickname != null)
		{
			List<Byte> tmp2 = new ArrayList<>();
			tmp2.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(teamNickname.length()).array()));
			while (tmp2.size() > 0 && tmp2.get(tmp2.size() - 1) == 0)
				tmp2.remove(tmp2.size() - 1);
			s.add((byte) tmp2.size());
			s.addAll(tmp2);
			
			s.addAll(b2B(teamNickname.getBytes(Charset.forName("ISO-8859-1"))));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize sideName
		byte tmp3;
		tmp3 = s[offset];
		offset += Byte.BYTES;
		if (tmp3 == 1)
		{
			byte tmp4;
			tmp4 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp5 = Arrays.copyOfRange(s, offset, offset + tmp4);
			offset += tmp4;
			int tmp6;
			tmp6 = ByteBuffer.wrap(Arrays.copyOfRange(tmp5, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			sideName = new String(s, offset, tmp6, Charset.forName("ISO-8859-1"));
			offset += tmp6;
		}
		else
			sideName = null;
		
		// deserialize agentName
		byte tmp7;
		tmp7 = s[offset];
		offset += Byte.BYTES;
		if (tmp7 == 1)
		{
			byte tmp8;
			tmp8 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp9 = Arrays.copyOfRange(s, offset, offset + tmp8);
			offset += tmp8;
			int tmp10;
			tmp10 = ByteBuffer.wrap(Arrays.copyOfRange(tmp9, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			agentName = new String(s, offset, tmp10, Charset.forName("ISO-8859-1"));
			offset += tmp10;
		}
		else
			agentName = null;
		
		// deserialize teamNickname
		byte tmp11;
		tmp11 = s[offset];
		offset += Byte.BYTES;
		if (tmp11 == 1)
		{
			byte tmp12;
			tmp12 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp13 = Arrays.copyOfRange(s, offset, offset + tmp12);
			offset += tmp12;
			int tmp14;
			tmp14 = ByteBuffer.wrap(Arrays.copyOfRange(tmp13, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			teamNickname = new String(s, offset, tmp14, Charset.forName("ISO-8859-1"));
			offset += tmp14;
		}
		else
			teamNickname = null;
		
		return offset;
	}
}
