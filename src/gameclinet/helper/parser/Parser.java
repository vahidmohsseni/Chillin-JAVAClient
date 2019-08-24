package gameclinet.helper.parser;

import ks.KSObject;
import gameclinet.helper.messages.Message;

import java.nio.charset.StandardCharsets;


public class Parser {

    public MessageFactory messageFactory;

    public Parser() {

        messageFactory = new MessageFactory();
    }


    public byte[] encode(KSObject payload_obj){
        Message msg = new Message();
        msg.type = payload_obj.Name();
        msg.payload = new String(payload_obj.serialize(), StandardCharsets.ISO_8859_1);


        return msg.serialize();
    }

    public KSObject decode(byte[] data){

        KSObject result_msg;
        Message msg = new Message();
        msg.deserialize(data);

        result_msg = messageFactory.getMessage(msg.type);
        result_msg.deserialize(msg.payload.getBytes(StandardCharsets.ISO_8859_1));


        return result_msg;
    }

    public static byte[] getBytes(String string){
        return string.getBytes(StandardCharsets.ISO_8859_1);
    }

    public static String getString(byte[] bytes){
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

}

