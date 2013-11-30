package twitterAPI;



import java.util.Vector;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * 
 */

/**
 * @author user
 *
 */
public class Twitter_testing {

	/**
	 * @param args
	 */
	
	/*
	 
	Just a simple Processing and Twitter thingy majiggy
	 
	RobotGrrl.com
	 
	Code licensed under:
	CC-BY
	 
	*/
	 
	// First step is to register your Twitter application at dev.twitter.com
	// Once registered, you will have the info for the OAuth tokens
	// You can get the Access token info by clicking on the button on the
	// right on your twitter app's page
	// Good luck, and have fun!
	 
	// This is where you enter your Oauth info
	static String OAuthConsumerKey = "SlzgGdUxCJNdDOtnNyFaCA";
	static String OAuthConsumerSecret = "8Gq1uQRL410JYs5BksiD1Q5RpfRo1HhYueOu6jo";
	 
	// This is where you enter your Access Token info
	static String AccessToken = "2217976202-RCZisGUldqZwwQ2wJacBSEks3nBl2vOmf19X3Hj";
	static String AccessTokenSecret = "M7w451umoEnYxdxNgE7zuiJyFivxjOq4Is669kSWk7NsP";
	 
	// Just some random variables kicking around
	String myTimeline;
	java.util.List statuses = null;
	User[] friends;
	TwitterFactory twitterFactory;
	Twitter twitter;
	RequestToken requestToken;
	String[] theSearchTweets = new String[11];
	//variable buat nyimpen hasil search
	public Vector<searchHandler> shholder = new Vector<searchHandler>();
	 
	 
	public void setup() {
            //size(100,100);
            //background(0);
            
            connectTwitter();
            //sendTweet("Hey from Simple Processing woop woop #loadedsith #robotgirl");
	}
	 
	 
	void draw() {
            //background(0);
	}
	 
	 
	// Initial connection
	private void connectTwitter() {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setOAuthConsumerKey(OAuthConsumerKey);
            cb.setOAuthConsumerSecret( OAuthConsumerSecret );
            cb.setOAuthAccessToken( AccessToken);
            cb.setOAuthAccessTokenSecret( AccessTokenSecret );
            twitterFactory = new TwitterFactory(cb.build());
            twitter = twitterFactory.getInstance();
            System.out.println("connected");
	}
	 
	// Sending a tweet
	public void sendTweet(String t) {
            try {
                Status status = twitter.updateStatus(t);
                System.out.println("Successfully updated the status to [" + status.getText() + "].");
            } catch(TwitterException e) {
                System.out.println("Send tweet: " + e + " Status code: " + e.getStatusCode());
            }
	}
	 
	 
	// Loading up the access token
	private static twitter4j.auth.AccessToken loadAccessToken(){
            return new twitter4j.auth.AccessToken(AccessToken, AccessTokenSecret);
	}
	 
	 
	// Get your tweets
	public void getTimeline() {
            try {
                statuses = twitter.getUserTimeline();
            } catch(TwitterException e) {
                System.out.println("Get timeline: " + e + " Status code: " + e.getStatusCode());
            }

            for(int i=0; i<statuses.size(); i++) {
                Status status = (Status)statuses.get(i);
                System.out.println(status.getUser().getName() + ": " + status.getText());
            }
	}
	 
	 
	// Search for tweets
	public void getSearchTweets(String regex) {
            //String queryStr = "@RobotGrrl";
            try {
                //Twitter twitter = TwitterFactory.getSingleton();
                Query query = new Query(regex);
                QueryResult result = twitter.search(query);
                for (Status status : result.getTweets()) {
				
                 searchHandler sh = new searchHandler();
					sh.nama =  status.getUser().getScreenName();
					sh.tweet = status.getText();
					
					
					StringBuffer address = new StringBuffer();
					address.append("http://twitter.com/#!/");
					address.append(sh.nama);
					address.append("/status/");
					address.append(status.getId());

					String theAddressYouWant = address.toString();
					sh.URL = theAddressYouWant;
					shholder.add(sh);
					//System.out.println(theAddressYouWant); ini buat test bener apa ga url nya , udah bener
					//System.out.println("@" + sh.nama + ":" + sh.tweet);
                }
            } catch (TwitterException e) {
                System.out.println("Search tweets: " + e);
            }
	}
	/*public static void main(String[] args) {
		Twitter_testing test = new Twitter_testing();
		test.setup();
		//test.getTimeline();
		test.getSearchTweets();
		//test.sendTweet("testing kirim tweet  imbaaaa:v");
		
	}*/
}
