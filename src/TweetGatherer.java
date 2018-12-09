import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.IOException;
import java.util.ArrayList;

public class TweetGatherer 
{
	private Twitter twitter;
    private ArrayList<Status> statuses;
    
    TweetGatherer(Twitter twitter)
    {
    	this.twitter = twitter;
    	this.statuses = new ArrayList<Status>();
    }
    
    public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	public ArrayList<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(ArrayList<Status> statuses) {
		this.statuses = statuses;
	}
    public void locationQuery(String searchString, double latitude, double longitude, String date)
    {
    	Query queryOfInterest = new Query(searchString);
    	queryOfInterest.setCount(100);
//    	queryOfInterest.setGeoCode(new GeoLocation(latitude, longitude), 50, Query.MILES);
    	queryOfInterest.setSince(date);
    	
    	try
    	{
    		QueryResult result = twitter.search(queryOfInterest);
    		int counter = 0;
    		System.out.println("This many tweets found: " + result.getTweets().size());
    		for(Status tweet: result.getTweets())
    		{
    			counter++;
    			System.out.println("Tweet # " + counter + ": " + tweet.getText());
    		}
    	}
    	catch(TwitterException te)
    	{
    		te.printStackTrace();
    	}
    }
}
