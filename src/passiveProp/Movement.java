package passiveProp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2d;

import behaviours.Behaviour;
import bioSimulation.Agent;

public class Movement extends Passive {

	int cruiseSpeed;
	int sprintSpeed;
	Random rnd = new Random();
	Vector2d carbVelocity = new Vector2d(0, 0);

	public Movement(int cruiseSpeed, int sprintSpeed) {
		this.cruiseSpeed = cruiseSpeed /20;
		this.sprintSpeed = this.cruiseSpeed + sprintSpeed / 60;

	}

	@Override
	public void initialise(Agent agent) {

		agent.setCruiseSpeed(cruiseSpeed);
		agent.setSprintSpeed(sprintSpeed);

		// agent.setVelocity(new Vector2d(rnd.nextInt(5),rnd.nextInt(5)));

	}
	/*
	@Override
	public void Update(Agent agent, ArrayList<Agent> population) {

		if (!agent.isExcited()) {
			if (agent.getVelocity().length() > cruiseSpeed) {
				carbVelocity = agent.getVelocity();
				carbVelocity.scale(agent.limitSpeed(agent.getVelocity()));
				agent.setVelocity(carbVelocity);
			}
		}
	}

	@Override
	public void Update(Agent agent, Agent otherAgent, Vector2d distanceVec,
			double distance) {
		if (!agent.isExcited()) {
			if (agent.getVelocity().lengthSquared() > cruiseSpeed) {
				carbVelocity = agent.getVelocity();
				carbVelocity.scale(agent.limitSpeed(agent.getVelocity()));
				agent.setVelocity(carbVelocity);
			}
		}

	} */

}
