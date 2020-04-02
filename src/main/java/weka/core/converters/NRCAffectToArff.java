package weka.core.converters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

//Builds an arff dataset from the NRC Affective Lexicon analysis. 
 
public class NRCAffectToArff {
	
	/**
	 * Creates a Weka Instances object from the lexicon.
	 * @param collectionPath the file path of the lexicon.
	 * @return an Instances object
	 * @throws Exception if a wrong file is used.
	 */
	public Instances createDataset(String collectionPath) throws Exception {

		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		// The content of the tweet
		attributes.add(new Attribute("term", (ArrayList<String>) null));
		attributes.add(new Attribute("angerScore"));
		attributes.add(new Attribute("fearScore"));
		attributes.add(new Attribute("sadnessScore"));
		attributes.add(new Attribute("joyScore"));
		
		
		Instances dataset = new Instances(
				"The NRC Affect Intensity Lexicon v0.5. More info at:www.saifmohammad.com/WebPages/AffectIntensity.htm", attributes, 0); 

		Map<String,Map<String,Double>> mapper=new HashMap<String,Map<String,Double>>();
		
		BufferedReader bf = new BufferedReader(new FileReader(collectionPath));
		String line;		
		while ((line = bf.readLine()) != null) {
			String parts[] = line.split("\t");
			String term=parts[0];
			Double score= Double.parseDouble(parts[1]);
			String affectDim=parts[2];
	//		System.out.println(term+" "+score+" "+affectDim);
			
			if(!mapper.containsKey(term)){
				Map<String,Double> scoreVals=new HashMap<String,Double>();
				scoreVals.put(affectDim, score);
				mapper.put(term,scoreVals);
			}
			else{
				Map<String,Double> scoreVals=mapper.get(term);
				scoreVals.put(affectDim, score);
			}

		}
	
		
		String[] sortedWords=mapper.keySet().toArray(new String[0]);
		Arrays.sort(sortedWords);
		
		for(String word:sortedWords){
			Map<String,Double> scoreVals=mapper.get(word);
			double angerScore= scoreVals.containsKey("anger")?scoreVals.get("anger"):weka.core.Utils.missingValue();
			double fearScore= scoreVals.containsKey("fear")?scoreVals.get("fear"):weka.core.Utils.missingValue();
			double sadnessScore= scoreVals.containsKey("sadness")?scoreVals.get("sadness"):weka.core.Utils.missingValue();
			double joyScore= scoreVals.containsKey("joy")?scoreVals.get("joy"):weka.core.Utils.missingValue();
		    
			double values[] = new double[5];
			values[0]=dataset.attribute(0).addStringValue(word);
			values[1]=angerScore;
			values[2]=fearScore;
			values[3]=sadnessScore;
			values[4]=joyScore;
		
			Instance inst = new DenseInstance(1, values);
			dataset.add(inst);
			
		}
		
		

		bf.close();

		return dataset;
	}
	
	
	/**
	 * Main method for testing this class.
	 * 
	 * should contain the path of input dataset and the name of
	 *            target file scheme (see Evaluation)
	 * @param args arguments           
	 */
	static public void main(String args[]) {

		if (args.length == 2) {

			NRCAffectToArff na = new NRCAffectToArff();

			try {
				Instances dataset = na.createDataset(args[0]);
				ArffSaver saver = new ArffSaver();
				saver.setInstances(dataset);

				saver.setFile(new File(args[1]));
				saver.writeBatch();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	

}
