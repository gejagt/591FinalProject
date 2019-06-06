import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
 

public class TwitterSentimentStatistician 
{
	//authentication details (my personal ones kept here for convenience)
//	public static final String CONSUMER_KEY = "Nw80TNOxfinETYstIYkk85Zvc";
//	public static final String CONSUMER_SECRET = "iAKzkPsnlbJX6ttOXjuqfJ233DKGihpOcUE4psSELxTPdCxvbx";	
//	public static final String ACCESS_TOKEN = "1070985899045986304-drLPwsrax742dfixYA2npCA0xnzqVV";
//	public static final String ACCESS_TOKEN_SECRET = "wgnAVC6ymZ99jDLQvNlURI6Aag0Yo9gtkUJUVPpSt7V4E";
	
	public static void main(String[] args) throws IOException, TwitterException
	{	
	 
	    try
	    {
	    	//Get login details from user
	    	Scanner scanner = new Scanner(System.in);
	    	System.out.println("Your consumer key?");
	    	String consumerKey = scanner.nextLine();
	    	System.out.println("Your consumer secret key?");
	    	String consumerSecretKey = scanner.nextLine();
	    	System.out.println("Your access token?");
	    	String accessToken = scanner.nextLine();
	    	System.out.println("Your secret access token?");
	    	String secretAccessToken = scanner.nextLine();
	    	//authenticate
			ConfigurationBuilder cb = new ConfigurationBuilder();
		    cb.setDebugEnabled(true)
		      .setOAuthConsumerKey(consumerKey)
		      .setOAuthConsumerSecret(consumerSecretKey)
		      .setOAuthAccessToken(accessToken)
		      .setOAuthAccessTokenSecret(secretAccessToken);
		    TwitterFactory tf = new TwitterFactory(cb.build());
		    Twitter twitter = tf.getInstance();
		    
		    //read in training file and report details of file
		    System.out.println("Please enter the path of the File you will be training with");
		    String fileString = scanner.nextLine();
	    	File trainingSet = new File(fileString);
	    	TweetClassifier classifier = new TweetClassifier();
	    	classifier.calculateProbability(trainingSet);
			System.out.println("Trained Positive Tweets: " + TweetClassifier.positiveTweetCount);
			System.out.println("Trained Negative Tweets: " + TweetClassifier.negativeTweetCount);
			System.out.println("Trained Positive Word Count: " + TweetClassifier.positiveWordCount);
			System.out.println("Trained Negative Word Count: " + TweetClassifier.negativeWordCount);
	    	System.out.println("");
			//analyze and report details of user's homepage
			List<Status> yourHomeStatuses = twitter.getHomeTimeline();
	    	double homepagePositive = 0.0;
	    	double homepageNegative = 0.0;
	    	for (Status status : yourHomeStatuses) 
	    	{
	    		double howHappy = classifier.classify(status.getText());
	    		System.out.println(status.getUser().getName() 
					+ ":" +status.getText());
	    		System.out.println("This tweet from your homepage has a " + (100 * howHappy) + "% chance of being positive");
	    		System.out.println("It has a " + (100 - (100 * howHappy)) + "% chance of being negative");
	    		if(100*howHappy > 50)
	    		{
	    			homepagePositive++;
	    		}
	    		else
	    		{
	    			homepageNegative++;
	    		}
	    	}
	    	double homepageStatHolder = classifier.findPercentagePositivity(homepagePositive, homepageNegative);
	    	System.out.println("Your homepage is this positive: " 
				+ homepageStatHolder + "%");
	    	System.out.println("");

	    	//prompt user for what they would data from Twitter on then print
	    	System.out.println("Please input a search term for Tweets you'd like to analyze");
	    	String inputSearchTerm = scanner.nextLine();
	    	System.out.println("Please input the date you're interested in those Tweets for, use the format YYYY-MM-DD");
	    	String inputDate = scanner.nextLine();
	    	System.out.println("And how many? Integer please.");
	    	int inputQuantity = scanner.nextInt();

	    	TweetGatherer tweetGetter = new TweetGatherer(twitter);
	    	ArrayList<String> getMeTweets = tweetGetter.getWordDateQuery(inputSearchTerm, inputDate, inputQuantity);
	    	int searchPagePositive = 0;
	    	int searchPageNegative = 0;
	    	for(String tweet: getMeTweets)
	    	{
	    		double howHappy = classifier.classify(tweet);
	    		System.out.println("" + tweet);
	    		System.out.println("has a:" +(100 * howHappy) + "% chance of being positive");
	    		System.out.println("It has a " + (100 - (100 * howHappy)) + "% chance of being negative");
	    		if(100*howHappy > 50)
	    		{
	    			searchPagePositive++;
	    		}
	    		else
	    		{
	    			searchPageNegative++;
	    		}	
	    	}
		double thisPageStatHolder = classifier.findPercentagePositivity(searchPagePositive, searchPageNegative);
		System.out.println("The results of the search are this positive: " 
				+ thisPageStatHolder + "%");
		System.out.println("");
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
	    catch(TwitterException te)
	    {
	    	te.printStackTrace();
	    }

	}
}
