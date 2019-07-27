package gameclinet.helper.parser;

import gameclinet.helper.messages.*;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

class MessageFactory {

    private Map<String, KSObject> installed_messages;
//    private Map<String, KSObject> installed_commands;

    public MessageFactory(JSONArray ks_command_files) {
        installed_messages = new HashMap<>();
//        installed_commands = new HashMap<>();
        load_messages();


    }

    private void load_messages(){
        register_on_messages(new AgentJoined());
        register_on_messages(new AgentLeft());
        register_on_messages(new BaseCommand());
        register_on_messages(new BaseSnapshot());
        register_on_messages(new ClientJoined());
        register_on_messages(new EndGame());
        register_on_messages(new JoinOfflineGame());
        register_on_messages(new JoinOnlineGame());
        register_on_messages(new Message());
        register_on_messages(new RealtimeCommand());
        register_on_messages(new RealtimeSnapshot());
        register_on_messages(new StartGame());
        register_on_messages(new TurnbasedCommand());
        register_on_messages(new TurnbasedSnapshot());
    }

    private void register_on_messages(KSObject ks){
        installed_messages.put(ks.Name(), ks);

    }


    public KSObject get_message(String message_name){
        return installed_messages.get(message_name);

    }
}
