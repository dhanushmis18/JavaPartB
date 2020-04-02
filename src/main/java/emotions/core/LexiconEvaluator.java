package affective.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

//This abstract class provides the structure for all classes calculating attributes from lexicons.
 
public abstract class LexiconEvaluator implements Serializable {

	/** for serialization */
	private static final long serialVersionUID = 1L;
	
	/** The lexicon file */
	protected String path;
	
	/** The name of the lexicon */
	protected String name; 
	
	/** A list with all the features provided by the lexicon evaluator */
	protected List<String> featureNames; 
	
	
	/**
	 * initializes the Object
	 * 
	 * @param path the file with the lexicon
	 * @param name the prefix for all the attributes calculated from this lexicon
	 */	
	public LexiconEvaluator(String path,String name){
		this.path=path;
		this.name=name;
	}
	
	/**
	 * initializes the dictionary
	 * @throws IOException in case of wrong file
	 */	
	public abstract void processDict()  throws IOException;
	

	/**
	 * Calculates lexicon-based feature values from a list of tokens
	 * @param tokens a tokenized tweet
	 * @return a mapping between attribute names and their scores
	 */	
	public abstract Map<String,Double> evaluateTweet(List<String> tokens);

	/**
	 * Gets the feature names
	 * 
	 * @return the feature names.
	 */	
	public List<String> getFeatureNames() {
		return featureNames;
	}

	
	
}
