package gameclinet.helper.parser;

import gameclinet.helper.messages.*;
import ks.KSObject;

import java.util.HashMap;
import java.util.Map;

class MessageFactory {

    private Map<String, KSObject> installed_messages;

    MessageFactory() {
        installed_messages = new HashMap<>();
        loadMessages();


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


    private void registerOnMessages(KSObject ks){
        installed_messages.put(ks.Name(), ks);

    }



    KSObject getMessage(String message_name){
        try {
            return installed_messages.get(message_name).getClass().getDeclaredConstructor().newInstance();
        }
        catch (Exception e){
            System.out.println("Error in creating new instance in getMessage MessageFactory!");
            return installed_messages.get(message_name);
        }
    }

}
