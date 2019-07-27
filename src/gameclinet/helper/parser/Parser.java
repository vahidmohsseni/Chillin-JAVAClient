package gameclinet.helper.parser;

import gameclinet.helper.messages.KSObject;
import gameclinet.helper.messages.Message;

import org.json.JSONArray;

import java.nio.charset.StandardCharsets;


public class Parser {

    public MessageFactory messageFactory;

    public Parser(JSONArray ks_command_files) {

        messageFactory = new MessageFactory(ks_command_files);
    }


    public byte[] encode(KSObject payload_obj){
        Message msg = new Message();
        msg.type = payload_obj.Name();
        msg.payload = new String(payload_obj.serialize(), StandardCharsets.ISO_8859_1);


        return msg.serialize();
    }

    public KSObject decode(byte[] data){

        KSObject result;
        Message msg = new Message();
        msg.deserialize(data);

        result = messageFactory.get_message(msg.type);
        result.deserialize(msg.payload.getBytes());

        return result;
    }


}

