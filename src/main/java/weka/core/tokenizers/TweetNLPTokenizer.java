package weka.core.tokenizers;


import java.util.Iterator;
import java.util.List;

import cmu.arktweetnlp.Twokenize;

import weka.core.RevisionUtils;
import weka.core.TechnicalInformation;
import weka.core.TechnicalInformation.Type;

public class TweetNLPTokenizer extends Tokenizer {

	/** For serialization.    **/
	private static final long serialVersionUID = 4352757127093531518L;


	/** the actual tokenizer */
	protected transient Iterator<String> m_tokenIterator;

	

	/**
	 * Returns a string describing this tokenizer.
	 * 
	 * @return a description of the filter suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	@Override
	public String globalInfo() {
		return "A Twitter-specific tokenizer based on the CMU TweetNLP library.\n" + getTechnicalInformation().toString();				
	}

	

	 /**
     * Returns an instance of a TechnicalInformation object, containing
     * detailed information about the technical background of this class,
     * e.g., paper reference or book this class is based on.
     *
     * @return the technical information about this class
     */
    public TechnicalInformation getTechnicalInformation() {
        TechnicalInformation result;

        result = new TechnicalInformation(Type.INPROCEEDINGS);
        result.setValue(TechnicalInformation.Field.AUTHOR, "Gimpel, Kevin and Schneider, Nathan and O'Connor, Brendan and Das, Dipanjan and Mills, Daniel and Eisenstein, Jacob and Heilman, Michael and Yogatama, Dani and Flanigan, Jeffrey and Smith, Noah A");
        result.setValue(TechnicalInformation.Field.TITLE, "Part-of-speech tagging for twitter: Annotation, features, and experiments");
        result.setValue(TechnicalInformation.Field.YEAR, "2011");
        result.setValue(TechnicalInformation.Field.URL, "http://www.cs.cmu.edu/~ark/TweetNLP/");
        result.setValue(TechnicalInformation.Field.NOTE, "The Weka tokenizer works with version 0.32 of TweetNLP.");

        return result;
}
	
	

	/**
	 * Tests if this enumeration contains more elements.
	 * 
	 * @return true if and only if this enumeration object contains at least one
	 *         more element to provide; false otherwise.
	 */
	public boolean hasMoreElements() {
		return this.m_tokenIterator.hasNext();	
	}

	/**
	 * Returns the next element of this enumeration if this enumeration object has
	 * at least one more element to provide.
	 * 
	 * @return the next element of this enumeration.
	 */
	@Override
	public String nextElement() {
		return this.m_tokenIterator.next();	
	}

	/**
	 * Sets the string to tokenize. Tokenization happens immediately.
	 * 
	 * @param s the string to tokenize
	 */
	@Override
	public void tokenize(String s) {

		List<String> words=Twokenize.tokenizeRawTweetText(s);
		this.m_tokenIterator=words.iterator();	


	}

	

	
	  /**
	   * Returns the revision string.
	   * 
	   * @return the revision
	   */
	  public String getRevision() {
	    return RevisionUtils.extract("$Revision: 1 $");
	  }
	
	
	/**
	 * Runs the tokenizer with the given options and strings to tokenize. The
	 * tokens are printed to stdout.
	 * 
	 * @param args the commandline options and strings to tokenize
	 */
	public static void main(String[] args) {
		runTokenizer(new TweetNLPTokenizer(), args);
	}

}
