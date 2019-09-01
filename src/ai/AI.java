package ai;

import team.koala.chillin.client.TurnbasedAI;
import ks.models.*;
import ks.commands.*;


public class AI extends TurnbasedAI<World> {
	
	public AI(World world) {
		super(world);
	}

	@Override
	public void initialize() {
		System.out.println("initialize");
	}

	@Override
	public void decide() {
		System.out.println("decide: " + this.currentCycle);

		for(int i = 0; i < world.getBoard().length; i++)
			for(int j = 0; j < world.getBoard()[0].length; j++)
				if (world.getBoard()[i][j] == ECell.Empty) {
					Place p = new Place();
					p.setX(j);
					p.setY(i);
					this.sendCommand(p);
					return;
				}
	}
}
