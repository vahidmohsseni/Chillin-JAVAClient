package gameclinet.helper.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinOnlineGame extends KSObject
{
    public String token;
    public String agentName;


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
            List<Byte> tmp20 = new ArrayList<>();
            tmp20.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(token.length()).array()));
            while (tmp20.size() > 0 && tmp20.get(tmp20.size() - 1) == 0)
                tmp20.remove(tmp20.size() - 1);
            s.add((byte) tmp20.size());
            s.addAll(tmp20);

            s.addAll(b2B(token.getBytes(Charset.forName("ISO-8859-1"))));
        }

        // serialize agentName
        s.add((byte) ((agentName == null) ? 0 : 1));
        if (agentName != null)
        {
            List<Byte> tmp21 = new ArrayList<>();
            tmp21.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(agentName.length()).array()));
            while (tmp21.size() > 0 && tmp21.get(tmp21.size() - 1) == 0)
                tmp21.remove(tmp21.size() - 1);
            s.add((byte) tmp21.size());
            s.addAll(tmp21);

            s.addAll(b2B(agentName.getBytes(Charset.forName("ISO-8859-1"))));
        }

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize token
        byte tmp22;
        tmp22 = s[offset];
        offset += Byte.BYTES;
        if (tmp22 == 1)
        {
            byte tmp23;
            tmp23 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp24 = Arrays.copyOfRange(s, offset, offset + tmp23);
            offset += tmp23;
            int tmp25;
            tmp25 = ByteBuffer.wrap(Arrays.copyOfRange(tmp24, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            token = new String(s, offset, tmp25, Charset.forName("ISO-8859-1"));
            offset += tmp25;
        }
        else
            token = null;

        // deserialize agentName
        byte tmp26;
        tmp26 = s[offset];
        offset += Byte.BYTES;
        if (tmp26 == 1)
        {
            byte tmp27;
            tmp27 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp28 = Arrays.copyOfRange(s, offset, offset + tmp27);
            offset += tmp27;
            int tmp29;
            tmp29 = ByteBuffer.wrap(Arrays.copyOfRange(tmp28, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            agentName = new String(s, offset, tmp29, Charset.forName("ISO-8859-1"));
            offset += tmp29;
        }
        else
            agentName = null;

        return offset;
    }
}