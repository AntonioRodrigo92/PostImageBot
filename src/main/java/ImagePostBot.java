import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImagePostBot {

    public static void main (String[] args) {
        String paramPath = args[0];
        ParamGetter pg = null;
        try {
            pg = new ParamGetter(paramPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String USERNAME = pg.getRedditUsername();
        String PASSWORD = pg.getRedditPassword();
        String SUBREDDIT = pg.getSubReddit();

        String API_KEY = pg.getTwitterAPIKey();
        String API_SECRET_KEY = pg.getTwitterAPISecret();
        String ACCESS_TOKEN = pg.getTwitterAccessToken();
        String ACCESS_TOKEN_SECRET = pg.getTwitterAccessSecretToken();

        SubRedditPost subRedditPost;
        int attempts = 0;

        while (attempts < 5) {
            try {
                subRedditPost = new SubRedditPost(USERNAME, PASSWORD, SUBREDDIT, "top");

                System.out.println("redditAPI created");
                TweetAPI tweetAPI = new TweetAPI(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
                System.out.println("tweetAPI created");

                String author = subRedditPost.getAuthor();
                String title = subRedditPost.getTitle();
                String imageUrl = subRedditPost.getImageUrl();
                String permalink = subRedditPost.getPermalink();
//                boolean isLocked = redditAPI.isLocked();

                String comment = "\n" + title + "\n\nImagem postada por u/" + author + ". Obrigado!" +
                        "\n(link: " + permalink + ")";
                BufferedImage img = ImageDownloader.urlToImage(imageUrl);

                int w = img.getWidth();
                int h = img.getHeight();

                System.out.println("w: " + w);
                System.out.println("h: " + h);

                while (w > 1200 || h > 675) {
                    img = ImageDownloader.resizeImage(img);
                    w = img.getWidth();
                    h = img.getHeight();

                    System.out.println("w: " + w);
                    System.out.println("h: " + h);
                }

                InputStream imgFile = ImageDownloader.bufferedImageToInputStream(img);
                tweetAPI.postImageTweet(comment, imgFile, title);
                break;

            } catch (Exception e) {
                e.printStackTrace();
                attempts++;
                System.out.println("attempts: " + attempts);
                try {
                    Thread.sleep(600000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
    }
}
