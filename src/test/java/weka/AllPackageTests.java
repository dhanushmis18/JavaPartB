package weka;

import junit.framework.Test;
import junit.framework.TestSuite;

import weka.filters.unsupervised.attribute.ASATest;
import weka.filters.unsupervised.attribute.PTCMTest;
import weka.filters.unsupervised.attribute.LexiconDistantSupervisionTest;
import weka.filters.unsupervised.attribute.TweetNLPPOSTaggerTest;
import weka.filters.supervised.attribute.PMILexiconExpanderTest;
import weka.filters.unsupervised.attribute.TweetCentroidTest;
import weka.filters.unsupervised.attribute.LabelWordVectorsTest;

import weka.filters.unsupervised.attribute.TweetToSentiStrengthFeatureVectorTest;
import weka.filters.unsupervised.attribute.TweetToSparseFeatureVectorTest;
import weka.filters.unsupervised.attribute.TweetToLexiconFeatureVectorTest;
import weka.filters.unsupervised.attribute.TweetToInputLexiconFeatureVectorTest;
import weka.filters.unsupervised.attribute.TweetToEmbeddingsFeatureVectorTest;
import weka.filters.unsupervised.attribute.TweetToWordListCountFeatureVectorTest;

public class AllPackageTests extends TestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();

    suite.addTestSuite(ASATest.class);
    suite.addTestSuite(PTCMTest.class);
    suite.addTestSuite(LexiconDistantSupervisionTest.class);

    suite.addTestSuite(PMILexiconExpanderTest.class);
    suite.addTestSuite(TweetCentroidTest.class);
    suite.addTestSuite(LabelWordVectorsTest.class);

    suite.addTestSuite(TweetToSparseFeatureVectorTest.class);
    suite.addTestSuite(TweetToLexiconFeatureVectorTest.class);
    suite.addTestSuite(TweetToInputLexiconFeatureVectorTest.class);
    suite.addTestSuite(TweetToSentiStrengthFeatureVectorTest.class);
    suite.addTestSuite(TweetToEmbeddingsFeatureVectorTest.class);
    suite.addTestSuite(TweetNLPPOSTaggerTest.class);
    suite.addTestSuite(TweetToWordListCountFeatureVectorTest.class);    


    return suite;
  }

  public static void main(String[] args) {

    junit.textui.TestRunner.run(suite());
  }
}
