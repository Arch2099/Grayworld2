package behaviours;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;

public class Share extends Behaviour {

	private int friendListBond1;
	private int friendListBond2;
	private int friendListSelector;
	private int shareRarius = 400;

	private ArrayList<Integer> friendList = new ArrayList<Integer>();

	public Share(int friendListBondA, int friendListBondB,
			int friendListSelector) {

		if (friendListBondA <= friendListBondB) {
			this.friendListBond1 = friendListBondA;
			this.friendListBond2 = friendListBondB;
		} else {
			this.friendListBond1 = friendListBondB;
			this.friendListBond2 = friendListBondA;
		}

		this.friendListSelector = friendListSelector;
	}

	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub
		if (friendListSelector < 85) {
			for (int species = 0; species < friendListSelector; species++) {
				friendList.add(species);
			}
		} else if (friendListSelector >= 85 && friendListSelector < 170) {
			for (int species = friendListBond1; species < friendListBond2; species++) {
				friendList.add(species);
			}
		} else {
			for (int species = friendListBond2; species < 256; species++) {
				friendList.add(species);
			}
		}
	}

	@Override
	public void Update(Agent agent, Agent otherAgent, Vector2d distanceVec,
			double distance) {
		if (!agent.equals(otherAgent)) {
			if (friendList.contains(otherAgent.getSpecie())
					&& otherAgent.isAlive()) {
				if (agent.getEnergy() > 99
						&& agent.getEnergy() > otherAgent.getEnergy()) {
					agent.addEnergy(-1);
					otherAgent.addEnergy(+1);
			//		System.out.println("SHARED");
				}
			}
		}
	}

	@Override
	public void Update(Agent agent, ArrayList<Agent> population) {
		// TODO Auto-generated method stub

	}
}