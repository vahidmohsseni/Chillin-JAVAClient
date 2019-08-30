package gameclinet.helper.messages;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class ClientJoined extends KSObject
{
	protected Boolean joined;
	protected String sideName;
	protected Map<String, List<String>> sides;
	
	// getters
	
	public Boolean getJoined()
	{
		return this.joined;
	}
	
	public String getSideName()
	{
		return this.sideName;
	}
	
	public Map<String, List<String>> getSides()
	{
		return this.sides;
	}
	
	
	// setters
	
	public void setJoined(Boolean joined)
	{
		this.joined = joined;
	}
	
	public void setSideName(String sideName)
	{
		this.sideName = sideName;
	}
	
	public void setSides(Map<String, List<String>> sides)
	{
		this.sides = sides;
	}
	
	
	public ClientJoined()
	{
	}
	
	public static final String nameStatic = "ClientJoined";
	
	@Override
	public String name() { return "ClientJoined"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize joined
		s.add((byte) ((joined == null) ? 0 : 1));
		if (joined != null)
		{
			s.add((byte) ((joined) ? 1 : 0));
		}
		
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
		
		// serialize sides
		s.add((byte) ((sides == null) ? 0 : 1));
		if (sides != null)
		{
			List<Byte> tmp1 = new ArrayList<>();
			tmp1.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(sides.size()).array()));
			while (tmp1.size() > 0 && tmp1.get(tmp1.size() - 1) == 0)
				tmp1.remove(tmp1.size() - 1);
			s.add((byte) tmp1.size());
			s.addAll(tmp1);
			
			for (Map.Entry<String, List<String>> tmp2 : sides.entrySet())
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
					
					for (String tmp5 : tmp2.getValue())
					{
						s.add((byte) ((tmp5 == null) ? 0 : 1));
						if (tmp5 != null)
						{
							List<Byte> tmp6 = new ArrayList<>();
							tmp6.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp5.length()).array()));
							while (tmp6.size() > 0 && tmp6.get(tmp6.size() - 1) == 0)
								tmp6.remove(tmp6.size() - 1);
							s.add((byte) tmp6.size());
							s.addAll(tmp6);
							
							s.addAll(b2B(tmp5.getBytes(Charset.forName("ISO-8859-1"))));
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
		// deserialize joined
		byte tmp7;
		tmp7 = s[offset];
		offset += Byte.BYTES;
		if (tmp7 == 1)
		{
			joined = (s[offset] == 0) ? false : true;
			offset += Byte.BYTES;
		}
		else
			joined = null;
		
		// deserialize sideName
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
			
			sideName = new String(s, offset, tmp11, Charset.forName("ISO-8859-1"));
			offset += tmp11;
		}
		else
			sideName = null;
		
		// deserialize sides
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
			
			sides = new HashMap<>();
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
				
				List<String> tmp18;
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
					
					tmp18 = new ArrayList<>();
					for (int tmp27 = 0; tmp27 < tmp26; tmp27++)
					{
						String tmp28;
						byte tmp29;
						tmp29 = s[offset];
						offset += Byte.BYTES;
						if (tmp29 == 1)
						{
							byte tmp30;
							tmp30 = s[offset];
							offset += Byte.BYTES;
							byte[] tmp31 = Arrays.copyOfRange(s, offset, offset + tmp30);
							offset += tmp30;
							int tmp32;
							tmp32 = ByteBuffer.wrap(Arrays.copyOfRange(tmp31, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
							
							tmp28 = new String(s, offset, tmp32, Charset.forName("ISO-8859-1"));
							offset += tmp32;
						}
						else
							tmp28 = null;
						tmp18.add(tmp28);
					}
				}
				else
					tmp18 = null;
				
				sides.put(tmp17, tmp18);
			}
		}
		else
			sides = null;
		
		return offset;
	}
}
