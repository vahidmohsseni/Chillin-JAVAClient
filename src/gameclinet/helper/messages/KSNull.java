package gameclinet.helper.messages;

import ks.KSObject;

public class KSNull extends KSObject {

    public KSNull (){

    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    protected int deserialize(byte[] s, int offset) {
        return 0;
    }
}
