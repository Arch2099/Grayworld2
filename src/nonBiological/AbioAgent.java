package nonBiological;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.vecmath.Vector2d;

import behaviours.Behaviour;
import behaviours.Movement;
import behaviours.Swarm;
import bioSimulation.Agent;
import bioSimulation.Gui;
import bioSimulation.World;

public class AbioAgent extends Agent {
	
	
	public AbioAgent(Vector2d initialPosition) {
		super(initialPosition);
		
		Random rnd = new Random();
		velocity = new Vector2d(rnd.nextInt(30)-15,rnd.nextInt(30)-15);
		
		
		Behaviour movement = new Movement(40,40);
		Behaviour repulsion = new Repulsion();
		behaviours.add(movement);
		behaviours.add(repulsion);
		
		
		position = initialPosition;
		
		charge = 1;
		energy = 100;
		size = 4;
		color = Color.BLUE;
	}
	
	public AbioAgent(Vector2d initialPosition,int type) {
		super(initialPosition);
		
		Random rnd = new Random();
		velocity = new Vector2d(rnd.nextInt(30)-15,rnd.nextInt(30)-15);
		
		
		Behaviour movement = new Movement(25,25);
		Behaviour attraction = new Attraction();
		behaviours.add(movement);
		behaviours.add(attraction);
		
		
		position = initialPosition;
		
		charge = -1;
		energy = 100;
		size = 1;
		color = Color.RED;
	}

	
	
	@Override
	public void Update(ArrayList<Agent> population)
	{
		for (Behaviour behaviour : behaviours) {
			//System.out.println("updatevelocity: " + velocity.length());
			behaviour.Update(this, population);
		}
		
		//convection test
		
		//velocity.add(new Vector2d(0,0.05f));
		//velocity.add(new Vector2d(0,1/position.y));
		
		// bounds to be fixed!
					if (position.x < 0)
					{	velocity.add(new Vector2d(5,0));
						//velocity.set(-velocity.x,velocity.y);
					}
					
					if ((position.x > World.getWbound()) &&  velocity.x > 0)
					{
						velocity.add(new Vector2d(-5,0));
						
					//	velocity.set(-velocity.x,velocity.y);
					}
					
					
					if (position.y < 0)
					{
						velocity.add(new Vector2d(0,5));
						//velocity.set(velocity.x,-velocity.y);
					}
					
					if ((position.y > (World.getHbound()-60)) && velocity.y > 0)
					{
						velocity.add(new Vector2d(0,-5));
					//	velocity.set(velocity.x,-velocity.y);
					}
					
					
					position.add(velocity);
	}



	
	
}
