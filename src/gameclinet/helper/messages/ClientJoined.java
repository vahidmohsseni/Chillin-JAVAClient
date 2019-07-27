package gameclinet.helper.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.*;

public class ClientJoined extends KSObject
{
    public Boolean joined;
    public String sideName;
    public Map<String, List<String>> sides;


    public ClientJoined()
    {
    }

    public static final String NameStatic = "ClientJoined";

    @Override
    public String Name() { return "ClientJoined"; }

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
            List<Byte> tmp30 = new ArrayList<>();
            tmp30.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(sideName.length()).array()));
            while (tmp30.size() > 0 && tmp30.get(tmp30.size() - 1) == 0)
                tmp30.remove(tmp30.size() - 1);
            s.add((byte) tmp30.size());
            s.addAll(tmp30);

            s.addAll(b2B(sideName.getBytes(Charset.forName("ISO-8859-1"))));
        }

        // serialize sides
        s.add((byte) ((sides == null) ? 0 : 1));
        if (sides != null)
        {
            List<Byte> tmp31 = new ArrayList<>();
            tmp31.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(sides.size()).array()));
            while (tmp31.size() > 0 && tmp31.get(tmp31.size() - 1) == 0)
                tmp31.remove(tmp31.size() - 1);
            s.add((byte) tmp31.size());
            s.addAll(tmp31);

            for (Map.Entry<String, List<String>> tmp32 : sides.entrySet())
            {
                s.add((byte) ((tmp32.getKey() == null) ? 0 : 1));
                if (tmp32.getKey() != null)
                {
                    List<Byte> tmp33 = new ArrayList<>();
                    tmp33.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp32.getKey().length()).array()));
                    while (tmp33.size() > 0 && tmp33.get(tmp33.size() - 1) == 0)
                        tmp33.remove(tmp33.size() - 1);
                    s.add((byte) tmp33.size());
                    s.addAll(tmp33);

                    s.addAll(b2B(tmp32.getKey().getBytes(Charset.forName("ISO-8859-1"))));
                }

                s.add((byte) ((tmp32.getValue() == null) ? 0 : 1));
                if (tmp32.getValue() != null)
                {
                    List<Byte> tmp34 = new ArrayList<>();
                    tmp34.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp32.getValue().size()).array()));
                    while (tmp34.size() > 0 && tmp34.get(tmp34.size() - 1) == 0)
                        tmp34.remove(tmp34.size() - 1);
                    s.add((byte) tmp34.size());
                    s.addAll(tmp34);

                    for (String tmp35 : tmp32.getValue())
                    {
                        s.add((byte) ((tmp35 == null) ? 0 : 1));
                        if (tmp35 != null)
                        {
                            List<Byte> tmp36 = new ArrayList<>();
                            tmp36.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp35.length()).array()));
                            while (tmp36.size() > 0 && tmp36.get(tmp36.size() - 1) == 0)
                                tmp36.remove(tmp36.size() - 1);
                            s.add((byte) tmp36.size());
                            s.addAll(tmp36);

                            s.addAll(b2B(tmp35.getBytes(Charset.forName("ISO-8859-1"))));
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
        byte tmp37;
        tmp37 = s[offset];
        offset += Byte.BYTES;
        if (tmp37 == 1)
        {
            joined = (s[offset] == 0) ? false : true;
            offset += Byte.BYTES;
        }
        else
            joined = null;

        // deserialize sideName
        byte tmp38;
        tmp38 = s[offset];
        offset += Byte.BYTES;
        if (tmp38 == 1)
        {
            byte tmp39;
            tmp39 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp40 = Arrays.copyOfRange(s, offset, offset + tmp39);
            offset += tmp39;
            int tmp41;
            tmp41 = ByteBuffer.wrap(Arrays.copyOfRange(tmp40, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            sideName = new String(s, offset, tmp41, Charset.forName("ISO-8859-1"));
            offset += tmp41;
        }
        else
            sideName = null;

        // deserialize sides
        byte tmp42;
        tmp42 = s[offset];
        offset += Byte.BYTES;
        if (tmp42 == 1)
        {
            byte tmp43;
            tmp43 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp44 = Arrays.copyOfRange(s, offset, offset + tmp43);
            offset += tmp43;
            int tmp45;
            tmp45 = ByteBuffer.wrap(Arrays.copyOfRange(tmp44, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            sides = new HashMap<>();
            for (int tmp46 = 0; tmp46 < tmp45; tmp46++)
            {
                String tmp47;
                byte tmp49;
                tmp49 = s[offset];
                offset += Byte.BYTES;
                if (tmp49 == 1)
                {
                    byte tmp50;
                    tmp50 = s[offset];
                    offset += Byte.BYTES;
                    byte[] tmp51 = Arrays.copyOfRange(s, offset, offset + tmp50);
                    offset += tmp50;
                    int tmp52;
                    tmp52 = ByteBuffer.wrap(Arrays.copyOfRange(tmp51, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

                    tmp47 = new String(s, offset, tmp52, Charset.forName("ISO-8859-1"));
                    offset += tmp52;
                }
                else
                    tmp47 = null;

                List<String> tmp48;
                byte tmp53;
                tmp53 = s[offset];
                offset += Byte.BYTES;
                if (tmp53 == 1)
                {
                    byte tmp54;
                    tmp54 = s[offset];
                    offset += Byte.BYTES;
                    byte[] tmp55 = Arrays.copyOfRange(s, offset, offset + tmp54);
                    offset += tmp54;
                    int tmp56;
                    tmp56 = ByteBuffer.wrap(Arrays.copyOfRange(tmp55, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

                    tmp48 = new ArrayList<>();
                    for (int tmp57 = 0; tmp57 < tmp56; tmp57++)
                    {
                        String tmp58;
                        byte tmp59;
                        tmp59 = s[offset];
                        offset += Byte.BYTES;
                        if (tmp59 == 1)
                        {
                            byte tmp60;
                            tmp60 = s[offset];
                            offset += Byte.BYTES;
                            byte[] tmp61 = Arrays.copyOfRange(s, offset, offset + tmp60);
                            offset += tmp60;
                            int tmp62;
                            tmp62 = ByteBuffer.wrap(Arrays.copyOfRange(tmp61, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

                            tmp58 = new String(s, offset, tmp62, Charset.forName("ISO-8859-1"));
                            offset += tmp62;
                        }
                        else
                            tmp58 = null;
                        tmp48.add(tmp58);
                    }
                }
                else
                    tmp48 = null;

                sides.put(tmp47, tmp48);
            }
        }
        else
            sides = null;

        return offset;
    }
}