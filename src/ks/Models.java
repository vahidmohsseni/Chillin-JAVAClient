package ks;

import java.lang.*;
import java.util.*;


public class Models
{
	public enum ECell
	{
		Empty((byte) 0),
		X((byte) 1),
		O((byte) 2),
		;

		private final byte value;
		ECell(byte value) { this.value = value; }
		public byte getValue() { return value; }
		
		private static Map<Byte, ECell> reverseLookup;
		
		public static ECell of(byte value)
		{
			if (reverseLookup == null)
			{
				reverseLookup = new HashMap<>();
				for (ECell c : ECell.values())
					reverseLookup.put(c.getValue(), c);
			}
			return reverseLookup.get(value);
		}
	}
	
	public static class World extends KSObject
	{
		public ECell[][] board;
		

		public World()
		{
		}
		
		public static final String NameStatic = "World";
		
		@Override
		public String Name() { return "World"; }
		
		@Override
		public byte[] serialize()
		{
			List<Byte> s = new ArrayList<>();
			
			// serialize board
			s.add((byte) ((board == null) ? 0 : 1));
			if (board != null)
			{
				for (int tmp0 = 0; tmp0 < 3; tmp0++)
				{
					for (int tmp1 = 0; tmp1 < 3; tmp1++)
					{
						s.add((byte) ((board[tmp0][tmp1] == null) ? 0 : 1));
						if (board[tmp0][tmp1] != null)
						{
							s.add((byte) (board[tmp0][tmp1].getValue()));
						}
					}
				}
			}
			
			return B2b(s);
		}
		
		@Override
		protected int deserialize(byte[] s, int offset)
		{
			// deserialize board
			byte tmp2;
			tmp2 = s[offset];
			offset += Byte.BYTES;
			if (tmp2 == 1)
			{
				board = new ECell[3][3];
				for (int tmp3 = 0; tmp3 < 3; tmp3++)
				{
					for (int tmp4 = 0; tmp4 < 3; tmp4++)
					{
						byte tmp5;
						tmp5 = s[offset];
						offset += Byte.BYTES;
						if (tmp5 == 1)
						{
							byte tmp6;
							tmp6 = s[offset];
							offset += Byte.BYTES;
							board[tmp3][tmp4] = ECell.of(tmp6);
						}
						else
							board[tmp3][tmp4] = null;
					}
				}
			}
			else
				board = null;
			
			return offset;
		}
	}
} // Models
