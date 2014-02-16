package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import bioSimulation.Agent;

public class Genealogy {
	private ArrayList<String> genData;
	private ArrayList<String> agentDetailsList;
	private String mutantRecord;
	private String agentInfo;

	public Genealogy() {
		genData = new ArrayList<String>();
		agentDetailsList = new ArrayList<String>();
		
		mutantRecord ="agentID,Source,Target \n";
		genData.add(mutantRecord);
		
		agentInfo = "agentID,DOB,DNA \n";
		agentDetailsList.add(agentInfo);
	}

	public void newBorn(Agent agent,Agent parent) {
		if (agent.isMutant()) {
			mutantRecord = "";
			mutantRecord += agent.getAgentID() + ",";
			mutantRecord += agent.getAgentParent() + ",";
			mutantRecord += agent.getColor().getRed() + ".";
			mutantRecord += agent.getColor().getGreen() + ".";
			mutantRecord += agent.getColor().getBlue() + "-"; // + ",";
			mutantRecord += parent.getAgentID()+ "\n";
			// record += agent.getDNA() + "\n";

			genData.add(mutantRecord);
		}
		
		agentInfo = "";
		agentInfo += agent.getAgentID() + ",";
		agentInfo += agent.getDOB() + ",";
		agentInfo += agent.getDNA() + "\n" ;
		agentDetailsList.add(agentInfo);

	}

	public void saveGenealogy(String fileName) throws IOException {
		try{
		BufferedWriter br = new BufferedWriter(new FileWriter(fileName +".csv"));
		StringBuilder sb = new StringBuilder();
		for (String data : genData) {
		 br.append(data);
		 
		}

		br.flush();
		br.close();
		}  catch(IOException e)
		{
		     e.printStackTrace();
		} 
		
		try{
			BufferedWriter br2 = new BufferedWriter(new FileWriter(fileName +"DATA.csv"));
			StringBuilder sb2 = new StringBuilder();
			for (String info : agentDetailsList) {
			 br2.append(info);
			 
			}

			br2.flush();
			br2.close();
			}  catch(IOException e)
			{
			     e.printStackTrace();
			} 
	}

}
