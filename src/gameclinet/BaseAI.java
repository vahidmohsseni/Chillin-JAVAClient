package gameclinet;

import gameclinet.helper.messages.BaseCommand;
import gameclinet.helper.messages.BaseSnapshot;
import gameclinet.helper.messages.KSObject;
import gameclinet.helper.parser.Parser;


import java.util.*;

public abstract class BaseAI {

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
        this.world.deserialize(Parser.getBytes(snapshot.worldPayload));
    }

    public boolean allowedToDecide(){
        return true;
    }


    protected void _sendCommands(BaseCommand command, BaseCommand msg){
        msg.type = command.type;
        msg.payload =Parser.getString(command.serialize());
        commandSendQueue.add(msg);
    }

    protected void _sendCommands(BaseCommand command){
        BaseCommand msg = new BaseCommand();
        msg.type = command.type;
        msg.payload =Parser.getString(command.serialize());
        commandSendQueue.add(msg);
    }

    public void sendCommand(BaseCommand command){
        if (allowedToDecide()){
            _sendCommands(command);
        }
    }

    public void initialize(){

    }

    public void decide(){

    }
}
