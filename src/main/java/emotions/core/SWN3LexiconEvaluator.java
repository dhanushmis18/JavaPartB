package affective.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
 
//This class is used for evaluating SentiWordnet.
 
public class SWN3LexiconEvaluator extends LexiconEvaluator{

	/** For serialization. */
	private static final long serialVersionUID = 1576067300486821206L;

	/** The dictionary. */
	protected Map<String, Double> dict;

	/**
	 * initializes the Object
	 * 
	 * @param path the file with the lexicon
	 * @param name the prefix for all the attributes calculated from this lexicon
	 */
	public SWN3LexiconEvaluator(String path, String name) {
		super(path,name);

		this.dict = new HashMap<String, Double>();	

		this.featureNames=new ArrayList<String>();
		this.featureNames.add(name+"-posScore");
		this.featureNames.add(name+"-negScore");
	}


	/* (non-Javadoc)
	 * @see affective.core.LexiconEvaluator#processDict()
	 */
	@Override
	public void processDict() throws IOException {


		FileInputStream fin = new FileInputStream(this.path);
		GZIPInputStream gzis = new GZIPInputStream(fin);
		InputStreamReader xover = new InputStreamReader(gzis);
		BufferedReader bf = new BufferedReader(xover);


		String line = "";

		// discard comments
		while ((line = bf.readLine()) != null) {
			if (line.startsWith("#") || line.startsWith("				#")) {
				continue;
			}

			String[] data = line.split("\t");

			// Difference between positive and negative score for one particular Synset
			Double polScore = Double.parseDouble(data[2])
					- Double.parseDouble(data[3]);

			// extract all the synset terms
			String[] sysSetTerms = data[4].split(" ");
			for (String w : sysSetTerms) {
				String[] w_n = w.split("#");

				String word=w_n[0];
				// the word's rank, small values indicate a more popular meaning
				// More popular word receive a higher weight
				int rank = Integer.parseInt(w_n[1]);

				if (this.dict.containsKey(word)) {
					Double prevScore=this.dict.get(word);
					this.dict.put(word, prevScore + polScore/(1+rank));
				} else {
					this.dict.put(word, polScore/(1+rank));
				}
			}
		}

		bf.close();
		xover.close();
		gzis.close();
		fin.close();
	}


	/* (non-Javadoc)
	 * @see affective.core.LexiconEvaluator#evaluateTweet(java.util.List)
	 */
	@Override
	public Map<String, Double> evaluateTweet(List<String> tokens) {
		Map<String, Double> strengthScores = new HashMap<String, Double>();
		double posScore = 0;
		double negScore = 0;
		for (String w : tokens) {

			if (this.dict.containsKey(w)) {
				double value = this.dict.get(w);
				if (value > 0) {
					posScore += value;
				} else {
					negScore += value;
				}
			}

		}
		strengthScores.put(name+"-posScore", posScore);
		strengthScores.put(name+"-negScore", negScore);

		return strengthScores;
	}

}
