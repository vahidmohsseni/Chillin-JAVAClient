package gameclinet.helper.messages;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class StartGame extends gameclinet.helper.messages.KSObject
{
    public Integer startTime;


    public StartGame()
    {
    }

    public static final String NameStatic = "StartGame";

    @Override
    public String Name() { return "StartGame"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize startTime
        s.add((byte) ((startTime == null) ? 0 : 1));
        if (startTime != null)
        {
            s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(startTime).array()));
        }

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize startTime
        byte tmp88;
        tmp88 = s[offset];
        offset += Byte.BYTES;
        if (tmp88 == 1)
        {
            startTime = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
            offset += Integer.BYTES;
        }
        else
            startTime = null;

        return offset;
    }
}
