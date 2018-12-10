import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
 

public class TweetReader 
{
	public static final String CONSUMER_KEY = "Nw80TNOxfinETYstIYkk85Zvc";
	public static final String CONSUMER_SECRET = "iAKzkPsnlbJX6ttOXjuqfJ233DKGihpOcUE4psSELxTPdCxvbx";	
	public static final String ACCESS_TOKEN = "1070985899045986304-drLPwsrax742dfixYA2npCA0xnzqVV";
	public static final String ACCESS_TOKEN_SECRET = "wgnAVC6ymZ99jDLQvNlURI6Aag0Yo9gtkUJUVPpSt7V4E";
	
	public static void main(String[] args) throws IOException, TwitterException
	{	
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	      .setOAuthConsumerKey(CONSUMER_KEY)
	      .setOAuthConsumerSecret(CONSUMER_SECRET)
	      .setOAuthAccessToken(ACCESS_TOKEN)
	      .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    Twitter twitter = tf.getInstance();
	    
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);

	    List<Status> yourHomeStatuses = twitter.getHomeTimeline();
	    int homepagePositive = 0;
	    int homepageNegative = 0;
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
		
		System.out.println("Please input a search term for Tweets you'd like to analyze");
		Scanner scanner = new Scanner(System.in);
		String inputSearchTerm = scanner.nextLine();
		System.out.println("Please input the date you're interested in those Tweets for, use the format YYYY-MM-DD");
	    String inputDate = scanner.nextLine();
		System.out.println("And how many? Integer please.");
		int inputQuantity = scanner.nextInt();
	    
	    TweetGatherer tweetGetter = new TweetGatherer(twitter);
	    ArrayList<String> getMeTweets = tweetGetter.getWordDateQuery(inputSearchTerm, inputDate, inputQuantity);
		double searchPagePositive = 0;
		double searchPageNegative = 0;
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
		
//		System.out.println("Any page you'd like to find some Tweets from?");
//		String profileInput = scanner.nextLine();
	}
}
