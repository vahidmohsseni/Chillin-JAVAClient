package gameclinet.helper.messages;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.*;

public class EndGame extends KSObject
{
    public String winnerSidename;
    public Map<String, Map<String, String>> details;


    public EndGame()
    {
    }

    public static final String NameStatic = "EndGame";

    @Override
    public String Name() { return "EndGame"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize winnerSidename
        s.add((byte) ((winnerSidename == null) ? 0 : 1));
        if (winnerSidename != null)
        {
            List<Byte> tmp89 = new ArrayList<>();
            tmp89.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(winnerSidename.length()).array()));
            while (tmp89.size() > 0 && tmp89.get(tmp89.size() - 1) == 0)
                tmp89.remove(tmp89.size() - 1);
            s.add((byte) tmp89.size());
            s.addAll(tmp89);

            s.addAll(b2B(winnerSidename.getBytes(Charset.forName("ISO-8859-1"))));
        }

        // serialize details
        s.add((byte) ((details == null) ? 0 : 1));
        if (details != null)
        {
            List<Byte> tmp90 = new ArrayList<>();
            tmp90.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(details.size()).array()));
            while (tmp90.size() > 0 && tmp90.get(tmp90.size() - 1) == 0)
                tmp90.remove(tmp90.size() - 1);
            s.add((byte) tmp90.size());
            s.addAll(tmp90);

            for (Map.Entry<String, Map<String, String>> tmp91 : details.entrySet())
            {
                s.add((byte) ((tmp91.getKey() == null) ? 0 : 1));
                if (tmp91.getKey() != null)
                {
                    List<Byte> tmp92 = new ArrayList<>();
                    tmp92.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp91.getKey().length()).array()));
                    while (tmp92.size() > 0 && tmp92.get(tmp92.size() - 1) == 0)
                        tmp92.remove(tmp92.size() - 1);
                    s.add((byte) tmp92.size());
                    s.addAll(tmp92);

                    s.addAll(b2B(tmp91.getKey().getBytes(Charset.forName("ISO-8859-1"))));
                }

                s.add((byte) ((tmp91.getValue() == null) ? 0 : 1));
                if (tmp91.getValue() != null)
                {
                    List<Byte> tmp93 = new ArrayList<>();
                    tmp93.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp91.getValue().size()).array()));
                    while (tmp93.size() > 0 && tmp93.get(tmp93.size() - 1) == 0)
                        tmp93.remove(tmp93.size() - 1);
                    s.add((byte) tmp93.size());
                    s.addAll(tmp93);

                    for (Map.Entry<String, String> tmp94 : tmp91.getValue().entrySet())
                    {
                        s.add((byte) ((tmp94.getKey() == null) ? 0 : 1));
                        if (tmp94.getKey() != null)
                        {
                            List<Byte> tmp95 = new ArrayList<>();
                            tmp95.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp94.getKey().length()).array()));
                            while (tmp95.size() > 0 && tmp95.get(tmp95.size() - 1) == 0)
                                tmp95.remove(tmp95.size() - 1);
                            s.add((byte) tmp95.size());
                            s.addAll(tmp95);

                            s.addAll(b2B(tmp94.getKey().getBytes(Charset.forName("ISO-8859-1"))));
                        }

                        s.add((byte) ((tmp94.getValue() == null) ? 0 : 1));
                        if (tmp94.getValue() != null)
                        {
                            List<Byte> tmp96 = new ArrayList<>();
                            tmp96.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp94.getValue().length()).array()));
                            while (tmp96.size() > 0 && tmp96.get(tmp96.size() - 1) == 0)
                                tmp96.remove(tmp96.size() - 1);
                            s.add((byte) tmp96.size());
                            s.addAll(tmp96);

                            s.addAll(b2B(tmp94.getValue().getBytes(Charset.forName("ISO-8859-1"))));
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
        byte tmp97;
        tmp97 = s[offset];
        offset += Byte.BYTES;
        if (tmp97 == 1)
        {
            byte tmp98;
            tmp98 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp99 = Arrays.copyOfRange(s, offset, offset + tmp98);
            offset += tmp98;
            int tmp100;
            tmp100 = ByteBuffer.wrap(Arrays.copyOfRange(tmp99, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            winnerSidename = new String(s, offset, tmp100, Charset.forName("ISO-8859-1"));
            offset += tmp100;
        }
        else
            winnerSidename = null;

        // deserialize details
        byte tmp101;
        tmp101 = s[offset];
        offset += Byte.BYTES;
        if (tmp101 == 1)
        {
            byte tmp102;
            tmp102 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp103 = Arrays.copyOfRange(s, offset, offset + tmp102);
            offset += tmp102;
            int tmp104;
            tmp104 = ByteBuffer.wrap(Arrays.copyOfRange(tmp103, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            details = new HashMap<>();
            for (int tmp105 = 0; tmp105 < tmp104; tmp105++)
            {
                String tmp106;
                byte tmp108;
                tmp108 = s[offset];
                offset += Byte.BYTES;
                if (tmp108 == 1)
                {
                    byte tmp109;
                    tmp109 = s[offset];
                    offset += Byte.BYTES;
                    byte[] tmp110 = Arrays.copyOfRange(s, offset, offset + tmp109);
                    offset += tmp109;
                    int tmp111;
                    tmp111 = ByteBuffer.wrap(Arrays.copyOfRange(tmp110, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

                    tmp106 = new String(s, offset, tmp111, Charset.forName("ISO-8859-1"));
                    offset += tmp111;
                }
                else
                    tmp106 = null;

                Map<String, String> tmp107;
                byte tmp112;
                tmp112 = s[offset];
                offset += Byte.BYTES;
                if (tmp112 == 1)
                {
                    byte tmp113;
                    tmp113 = s[offset];
                    offset += Byte.BYTES;
                    byte[] tmp114 = Arrays.copyOfRange(s, offset, offset + tmp113);
                    offset += tmp113;
                    int tmp115;
                    tmp115 = ByteBuffer.wrap(Arrays.copyOfRange(tmp114, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

                    tmp107 = new HashMap<>();
                    for (int tmp116 = 0; tmp116 < tmp115; tmp116++)
                    {
                        String tmp117;
                        byte tmp119;
                        tmp119 = s[offset];
                        offset += Byte.BYTES;
                        if (tmp119 == 1)
                        {
                            byte tmp120;
                            tmp120 = s[offset];
                            offset += Byte.BYTES;
                            byte[] tmp121 = Arrays.copyOfRange(s, offset, offset + tmp120);
                            offset += tmp120;
                            int tmp122;
                            tmp122 = ByteBuffer.wrap(Arrays.copyOfRange(tmp121, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

                            tmp117 = new String(s, offset, tmp122, Charset.forName("ISO-8859-1"));
                            offset += tmp122;
                        }
                        else
                            tmp117 = null;

                        String tmp118;
                        byte tmp123;
                        tmp123 = s[offset];
                        offset += Byte.BYTES;
                        if (tmp123 == 1)
                        {
                            byte tmp124;
                            tmp124 = s[offset];
                            offset += Byte.BYTES;
                            byte[] tmp125 = Arrays.copyOfRange(s, offset, offset + tmp124);
                            offset += tmp124;
                            int tmp126;
                            tmp126 = ByteBuffer.wrap(Arrays.copyOfRange(tmp125, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

                            tmp118 = new String(s, offset, tmp126, Charset.forName("ISO-8859-1"));
                            offset += tmp126;
                        }
                        else
                            tmp118 = null;

                        tmp107.put(tmp117, tmp118);
                    }
                }
                else
                    tmp107 = null;

                details.put(tmp106, tmp107);
            }
        }
        else
            details = null;

        return offset;
    }
}