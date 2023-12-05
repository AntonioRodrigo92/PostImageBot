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

        String INSTAGRAM_USERNAME = pg.getInstagramUser();
        String INSTAGRAM_PASSWORD = pg.getInstagramPassword();

        SubRedditPost subRedditPost;
        int attempts = 0;

        while (attempts < 5) {
            try {
                subRedditPost = new SubRedditPost(USERNAME, PASSWORD, SUBREDDIT, "top");

                System.out.println("redditAPI created");
                InstagramAPI instagramAPI = new InstagramAPI(INSTAGRAM_USERNAME, INSTAGRAM_PASSWORD);
                System.out.println("tweetAPI created");

                String author = subRedditPost.getAuthor();
                String title = subRedditPost.getTitle();
                String imageUrl = subRedditPost.getImageUrl();
                String permalink = subRedditPost.getPermalink();

                System.out.println("author: " + author);
                System.out.println("title: " + title);
                System.out.println("imageURL: " + imageUrl);
                System.out.println("permalink: " + permalink);

                String comment = "\n" + title + "\n\nImagem postada por u/" + author + ". Obrigado!" +
                        "\n(link: " + permalink + ")";

                byte[] img = ImageDownloader.urlToByteArray(imageUrl);
                System.out.println("byte array successful");
                instagramAPI.postPic(img, comment);
                System.out.println("POSTED!");

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
