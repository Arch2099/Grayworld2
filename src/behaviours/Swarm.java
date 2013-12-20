package behaviours;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;

public class Swarm extends Behaviour{
	
	private Vector2d separationVec;
	private Vector2d velModifier;
	private Vector2d alignVec;
	private int separationRadius;
	private float separationFactor;
	private float maxSpeed;
	
	private Vector2d distanceVec;
	
	private Vector2d cohesionVec;
	private int cohesionRadius;
	private float cohesionFactor;
	
	private Vector2d avgVel;
	private int alignRadius;
	private float alignFactor;
	
	
		
	public Swarm()
		{
			separationVec = new Vector2d(0,0);
			velModifier = new Vector2d(0,0);
			distanceVec =  new Vector2d(0,0);
			cohesionVec =  new Vector2d(0,0);
			alignVec = new Vector2d(0,0);
			avgVel =  new Vector2d(0,0);
			separationRadius = 10;
			cohesionRadius = 40;
			alignRadius = 40;
			separationFactor = 0.3f;
			cohesionFactor = 0.02f;
			alignFactor = 0.5f;
			maxSpeed = 4f;
		}
	@Override
	public void initialise(Agent myagent) {
		// TODO Auto-generated method stub
		
		
		
		
	}
	
	public void Update(Agent agent,ArrayList<Agent> population) {
		
		
		Separation(agent,population);
		Cohesion(agent,population);
		Alignment(agent,population);
		
		//velModifier.set(0,0);
		//flock(agent,population);
		//System.out.println(agent.getVelocity());
		velModifier.add(agent.getVelocity());
		//System.out.println(velModifier.length());
		velModifier.scale(agent.limitSpeed(agent.getVelocity()));
        agent.setVelocity(velModifier);
        
		
		
		
	}
		


	private void flock(Agent agent, ArrayList<Agent> population) {
		for(Agent otherAgent : population){
			float distance = 0;
			cohesionVec.set(agent.getPosition());
			alignVec.set(0,0);
			if (!(agent.equals(otherAgent)))
			{
				distance = vector2dDistance(agent.getPosition(),otherAgent.getPosition());
				if(distance < separationRadius)
				{					
					velModifier.add(agent.getPosition());
					velModifier.sub(otherAgent.getPosition());
				}
				else if (distance< cohesionRadius)
				{
					cohesionVec.set(agent.getPosition());
					cohesionVec.sub(otherAgent.getPosition());
					cohesionVec.scale(cohesionFactor);
					velModifier.add(cohesionVec);
				}
				if (distance < alignRadius)
				{
					alignVec = otherAgent.getVelocity();
					alignVec.scale(alignFactor);
					velModifier.add(alignVec);
				}
				System.out.println(distance);
			}
			else 
			{
				System.out.println("self");
			}

				

		}
		
	}
	private void Cohesion(Agent agent,ArrayList<Agent> population) {
		cohesionVec.set(0,0);
        int neightbours = 0;
        Vector2d thisPos = new Vector2d(0,0);
        for(Agent otherAgent : population){
            thisPos.set(agent.getPosition());
            thisPos.sub(otherAgent.getPosition());
          // System.out.print(agent.getPosition());
           // System.out.print(otherAgent.getPosition()); //   DEBUG
            //System.out.println(thisPos.length());
            if( (thisPos.length()<cohesionRadius) && (thisPos.length() > 0.001)){
                cohesionVec.add(otherAgent.getPosition());
                neightbours++;
            }
        }
        //cohesionVec.sub(agent.getPosition());
        //neightbours--;
        
        if(neightbours > 0) {
        	cohesionVec.scale(1.0f/neightbours);
        	cohesionVec.sub(agent.getPosition());
        	cohesionVec.scale(cohesionFactor);
        }
        velModifier.add(cohesionVec);
		
		
	}

	private void Alignment(Agent agent,ArrayList<Agent> population) {
		avgVel.set(0,0);
        int neightbours = 0;
        for(Agent otherAgent : population){
        	distanceVec.set(agent.getPosition());
        	distanceVec.sub(otherAgent.getPosition());
            if ((distanceVec.length() < alignRadius) && (distanceVec.length() > 0.001)) {
                avgVel.add(otherAgent.getVelocity());  
                neightbours++;
            }
        }
        //avgVel.sub(agent.getVelocity());
        //neightbours--;
        if(neightbours > 0) {
            avgVel.scale(1.0f/neightbours);
            avgVel.scale(alignFactor); 
        } 
        velModifier.add(avgVel);
		
	}

	private void Separation(Agent agent,ArrayList<Agent> population) {
		separationVec.set(0,0);
        int neightbours = 0;
        for(Agent otherAgent : population)
        {
            distanceVec.set(agent.getPosition());
            distanceVec.sub(otherAgent.getPosition());
            if((distanceVec.length() < separationRadius)&& (distanceVec.length() > 0.001)) {
                separationVec.add(distanceVec);
                neightbours++; 
            }
        }
		//neightbours--;
        if(neightbours > 0) {
        	separationVec.scale(1.0f/neightbours);
        	separationVec.scale(separationFactor);
        }   
        velModifier.add(separationVec);
		
	}

		
	public float vector2dDistance(Vector2d v1,Vector2d v2)
	{
		float vectorDistance;
		vectorDistance = (float) Math.sqrt((v1.x - v2.x)*(v1.x - v2.x)+(v1.y -v2.y)*(v1.y -v2.y));
		return vectorDistance;
	}

}
