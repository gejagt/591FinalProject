import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

class TweetGathererTest {
	public static final String CONSUMER_KEY = "Nw80TNOxfinETYstIYkk85Zvc";
	public static final String CONSUMER_SECRET = "iAKzkPsnlbJX6ttOXjuqfJ233DKGihpOcUE4psSELxTPdCxvbx";	
	public static final String ACCESS_TOKEN = "1070985899045986304-drLPwsrax742dfixYA2npCA0xnzqVV";
	public static final String ACCESS_TOKEN_SECRET = "wgnAVC6ymZ99jDLQvNlURI6Aag0Yo9gtkUJUVPpSt7V4E";


	/**
	 * Test to see if Twitter really does cap the amount of tweets per query
	 */
	@Test
	void testgetWordDateQueryLimit() 
	{
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	      .setOAuthConsumerKey(CONSUMER_KEY)
	      .setOAuthConsumerSecret(CONSUMER_SECRET)
	      .setOAuthAccessToken(ACCESS_TOKEN)
	      .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
	    TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		ArrayList<String> tweetHolder = new ArrayList<>();
		TweetGatherer tweetGetter = new TweetGatherer(twitter);
	    try
	    {
		tweetHolder = tweetGetter.getWordDateQuery("Trump", "2018-12-09", 5000);
	    }
	    catch(TwitterException te)
	    {
	    	te.printStackTrace();
	    }
		assertTrue(tweetHolder.size() < 1000);
	}
	/**
	 * Test to see if a request for an illegal amount of tweets (-1) defaults to a positive value
	 */
	@Test
	void testgetWordDateQueryIllegalInput() 
	{
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(CONSUMER_KEY)
      .setOAuthConsumerSecret(CONSUMER_SECRET)
      .setOAuthAccessToken(ACCESS_TOKEN)
      .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
    TwitterFactory tf = new TwitterFactory(cb.build());
	Twitter twitter = tf.getInstance();
	ArrayList<String> tweetHolder = new ArrayList<>();
	TweetGatherer tweetGetter = new TweetGatherer(twitter);
    try
    {
	tweetHolder = tweetGetter.getWordDateQuery("Trump", "2018-12-09", -1);
    }
    catch(TwitterException e)
    {
    	e.printStackTrace();
    }
	assertTrue(tweetHolder.size() > 0);
	}
	/**
	 * Test to see if tweets from a non-existent date will not be returned
	 */
	@Test
	void testgetWordDateQueryNonExistentDate() 
	{
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(CONSUMER_KEY)
      .setOAuthConsumerSecret(CONSUMER_SECRET)
      .setOAuthAccessToken(ACCESS_TOKEN)
      .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
    TwitterFactory tf = new TwitterFactory(cb.build());
	Twitter twitter = tf.getInstance();
	TweetGatherer tweetGetter = new TweetGatherer(twitter);
	ArrayList<String> tweetHolder = new ArrayList<>();
    try
    {
    	tweetHolder = tweetGetter.getWordDateQuery("Trump", "2018-12-00", 10);	
    }
    catch(TwitterException e)
    {
    	e.printStackTrace();
    }
	assertTrue(tweetHolder.size() == 0);
	}
	/** 
	 * Test to see if a search for tweets with a future time stamp returns nothing
	 */
	@Test
	void testgetWordDateQueryFutureDate() 
	{
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(CONSUMER_KEY)
      .setOAuthConsumerSecret(CONSUMER_SECRET)
      .setOAuthAccessToken(ACCESS_TOKEN)
      .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
    TwitterFactory tf = new TwitterFactory(cb.build());
	Twitter twitter = tf.getInstance();
	TweetGatherer tweetGetter = new TweetGatherer(twitter);
	ArrayList<String> tweetHolder = new ArrayList<>();
    try
    {
    	tweetHolder = tweetGetter.getWordDateQuery("Trump", "2030-12-00", 10);	
    }
    catch(TwitterException te)
    {
    	te.printStackTrace();
    }
	assertTrue(tweetHolder.size() == 0);
	}
	
	/** 
	 * Test to see if a search for tweets with a past time stamp unavailable online 
	 * is ignored and defaults to another date (typically more recent tweets are displayed)
	 */
	@Test
	void testgetWordDateQueryPastDate() 
	{
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(CONSUMER_KEY)
      .setOAuthConsumerSecret(CONSUMER_SECRET)
      .setOAuthAccessToken(ACCESS_TOKEN)
      .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
    TwitterFactory tf = new TwitterFactory(cb.build());
	Twitter twitter = tf.getInstance();
	TweetGatherer tweetGetter = new TweetGatherer(twitter);
	ArrayList<String> tweetHolder = new ArrayList<>();
    try
    {
    	tweetHolder = tweetGetter.getWordDateQuery("Trump", "2005-12-01", 10);	
    }
    catch(TwitterException te)
    {
    	te.printStackTrace();
    }
	assertTrue(tweetHolder.size() > 0);
	}
}
