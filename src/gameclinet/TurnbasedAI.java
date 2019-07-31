package gameclinet;

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
        this.turnAllowedSides = snapshot.turnAllowedSides;

    }


    @Override
    public boolean allowedToDecide() {
        return this.turnAllowedSides.contains(this.mySide);
    }
}
