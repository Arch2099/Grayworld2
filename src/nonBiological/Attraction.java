package nonBiological;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import behaviours.Behaviour;
import bioSimulation.Agent;

public class Attraction extends Behaviour{

	private Vector2d attractVec;
	private float attractFactor;
	private Vector2d velModifier;
	private int attractRadius;
	
	public Attraction()
	{
		attractFactor = 0.01f;
		attractRadius = 30;
		velModifier = new Vector2d(0,0);
		attractVec = new Vector2d(0,0);
	}
	
	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update(Agent agent, ArrayList<Agent> population) {
		attractVec.set(0, 0);
		int neightbours = 0;
		Vector2d thisPos = new Vector2d(0, 0);
		for (Agent otherAgent : population) {

			if (otherAgent.getCharge() > 0) {
				thisPos.set(agent.getPosition());
				thisPos.sub(otherAgent.getPosition());
				// System.out.print(agent.getPosition());
				// System.out.print(otherAgent.getPosition()); // DEBUG
				// System.out.println(thisPos.length());
				if ((thisPos.length() < attractRadius)
						&& (thisPos.length() > 0.001)) {
					attractVec.add(otherAgent.getPosition());
					neightbours++;
				}
			}

			// cohesionVec.sub(agent.getPosition());
			// neightbours--;
		}
		
			if (neightbours > 0) {
				attractVec.scale(1.0f / neightbours);
				attractVec.sub(agent.getPosition());
				attractVec.scale(attractFactor);
			}
			velModifier.add(attractVec);
			agent.setVelocity(velModifier);
		
	}

	@Override
	public void Update(Agent agent, Agent otherAgent, Vector2d distanceVec,
			double distance) {
		// TODO Auto-generated method stub
		
	}
}
