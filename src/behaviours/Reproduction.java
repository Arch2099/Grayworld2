package behaviours;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;

public class Reproduction extends Behaviour {

	private int reproductionMethod;
	private int parthenogenesisCounter = 0;
	
	public Reproduction (int reproductionMethod)
	{
		this.reproductionMethod = reproductionMethod;			
	}
	private Vector2d velModifier = new Vector2d(0,0);
	private Vector2d cohesionVec = new Vector2d(0,0);
	
	private float attractionFactor = 0.05f;
	private float attractionRadius = 60;
	private float maxSpeed;
	
	
	@Override
	public void initialise(Agent agent) {
		//attractionRadius = agent.getSenseRange();
		//maxSpeed = agent.getMaxSpeed();
		
	}
	
	@Override
	public void Update(Agent agent,ArrayList<Agent> population) {
		
		if (reproductionMethod <100)
		{
			Mitosis(agent);
		}
		else
		{
			Meiosis(agent, population);
		}
		
	}
	public void Mitosis(Agent agent)
	{
		if (agent.getEnergy() >= 200)
		{
		agent.setPartnerDNA(agent.getDNA());
        agent.setMated(true);
		}
	}

	public void Meiosis(Agent agent, ArrayList<Agent> population) {

		if (agent.getEnergy() >= 200) {
			cohesionVec.set(0, 0);
			int neightbours = 0;
			Vector2d thisPos = new Vector2d(0, 0);
			for (Agent otherAgent : population) {
				if (agent.getSpecie() == otherAgent.getSpecie() && otherAgent.isAlive()) {
					thisPos.set(agent.getPosition());
					thisPos.sub(otherAgent.getPosition());
									
					if ((thisPos.length() < agent.getInteractionRange())
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
		
		if(parthenogenesisCounter > 600)
		{
			Mitosis(agent);
			parthenogenesisCounter = 0;
		}

	}
	
	/*
	public double limitSpeed(Vector2d vel){
        if(vel.length() > maxSpeed)
            return maxSpeed/vel.length();
        else
            return 1.0f;
    }
*/
}
