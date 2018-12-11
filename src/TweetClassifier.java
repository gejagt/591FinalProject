import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import twitter4j.*;
import twitter4j.TwitterException;

/**
 * TweetClassifier applies Naive Bayes to create an implementation of the Bag of Words model, 
 * the model widely known in natural language processing and information retrieval.
 * TweetClassifier objects hold probabilities to calculate the likelihood of a word being used positively or negatively.
 * The method classify() determines the aforementioned probabilities after calculateProbability is trained with a csv of labeled tweets.
 */
public class TweetClassifier
{
	public static double positiveProbability;
	public static double negativeProbability;
	public static int positiveTweetCount = 0;
	public static int negativeTweetCount = 0;
	public static int positiveWordCount = 0;
	public static int negativeWordCount = 0;
	private HashMap<String, Integer> positiveCountHolder;
	private HashMap<String, Double> positiveProbabilityHolder;
	private HashMap<String, Integer> negativeCountHolder;
	private HashMap<String, Double> negativeProbabilityHolder;
	
	/**
	 * Constructor for TweetClassifier
	 */
	TweetClassifier()
	{
		positiveCountHolder = new HashMap<String, Integer>();
		positiveProbabilityHolder = new HashMap<String, Double>();
		negativeCountHolder = new HashMap<String, Integer>();
		negativeProbabilityHolder = new HashMap<String, Double>();	
	}
	/**
	 *  Gets HashMap of positive words and their counts
	 * @return HashMap of positive words and their counts
	 */
	public HashMap<String, Integer> getPositiveCountHolder() 
	{
		return positiveCountHolder;
	}
	/**
	 * Gets HashMap of positive words and their probabilities
	 * @return HashMap of positive words and their probabilities
	 */
	public HashMap<String, Double> getPositiveProbabilityHolder() 
	{
		return positiveProbabilityHolder;
	}
	/**
	 * Gets HashMap of negative words and their counts
	 * @return HashMap of negative words and their counts
	 */
	public HashMap<String, Integer> getNegativeCountHolder() 
	{
		return negativeCountHolder;
	}

	/**
	 * Gets HashMap of negative words and their probabilities
	 * @return HashMap of negative words and their probabilities
	 */
	public HashMap<String, Double> getNegativeProbabilityHolder() {
		return negativeProbabilityHolder;
	}

	/**
	 * classify will take a String and return the probability that the string is used positively, applying Bayes
	 * @param tweet is a String that is passed in to be analyzed, must be a single word
	 * @return probability that the string is used positively, returned as a double
	 */
	public double classify(String tweet)
	{
		String[] arrayToClassify = findTweetArray(tweet);
		double positiveWordProbability = 1.0;
		double negativeWordProbability = 1.0;
		double positiveSentimentProbability;
		for(String word: arrayToClassify)
		{
			if(positiveProbabilityHolder.get(word) == null || negativeProbabilityHolder.get(word) == null)
			{
				positiveWordProbability = positiveWordProbability * 1;
				negativeWordProbability = negativeWordProbability * 1;
			}
			else
			{	
			positiveWordProbability = positiveWordProbability * positiveProbabilityHolder.get(word);
			negativeWordProbability = negativeWordProbability * negativeProbabilityHolder.get(word);
			}
		}

		positiveSentimentProbability = (positiveWordProbability * positiveProbability)/((positiveWordProbability * positiveProbability)
				+ negativeWordProbability * negativeProbability);
		
		return positiveSentimentProbability;
	}
	/**
	 * Parses a tweet String and breaks it down into a String array for analysis
	 * @param tweet is a String from a tweet to be broken down
	 * @return Array of Strings is returned to be put into HashMaps for training
	 */
	public String[] findTweetArray(String tweet)
	{
		String tweetLowerCase = tweet.toLowerCase();
		String[] tweetWordsArray = tweetLowerCase.split(("[.;,: -?!()\\[\\]\b\t\n\f\r\"\\|]+"));
		return tweetWordsArray;
	}
	/**
	 * Given a pre-labeled file of Tweets as a training set, calculateProbability calculates all the relevant probabilities for using Naive Bayes
	 * @param file Tweets training set in csv
	 * @throws FileNotFoundException if File cannot be find, exception is thrown
	 */
	public void calculateProbability(File file) throws FileNotFoundException
	{
//		int positiveTweetCount = 0;
//		int negativeTweetCount = 0;
//		int positiveWordCount = 0;
//		int negativeWordCount = 0;
//		
		try
		{
			Scanner scanner = new Scanner(file);
			
			while(scanner.hasNext())
			{
				String keyStringHolder = scanner.nextLine();
				String[] keyStringArray = keyStringHolder.split("\",\"");
				String tweetExtracted = keyStringArray[5];
				String [] tweetWordsArray = findTweetArray(tweetExtracted);
				if(keyStringArray[0].equals("\"0"))
				{
					negativeTweetCount++;
					for(String index: tweetWordsArray)
					{
						negativeWordCount++;
						if(negativeCountHolder.containsKey(index))
						{
							negativeCountHolder.put(index, negativeCountHolder.get(index) + 1);
						}
						else
						{
							negativeCountHolder.put(index, 1);
						}
					}
					
					
				}
				else
				{
					positiveTweetCount++;
					for(String index: tweetWordsArray)
					{
						positiveWordCount++;
						if(positiveCountHolder.containsKey(index))
						{
							positiveCountHolder.put(index, positiveCountHolder.get(index) + 1);
						}
						else
						{
							positiveCountHolder.put(index, 1);
						}
					}
				}
				
				
			}
			
			positiveProbability = (double)positiveTweetCount / (positiveTweetCount + negativeTweetCount);
			negativeProbability = 1 - positiveProbability;
			for(String key: negativeCountHolder.keySet())
			{
				int negativeValueHolder = negativeCountHolder.get(key);
				double negativeWordProbability = (double)negativeValueHolder / negativeWordCount;
				negativeProbabilityHolder.put(key, negativeWordProbability);
			}
			for(String key: positiveCountHolder.keySet())
			{
				int positiveValueHolder = positiveCountHolder.get(key);
				double positiveWordProbability = (double)positiveValueHolder / positiveWordCount;
				positiveProbabilityHolder.put(key, positiveWordProbability);
			}

		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
	/**
	 * findPercentagePositivity tracks the total percentage of tweets found that are positive
	 * @param positiveTweets number of positive tweets from query
	 * @param negativeTweets number of negative tweets from query
	 * @return percentage of tweets that are positive
	 */
	public double findPercentagePositivity(double positiveTweets, double negativeTweets)
	{
		double percentagePositivity = 100*(positiveTweets / (positiveTweets + negativeTweets));
		if(percentagePositivity > 0 && positiveTweets >= 0 && negativeTweets >= 0)
		{
			return percentagePositivity;
		}
		else
		{
			return 0.0;
		}
	}
}
