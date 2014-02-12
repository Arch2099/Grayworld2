/*************************************************************************
This class produce all the living elements(agents) of the simulation.
All the characteristics of the agents are determinate by their DNA which
contain the information in the following format :










 **************************************************************************** */
package bioSimulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2d;

import passiveProp.Movement;
import passiveProp.Passive;
import passiveProp.Senses;
import nonBiological.AbioAgent;
import behaviours.Attack;
import behaviours.Behaviour;
import behaviours.Diet;
import behaviours.Fear;
import behaviours.Reproduction;
import behaviours.Share;
import behaviours.Swarm;

public class LifeFactory {

	public static int KINGDOM_GENE = 0;
	public static int MOVEMENT_GENE = 1;
	public static int DIET_GENE = 2;
	public static int SENSES_GENE = 3;
	public static int REPROD_GENE = 4;
	public static int FEAR_GENE = 5;
	public static int ATTACK_GENE = 6;
	public static int SWARM_GENE = 0; // chromosome 2
	public static int SHARE_GENE = 1; // chromosome 2

	public static int CHROMOSOME1 = 0;
	public static int CHROMOSOME2 = 1;
	public static int CHROMOSOME3 = 2;

	private final int PROTISTA = 0;
	private final int PLANTAE = 1;
	private final int ANIMALIA = 2;

	private ArrayList<String> geneList = new ArrayList<String>(); // token which
																	// hold the
																	// information
																	// to create
																	// a
																	// behaviour
	private ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>(); // list
																			// of
																			// active
																			// behaviours
	private ArrayList<Passive> passives = new ArrayList<Passive>(); // llist of
																	// passive
																	// properties
	private int upkeep =100; // agent's passive energy used at rest. it increases
						// with complexity/number of features
	private String chromoDelimiter = "/"; // separates chromosomes inside the
											// DNA
	private String geneDelimiter = "-"; // separates genes inside the chromosome
	private String intraGeneDelimiter = ","; // separates parameters inside a
												// gene(exons)

	private int redColor;
	private int greenColor;
	private int blueColor;
	private Color agentColor;
	private int IDcounter = 0;
	private boolean mutant;

	private double mutationProbability = 0.04;
	private int cellStructure; // determinate kingdom

	public LifeFactory() {
	};

	public Agent createAgent(Vector2d position, String chromosome1,
			String chromosome2, String chromosome3) {
		Agent agent = null;

		String[] geneList1 = chromosome1.split(geneDelimiter);
		String[] geneList2 = chromosome2.split(geneDelimiter);
		String[] geneList3 = chromosome3.split(geneDelimiter);

		return agent;
	}

	public Agent createAgent(Vector2d position, String DNA,String parent) {
		
		behaviours = new ArrayList<Behaviour>();
		redColor = 0;
		greenColor = 0;
		blueColor = 0;
		String[] chromosomes = DNA.split(chromoDelimiter); // separate the list
															// of chromosome
		String[] geneList1 = chromosomes[CHROMOSOME1].split(geneDelimiter); // separate
																			// the
																			// list
																			// of
																			// genes
		// System.out.println(DNA);
		String[] geneList2 = chromosomes[CHROMOSOME2].split(geneDelimiter);
		String[] geneList3 = chromosomes[CHROMOSOME3].split(geneDelimiter);

		cellStructure = Integer.parseInt(geneList1[KINGDOM_GENE]);
		determineKingdom();

		String[] exon = geneList1[MOVEMENT_GENE].split(intraGeneDelimiter); // part
																			// of
																			// the
																			// gene
																			// which
																			// contain
																			// the
																			// information

		if (Integer.parseInt(exon[0]) > 50) {

			Passive movement = new Movement(Integer.parseInt(exon[1]),
					Integer.parseInt(exon[0]));
			// redColor +=Integer.parseInt(exon[1])/10;
			passives.add(movement);
		}

		String[] exon1 = geneList1[DIET_GENE].split(intraGeneDelimiter);

		if (Integer.parseInt(exon1[0]) > 64) {
			int myDiet = Integer.parseInt(exon1[1]);
			Behaviour diet = new Diet(myDiet);
			behaviours.add(diet);

			if (myDiet < 86) {
				blueColor += 64;
			} else if (myDiet > 172) {
				redColor += 64;
			} else {
				greenColor += 64;
			}
			
			upkeep += 5;
		}

		String[] exon2 = geneList1[SENSES_GENE].split(intraGeneDelimiter);

		if (Integer.parseInt(exon2[0]) > 50) {

			Passive senses = new Senses(Integer.parseInt(exon2[1]));
			passives.add(senses);
		}

		String[] exon3 = geneList1[REPROD_GENE].split(intraGeneDelimiter);

		if (Integer.parseInt(exon3[0]) > 32) {
			Behaviour reproduction = new Reproduction(
					Integer.parseInt(exon3[1]));
			behaviours.add(reproduction);
		}

		String[] exon4 = geneList1[FEAR_GENE].split(intraGeneDelimiter);

		if (Integer.parseInt(exon4[0]) > 50) {
			
			// redColor -= 25;
			blueColor += 32;
			ArrayList<Integer> fearSwitchList = new ArrayList<Integer>();
			String[] switchGeneList = geneList3[FEAR_GENE]
					.split(intraGeneDelimiter);
			for (String geneSwitch : switchGeneList) {
				fearSwitchList.add(Integer.parseInt(geneSwitch));
			}

			Behaviour fear = new Fear(fearSwitchList,
					Integer.parseInt(exon3[1]), Integer.parseInt(exon3[2]));
			behaviours.add(fear);
		}

		String[] exon5 = geneList1[ATTACK_GENE].split(intraGeneDelimiter);

		if (Integer.parseInt(exon5[0]) > 50) {
			redColor +=16;

			String[] attackSwitchList = geneList3[ATTACK_GENE]
					.split(intraGeneDelimiter);

			Behaviour attack = new Attack(Integer.parseInt(exon5[0]),
					Integer.parseInt(exon4[1]), Integer.parseInt(exon3[2]),
					Integer.parseInt(attackSwitchList[0]),
					Integer.parseInt(attackSwitchList[1]),
					Integer.parseInt(attackSwitchList[2]));
			behaviours.add(attack);
			
			redColor += Integer.parseInt(attackSwitchList[2])/16;
			upkeep += 10;
		}

		String[] exon6 = geneList2[SWARM_GENE].split(intraGeneDelimiter);

		if (Integer.parseInt(exon6[0]) > 140) {
			
			greenColor += 16;
			String[] swarmList = geneList3[SWARM_GENE]
					.split(intraGeneDelimiter);

			Behaviour swarm = new Swarm(Integer.parseInt(swarmList[0]),
					Integer.parseInt(swarmList[1]),
					Integer.parseInt(swarmList[2]));
			behaviours.add(swarm);
			
			upkeep -= 10;
		}

		String[] exon7 = geneList2[SHARE_GENE].split(intraGeneDelimiter);

		if (Integer.parseInt(exon7[0]) > 140) {
			greenColor += 16;
			String[] swarmList = geneList3[SWARM_GENE]
					.split(intraGeneDelimiter);

			Behaviour share = new Share(Integer.parseInt(swarmList[0]),
					Integer.parseInt(swarmList[1]),
					Integer.parseInt(swarmList[2]));
			behaviours.add(share);
		}

		// System.out.println(redColor + " " + greenColor+ " " + blueColor);
		agentColor = new Color(redColor, greenColor, blueColor);
		
		

		IDcounter++;
		
		Agent agent = new Agent(position, cellStructure, 100, 100, 2,
				agentColor, passives, behaviours, upkeep, DNA,parent,IDcounter,mutant);
		
		if(mutant == true)
		{
			System.out.println(" new mutant");
		}
		// dummy genes

		return agent;
	}

	private int determineKingdom() {
		if (cellStructure < 43) {
			cellStructure = PROTISTA;
			blueColor = 128;// is a fungae, bacteria or protozoa
		} else if (cellStructure >= 43 && cellStructure < 128) {
			cellStructure = PLANTAE; // is a plant
			greenColor = 128;
		} else {
			cellStructure = ANIMALIA; // is an animal
			redColor = 128;
		}

		return cellStructure;
	}

	public Agent createTestAgent() // test
	{
		Random rnd = new Random();
		Agent agent = new Agent(
				new Vector2d(rnd.nextInt(255), rnd.nextInt(255)), 5);
		return agent;
	}

	public AbioAgent createAtom() // test
	{
		Random rnd = new Random();
		AbioAgent agent = new AbioAgent(new Vector2d(rnd.nextInt(455),
				rnd.nextInt(455)));
		return agent;
	}

	public AbioAgent createElectron() // test
	{
		Random rnd = new Random();
		AbioAgent agent = new AbioAgent(new Vector2d(rnd.nextInt(455),
				rnd.nextInt(455)), 1);
		return agent;
	}

	public String HaploidCrossOver(String DNA1, String DNA2) {
		mutant = false;
		String childDNA = "";

		// deconstruct first parent DNA
		String[] chromosomes1 = DNA1.split(chromoDelimiter);
		String[] geneList1a = chromosomes1[0].split(geneDelimiter);
		String[] geneList2a = chromosomes1[1].split(geneDelimiter);
		String[] geneList3a = chromosomes1[2].split(geneDelimiter);

		// deconstruct second parent DNA
		String[] chromosomes2 = DNA2.split(chromoDelimiter);
		String[] geneList1b = chromosomes2[0].split(geneDelimiter);
		String[] geneList2b = chromosomes2[1].split(geneDelimiter);
		String[] geneList3b = chromosomes2[2].split(geneDelimiter);

		// strings for the recombined gene
		String[] geneList1c = new String[geneList1a.length];
		String[] geneList2c = new String[geneList2a.length];
		String[] geneList3c = new String[geneList3a.length];

		// each gene is taken from one of the 2 parents with 50% probability
		// of being inherited from 1 of the first 2 chromosome
		for (int i = 0; i < geneList1c.length; i++) {
			Random rnd = new Random();
			if (rnd.nextDouble() > 0.5) {
				geneList1c[i] = mutate(geneList1a[i]) + geneDelimiter;
			} else {
				geneList1c[i] = mutate(geneList1b[i]) + geneDelimiter;
			}
			childDNA = childDNA + geneList1c[i];
		}
		childDNA = childDNA.substring(0, childDNA.length() - 1); // eliminate
																	// the extra
																	// "-"
																	// delimiter
		childDNA += chromoDelimiter; // add end of chromosome sign

		// recombination of the second 2 chromosome
		for (int i = 0; i < geneList2c.length; i++) {
			Random rnd = new Random();
			if (rnd.nextDouble() > 0.5) {
				geneList2c[i] = mutate(geneList2a[i]) + geneDelimiter;
			} else {
				geneList2c[i] = mutate(geneList2b[i]) + geneDelimiter;
			}
			childDNA = childDNA + geneList2c[i];
		}
		childDNA = childDNA.substring(0, childDNA.length() - 1); // eliminate
																	// the extra
																	// "-"
																	// delimiter
		childDNA += chromoDelimiter; // add end of chromosome sign

		// recobination of the last(3rd) chromosome

		for (int i = 0; i < geneList3c.length; i++) {
			Random rnd = new Random();
			if (rnd.nextDouble() > 0.5) {
				geneList3c[i] = mutate(geneList3a[i]) + geneDelimiter;
			} else {
				geneList3c[i] = mutate(geneList3b[i]) + geneDelimiter;
			}
			childDNA = childDNA + geneList3c[i];
		}
		childDNA = childDNA.substring(0, childDNA.length() - 1); // eliminate
																	// the extra
																	// "-"
																	// delimiter

		return childDNA; // end of crossover
	}

	public String mutate(String geneToMutate) {
		
		Random rnd = new Random();
		String geneM = "";
		String[] exonToMutate = geneToMutate.split(intraGeneDelimiter); // separate
																		// the
																		// strings
																		// in
																		// the
																		// genes(removing
																		// commas)
		for (String exonM : exonToMutate) {
			if (rnd.nextDouble() < mutationProbability) {
				int exonMVal = Integer.parseInt(exonM); // trasform the string
														// in to an int
				String exonBinary = Integer.toBinaryString(exonMVal); // trasform
																		// the
																		// value
																		// to
																		// binary
				String tempExon = "";
				for (int i = 0; i < (8 - exonBinary.length()); i++) // **********************
				{ //
					tempExon += "0"; // fill the missing 0
				} // *******************
				tempExon += exonBinary;
				int mutatingBit = rnd.nextInt(8);
				if (tempExon.charAt(mutatingBit) == '1') // pick one of the 8
															// bit and switch
															// value.
				{
					exonM = tempExon.substring(0, mutatingBit) + '0'
							+ tempExon.substring(mutatingBit + 1);
				} else {
					exonM = tempExon.substring(0, mutatingBit) + '1'
							+ tempExon.substring(mutatingBit + 1);
				}

				exonMVal = Integer.parseInt(exonM, 2); // transform the binary
														// value in int
				geneM += exonMVal + intraGeneDelimiter; // rebuild the gene
				mutant= true;
				// System.out.println("mutation!");
			} else {
				geneM += exonM + intraGeneDelimiter;
			}

		}
		geneM = geneM.substring(0, geneM.length() - 1); // remove the last
														// intragenedelimiter

		return geneM;
	}

}
