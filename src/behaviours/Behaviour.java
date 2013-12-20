package behaviours;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2d;
import bioSimulation.Agent;

public abstract class Behaviour {
	
	public abstract void initialise(Agent agent);
	public abstract void Update(Agent agent,ArrayList<Agent> population);
}
