package gameclinet.helper.messages;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgentJoined extends KSObject
{
    public String sideName;
    public String agentName;
    public String teamNickname;


    public AgentJoined()
    {
    }

    public static final String NameStatic = "AgentJoined";

    @Override
    public String Name() { return "AgentJoined"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize sideName
        s.add((byte) ((sideName == null) ? 0 : 1));
        if (sideName != null)
        {
            List<Byte> tmp63 = new ArrayList<>();
            tmp63.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(sideName.length()).array()));
            while (tmp63.size() > 0 && tmp63.get(tmp63.size() - 1) == 0)
                tmp63.remove(tmp63.size() - 1);
            s.add((byte) tmp63.size());
            s.addAll(tmp63);

            s.addAll(b2B(sideName.getBytes(Charset.forName("ISO-8859-1"))));
        }

        // serialize agentName
        s.add((byte) ((agentName == null) ? 0 : 1));
        if (agentName != null)
        {
            List<Byte> tmp64 = new ArrayList<>();
            tmp64.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(agentName.length()).array()));
            while (tmp64.size() > 0 && tmp64.get(tmp64.size() - 1) == 0)
                tmp64.remove(tmp64.size() - 1);
            s.add((byte) tmp64.size());
            s.addAll(tmp64);

            s.addAll(b2B(agentName.getBytes(Charset.forName("ISO-8859-1"))));
        }

        // serialize teamNickname
        s.add((byte) ((teamNickname == null) ? 0 : 1));
        if (teamNickname != null)
        {
            List<Byte> tmp65 = new ArrayList<>();
            tmp65.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(teamNickname.length()).array()));
            while (tmp65.size() > 0 && tmp65.get(tmp65.size() - 1) == 0)
                tmp65.remove(tmp65.size() - 1);
            s.add((byte) tmp65.size());
            s.addAll(tmp65);

            s.addAll(b2B(teamNickname.getBytes(Charset.forName("ISO-8859-1"))));
        }

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize sideName
        byte tmp66;
        tmp66 = s[offset];
        offset += Byte.BYTES;
        if (tmp66 == 1)
        {
            byte tmp67;
            tmp67 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp68 = Arrays.copyOfRange(s, offset, offset + tmp67);
            offset += tmp67;
            int tmp69;
            tmp69 = ByteBuffer.wrap(Arrays.copyOfRange(tmp68, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            sideName = new String(s, offset, tmp69, Charset.forName("ISO-8859-1"));
            offset += tmp69;
        }
        else
            sideName = null;

        // deserialize agentName
        byte tmp70;
        tmp70 = s[offset];
        offset += Byte.BYTES;
        if (tmp70 == 1)
        {
            byte tmp71;
            tmp71 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp72 = Arrays.copyOfRange(s, offset, offset + tmp71);
            offset += tmp71;
            int tmp73;
            tmp73 = ByteBuffer.wrap(Arrays.copyOfRange(tmp72, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            agentName = new String(s, offset, tmp73, Charset.forName("ISO-8859-1"));
            offset += tmp73;
        }
        else
            agentName = null;

        // deserialize teamNickname
        byte tmp74;
        tmp74 = s[offset];
        offset += Byte.BYTES;
        if (tmp74 == 1)
        {
            byte tmp75;
            tmp75 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp76 = Arrays.copyOfRange(s, offset, offset + tmp75);
            offset += tmp75;
            int tmp77;
            tmp77 = ByteBuffer.wrap(Arrays.copyOfRange(tmp76, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            teamNickname = new String(s, offset, tmp77, Charset.forName("ISO-8859-1"));
            offset += tmp77;
        }
        else
            teamNickname = null;

        return offset;
    }
}