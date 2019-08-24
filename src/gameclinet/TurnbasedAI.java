package gameclinet;

import gameclinet.helper.messages.BaseCommand;
import gameclinet.helper.messages.TurnbasedCommand;
import gameclinet.helper.messages.TurnbasedSnapshot;
import ks.KSObject;


import java.util.ArrayList;
import java.util.List;

public class TurnbasedAI extends RealtimeAI {

    public List<String> turnAllowedSides;
    public TurnbasedAI(KSObject in_world) {
        super(in_world);
        turnAllowedSides = new ArrayList<>();
    }


    public void updateTurnbased(TurnbasedSnapshot snapshot){
        super.updateRealtime(snapshot);
        this.turnAllowedSides = snapshot.getTurnAllowedSides();

    }


    @Override
    public boolean allowedToDecide() {
        return this.turnAllowedSides.contains(this.mySide);
    }

    public void _sendCommand(KSObject command, TurnbasedCommand msg){
        super._sendCommand(command, msg);
    }

    public void _sendCommand(KSObject command){
        TurnbasedCommand msg = new TurnbasedCommand();
        super._sendCommand(command, msg);
    }


    public void sendCommand(KSObject command){
        if (allowedToDecide()){
            _sendCommand(command);
        }
    }
}
