package data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import bioSimulation.Agent;

public class SpeciesOrganizer {

	ArrayList<Float> mySpecies = new ArrayList<Float>();
	ArrayList<Float> temp = new ArrayList<Float>();
	List<Color> list = new ArrayList<Color>();
	Map<Color, Integer> map = new HashMap<Color, Integer>();
	private int speciesNum;
	private int speciesPop;
	private Color speciesColor;
	
	public SpeciesOrganizer(Integer speciesNum,Integer speciesPop,Color speciesColor){
		
		this.speciesNum = speciesNum;
		this.speciesPop = speciesPop;
		this.speciesColor = speciesColor;
	}
public SpeciesOrganizer(){
		
	
	}

	public void speciesData(ArrayList<Agent> population) {
		list.clear();
		map.clear();
		for (Agent agent : population) {
			list.add(agent.getColor());				

		}
		Set<Color> uniqueSet = new HashSet<Color>(list);
		for (Color temp : uniqueSet) {
			speciesPop = Collections.frequency(list, temp);
			//System.out.println(speciesPop);
			//speciesNum = agent.getSpecie();
			speciesColor = temp;
			
		}
		
		
		System.out.println("\nExample 3 - Count all with Map");
		
		
		for (Color temp : list) {
			Integer count = map.get(temp);
			map.put(temp, (count == null) ? 1 : count + 1);
		} /*
		printMap(map);

		System.out.println("\nSorted Map");
		Map<Color, Integer> treeMap = new TreeMap<Color, Integer>(map);
		printMap(treeMap);
		*/
		//return map;
		
		
		//return null; 
	}
	
	public Map<Color, Integer> getMap()
	{
		return map;
	}
	
	public void printMap(Map<Color, Integer> map){

		for (Entry<Color, Integer> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
				+ entry.getValue());
		}

	 }

	public int getSpeciesNum() {
		return speciesNum;
	}

	public void setSpeciesNum(int speciesNum) {
		this.speciesNum = speciesNum;
	}

	public int getSpeciesPop() {
		return speciesPop;
	}

	public void setSpeciesPop(int speciesPop) {
		this.speciesPop = speciesPop;
	}

	public Color getSpeciesColor() {
		return speciesColor;
	}

	public void setSpeciesColor(Color speciesColor) {
		this.speciesColor = speciesColor;
	}
	
	
	
	
}
