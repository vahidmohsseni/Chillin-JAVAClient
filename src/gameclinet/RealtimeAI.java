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
        this.currentCycle = snapshot.currentCycle;
        this.duration = snapshot.cycleDuration;
    }


    protected void _sendCommand(BaseCommand command, RealtimeCommand msg) {
        msg.cycle = this.currentCycle;
        super._sendCommand(command, msg);
    }

    public void _sendCommand(BaseCommand command){
        RealtimeCommand msg = new RealtimeCommand();
        msg.cycle = this.currentCycle;
        super._sendCommand(command, msg);
    }


    public void sendCommand(BaseCommand command){
        if (allowedToDecide()){
            _sendCommand(command);
        }
    }


}
