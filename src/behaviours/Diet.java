package behaviours;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;
import bioSimulation.World;

public class Diet extends Behaviour {

	private int dietType;
	private int dietGene;
	private Vector2d eatingVec = new Vector2d(0,0);
	private int attractionRadius = 50;
	private float attractionFactor = 0.3f;
	 private Vector2d velModifier = new Vector2d(0,0);
	 
	
	 public Diet(int dietGene)
	 {
		 this.dietGene = dietGene;
	 }
	 
	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub
		
		if (dietGene < 128)
		{
			dietType = 0;   //digest fungi, bacteria
		}
		if (dietGene >= 128 && dietGene < 162 )
		{
			dietType = 1;  // digest plants
		}
		if (dietGene >= 162 )
		{
			dietType = 2;  // digest animals
		}
		//agent.setAgentType(dietType);
	}
	@Override
	public void Update(Agent agent,ArrayList<Agent> population) {

		
			eatingVec.set(0, 0);			
			Vector2d thisPos = new Vector2d(0, 0);
			//foreach start
			for (Agent otherAgent : population) {
				//solve this diet problem
				if (!agent.equals(otherAgent))
				{
				if (dietType==otherAgent.getKingdom() && !otherAgent.isAlive()) {
					thisPos.set(agent.getPosition());
					thisPos.sub(otherAgent.getPosition());
									
					if ((thisPos.length() < agent.getInteractionRange() && !otherAgent.isDevoured())
							&& (thisPos.length() > 0.001)) {
						otherAgent.setDevoured(true); //gnam
						agent.addEnergy(otherAgent.getSize()*25);
						break;
					}
					
					if ((thisPos.length() < attractionRadius)
							&& (thisPos.length() > 0.001)) {
						eatingVec.add(otherAgent.getPosition());
						eatingVec.sub(agent.getPosition());
						eatingVec.scale(attractionFactor);
						
						
						velModifier.add(eatingVec);
						//velModifier.add(agent.getVelocity());
						// System.out.println(velModifier.length());
						velModifier.scale(agent.limitSpeed(velModifier));
						agent.setVelocity(velModifier);
						break;
					}
				}
				}

			}
			
			
			
						
		
		
		
		

	}
	
		
		
	}

	


