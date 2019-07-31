package gameclinet.helper.parser;

import gameclinet.helper.messages.*;
import ks.KSObject;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

class MessageFactory {

    private Map<String, KSObject> installed_messages;
    private Map<String, KSObject> installed_commands;

    MessageFactory(JSONArray ks_command_files) {
        installed_messages = new HashMap<>();
        installed_commands = new HashMap<>();
        loadMessages();
        loadCommands();


    }

    private void loadMessages(){
        registerOnMessages(new AgentJoined());
        registerOnMessages(new AgentLeft());
        registerOnMessages(new BaseCommand());
        registerOnMessages(new BaseSnapshot());
        registerOnMessages(new ClientJoined());
        registerOnMessages(new EndGame());
        registerOnMessages(new JoinOfflineGame());
        registerOnMessages(new JoinOnlineGame());
        registerOnMessages(new Message());
        registerOnMessages(new RealtimeCommand());
        registerOnMessages(new RealtimeSnapshot());
        registerOnMessages(new StartGame());
        registerOnMessages(new TurnbasedCommand());
        registerOnMessages(new TurnbasedSnapshot());
    }

    private void loadCommands(){
        try {

            Object newObject = Class.forName("ks.Commands").newInstance();
//            System.out.println(newObject.getClass());
            System.out.println("TODO: loading commands!");
        }
        catch (Exception e){
            System.out.println("ERROR IN LOADING ks ");
            System.out.println(e.toString());
        }
    }

    private void registerOnMessages(KSObject ks){
        installed_messages.put(ks.Name(), ks);

    }

    private void registerOnCommands(KSObject ks){
        installed_commands.put(ks.Name(), ks);

    }


    KSObject getMessage(String message_name){
        return installed_messages.get(message_name);
    }

    public KSObject getCommand(String command_name){
        return installed_commands.get(command_name);

    }
}
