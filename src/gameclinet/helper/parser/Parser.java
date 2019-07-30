package gameclinet.helper.parser;

import gameclinet.helper.messages.BaseCommand;
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

    public KSObject[] decode(byte[] data){

        KSObject result_msg;
        Message msg = new Message();
        msg.deserialize(data);

        result_msg = messageFactory.get_message(msg.type);
        result_msg.deserialize(msg.payload.getBytes());
        KSObject result_cmd = null;
        if (BaseCommand.class.isInstance(result_msg)){
//            result_cmd = messageFactory.get_message();
        }

        return new KSObject[]{result_msg, result_cmd};
    }

    public static byte[] getBytes(String string){
        return string.getBytes(StandardCharsets.ISO_8859_1);
    }

    public static String getString(byte[] bytes){
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

}

