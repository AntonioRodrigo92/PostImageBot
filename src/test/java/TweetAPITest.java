import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twitter4j.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetAPITest {

    @InjectMocks
    private TweetAPI tweetAPI = new TweetAPI("", "", "", "");
    @Mock
    private Twitter twitterMock;



   @Test
   void should_throwException_when_getTimeLine() throws TwitterException {
       //   given
       when(twitterMock.getHomeTimeline()).thenThrow(TwitterException.class);
       //   when
       Executable executable = () -> tweetAPI.getTimeline();
       //   then
       assertThrows(TwitterException.class, executable);
   }

   @Disabled
   @Test
   void should_returnTimeLine() throws TwitterException {
       //   given
       ResponseList<Status> responses = null;
       when(twitterMock.getHomeTimeline()).thenReturn(responses);
       List<String> expected = null;
       //   when
       List<String> actual = tweetAPI.getTimeline();
       //   then
       assertEquals(expected, actual);
   }

    @Test
    void should_ThrowException_when_PostTweet() throws TwitterException {
        //  given
        when(twitterMock.updateStatus(anyString())).thenThrow(TwitterException.class);
        //  when
        Executable executable = () -> tweetAPI.postTweet(anyString());
        //  then
        assertThrows(TwitterException.class, executable);
    }

    @Test
    void should_call_updateStatus_once_when_PostTweet() throws TwitterException {
        //  given
        String tweet = "top kek";
        //  when
        tweetAPI.postTweet(tweet);
        //  then
        verify(twitterMock, times(1)).updateStatus(tweet);
    }

    @Test
    void should_throwException_when_postImageTweet() throws TwitterException, FileNotFoundException {
        //  given
        String tweet = "anyString()";
        InputStream img = new FileInputStream("C:\\Users\\Antonio\\Desktop\\HappyHotelApp\\class+diagram.png");
        String imgName = "anyString()";

        lenient().when(twitterMock.uploadMedia(anyString(), any())).thenThrow(TwitterException.class);
        lenient().when(twitterMock.updateStatus((String) any())).thenThrow(TwitterException.class);
        //  when
        Executable executable = () -> tweetAPI.postImageTweet(tweet, img, imgName);
        //  then
        assertThrows(TwitterException.class, executable);
    }

    @Disabled
    @Test
    void should_call_correctMethods_when_PostTweet() throws TwitterException, FileNotFoundException {
        //  given
        String tweet = "anyString()";
        InputStream img = new FileInputStream("C:\\Users\\Antonio\\Desktop\\HappyHotelApp\\class+diagram.png");
        String imgName = "anyString()";

        //  when
        tweetAPI.postImageTweet(tweet, img, imgName);
        //  then
        verify(twitterMock, times(1)).uploadMedia(imgName, img);
    }

}