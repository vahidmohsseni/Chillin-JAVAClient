package gameclinet.helper.messages;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseCommand extends KSObject
{
    public String type;
    public String payload;


    public BaseCommand()
    {
    }

    public static final String NameStatic = "BaseCommand";

    @Override
    public String Name() { return "BaseCommand"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize type
        s.add((byte) ((type == null) ? 0 : 1));
        if (type != null)
        {
            List<Byte> tmp147 = new ArrayList<>();
            tmp147.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(type.length()).array()));
            while (tmp147.size() > 0 && tmp147.get(tmp147.size() - 1) == 0)
                tmp147.remove(tmp147.size() - 1);
            s.add((byte) tmp147.size());
            s.addAll(tmp147);

            s.addAll(b2B(type.getBytes(Charset.forName("ISO-8859-1"))));
        }

        // serialize payload
        s.add((byte) ((payload == null) ? 0 : 1));
        if (payload != null)
        {
            List<Byte> tmp148 = new ArrayList<>();
            tmp148.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(payload.length()).array()));
            while (tmp148.size() > 0 && tmp148.get(tmp148.size() - 1) == 0)
                tmp148.remove(tmp148.size() - 1);
            s.add((byte) tmp148.size());
            s.addAll(tmp148);

            s.addAll(b2B(payload.getBytes(Charset.forName("ISO-8859-1"))));
        }

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize type
        byte tmp149;
        tmp149 = s[offset];
        offset += Byte.BYTES;
        if (tmp149 == 1)
        {
            byte tmp150;
            tmp150 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp151 = Arrays.copyOfRange(s, offset, offset + tmp150);
            offset += tmp150;
            int tmp152;
            tmp152 = ByteBuffer.wrap(Arrays.copyOfRange(tmp151, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            type = new String(s, offset, tmp152, Charset.forName("ISO-8859-1"));
            offset += tmp152;
        }
        else
            type = null;

        // deserialize payload
        byte tmp153;
        tmp153 = s[offset];
        offset += Byte.BYTES;
        if (tmp153 == 1)
        {
            byte tmp154;
            tmp154 = s[offset];
            offset += Byte.BYTES;
            byte[] tmp155 = Arrays.copyOfRange(s, offset, offset + tmp154);
            offset += tmp154;
            int tmp156;
            tmp156 = ByteBuffer.wrap(Arrays.copyOfRange(tmp155, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            payload = new String(s, offset, tmp156, Charset.forName("ISO-8859-1"));
            offset += tmp156;
        }
        else
            payload = null;

        return offset;
    }
}