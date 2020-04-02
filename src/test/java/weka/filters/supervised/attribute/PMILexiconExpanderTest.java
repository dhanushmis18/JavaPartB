package weka.filters.supervised.attribute;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.AbstractFilterTest;
import weka.filters.Filter;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;

public class PMILexiconExpanderTest extends AbstractFilterTest {

    public PMILexiconExpanderTest(String name) {
        super(name);
    }

    /** Creates a default PMILexiconExpander filter */
    public Filter getFilter() {
	Filter f = null;

	// Check to see if the test is run from directory containing build_package.xml
	if ((new File(".." + File.separator + "AffectiveTweets" + File.separator + "build_package.xml")).exists()) {
	    File backup = weka.core.WekaPackageManager.PACKAGES_DIR;
	    weka.core.WekaPackageManager.PACKAGES_DIR = new java.io.File(".."); // So that default lexicon, etc., is found.
	    f = new PMILexiconExpander();
	    weka.core.WekaPackageManager.PACKAGES_DIR = backup;
	} else {
	    f = new PMILexiconExpander(); // Hope that the package is installed.
	}
	return f;
    }

    /**
     * Test for the FilteredClassifier used with this filter.
     *
     * @return the configured FilteredClassifier
     */
    protected FilteredClassifier getFilteredClassifier() {
        FilteredClassifier	result;

        result = new FilteredClassifier();

	weka.filters.MultiFilter mf = new weka.filters.MultiFilter();
	Filter[] filters = new Filter[2];
	filters[0] = getFilter();
	weka.filters.unsupervised.attribute.RemoveType rt = new weka.filters.unsupervised.attribute.RemoveType(); // Need to remove string attributes because they are kept by this filter.
	filters[1] = rt;
	mf.setFilters(filters);
	result.setFilter(mf);
        result.setClassifier(new weka.classifiers.functions.SMO());

        return result;
    }

    /**
     * Data to be used for FilteredClassifier test.
     *
     * @return the configured FilteredClassifier
     */
    protected Instances getFilteredClassifierData() throws Exception {
        Instances result;

	// Check to see if the test is run from directory containing build_package.xml
	if ((new File(".." + File.separator + "AffectiveTweets" + File.separator + "build_package.xml")).exists()) {
	    result = (new weka.core.converters.ConverterUtils.DataSource("data" + File.separator + "sent140test.arff.gz")).getDataSet();
	} else { // Hope that package is installed.
	    result = (new weka.core.converters.ConverterUtils.DataSource(weka.core.WekaPackageManager.PACKAGES_DIR.toString() + File.separator + "data" + File.separator + "sent140test.arff.gz")).getDataSet();
	}

	result.setClassIndex(result.numAttributes() - 1);

        return result;
    }

    /**
     * Called by JUnit before each test method. Sets up the Instances object to use based on 
     * one of the datasets that comes with the package.
     *
     * @throws Exception if an error occurs reading the example instances.
     */
    protected void setUp() throws Exception {
        super.setUp();

	// Check to see if the test is run from directory containing build_package.xml
	if ((new File(".." + File.separator + "AffectiveTweets" + File.separator + "build_package.xml")).exists()) {
	    m_Instances = (new weka.core.converters.ConverterUtils.DataSource("data" + File.separator + "sent140test.arff.gz")).getDataSet();
	} else { // Hope that package is installed.
	    m_Instances = (new weka.core.converters.ConverterUtils.DataSource(weka.core.WekaPackageManager.PACKAGES_DIR.toString() + File.separator + "data" + File.separator + "sent140test.arff.gz")).getDataSet();
	}

	m_Instances.setClassIndex(m_Instances.numAttributes() - 1);
    }
    
    public void testFilteredClassifier() {
    	
    }

    public static Test suite() {
        return new TestSuite(PMILexiconExpanderTest.class);
    }

    public static void main(String[] args){
        junit.textui.TestRunner.run(suite());
    }
}
