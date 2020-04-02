package affective.core;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.wlv.sentistrength.SentiStrength;

//This class is used for evaluating a tweet with SentiStrength.
 
public class SentiStrengthEvaluator extends LexiconEvaluator  {


	/** For serialization. */
	private static final long serialVersionUID = -2094228012480778199L;
	
	/** The SentiStrengh object. */
	protected transient SentiStrength sentiStrength;
	

	/**
	 * initializes the Object
	 * 
	 * @param file the file with the lexicon
	 * @param name the prefix for all the attributes calculated from this lexicon
	 */	
	public SentiStrengthEvaluator(String file,String name) {
		super(file,name);
		
	
				
		this.featureNames=new ArrayList<String>();
		this.featureNames.add(name+"-posScore");
		this.featureNames.add(name+"-negScore");

	}


	/* (non-Javadoc)
	 * @see affective.core.LexiconEvaluator#processDict()
	 */
	@Override
	public void processDict() throws IOException  {
		this.sentiStrength = new SentiStrength();
		String sentiParams[] = {"sentidata", this.path, "trinary"};
		this.sentiStrength.initialise(sentiParams);		
	}



	/* (non-Javadoc)
	 * @see affective.core.LexiconEvaluator#evaluateTweet(java.util.List)
	 */
	@Override
	public Map<String, Double> evaluateTweet(List<String> tokens) {
	
		Map<String, Double> strengthScores = new HashMap<String, Double>();
		
		String sentence = "";
		for (int i = 0; i < tokens.size(); i++) {
			sentence += tokens.get(i);
			if (i < tokens.size() - 1) {
				sentence += "+";
			}
		}
		
		String result = sentiStrength.computeSentimentScores(sentence);
		
		String[] values = result.split(" ");
		strengthScores.put(name+"-posScore", Double.parseDouble(values[0]));
		strengthScores.put(name+"-negScore", Double.parseDouble(values[1]));

		return strengthScores;
	}
	
}
