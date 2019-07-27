package gameclinet.helper.messages;


import java.util.ArrayList;
import java.util.List;

public class TurnbasedCommand extends RealtimeCommand
{


    public TurnbasedCommand()
    {
    }

    public static final String NameStatic = "TurnbasedCommand";

    @Override
    public String Name() { return "TurnbasedCommand"; }

    @Override
    public byte[] serialize()
    {
        List<Byte> s = new ArrayList<>();

        // serialize parents
        s.addAll(b2B(super.serialize()));

        return B2b(s);
    }

    @Override
    protected int deserialize(byte[] s, int offset)
    {
        // deserialize parents
        offset = super.deserialize(s, offset);

        return offset;
    }
}
