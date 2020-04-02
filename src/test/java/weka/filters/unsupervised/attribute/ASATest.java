package weka.filters.unsupervised.attribute;

import weka.classifiers.meta.FilteredClassifier;
import weka.filters.AbstractFilterTest;
import weka.filters.Filter;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;

public class ASATest extends AbstractFilterTest {

    public ASATest(String name) {
        super(name);
    }

    /** Creates a default ASA filter */
    public Filter getFilter() {
	Filter f = null;

	// Check to see if the test is run from directory containing build_package.xml
	if ((new File(".." + File.separator + "AffectiveTweets" + File.separator + "build_package.xml")).exists()) {
	    File backup = weka.core.WekaPackageManager.PACKAGES_DIR;
	    weka.core.WekaPackageManager.PACKAGES_DIR = new java.io.File(".."); // So that default lexicon, etc., is found.
	    f = new ASA();
	    weka.core.WekaPackageManager.PACKAGES_DIR = backup;
	} else {
	    f = new ASA(); // Hope that the package is installed.
	}
	return f;
    }

    /**
     * ASA is not suitable for use in a FilteredClassifier, so this just creates a dummy
     * FilteredClassifier so that the tests run through.
     *
     * @return the configured FilteredClassifier
     */
    protected FilteredClassifier getFilteredClassifier() {
        FilteredClassifier	result;

        result = new FilteredClassifier();

        result.setFilter(new weka.filters.AllFilter());
        result.setClassifier(new weka.classifiers.rules.ZeroR());

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
    
    
    /* (non-Javadoc)
     * @see weka.filters.AbstractFilterTest#testBuffered()
     */
    public void testBuffered(){}

    public static Test suite() {
        return new TestSuite(ASATest.class);
    }

    public static void main(String[] args){
        junit.textui.TestRunner.run(suite());
    }
}
