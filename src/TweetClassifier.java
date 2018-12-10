import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class TweetClassifier
{
	public double positiveProbability;
	public double negativeProbability;
	private HashMap<String, Integer> positiveCountHolder;
	private HashMap<String, Double> positiveProbabilityHolder;
	private HashMap<String, Integer> negativeCountHolder;
	private HashMap<String, Double> negativeProbabilityHolder;
	
	TweetClassifier()
	{
		positiveCountHolder = new HashMap<String, Integer>();
		positiveProbabilityHolder = new HashMap<String, Double>();
		negativeCountHolder = new HashMap<String, Integer>();
		negativeProbabilityHolder = new HashMap<String, Double>();	
	}
	
	public HashMap<String, Integer> getPositiveCountHolder() {
		return positiveCountHolder;
	}

	public void setPositiveCountHolder(HashMap<String, Integer> positiveCountHolder) {
		this.positiveCountHolder = positiveCountHolder;
	}

	public HashMap<String, Double> getPositiveProbabilityHolder() {
		return positiveProbabilityHolder;
	}

	public void setPositiveProbabilityHolder(HashMap<String, Double> positiveProbabilityHolder) {
		this.positiveProbabilityHolder = positiveProbabilityHolder;
	}

	public HashMap<String, Integer> getNegativeCountHolder() {
		return negativeCountHolder;
	}

	public void setNegativeCountHolder(HashMap<String, Integer> negativeCountHolder) {
		this.negativeCountHolder = negativeCountHolder;
	}

	public HashMap<String, Double> getNegativeProbabilityHolder() {
		return negativeProbabilityHolder;
	}

	public void setNegativeProbabilityHolder(HashMap<String, Double> negativeProbabilityHolder) {
		this.negativeProbabilityHolder = negativeProbabilityHolder;
	}

	public double classify(String tweet)
	{
		String[] arrayToClassify = findTweetArray(tweet);
		double positiveWordProbability = 1.0;
		double negativeWordProbability = 1.0;
		double positiveSentimentProbability;
		for(String word: arrayToClassify)
		{
			positiveWordProbability = positiveWordProbability * positiveProbabilityHolder.get(word);
			negativeWordProbability = negativeWordProbability * negativeProbabilityHolder.get(word);
		}
		
		positiveSentimentProbability = (positiveWordProbability * positiveProbability)/((positiveWordProbability * positiveProbability)
				+ negativeWordProbability * negativeProbability);
		
		return positiveSentimentProbability;
	}
	
	public String[] findTweetArray(String tweet)
	{
		String tweetLowerCase = tweet.toLowerCase();
		String[] tweetWordsArray = tweetLowerCase.split(("[.;,: -?!()\\[\\]\b\t\n\f\r\"\\|]+"));
		return tweetWordsArray;
	}
	public void calculateProbability(File file)
	{
		int positiveTweetCount = 0;
		int negativeTweetCount = 0;
		int positiveWordCount = 0;
		int negativeWordCount = 0;
		
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
			System.out.println("Positive Tweets: " + positiveTweetCount);
			System.out.println("Negative Tweets: " + negativeTweetCount);
			System.out.println("Positive Word Count: " + positiveWordCount);
			System.out.println("Negative Word Count: " + negativeWordCount);
			double test = negativeProbabilityHolder.get("love");
			double testPos = positiveProbabilityHolder.get("love");
			System.out.println("" + test);
			System.out.println("" + testPos);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
