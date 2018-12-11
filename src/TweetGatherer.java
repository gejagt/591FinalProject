import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TweetGatherer 
{
	private Twitter twitter;
    private ArrayList<Status> statuses;
    
    /**
     * TwitterGatherer constructor
     * @param twitter
     */
    TweetGatherer(Twitter twitter)
    {
    	this.twitter = twitter;
    	this.statuses = new ArrayList<Status>();
    }
    
    /**
     * getWordDateQuery scrapes Twitter for a user-provided string of interest with user-set parameters
     * @param searchString is the string of interest
     * @param date is the day of interest
     * @param quantity is the amount of tweets requested
     * @return ArrayList of requested tweets is returned
     * @throws TwitterException Problems with Twitter API are thrown
     */
    public ArrayList<String> getWordDateQuery(String searchString, String date, int quantity) throws TwitterException
    {
    	Query queryOfInterest = new Query(searchString);
    	queryOfInterest.setCount(quantity);
    	queryOfInterest.setSince(date);
    	ArrayList<String> tweetAggregate = new ArrayList<String>();
    	
    	try
    	{
    		QueryResult result = twitter.search(queryOfInterest);
    		int counter = 0;
    		System.out.println("This many tweets found: " + result.getTweets().size());
    		for(Status tweet: result.getTweets())
    		{
    			counter++;
    			tweetAggregate.add(tweet.getText());
    		}

    	}
    	catch(TwitterException te)
    	{
    		te.printStackTrace();
    	}
    	
    	return tweetAggregate;
    }
}
