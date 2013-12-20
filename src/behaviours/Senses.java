package behaviours;

import java.util.ArrayList;
import java.util.List;

import bioSimulation.Agent;

public class Senses extends Behaviour {
	
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
	
	@Override
	public void Update(Agent agent,ArrayList<Agent> population){
		// TODO Auto-generated method stub
		
	}
	

}
