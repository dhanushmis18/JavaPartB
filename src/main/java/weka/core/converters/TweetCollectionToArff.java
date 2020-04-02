package weka.core.converters;

import weka.core.Instances;

//Builds an arff dataset from a collection of tweets in a given file

public abstract class TweetCollectionToArff {
	
	
	/**
	 * Creates the dataset.
	 * @param collectionPath the file wit he the input collection
	 * @return the Instances weka object
	 * @throws Exception if something goes wrong
	 */
	public abstract Instances createDataset(String collectionPath) throws Exception;
}
