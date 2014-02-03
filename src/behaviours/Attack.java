package behaviours;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;

public class Attack extends Behaviour {

	private int damage;
	private int aggressionRange;
	private float aggressionFactor = 0.2f;
	private int determination;
	private Vector2d velModifier = new Vector2d(0, 0);
	private Vector2d attackVec = new Vector2d(0, 0);

	private ArrayList<Integer> attackList = new ArrayList<Integer>();
	private int attackListBond1;
	private int attackListBond2;
	private int attackListSelector;

	// private

	public Attack(int damage, int aggresionRange, int determination,
			int attackListBondA, int attackListBondB, int attackListSelector) {
		this.damage = damage;
		this.aggressionRange = 50;// = aggresionRange/5;
		this.determination = determination;
		if (attackListBondA <= attackListBondB) {
			this.attackListBond1 = attackListBondA;
			this.attackListBond2 = attackListBondB;
		} else {
			this.attackListBond1 = attackListBondB;
			this.attackListBond2 = attackListBondA;
		}

		this.attackListSelector = attackListSelector;
	}

	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub

		if (attackListSelector < 85) {
			for (int species = 0; species < attackListBond1; species++) {
				attackList.add(species);
			}
		} else if (attackListSelector >= 85 && attackListSelector < 170) {
			for (int species = attackListBond1; species < attackListBond2; species++) {
				attackList.add(species);
			}
		} else {
			for (int species = attackListBond2; species < 256; species++) {
				attackList.add(species);
			}
		}
	}

	@Override
	public void Update(Agent agent, ArrayList<Agent> population) {

		attackVec.set(0, 0);

		Vector2d thisPos = new Vector2d(0, 0);

		for (Agent otherAgent : population) {
			if ((attackList.contains(otherAgent.getSpecie()) && otherAgent
					.isAlive())) {
				thisPos.set(agent.getPosition());
				thisPos.sub(otherAgent.getPosition());

				if ((thisPos.lengthSquared() < agent.getInteractionRangeSq())
						&& (thisPos.length() > 0.001)) {

					otherAgent.takeDamage(damage);
					
					break;
				}
				if ((thisPos.length() < aggressionRange)
						&& (thisPos.length() > 0.001)) {
					attackVec.add(otherAgent.getPosition());
					attackVec.sub(agent.getPosition());
					attackVec.scale(aggressionFactor);
					agent.setExcited(true);
					break;
				}
			}
		}

		velModifier.add(attackVec);
		velModifier.add(agent.getVelocity());
		// System.out.println(velModifier.length());
		velModifier.scale(agent.limitSpeed(velModifier));
		agent.setVelocity(velModifier);

	}

	@Override
	public void Update(Agent agent, Agent otherAgent, Vector2d distanceVec,
			double distanceSq) {

		if (attackList.contains(otherAgent.getSpecie()) && otherAgent.isAlive() && !agent.equals(otherAgent)) {
			
			attackVec.set(distanceVec);
			attackVec.scale(aggressionFactor);
			agent.setExcited(true);

			if (distanceSq < agent.getInteractionRangeSq()) {
				otherAgent.takeDamage(damage);
			//	agent.setVelocity(new Vector2d(0,0));
			//	System.out.println(attackList);
			}

			velModifier.add(attackVec);
			velModifier.add(agent.getVelocity());
			// System.out.println(velModifier.length());
			velModifier.scale(agent.limitSpeed(velModifier));
			agent.setVelocity(velModifier);
		}

	}

}
