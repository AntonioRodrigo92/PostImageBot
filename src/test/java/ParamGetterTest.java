import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ParamGetterTest {
    private ParamGetter pg;

    @BeforeEach
    void setup() throws FileNotFoundException {
        String path = "C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\parameters.txt";
        pg = new ParamGetter(path);
    }

    @Test
    void should_returnRedditUsername_when_correctPath() {
        //  given
        String expected = "placeholder1";
        //  when
        String actual = pg.getRedditUsername();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnRedditPassword_when_correctPath() {
        //  given
        String expected = "placeholder2";
        //  when
        String actual = pg.getRedditPassword();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnSubReddit_when_correctPath() {
        //  given
        String expected = "earthPorn";
        //  when
        String actual = pg.getSubReddit();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnTwitterAPIKey_when_correctPath() {
        //  given
        String expected = "placeholder3";
        //  when
        String actual = pg.getTwitterAPIKey();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnTwitterAPISecret_when_correctPath() {
        //  given
        String expected = "placeholder4";
        //  when
        String actual = pg.getTwitterAPISecret();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnTwitterAccessToken_when_correctPath() {
        //  given
        String expected = "placeholder5";
        //  when
        String actual = pg.getTwitterAccessToken();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnTwitterAccessSecretToken_when_correctPath() {
        //  given
        String expected = "placeholder6";
        //  when
        String actual = pg.getTwitterAccessSecretToken();
        //  then
        assertEquals(expected, actual);
    }

    @Test
    void should_throwFileNotFoundException_when_incorrectPath() {
        //  given
        String incorrectPath = "C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\pas.txt";
        //  when
        Executable executable = () -> new ParamGetter(incorrectPath);
        //  then
        assertThrows(FileNotFoundException.class, executable);
    }

}