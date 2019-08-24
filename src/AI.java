import gameclinet.TurnbasedAI;
import ks.models.*;
import ks.commands.*;


public class AI extends TurnbasedAI {

    World world;

    public AI(World world1) {
        super(world1);
        world = world1;

    }

    public void decide() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if (world.getBoard()[i][j] == ECell.Empty){
                    Place p = new Place();
                    p.setX(j);
                    p.setY(i);
                    sendCommand(p);
                    return;
                }
            }
        }

    }

}
