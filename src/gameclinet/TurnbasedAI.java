package gameclinet;

import gameclinet.helper.messages.KSObject;
import gameclinet.helper.messages.TurnbasedSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TurnbasedAI extends RealtimeAI {

    public List<String> turnAllowedSides;
    public TurnbasedAI(KSObject in_world) {
        super(in_world);
        turnAllowedSides = new ArrayList<>();
    }


    public void update(TurnbasedSnapshot snapshot){
        super.update(snapshot);
        this.turnAllowedSides = snapshot.turnAllowedSides;

    }


    @Override
    public boolean allowedToDecide() {
        return this.turnAllowedSides.contains(this.mySide);
    }
}
