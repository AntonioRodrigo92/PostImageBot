import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubRedditPostTest {
    private SubRedditPost subRedditPost;

    @BeforeEach
    void setup() {
        String username = "";
        String password = "";
        String subReddit = "earthPorn";
        String searchType = "top";
        subRedditPost = new SubRedditPost(username, password, subReddit, searchType);
    }

    @Test
    void should_returnPostAuthor_when_RinchoaStress() {
        //  given
        String expected = "peeweekid";
        //  when
        String actual = subRedditPost.getAuthor();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnPostTitle_when_RinchoaStress() {
        //  given
        String expected = "I drove 5,000 miles last summer and this was one of the most incredible spots I visited | Monument Valley, AZ [3527x4564][OC]";
        //  when
        String actual = subRedditPost.getTitle();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnImageURL_when_RinchoaStress() {
        //  given
        String expected = "https://i.redd.it/mp1e6x68xu191.jpg";
        //  when
        String actual = subRedditPost.getImageUrl();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnPostPermalink_when_RinchoaStress() {
        //  given
        String expected = "www.reddit.com/r/EarthPorn/comments/uyd48k/i_drove_5000_miles_last_summer_and_this_was_one/";
        //  when
        String actual = subRedditPost.getPermalink();
        //  then
        assertEquals(expected, actual);
    }

}
