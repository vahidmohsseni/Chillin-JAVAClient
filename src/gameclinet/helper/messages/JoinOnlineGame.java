package gameclinet.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class JoinOnlineGame extends KSObject
{
	protected String token;
	protected String agentName;
	
	// getters
	
	public String getToken()
	{
		return this.token;
	}
	
	public String getAgentName()
	{
		return this.agentName;
	}
	
	
	// setters
	
	public void setToken(String token)
	{
		this.token = token;
	}
	
	public void setAgentName(String agentName)
	{
		this.agentName = agentName;
	}
	
	
	public JoinOnlineGame()
	{
	}
	
	public static final String NameStatic = "JoinOnlineGame";
	
	@Override
	public String Name() { return "JoinOnlineGame"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize token
		s.add((byte) ((token == null) ? 0 : 1));
		if (token != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(token.length()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			s.addAll(b2B(token.getBytes(Charset.forName("ISO-8859-1"))));
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
		// deserialize token
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
			
			token = new String(s, offset, tmp5, Charset.forName("ISO-8859-1"));
			offset += tmp5;
		}
		else
			token = null;
		
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
