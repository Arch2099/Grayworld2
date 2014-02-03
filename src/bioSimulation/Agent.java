package bioSimulation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2d;

import passiveProp.Movement;
import passiveProp.Passive;
import behaviours.*;

public class Agent {

	private final int PROTISTA = 0;
	private final int PLANTAE = 1;
	private final int ANIMALIA = 2;

	private final int DEFENCE = 0;
	private final int ATTACK = 1;

	private final int interactionRangeSq = 100;
	private int senseRange;
	protected ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
	protected ArrayList<Passive> passives = new ArrayList<Passive>();
	protected Vector2d position = new Vector2d(0, 0);
	protected Vector2d velocity = new Vector2d(0, 0);
	protected Vector2d distanceToNextAgent = new Vector2d(0, 0);
	protected double distanceToNextSq = 0; // hold distance between agent and
											// other agent in the iterations
	protected int activationRadiusSq = 2000; // range squared that activate
												// behaviour check
	protected int priority; // 0 defence or 1 attack
	protected boolean isAttacking = false;
	protected boolean isFleeing = false;

	protected boolean excited;

	protected float sprintSpeed;
	protected float cruiseSpeed;
	protected float maxSpeed;
	protected int age = 0;
	protected int charge = 0;

	private Boolean mated = false;
	// private int agentType;
	private int kingdom; // 0= animalia 1=plantae 2=protista

	public int getKingdom() {
		return kingdom;
	}

	private boolean isAlive;
	private boolean devoured = false;
	private int deathCounter;
	private int health;
	protected float energy;
	protected float upkeep;
	protected int size;
	protected Color color;
	private String DNA;
	private String partnerDNA;
	private int species;

	private int specieR;
	private int specieG;
	private int specieB;

	public Agent(Vector2d initialPosition, int kingdom, int initialHealth,
			int initialEnergy, int initialSize, Color agentColor,
			ArrayList<Passive> passivesList,
			ArrayList<Behaviour> behavioursList, int upkeep, String DNA) {
		isAlive = true;
		passives = passivesList;
		behaviours = behavioursList;
		position.set(initialPosition);
		health = initialHealth;
		energy = initialEnergy;
		size = initialSize;
		color = agentColor;

		this.kingdom = kingdom;
		this.upkeep = upkeep;
		this.DNA = DNA;
		partnerDNA = "";
	}

	// / constructor for tests
	public Agent(Vector2d initialPosition) {
		velocity = new Vector2d(0, 0);
		position = initialPosition;
		health = 100;
		energy = 100;
		size = 10;
		color = Color.RED;
	} // test end

	// / constructor for tests
	public Agent(Vector2d initialPosition, int sense) {
		senseRange = sense;
		isAlive = true;
		Random rnd = new Random();
		velocity = new Vector2d(rnd.nextInt(30) - 15, rnd.nextInt(30) - 15);
		Swarm swarm = new Swarm();
		// Behaviour fear = new Fear();
		// behaviours.add(fear);
		Passive movement = new Movement(60, 60);
		// behaviours.add(movement);
		passives.add(movement);
		behaviours.add(swarm);
		position = initialPosition;
		health = 100;
		energy = 100;
		size = 1;
		color = Color.RED;
	} // / test end

	public void initilise() {
		Random rnd = new Random();

		for (Passive passive : passives) {
			passive.initialise(this);
		}

		determineSpecie();
		kingdomModifier();

		for (Behaviour behaviour : behaviours) {
			behaviour.initialise(this);
		}

		maxSpeed = cruiseSpeed;
		velocity.set(new Vector2d(rnd.nextFloat()- 0.5, rnd.nextFloat() - 0.5));
		// System.out.println("newborn velocity: " + velocity.length());

	}

	private void kingdomModifier() {

		switch (kingdom) {
		case ANIMALIA: {
			
			break;
		}
		case PLANTAE: {
			cruiseSpeed = cruiseSpeed / 20;
			sprintSpeed = sprintSpeed / 20;
			break;
		}
		case PROTISTA: {

			break;
		}
		default: {
			System.out.println("something gone wrong ,kingdom: " + kingdom);
			break;
		}
		}

	}

	private void determineSpecie() {

		// species are determinate by their main shade
		// the first 3 most significant bits of the color red and green
		// are added to the first 2 most significant bits of the blue color
		// generating a combination of 256 possible species
		specieR = color.getRed();
		specieG = color.getGreen();
		specieB = color.getBlue();

		String redString = Integer.toBinaryString(specieR);
		String tempString = "";
		for (int i = 0; i < (8 - redString.length()); i++) {
			tempString += "0";
		}
		redString = tempString + redString;
		redString = redString.substring(0, 3);

		String greenString = Integer.toBinaryString(specieG);

		tempString = "";
		for (int i = 0; i < (8 - greenString.length()); i++) {
			tempString += "0";
		}
		greenString = tempString + greenString;
		greenString = greenString.substring(0, 3);

		String blueString = Integer.toBinaryString(specieB);
		tempString = "";
		for (int i = 0; i < (8 - blueString.length()); i++) {
			tempString += "0";
		}
		blueString = tempString + blueString;
		blueString = blueString.substring(0, 2);

		//species = Integer.parseInt((redString[0] + greenString + blueString), 2);
		species =Integer.parseInt(redString.substring(0, 1) + 
				greenString.substring(0, 1) +
				blueString.substring(0, 1) +
				redString.substring(1, 2) + 
				greenString.substring(1, 2) +
				blueString.substring(1, 2) +
				redString.substring(2, 3) + 
				greenString.substring(2, 3),
				2);
				
				
				
		//System.out.println("newborn species :" + species);

	}

	public void Update(ArrayList<Agent> population) {

		// velocity.set(new Vector2d(rnd.nextInt(10)-5,rnd.nextInt(10)-5));
		// velocity =new Vector2d(7,3);

		// System.out.println("update");

		if (isAlive) {

			excited = false;
			isAttacking = false;
			isFleeing = false;

			for (Agent otherAgent : population) {
				distanceToNextAgent.set(otherAgent.getPosition());
				distanceToNextAgent.sub(position);
				distanceToNextSq = distanceToNextAgent.lengthSquared();

				if (distanceToNextSq < activationRadiusSq) {
					for (Behaviour behaviour : behaviours) {
						behaviour.Update(this, otherAgent, distanceToNextAgent,
								distanceToNextSq);
					}
				}
				/*
				 * if((priority == ATTACK) && (isAttacking)) { break; }
				 * if((priority == DEFENCE) && (isFleeing)) { break; }
				 */
				// System.out.println("velocity: " + velocity.length());

			}
			
			velocity.scale(limitSpeed(velocity));
			position.add(velocity);
			/*
			 * for (Behaviour behaviour : behaviours) {
			 * //System.out.println("updatevelocity: " + velocity.length());
			 * behaviour.Update(this, population); } position.add(velocity); //
			 * System.out.println("velocity: " + velocity.length());
			 */

			// energy consumed by agent
			energy -= 1;
			energy -= World.consumedEnergy(size, velocity.lengthSquared())/30; // System.out.println(energy);
			
			//System.out.println("energyconsumed: " + World.consumedEnergy(size, velocity.lengthSquared()));
			// to do : change to a switch

			switch (kingdom) {
			case ANIMALIA: {
				if (energy >= size*50)
				{
					size++;
				}
				
				break;
			}
			case PLANTAE: {
				energy += (World.photosyntesis(size));
				if (energy >= size*25)
				{
					size++;
				}
				break;
			}
			case PROTISTA: {
				if (World.resourceGather(position)) {
					energy += 40;
				}
				break;
			}
			default: {
				System.out.println("something gone wrong ,kingdom: " + kingdom);
				break;
			}
			}

			if (energy <= 0) {
				health--;
				energy =0;
			}
			if (energy > size*500)
				energy = size*500;
			if (health < 0) {
				isAlive = false;
				color = new Color(color.getRed(),color.getGreen(),color.getBlue(),200);
			}

			age++;
			if (age > size*size*1000) {
				isAlive = false;	
				color = new Color(color.getRed(),color.getGreen(),color.getBlue(),200);
			}

			// bounds to be fixed!
			if (position.x < 0)
				position.x = Gui.WIDTH;

			if (position.x > Gui.WIDTH)
				position.x = 0;

			if (position.y < 0)
				position.y = Gui.HEIGHT;

			if (position.y > Gui.HEIGHT)
				position.y = 0;
		}
		else if (deathCounter >= 3000)
		{
			devoured = true;
		}
		else
		{
			deathCounter ++;
		}
	}

	public Vector2d getDistanceToNextAgent() {
		return distanceToNextAgent;
	}

	public void setDistanceToNextAgent(Vector2d distanceToNextAgent) {
		this.distanceToNextAgent = distanceToNextAgent;
	}

	public void paint(Graphics g) {
		g.setColor(color);

		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawOval((int) position.x - (size / 2),
				(int) position.y - (size / 2), size, size);
		g.drawLine((int) position.x, (int) position.y,
				(int) (position.x - velocity.x),
				(int) (position.y - velocity.y));
	}

	public double limitSpeed(Vector2d vel) {
		if (excited) {
			maxSpeed = sprintSpeed;
		} else {
			maxSpeed = cruiseSpeed;
		}

		if (vel.length() > maxSpeed)
			return (maxSpeed) / vel.length();
		else
			return 1.0f;
	}

	public Vector2d getPosition() {
		return position;
	}

	public void setPosition(Vector2d position) {
		this.position = position;
	}

	public Vector2d getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2d velocity) {
		this.velocity = velocity;
	}

	public void modifyVelocity(Vector2d modifier) {
		this.velocity.add(modifier);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSenseRange() {
		return senseRange;
	}

	public void setSenseRange(int senseRange) {
		this.senseRange = senseRange;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public String getDNA() {
		return DNA;
	}

	public String getPartnerDNA() {
		return partnerDNA;
	}

	public void setPartnerDNA(String partnerDNA) {
		this.partnerDNA = partnerDNA;
	}

	public float getEnergy() {
		return energy;
	}

	public void addEnergy(float energy) {
		this.energy += energy;
	}

	public Boolean hasMated() {
		return mated;
	}

	public void setMated(Boolean mated) {
		this.mated = mated;
	}

	public int getInteractionRangeSq() {
		return interactionRangeSq;
	}

	public int getSpecie() {
		return species;
	}

	public void setSpecie(int specie) {
		this.species = specie;
	}

	public int getHealth() {
		return health;
	}

	public void takeDamage(int damage) {
		this.health -= damage;
	}

	public boolean isDevoured() {
		return devoured;
	}

	public void setDevoured(boolean devoured) {
		this.devoured = devoured;
	}

	public float getSprintSpeed() {
		return sprintSpeed;
	}

	public void setSprintSpeed(float sprintSpeed) {
		this.sprintSpeed = sprintSpeed;
	}

	public boolean isExcited() {
		return excited;
	}

	public void setExcited(boolean excited) {
		this.excited = excited;
	}

	public float getCruiseSpeed() {
		return cruiseSpeed;
	}

	public void setCruiseSpeed(float cruiseSpeed) {
		this.cruiseSpeed = cruiseSpeed;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

}
