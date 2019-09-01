package team.koala.chillin.client.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class EndGame extends KSObject
{
	protected String winnerSidename;
	protected Map<String, Map<String, String>> details;
	
	// getters
	
	public String getWinnerSidename()
	{
		return this.winnerSidename;
	}
	
	public Map<String, Map<String, String>> getDetails()
	{
		return this.details;
	}
	
	
	// setters
	
	public void setWinnerSidename(String winnerSidename)
	{
		this.winnerSidename = winnerSidename;
	}
	
	public void setDetails(Map<String, Map<String, String>> details)
	{
		this.details = details;
	}
	
	
	public EndGame()
	{
	}
	
	public static final String nameStatic = "EndGame";
	
	@Override
	public String name() { return "EndGame"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize winnerSidename
		s.add((byte) ((winnerSidename == null) ? 0 : 1));
		if (winnerSidename != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(winnerSidename.length()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			s.addAll(b2B(winnerSidename.getBytes(Charset.forName("ISO-8859-1"))));
		}
		
		// serialize details
		s.add((byte) ((details == null) ? 0 : 1));
		if (details != null)
		{
			List<Byte> tmp1 = new ArrayList<>();
			tmp1.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(details.size()).array()));
			while (tmp1.size() > 0 && tmp1.get(tmp1.size() - 1) == 0)
				tmp1.remove(tmp1.size() - 1);
			s.add((byte) tmp1.size());
			s.addAll(tmp1);
			
			for (Map.Entry<String, Map<String, String>> tmp2 : details.entrySet())
			{
				s.add((byte) ((tmp2.getKey() == null) ? 0 : 1));
				if (tmp2.getKey() != null)
				{
					List<Byte> tmp3 = new ArrayList<>();
					tmp3.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp2.getKey().length()).array()));
					while (tmp3.size() > 0 && tmp3.get(tmp3.size() - 1) == 0)
						tmp3.remove(tmp3.size() - 1);
					s.add((byte) tmp3.size());
					s.addAll(tmp3);
					
					s.addAll(b2B(tmp2.getKey().getBytes(Charset.forName("ISO-8859-1"))));
				}
				
				s.add((byte) ((tmp2.getValue() == null) ? 0 : 1));
				if (tmp2.getValue() != null)
				{
					List<Byte> tmp4 = new ArrayList<>();
					tmp4.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp2.getValue().size()).array()));
					while (tmp4.size() > 0 && tmp4.get(tmp4.size() - 1) == 0)
						tmp4.remove(tmp4.size() - 1);
					s.add((byte) tmp4.size());
					s.addAll(tmp4);
					
					for (Map.Entry<String, String> tmp5 : tmp2.getValue().entrySet())
					{
						s.add((byte) ((tmp5.getKey() == null) ? 0 : 1));
						if (tmp5.getKey() != null)
						{
							List<Byte> tmp6 = new ArrayList<>();
							tmp6.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp5.getKey().length()).array()));
							while (tmp6.size() > 0 && tmp6.get(tmp6.size() - 1) == 0)
								tmp6.remove(tmp6.size() - 1);
							s.add((byte) tmp6.size());
							s.addAll(tmp6);
							
							s.addAll(b2B(tmp5.getKey().getBytes(Charset.forName("ISO-8859-1"))));
						}
						
						s.add((byte) ((tmp5.getValue() == null) ? 0 : 1));
						if (tmp5.getValue() != null)
						{
							List<Byte> tmp7 = new ArrayList<>();
							tmp7.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp5.getValue().length()).array()));
							while (tmp7.size() > 0 && tmp7.get(tmp7.size() - 1) == 0)
								tmp7.remove(tmp7.size() - 1);
							s.add((byte) tmp7.size());
							s.addAll(tmp7);
							
							s.addAll(b2B(tmp5.getValue().getBytes(Charset.forName("ISO-8859-1"))));
						}
					}
				}
			}
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize winnerSidename
		byte tmp8;
		tmp8 = s[offset];
		offset += Byte.BYTES;
		if (tmp8 == 1)
		{
			byte tmp9;
			tmp9 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp10 = Arrays.copyOfRange(s, offset, offset + tmp9);
			offset += tmp9;
			int tmp11;
			tmp11 = ByteBuffer.wrap(Arrays.copyOfRange(tmp10, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			winnerSidename = new String(s, offset, tmp11, Charset.forName("ISO-8859-1"));
			offset += tmp11;
		}
		else
			winnerSidename = null;
		
		// deserialize details
		byte tmp12;
		tmp12 = s[offset];
		offset += Byte.BYTES;
		if (tmp12 == 1)
		{
			byte tmp13;
			tmp13 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp14 = Arrays.copyOfRange(s, offset, offset + tmp13);
			offset += tmp13;
			int tmp15;
			tmp15 = ByteBuffer.wrap(Arrays.copyOfRange(tmp14, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			details = new HashMap<>();
			for (int tmp16 = 0; tmp16 < tmp15; tmp16++)
			{
				String tmp17;
				byte tmp19;
				tmp19 = s[offset];
				offset += Byte.BYTES;
				if (tmp19 == 1)
				{
					byte tmp20;
					tmp20 = s[offset];
					offset += Byte.BYTES;
					byte[] tmp21 = Arrays.copyOfRange(s, offset, offset + tmp20);
					offset += tmp20;
					int tmp22;
					tmp22 = ByteBuffer.wrap(Arrays.copyOfRange(tmp21, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					
					tmp17 = new String(s, offset, tmp22, Charset.forName("ISO-8859-1"));
					offset += tmp22;
				}
				else
					tmp17 = null;
				
				Map<String, String> tmp18;
				byte tmp23;
				tmp23 = s[offset];
				offset += Byte.BYTES;
				if (tmp23 == 1)
				{
					byte tmp24;
					tmp24 = s[offset];
					offset += Byte.BYTES;
					byte[] tmp25 = Arrays.copyOfRange(s, offset, offset + tmp24);
					offset += tmp24;
					int tmp26;
					tmp26 = ByteBuffer.wrap(Arrays.copyOfRange(tmp25, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					
					tmp18 = new HashMap<>();
					for (int tmp27 = 0; tmp27 < tmp26; tmp27++)
					{
						String tmp28;
						byte tmp30;
						tmp30 = s[offset];
						offset += Byte.BYTES;
						if (tmp30 == 1)
						{
							byte tmp31;
							tmp31 = s[offset];
							offset += Byte.BYTES;
							byte[] tmp32 = Arrays.copyOfRange(s, offset, offset + tmp31);
							offset += tmp31;
							int tmp33;
							tmp33 = ByteBuffer.wrap(Arrays.copyOfRange(tmp32, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
							
							tmp28 = new String(s, offset, tmp33, Charset.forName("ISO-8859-1"));
							offset += tmp33;
						}
						else
							tmp28 = null;
						
						String tmp29;
						byte tmp34;
						tmp34 = s[offset];
						offset += Byte.BYTES;
						if (tmp34 == 1)
						{
							byte tmp35;
							tmp35 = s[offset];
							offset += Byte.BYTES;
							byte[] tmp36 = Arrays.copyOfRange(s, offset, offset + tmp35);
							offset += tmp35;
							int tmp37;
							tmp37 = ByteBuffer.wrap(Arrays.copyOfRange(tmp36, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
							
							tmp29 = new String(s, offset, tmp37, Charset.forName("ISO-8859-1"));
							offset += tmp37;
						}
						else
							tmp29 = null;
						
						tmp18.put(tmp28, tmp29);
					}
				}
				else
					tmp18 = null;
				
				details.put(tmp17, tmp18);
			}
		}
		else
			details = null;
		
		return offset;
	}
}
