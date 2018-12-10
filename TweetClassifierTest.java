import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class TweetClassifierTest 
{

	@Test
	void testClassifyPositive() 
	{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		double tweetPositiveProbability;
		tweetPositiveProbability = classifier.classify("I love the Warriors");
		assertTrue(tweetPositiveProbability > 0.5); 
	}
	
	@Test
	void testClassifyNegative() 
	{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		double tweetPositiveProbability;
		tweetPositiveProbability = classifier.classify("Dogs are terrible");
		assertTrue(tweetPositiveProbability < 0.5); 
	}

	@Test
	void testCapsFindTweetArray() 
	{
		TweetClassifier classifier = new TweetClassifier();
		String[] findTest = classifier.findTweetArray("HELLO THIS IS AN ALL CAPS TEST"); 
		assertTrue(findTest.length == 7);
		String compareString = "hello";
		assertTrue(compareString.equals(findTest[0]));
	}

	@Test
	void testPuncFindTweetArray() 
	{
		TweetClassifier classifier = new TweetClassifier();
		String[] findTest = classifier.findTweetArray("!!?()!!..,,:|[]\""); 
		assertTrue(findTest.length == 0);
	}
	
	@Test
	void testCalculateProbability() 
	{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		assertTrue(classifier.getPositiveProbabilityHolder().get("congratulations") > 
			classifier.getNegativeProbabilityHolder().get("congratulations"));
	}
	
	@Test
	void testfindPercentagePositivity()
	{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		assertTrue(classifier.findPercentagePositivity(-5,0) == 0.0);
	}

}
