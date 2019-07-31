import gameclinet.TurnbasedAI;
import ks.*;


public class AI extends TurnbasedAI {

    Models.World world;

    public AI(Models.World world1) {
        super(world1);
        world = world1;

    }

    public void decide() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if (world.board[i][j] == Models.ECell.Empty){
                    Commands.Place p = new Commands.Place();
                    p.x = j;
                    p.y = i;
                    sendCommand(p);
                    return;
                }
            }
        }

    }

}
