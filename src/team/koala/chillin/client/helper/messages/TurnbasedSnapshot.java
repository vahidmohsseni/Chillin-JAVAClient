package team.koala.chillin.client.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

public class TurnbasedSnapshot extends RealtimeSnapshot
{
	protected List<String> turnAllowedSides;
	
	// getters
	
	public List<String> getTurnAllowedSides()
	{
		return this.turnAllowedSides;
	}
	
	
	// setters
	
	public void setTurnAllowedSides(List<String> turnAllowedSides)
	{
		this.turnAllowedSides = turnAllowedSides;
	}
	
	
	public TurnbasedSnapshot()
	{
	}
	
	public static final String nameStatic = "TurnbasedSnapshot";
	
	@Override
	public String name() { return "TurnbasedSnapshot"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize parents
		s.addAll(b2B(super.serialize()));
		
		// serialize turnAllowedSides
		s.add((byte) ((turnAllowedSides == null) ? 0 : 1));
		if (turnAllowedSides != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(turnAllowedSides.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (String tmp1 : turnAllowedSides)
			{
				s.add((byte) ((tmp1 == null) ? 0 : 1));
				if (tmp1 != null)
				{
					List<Byte> tmp2 = new ArrayList<>();
					tmp2.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp1.length()).array()));
					while (tmp2.size() > 0 && tmp2.get(tmp2.size() - 1) == 0)
						tmp2.remove(tmp2.size() - 1);
					s.add((byte) tmp2.size());
					s.addAll(tmp2);
					
					s.addAll(b2B(tmp1.getBytes(Charset.forName("ISO-8859-1"))));
				}
			}
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize parents
		offset = super.deserialize(s, offset);
		
		// deserialize turnAllowedSides
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
			
			turnAllowedSides = new ArrayList<>();
			for (int tmp7 = 0; tmp7 < tmp6; tmp7++)
			{
				String tmp8;
				byte tmp9;
				tmp9 = s[offset];
				offset += Byte.BYTES;
				if (tmp9 == 1)
				{
					byte tmp10;
					tmp10 = s[offset];
					offset += Byte.BYTES;
					byte[] tmp11 = Arrays.copyOfRange(s, offset, offset + tmp10);
					offset += tmp10;
					int tmp12;
					tmp12 = ByteBuffer.wrap(Arrays.copyOfRange(tmp11, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					
					tmp8 = new String(s, offset, tmp12, Charset.forName("ISO-8859-1"));
					offset += tmp12;
				}
				else
					tmp8 = null;
				turnAllowedSides.add(tmp8);
			}
		}
		else
			turnAllowedSides = null;
		
		return offset;
	}
}
