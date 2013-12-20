package behaviours;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;

public class Fear extends Behaviour {

	private ArrayList<Integer> scaryList = new  ArrayList<Integer>();
	private float scaryFactor;
	private Vector2d distanceVec;
	
	private int activationRadius;
	private Vector2d fear;
	private Vector2d velModifier;
	private float maxSpeed;
	public Fear(ArrayList<Integer> scaryPopulation,int scaryFactor,int activationRadius)
	{
		distanceVec = new Vector2d(0,0);
		scaryList = scaryPopulation;
		this.scaryFactor = scaryFactor/100;
		this.activationRadius = activationRadius/3;
		velModifier = new Vector2d(0,0);
		fear = new Vector2d(0,0);
	}
	
	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub
		this.scaryFactor = 0.05f;
		activationRadius = 50;
		
	}

	@Override
	public void Update(Agent agent,ArrayList<Agent> population) {
		
		
		fear.set(0,0);
        int neightbours = 0;
        for(Agent otherAgent : population)
        {
        	if((scaryList.contains(otherAgent.getSpecie()) && otherAgent.isAlive()))
        	{
            distanceVec.set(agent.getPosition());
            distanceVec.sub(otherAgent.getPosition());
            if((distanceVec.length() < activationRadius) && (distanceVec.length() > 0.001)) {
            	fear.add(distanceVec);
                neightbours++; 
           }
        }
		//neightbours--;
        if(neightbours > 0) {
        	fear.scale(1.0f/neightbours);
        	fear.scale(scaryFactor);
        	agent.setExcited(true);
        }   
        velModifier.add(fear);
        
		
		
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
