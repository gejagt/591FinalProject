import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Executable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import twitter4j.TwitterException;
/**
 * TweetClassifierTest tests the methods of TweetClassifier with JUnit tests
 * @author Charles
 *
 */
class TweetClassifierTest 
{
	/**
	 * Tests whether or not a positive statement will produce a higher probability of positive result
	 */
	@Test
	void testClassifyPositive() 
	{
		try
		{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		double tweetPositiveProbability;
		tweetPositiveProbability = classifier.classify("I love the Warriors");
		assertTrue(tweetPositiveProbability > 0.5);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Tests whether or not an assumed negative statement will return a lower probability of being positive result
	 */
	@Test
	void testClassifyNegative() 
	{
		try
		{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		double tweetPositiveProbability;
		tweetPositiveProbability = classifier.classify("Dogs are terrible");
		assertTrue(tweetPositiveProbability < 0.5);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Test whether or not gibberish equally likely to be classified as positive or negative for a single word
	 */
	@Test
	void testClassifyGibberish() 
	{
		try
		{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		double tweetPositiveProbability;
		tweetPositiveProbability = classifier.classify("GoodFrankenChina");
		assertTrue(tweetPositiveProbability == 0.5); 
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Test whether or not all upper-case string with spaces will produce the correct, lower-case string to be analyzed
	 */
	@Test
	void testCapsFindTweetArray() 
	{
		TweetClassifier classifier = new TweetClassifier();
		String[] findTest = classifier.findTweetArray("HELLO THIS IS AN ALL CAPS TEST"); 
		assertTrue(findTest.length == 7);
		String compareString = "hello";
		assertTrue(compareString.equals(findTest[0]));
	}
	/**
	 * Test whether or not array of string of only delimiters is empty as expected
	 */
	@Test
	void testPuncFindTweetArray() 
	{
		TweetClassifier classifier = new TweetClassifier();
		String[] findTest = classifier.findTweetArray("!!?()!!..,,:|[]\""); 
		assertTrue(findTest.length == 0);
	}
	/**
	 * Test whether upper-case characters with multiple, random delimiters interspersed 
	 * still returns correct, lower-case string in correct order
	 */
	@Test
	void testPuncCapsFindTweetArray() 
	{
		TweetClassifier classifier = new TweetClassifier();
		String[] findTest = classifier.findTweetArray("H!!E?L(L)!!O..,,:|[]\"");
		String compareString = "o";
		assertTrue(compareString.equals(findTest[4]));
	}
	/**
	 * Test whether or not a positive word is more likely to be used positively as determined by HashMaps
	 */
	@Test
	void testCalculateProbabilityPositively() 
	{
		try
		{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		assertTrue(classifier.getPositiveProbabilityHolder().get("congratulations") > 
			classifier.getNegativeProbabilityHolder().get("congratulations"));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Test whether or not a negative word is more likely to be used negatively as determined by HashMaps
	 */
	@Test
	void testCalculateProbabilityNegatively() 
	{
		try
		{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		assertTrue(classifier.getPositiveProbabilityHolder().get("dislike") < 
			classifier.getNegativeProbabilityHolder().get("dislike"));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Test whether or not a gibberish word has equal probabilities of being positive or negative as determined by HashMaps
	 */
	@Test
	void testCalculateProbabilityGibberish() 
	{
		try
		{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		assertTrue(classifier.getPositiveProbabilityHolder().get("beanobearcracker") == 
			classifier.getNegativeProbabilityHolder().get("beanobearcracker"));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Test to see if bad input will be refused and a 0 returned
	 */
	@Test
	void testFindPercentagePositivityBadInput()
	{
		try
		{
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);
		assertTrue(classifier.findPercentagePositivity(-5,0) == 0.0);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
