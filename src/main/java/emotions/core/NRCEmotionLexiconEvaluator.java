package affective.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

//This class is used for evaluating the NRC-10 Emotion Lexicon
 
public class NRCEmotionLexiconEvaluator extends LexiconEvaluator  {


	/** For serialization. */
	private static final long serialVersionUID = 5020983098724465636L;

	/** A mapping between words and the affective scores. */
	protected Map<String, Map<String, Integer>> dict; // each word is mapped to


	/**
	 * initializes the Object
	 * 
	 * @param path the file with the lexicon
	 * @param name the prefix for all the attributes calculated from this lexicon
	 */
	public NRCEmotionLexiconEvaluator(String path,String name) {
		super(path,name);
		this.dict = new HashMap<String, Map<String, Integer>>();

		this.featureNames=new ArrayList<String>();
		this.featureNames.add(name+"-anger");
		this.featureNames.add(name+"-anticipation");
		this.featureNames.add(name+"-disgust");
		this.featureNames.add(name+"-fear");
		this.featureNames.add(name+"-joy");
		this.featureNames.add(name+"-sadness");
		this.featureNames.add(name+"-surprise");
		this.featureNames.add(name+"-trust");
		this.featureNames.add(name+"-negative");
		this.featureNames.add(name+"-positive");



	}

	/**
	 * Gets the dictionary mapping the words to their emotion associations
	 * 
	 * @return the dictionary.
	 */	
	public Map<String, Map<String, Integer>> getDict() {
		return this.dict;
	}

	
	/**
	 * Gets the emotions for a word
	 * @param word the emotion key name
	 * @return the emotions
	 */	
	public Map<String, Integer> getWord(String word) {
		if (this.dict.containsKey(word))
			return dict.get(word);
		else
			return null;
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

		String line;
		while ((line = bf.readLine()) != null) {
			String content[] = line.split("\t");
			String word = content[0];
			String emotion = content[1];
			int value = Integer.parseInt(content[2]);

			// I check whether the word has been inserted into the dict
			if (this.dict.containsKey(word)) {
				Map<String, Integer> emotionMap = this.dict.get(content[0]);
				emotionMap.put(emotion, value);
			} else {
				Map<String, Integer> emotionMap = new HashMap<String, Integer>();
				emotionMap.put(emotion, value);
				this.dict.put(word, emotionMap);
			}

		}

		bf.close();

	}

	
	/* (non-Javadoc)
	 * @see affective.core.LexiconEvaluator#evaluateTweet(java.util.List)
	 */
	@Override
	public Map<String, Double> evaluateTweet(List<String> words) {

		Map<String, Double> emoCount = new HashMap<String, Double>();

		double anger = 0.0;
		double anticipation = 0.0;
		double disgust = 0.0;
		double fear = 0.0;
		double joy = 0.0;
		double sadness = 0.0;
		double surprise = 0.0;
		double trust = 0.0;
		double negative = 0.0;
		double positive = 0.0;

		for (String word : words) {
			// I retrieve the EmotionMap if the word match the lexicon
			if (this.getDict().containsKey(word)) {
				Map<String, Integer> emotions = this.getDict().get(word);
				anger += emotions.get("anger");
				anticipation += emotions.get("anticipation");
				disgust += emotions.get("disgust");
				fear += emotions.get("fear");
				joy += emotions.get("joy");
				sadness += emotions.get("sadness");
				surprise += emotions.get("surprise");
				trust += emotions.get("trust");
				negative += emotions.get("negative");
				positive += emotions.get("positive");

			}
		}

		emoCount.put(name+"-anger", anger);
		emoCount.put(name+"-anticipation", anticipation);
		emoCount.put(name+"-disgust", disgust);
		emoCount.put(name+"-fear", fear);
		emoCount.put(name+"-joy", joy);
		emoCount.put(name+"-sadness", sadness);
		emoCount.put(name+"-surprise", surprise);
		emoCount.put(name+"-trust", trust);
		emoCount.put(name+"-negative", negative);
		emoCount.put(name+"-positive", positive);


		return emoCount;
	}



}
