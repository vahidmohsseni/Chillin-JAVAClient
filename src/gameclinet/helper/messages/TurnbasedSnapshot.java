package gameclinet.helper.messages;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TurnbasedSnapshot extends RealtimeSnapshot
{
    public List<String> turnAllowedSides;


    public TurnbasedSnapshot()
    {
    }

    public static final String NameStatic = "TurnbasedSnapshot";

    @Override
    public String Name() { return "TurnbasedSnapshot"; }

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
            List<Byte> tmp134 = new ArrayList<>();
            tmp134.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(turnAllowedSides.size()).array()));
            while (tmp134.size() > 0 && tmp134.get(tmp134.size() - 1) == 0)
                tmp134.remove(tmp134.size() - 1);
            s.add((byte) tmp134.size());
            s.addAll(tmp134);

            for (String tmp135 : turnAllowedSides)
            {
                s.add((byte) ((tmp135 == null) ? 0 : 1));
                if (tmp135 != null)
                {
                    List<Byte> tmp136 = new ArrayList<>();
                    tmp136.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp135.length()).array()));
                    while (tmp136.size() > 0 && tmp136.get(tmp136.size() - 1) == 0)
                        tmp136.remove(tmp136.size() - 1);
                    s.add((byte) tmp136.size());
                    s.addAll(tmp136);

                    s.addAll(b2B(tmp135.getBytes(Charset.forName("ISO-8859-1"))));
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
        byte tmp137;
        tmp137 = s[offset];
        offset += Byte.BYTES;
        if (tmp137 == 1)
        {
            byte tmp138;
            tmp138 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp139 = Arrays.copyOfRange(s, offset, offset + tmp138);
            offset += tmp138;
            int tmp140;
            tmp140 = ByteBuffer.wrap(Arrays.copyOfRange(tmp139, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            turnAllowedSides = new ArrayList<>();
            for (int tmp141 = 0; tmp141 < tmp140; tmp141++)
            {
                String tmp142;
                byte tmp143;
                tmp143 = s[offset];
                offset += Byte.BYTES;
                if (tmp143 == 1)
                {
                    byte tmp144;
                    tmp144 = s[offset];
                    offset += Byte.BYTES;
                    byte[] tmp145 = Arrays.copyOfRange(s, offset, offset + tmp144);
                    offset += tmp144;
                    int tmp146;
                    tmp146 = ByteBuffer.wrap(Arrays.copyOfRange(tmp145, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

                    tmp142 = new String(s, offset, tmp146, Charset.forName("ISO-8859-1"));
                    offset += tmp146;
                }
                else
                    tmp142 = null;
                turnAllowedSides.add(tmp142);
            }
        }
        else
            turnAllowedSides = null;

        return offset;
    }
}