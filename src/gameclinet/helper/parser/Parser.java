package gameclinet.helper.parser;

import ks.KSObject;
import gameclinet.helper.messages.Message;

import java.nio.charset.StandardCharsets;


public class Parser {

    public Parser() {}

    public byte[] encode(KSObject payloadObj) {
        Message msg = new Message();
        msg.setType(payloadObj.name());
        msg.setPayload(new String(payloadObj.serialize(), StandardCharsets.ISO_8859_1));
        return msg.serialize();
    }

    public KSObject decode(byte[] data) {
        KSObject resultMsg;
        Message msg = new Message();
        msg.deserialize(data);

        resultMsg = MessageFactory.getInstance().getMessage(msg.getType());
        resultMsg.deserialize(msg.getPayload().getBytes(StandardCharsets.ISO_8859_1));
        return resultMsg;
    }

    public static byte[] getBytes(String string) {
        return string.getBytes(StandardCharsets.ISO_8859_1);
    }

    public static String getString(byte[] bytes) {
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }
}
