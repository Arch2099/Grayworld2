package nonBiological;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import behaviours.Behaviour;
import bioSimulation.Agent;

public class Repulsion extends Behaviour{

	
	private float repulsionFactor;
	private Vector2d distanceVec;
	
	private int activationRadius;
	private Vector2d repulsionVec;
	private Vector2d velModifier;
	private float maxSpeed;
	public Repulsion()
	{
		distanceVec = new Vector2d(0,0);
		
		this.repulsionFactor = repulsionFactor/100;
		this.activationRadius = activationRadius/3;
		velModifier = new Vector2d(0,0);
		repulsionVec = new Vector2d(0,0);
	}
	
	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub
		this.repulsionFactor = 3f;
		activationRadius = 55;
		
	}

	@Override
	public void Update(Agent agent,ArrayList<Agent> population) {
		
		
		repulsionVec.set(0,0);
        int neightbours = 0;
        for(Agent otherAgent : population)
        {
			if (!agent.equals(otherAgent)) {
				if (agent.getCharge() == otherAgent.getCharge()) {
					distanceVec.set(agent.getPosition());
					distanceVec.sub(otherAgent.getPosition());
					if (distanceVec.length() < activationRadius) {
						Vector2d tempVec = new Vector2d(0,0);
						tempVec.add(distanceVec);
						tempVec.normalize();
						tempVec.scale(1/distanceVec.lengthSquared());
						
						repulsionVec.add(tempVec);
						//repulsionVec.add(distanceVec);  // old official
												
						//repulsionVec.normalize();
						//repulsionVec.scale(otherAgent.getVelocity().length());
						//repulsionVec.scale(1/distanceVec.lengthSquared());
						neightbours++;
					}
				}
           
        }
		//neightbours--;
        if(neightbours > 0) {
        	//repulsionVec.scale(1.0f/neightbours);
        	repulsionVec.scale(repulsionFactor);
        	agent.setExcited(true);
        }   
        velModifier.add(repulsionVec);
        
		
		
	}
        velModifier.add(agent.getVelocity());
		//System.out.println(velModifier.length());
		velModifier.scale(agent.limitSpeed(velModifier));
        agent.setVelocity(velModifier);
	}
	
	/*
	public double limitSpeed(Vector2d vel){
        if(vel.length() > maxSpeed)
            return maxSpeed/vel.length();
        else
            return 1.0f;
    }*/
}



