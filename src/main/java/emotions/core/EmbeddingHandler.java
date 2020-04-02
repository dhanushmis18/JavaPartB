package affective.core;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.Serializable;
import java.util.Enumeration;

import weka.core.Option;
import weka.core.OptionHandler;

//This abstract class is used for handling word vector or embeddings.

public abstract class EmbeddingHandler implements Serializable, OptionHandler {


	/** For serialization, **/ 
	private static final long serialVersionUID = -2789278587499972963L;
	

	/** Mapping between words and their vectors. */
	protected Object2ObjectMap<String, AbstractDoubleList> wordMap=new Object2ObjectOpenHashMap<String, AbstractDoubleList>();

	/** Number of dimensions of the embeddings. */ 
	protected int dimensions;



	/* (non-Javadoc)
	 * @see weka.filters.Filter#listOptions()
	 */
	public Enumeration<Option> listOptions() {
		//this.getClass().getSuperclass()
		return Option.listOptionsForClassHierarchy(this.getClass(), this.getClass().getSuperclass()).elements();
	}


	/* (non-Javadoc)
	 * @see weka.filters.Filter#getOptions()
	 */
	public String[] getOptions() {	
		return Option.getOptionsForHierarchy(this, this.getClass().getSuperclass());

	}




	/**
	 * Parses the options for this object.
	 *  
	 * @param options
	 *            the options to use
	 * @throws Exception
	 *             if setting of options fails
	 */
	public void setOptions(String[] options) throws Exception {
		Option.setOptionsForHierarchy(options, this, this.getClass().getSuperclass());
	}



	/**
	 * initializes the dictionary
	 * 
	 * @throws Exception in case of wrong file
	 */
	abstract public void createDict() throws Exception;

	
	/**
	 * Gets the dictionary mapping the words to their vectors
	 * 
	 * @return the dictionary.
	 */
	public Object2ObjectMap<String, AbstractDoubleList> getWordMap() {
		return wordMap;
	}


	/**
	 * Gets the dimensions variable
	 * 
	 * @return the value of the variable.
	 */	
	public int getDimensions() {
		return dimensions;
	}





}
