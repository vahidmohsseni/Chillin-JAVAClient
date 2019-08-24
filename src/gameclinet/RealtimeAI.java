package gameclinet;

import gameclinet.helper.messages.*;
import ks.KSObject;


public class RealtimeAI extends BaseAI {

    public Integer currentCycle;
    public Float duration;
    public RealtimeAI(KSObject in_world) {
        super(in_world);
        currentCycle = 0;
        duration = null;
    }

    public void updateRealtime(RealtimeSnapshot snapshot) {
        super.update(snapshot);
        this.currentCycle = snapshot.getCurrentCycle();
        this.duration = snapshot.getCycleDuration();
    }


    protected void _sendCommand(KSObject command, RealtimeCommand msg) {
        msg.setCycle(this.currentCycle);
        super._sendCommand(command, msg);
    }

    public void _sendCommand(KSObject command){
        RealtimeCommand msg = new RealtimeCommand();
        msg.setCycle(this.currentCycle);
        super._sendCommand(command, msg);
    }


    public void sendCommand(KSObject command){
        if (allowedToDecide()){
            _sendCommand(command);
        }
    }


}
