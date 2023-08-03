import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class TweetAPI {
    private String api_key;
    private String api_secret_key;
    private String access_token;
    private String access_token_secret;
    private Twitter twitter;

    public TweetAPI(String api_key, String api_secret, String access_token, String access_token_secret) {
        this.api_key = api_key;
        this.api_secret_key = api_secret;
        this.access_token = access_token;
        this.access_token_secret = access_token_secret;
        authenticate();
    }

    private void authenticate() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(api_key)
                .setOAuthConsumerSecret(api_secret_key)
                .setOAuthAccessToken(access_token)
                .setOAuthAccessTokenSecret(access_token_secret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public List<String> getTimeline() throws TwitterException {
        return twitter.getHomeTimeline().stream()
                .map(item -> item.getText())
                .collect(Collectors.toList());
    }

    public void postTweet(String tweet) throws TwitterException {
        Status status = twitter.updateStatus(tweet);
        System.out.println("Tweet postado: " + status.getText());
    }

    public void postImageTweet(String tweet, InputStream img, String imgName) throws TwitterException {
        long[] mediaIds = new long[1];
        UploadedMedia media = twitter.uploadMedia(imgName, img);
        mediaIds[0] = media.getMediaId();

        StatusUpdate statusUpdate = new StatusUpdate(tweet);
        statusUpdate.setMediaIds(mediaIds);
        Status status = twitter.updateStatus(statusUpdate);

        System.out.println("Tweet postado: " + status.getText());
    }

}
