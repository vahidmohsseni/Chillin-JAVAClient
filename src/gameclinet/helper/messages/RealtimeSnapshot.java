package gameclinet.helper.messages;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RealtimeSnapshot extends BaseSnapshot
{
    public Integer currentCycle;
    public Float cycleDuration;


    public RealtimeSnapshot()
    {
    }

    public static final String NameStatic = "RealtimeSnapshot";

    @Override
    public String Name() { return "RealtimeSnapshot"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize parents
        s.addAll(b2B(super.serialize()));

        // serialize currentCycle
        s.add((byte) ((currentCycle == null) ? 0 : 1));
        if (currentCycle != null)
        {
            s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(currentCycle).array()));
        }

        // serialize cycleDuration
        s.add((byte) ((cycleDuration == null) ? 0 : 1));
        if (cycleDuration != null)
        {
            s.addAll(b2B(ByteBuffer.allocate(Float.BYTES).order(ByteOrder.LITTLE_ENDIAN).putFloat(cycleDuration).array()));
        }

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize parents
        offset = super.deserialize(s, offset);

        // deserialize currentCycle
        byte tmp132;
        tmp132 = s[offset];
        offset += Byte.BYTES;
        if (tmp132 == 1)
        {
            currentCycle = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
            offset += Integer.BYTES;
        }
        else
            currentCycle = null;

        // deserialize cycleDuration
        byte tmp133;
        tmp133 = s[offset];
        offset += Byte.BYTES;
        if (tmp133 == 1)
        {
            cycleDuration = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Float.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
            offset += Float.BYTES;
        }
        else
            cycleDuration = null;

        return offset;
    }
}