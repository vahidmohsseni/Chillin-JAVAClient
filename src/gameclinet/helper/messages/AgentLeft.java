package gameclinet.helper.messages;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgentLeft extends KSObject
{
    public String sideName;
    public String agentName;


    public AgentLeft()
    {
    }

    public static final String NameStatic = "AgentLeft";

    @Override
    public String Name() { return "AgentLeft"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize sideName
        s.add((byte) ((sideName == null) ? 0 : 1));
        if (sideName != null)
        {
            List<Byte> tmp78 = new ArrayList<>();
            tmp78.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(sideName.length()).array()));
            while (tmp78.size() > 0 && tmp78.get(tmp78.size() - 1) == 0)
                tmp78.remove(tmp78.size() - 1);
            s.add((byte) tmp78.size());
            s.addAll(tmp78);

            s.addAll(b2B(sideName.getBytes(Charset.forName("ISO-8859-1"))));
        }

        // serialize agentName
        s.add((byte) ((agentName == null) ? 0 : 1));
        if (agentName != null)
        {
            List<Byte> tmp79 = new ArrayList<>();
            tmp79.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(agentName.length()).array()));
            while (tmp79.size() > 0 && tmp79.get(tmp79.size() - 1) == 0)
                tmp79.remove(tmp79.size() - 1);
            s.add((byte) tmp79.size());
            s.addAll(tmp79);

            s.addAll(b2B(agentName.getBytes(Charset.forName("ISO-8859-1"))));
        }

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize sideName
        byte tmp80;
        tmp80 = s[offset];
        offset += Byte.BYTES;
        if (tmp80 == 1)
        {
            byte tmp81;
            tmp81 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp82 = Arrays.copyOfRange(s, offset, offset + tmp81);
            offset += tmp81;
            int tmp83;
            tmp83 = ByteBuffer.wrap(Arrays.copyOfRange(tmp82, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            sideName = new String(s, offset, tmp83, Charset.forName("ISO-8859-1"));
            offset += tmp83;
        }
        else
            sideName = null;

        // deserialize agentName
        byte tmp84;
        tmp84 = s[offset];
        offset += Byte.BYTES;
        if (tmp84 == 1)
        {
            byte tmp85;
            tmp85 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp86 = Arrays.copyOfRange(s, offset, offset + tmp85);
            offset += tmp85;
            int tmp87;
            tmp87 = ByteBuffer.wrap(Arrays.copyOfRange(tmp86, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            agentName = new String(s, offset, tmp87, Charset.forName("ISO-8859-1"));
            offset += tmp87;
        }
        else
            agentName = null;

        return offset;
    }
}