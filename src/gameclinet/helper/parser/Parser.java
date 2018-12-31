package gameclinet.helper.parser;

import gameclinet.helper.messages.KSObject;
import gameclinet.helper.messages.Message;
import org.json.JSONArray;


public class Parser {

    public MessageFactory messageFactory;

    public Parser(JSONArray ks_command_files) {
        messageFactory = new MessageFactory(ks_command_files);
    }


    public byte[] encode(KSObject payload_obj){
        Message msg = new Message();
        msg.type = payload_obj.Name();
        msg.payload = payload_obj.serialize().toString();

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

