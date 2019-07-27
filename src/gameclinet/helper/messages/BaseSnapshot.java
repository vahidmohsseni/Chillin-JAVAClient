package gameclinet.helper.messages;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseSnapshot extends KSObject
{
    public String worldPayload;


    public BaseSnapshot()
    {
    }

    public static final String NameStatic = "BaseSnapshot";

    @Override
    public String Name() { return "BaseSnapshot"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize worldPayload
        s.add((byte) ((worldPayload == null) ? 0 : 1));
        if (worldPayload != null)
        {
            List<Byte> tmp127 = new ArrayList<>();
            tmp127.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(worldPayload.length()).array()));
            while (tmp127.size() > 0 && tmp127.get(tmp127.size() - 1) == 0)
                tmp127.remove(tmp127.size() - 1);
            s.add((byte) tmp127.size());
            s.addAll(tmp127);

            s.addAll(b2B(worldPayload.getBytes(Charset.forName("ISO-8859-1"))));
        }

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize worldPayload
        byte tmp128;
        tmp128 = s[offset];
        offset += Byte.BYTES;
        if (tmp128 == 1)
        {
            byte tmp129;
            tmp129 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp130 = Arrays.copyOfRange(s, offset, offset + tmp129);
            offset += tmp129;
            int tmp131;
            tmp131 = ByteBuffer.wrap(Arrays.copyOfRange(tmp130, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            worldPayload = new String(s, offset, tmp131, Charset.forName("ISO-8859-1"));
            offset += tmp131;
        }
        else
            worldPayload = null;

        return offset;
    }
}
