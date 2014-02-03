package passiveProp;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2d;

import behaviours.Behaviour;
import bioSimulation.Agent;

public class Senses extends Passive {
	
	private int updatedSense;
	public Senses(int senseRange)
	{
		updatedSense = senseRange;
	}
	
	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub
		agent.setSenseRange(updatedSense);
	}
	/*
	@Override
	public void Update(Agent agent,ArrayList<Agent> population){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update(Agent agent, Agent otherAgent, Vector2d distanceVec,
			double distance) {
		//nothing
		
	}
	*/

}
