package gameclinet;

import gameclinet.helper.messages.BaseCommand;
import gameclinet.helper.messages.BaseSnapshot;
import gameclinet.helper.parser.Parser;

import ks.KSObject;


import java.util.*;

public class BaseAI {

    BaseAI(KSObject in_world){
        world = in_world;
        otherSides = new LinkedHashSet<>();

    }
    private Queue<KSObject> commandSendQueue = null;
    public KSObject world;
    public Map<String, List<String>> sides;
    public String mySide;
    public String otherSide;
    public Set<String> otherSides;

    public void setCommandSendQueue(Queue q){
        commandSendQueue = q;
    }


    public void update(BaseSnapshot snapshot){
        this.world.deserialize(Parser.getBytes(snapshot.getWorldPayload()));
    }

    public boolean allowedToDecide(){
        return true;
    }


    protected void _sendCommand(KSObject command, BaseCommand msg){
        msg.setType(command.Name());
        msg.setPayload(Parser.getString(command.serialize()));
        commandSendQueue.add(msg);
    }

    protected void _sendCommand(KSObject command){
        BaseCommand msg = new BaseCommand();
        msg.setType(command.Name());
        msg.setPayload(Parser.getString(command.serialize()));
        commandSendQueue.add(msg);
    }

    public void sendCommand(KSObject command){
        if (allowedToDecide()){
            _sendCommand(command);
        }
    }

    public void initialize(){

    }

    public void decide(){

    }
}
