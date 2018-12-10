import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.awt.List;
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
    public ArrayList<String> getWordDateQuery(String searchString, String date, int quantity)
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
    
//    public ArrayList<String> getSomeoneQuery(String searchUser)
//    {
//    	Paging paging = new Paging(1, 15);
//    	ArrayList<String> pageStatuses = new ArrayList<String>();
//    	try
//    	{
//        	Twitter unauthenticatedTwitter = new TwitterFactory().getInstance();
//        	pageStatuses.addAll(unauthenticatedTwitter.getUserTimeline(searchUser, paging));
//
//    	}
//    	catch(TwitterException te)
//    	{
//    		te.printStackTrace();
//    	}
//    	
//    	return pageStatuses;


//    }
}
