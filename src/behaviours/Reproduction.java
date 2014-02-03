package behaviours;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;

public class Reproduction extends Behaviour {

	private int reproductionMethod;
	private int parthenogenesisCounter = 0;

	public Reproduction(int reproductionMethod) {
		this.reproductionMethod = reproductionMethod;
	}

	private Vector2d velModifier = new Vector2d(0, 0);
	private Vector2d cohesionVec = new Vector2d(0, 0); // old vector
	private Vector2d attractionVec = new Vector2d(0, 0);

	private float attractionFactor = 0.5f;
	private float attractionRadius = 60; // old radius
	private float attractionRadiusSq = 3600;
	private float maxSpeed;

	@Override
	public void initialise(Agent agent) {
		// attractionRadius = agent.getSenseRange();
		// maxSpeed = agent.getMaxSpeed();

	}

	@Override
	public void Update(Agent agent, ArrayList<Agent> population) {

		if (reproductionMethod < 100) {
			Mitosis(agent);
		} else {
			Meiosis(agent, population);
		}

	}

	public void Mitosis(Agent agent) {
		if (agent.getEnergy() >= 200) {
			agent.setPartnerDNA(agent.getDNA());
			agent.setMated(true);
		}
	}

	public void Meiosis(Agent agent, ArrayList<Agent> population) {

		System.out.println(velModifier.length());
		if (agent.getEnergy() >= 200) {
			cohesionVec.set(0, 0);
			int neightbours = 0;
			Vector2d thisPos = new Vector2d(0, 0);
			for (Agent otherAgent : population) {
				if (agent.getSpecie() == otherAgent.getSpecie()
						&& otherAgent.isAlive()) {
					thisPos.set(agent.getPosition());
					thisPos.sub(otherAgent.getPosition());

					if ((thisPos.length() < agent.getInteractionRangeSq())
							&& (thisPos.length() > 0.001)) {
						agent.setPartnerDNA(otherAgent.getDNA());
						agent.setMated(true);
						break;
					}

					if ((thisPos.length() < attractionRadius)
							&& (thisPos.length() > 0.001)) {
						cohesionVec.add(otherAgent.getPosition());
						agent.setExcited(true);
						neightbours++;
						break;
					}
				}

			}

			if (neightbours > 0) {
				cohesionVec.scale(1.0f / neightbours);
				cohesionVec.sub(agent.getPosition());
				cohesionVec.scale(attractionFactor);
			}
			velModifier.add(cohesionVec);
			velModifier.add(agent.getVelocity());
			// System.out.println(velModifier.length());
			velModifier.scale(agent.limitSpeed(velModifier));
			agent.setVelocity(velModifier);

			parthenogenesisCounter++;
		}

		if (parthenogenesisCounter > 600) {
			Mitosis(agent);
			parthenogenesisCounter = 0;
		}

	}

	@Override
	public void Update(Agent agent, Agent otherAgent, Vector2d distanceVec,
			double distance) {
		if (reproductionMethod < 60) {
			Mitosis(agent);
		} else {
			Meiosis(agent, otherAgent, distanceVec, distance);
		}

	}

	public void Meiosis(Agent agent, Agent otherAgent, Vector2d distanceVec,
			double distance) {
		
		if (agent.getEnergy() >= 200) {
			if (!agent.equals(otherAgent) && agent.getSpecie() == otherAgent.getSpecie()
					&& otherAgent.isAlive()) {
				
				if (distance < agent.getInteractionRangeSq()) {
					agent.setPartnerDNA(otherAgent.getDNA());
					agent.setMated(true);

				}

				if (distance < attractionRadiusSq) {
					
					attractionVec.set(0, 0);
					attractionVec.set(distanceVec);
					agent.setExcited(true);	
					
					attractionVec.scale(attractionFactor);
					
					velModifier.add(attractionVec);
					velModifier.add(agent.getVelocity());
					// System.out.println(velModifier.length());
					velModifier.scale(agent.limitSpeed(velModifier));
					agent.setVelocity(velModifier);

				}

				
			}
			//System.out.println("energy :" + agent.getEnergy());
			//System.out.println("partc :" + parthenogenesisCounter);
			parthenogenesisCounter++;
		}

		

		if (parthenogenesisCounter > 600) {
			Mitosis(agent);
			parthenogenesisCounter = 0;
		}

	}

	/*
	 * public double limitSpeed(Vector2d vel){ if(vel.length() > maxSpeed)
	 * return maxSpeed/vel.length(); else return 1.0f; }
	 */
}
