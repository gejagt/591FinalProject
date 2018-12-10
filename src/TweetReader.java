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
	public static final String CONSUMER_KEY = "L7VgTAwN8gjpW6uLrsQJABNnV";
	public static final String CONSUMER_SECRET = "hdtcXO0bobJNC5HNRAFrrGRjsdOC838e3NWatnlAeQcLA4qeHN";	
	public static final String ACCESS_TOKEN = "1070985899045986304-E9Iuqyq3X0q6PT6uuOIIFAoMdiBbuK";
	public static final String ACCESS_TOKEN_SECRET = "ERXQvND7Gj2JkhpxGz2eEcGJV3OaieGLzPUbENL7Gy1cG";
	
	public static void main(String[] args) throws IOException, TwitterException
	{	
//	    ConfigurationBuilder cb = new ConfigurationBuilder();
//	    cb.setDebugEnabled(true)
//	      .setOAuthConsumerKey(CONSUMER_KEY)
//	      .setOAuthConsumerSecret(CONSUMER_SECRET)
//	      .setOAuthAccessToken(ACCESS_TOKEN)
//	      .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
//	    TwitterFactory tf = new TwitterFactory(cb.build());
//	    Twitter twitter = tf.getInstance();
//
//	    List<Status> statuses = twitter.getHomeTimeline();
//	    TweetGatherer tweetGetter = new TweetGatherer(twitter);
//	    
//	    tweetGetter.locationQuery("Trump", 39.9526, 75.1652, "2018-12-08");
// 
//			for (Status status : statuses) 
//			{
//				System.out.println(status.getUser().getName() + ":" +
//						status.getText());
//			}
		File trainingSet = new File("C:\\Users\\Charles\\Downloads\\sentiment140\\training.1600000.processed.noemoticon.csv");
		TweetClassifier classifier = new TweetClassifier();
		classifier.calculateProbability(trainingSet);


	}
}
