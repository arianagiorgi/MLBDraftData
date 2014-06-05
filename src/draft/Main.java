package draft;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {


	public static void main(String[] args) throws FileNotFoundException{

		//locate csv file in bin
		String csvFile = "bin/drafthistory.csv";
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";

		try{
			br = new BufferedReader(new FileReader(csvFile));
			FileWriter writer = new FileWriter("output");
			
			Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
			List<List<String>> valueSet = new ArrayList<List<String>>();
			List<List<String>> playerSet = new ArrayList<List<String>>();
			
			while ((line = br.readLine()) != null){

				String[] draft = line.split(splitBy);

				//store into a map to compare variables within a row
				List<String> tempSet = new ArrayList<String>();
				for(int i=0; i<draft.length; i++){
					tempSet.add(draft[i]);
				}

				valueSet.add(tempSet);
			}
			


			for(int i=1; i<valueSet.size(); i++){
				int overall = Integer.parseInt(valueSet.get(i).get(2));
				String draft = valueSet.get(i).get(1);
				if(overall<=100 && draft.equals("June Amateur Draft")){
					playerSet.add(valueSet.get(i));
				}
			}
			
			//years
			for(int j=1965; j<2014; j++){
				int count = 0;
				
				for(int i=0; i<playerSet.size(); i++){
					//assign year and school
					int year = Integer.parseInt(playerSet.get(i).get(0));
					String school;
					if(playerSet.get(i).size() >= 13){	
						school = playerSet.get(i).get(12);
					}else if(playerSet.get(i).size() == 12){
						school = playerSet.get(i).get(11);
					}else{
						school = null;
					}
					
					//if correct year and in high school
					if(year == j && school.contains("HS")){
						count = count + 1;
					}
				}
				map.put(j, count);
			}
			
			writer.append("Year, # HS Players"+"\n");
			for(Map.Entry<Integer, Integer> entry : map.entrySet()){
				Integer key =entry.getKey();
				Integer value = entry.getValue();
				System.out.println(key+","+value);
				
				String keystring = Integer.toString(key);
				String valuestring = Integer.toString(value);
				writer.append(keystring);
				writer.append(",");
				writer.append(valuestring);
				writer.append("\n");
			}
			writer.flush();
			writer.close();
			
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException ex){
			ex.printStackTrace();
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
