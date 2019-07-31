package gameclinet.helper.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import ks.KSObject;


public class JoinOfflineGame extends KSObject
{
    public String teamNickname;
    public String agentName;


    public JoinOfflineGame()
    {
    }

    public static final String NameStatic = "JoinOfflineGame";

    @Override
    public String Name() { return "JoinOfflineGame"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize teamNickname
        s.add((byte) ((teamNickname == null) ? 0 : 1));
        if (teamNickname != null)
        {
            List<Byte> tmp10 = new ArrayList<>();
            tmp10.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(teamNickname.length()).array()));
            while (tmp10.size() > 0 && tmp10.get(tmp10.size() - 1) == 0)
                tmp10.remove(tmp10.size() - 1);
            s.add((byte) tmp10.size());
            s.addAll(tmp10);

            s.addAll(b2B(teamNickname.getBytes(Charset.forName("ISO-8859-1"))));
        }

        // serialize agentName
        s.add((byte) ((agentName == null) ? 0 : 1));
        if (agentName != null)
        {
            List<Byte> tmp11 = new ArrayList<>();
            tmp11.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(agentName.length()).array()));
            while (tmp11.size() > 0 && tmp11.get(tmp11.size() - 1) == 0)
                tmp11.remove(tmp11.size() - 1);
            s.add((byte) tmp11.size());
            s.addAll(tmp11);

            s.addAll(b2B(agentName.getBytes(Charset.forName("ISO-8859-1"))));
        }

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize teamNickname
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

            teamNickname = new String(s, offset, tmp15, Charset.forName("ISO-8859-1"));
            offset += tmp15;
        }
        else
            teamNickname = null;

        // deserialize agentName
        byte tmp16;
        tmp16 = s[offset];
        offset += Byte.BYTES;
        if (tmp16 == 1)
        {
            byte tmp17;
            tmp17 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp18 = Arrays.copyOfRange(s, offset, offset + tmp17);
            offset += tmp17;
            int tmp19;
            tmp19 = ByteBuffer.wrap(Arrays.copyOfRange(tmp18, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            agentName = new String(s, offset, tmp19, Charset.forName("ISO-8859-1"));
            offset += tmp19;
        }
        else
            agentName = null;

        return offset;
    }
}