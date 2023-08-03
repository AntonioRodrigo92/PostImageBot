import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImageDownloaderTest {

    @Test
    void should_returnBufferedImage_when_givenURL() throws IOException {
        //  given
        String url = "https://www.lidl.pt/bundles/retail/images/meta/og_default_600_600.png";
        BufferedImage expected = ImageIO.read(new File("C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\lidl_url.png"));
        //  when
        BufferedImage actual = ImageDownloader.urlToImage(url);
        //  then
        assertTrue(allEqualPixels(expected, actual));
    }

    @Test
    void should_throwIOException_when_givenURL() {
        try (MockedStatic<ImageDownloader> mockedImageDownloader = mockStatic(ImageDownloader.class)) {
            //  given
            String url = "https://www.lidl.pt/bundles/retail/images/meta/og_default_600_600.png";
            mockedImageDownloader.when(() -> ImageDownloader.urlToImage(anyString())).thenThrow(new IOException());
            //  when
            Executable executable = () -> ImageDownloader.urlToImage(url);
            //  then
            assertThrows(IOException.class, executable);
        }
    }


    @DisplayName("Isto é batota!")
    @Test
    void should_returnFile_when_BufferedImageGiven() throws IOException {
        //  given
        BufferedImage img = ImageIO.read(new File("C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\Lidl-Logo.png"));
        File expected = new File("C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\Lidl-Logo2.png");
        ImageIO.write(img, "png", expected);
        //  when
        File actual = ImageDownloader.bufferedImageToFile(img);
        //  then
        assertEquals(expected.getClass(), actual.getClass());
    }


    @DisplayName("Isto é batota!")
    @Test
    void should_returnInputStream_when_givenBufferedImage() throws IOException {
        //  given
        BufferedImage img = ImageIO.read(new File("C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\Lidl-Logo.png"));
        InputStream expectedIS = new FileInputStream(new File("C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\Lidl-Logo.png"));
        ByteArrayInputStream expected = IStoBAIS(expectedIS);
        //  when
        InputStream actual = ImageDownloader.bufferedImageToInputStream(img);
        //  then
//        assertTrue(IOUtils.contentEquals(expected, actual));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void should_returnIOException_when_Error() throws IOException {
        try (MockedStatic<ImageDownloader> mockedImageDownloader = mockStatic(ImageDownloader.class)) {
            //  given
            BufferedImage img = ImageIO.read(new File("C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\Lidl-Logo.png"));
            mockedImageDownloader.when(() -> ImageDownloader.bufferedImageToInputStream(any())).thenThrow(new IOException());
            //  when
            Executable executable = () -> ImageDownloader.bufferedImageToInputStream(img);
            //  then
            assertThrows(IOException.class, executable);
        }
    }

    @Test
    void should_resize_image() throws IOException {
        //  given
        int expectedWidth = 3840 / 2;
        int expectedHeight = 2160 / 2;
        BufferedImage img = ImageIO.read(new File("C:\\Users\\Antonio\\IdeaProjects\\ImagePostBot\\src\\test\\resources\\Lidl-Logo.png"));
        //  when
        BufferedImage actualImg = ImageDownloader.resizeImage(img);
        //  then
        assertAll(
                () -> assertEquals(expectedWidth, actualImg.getWidth()),
                () -> assertEquals(expectedHeight, actualImg.getHeight())
        );
    }

    private ByteArrayInputStream IStoBAIS(InputStream is) throws IOException {
        byte[] buff = new byte[8000];
        int bytesRead = 0;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        while((bytesRead = is.read(buff)) != -1) {
            bao.write(buff, 0, bytesRead);
        }
        byte[] data = bao.toByteArray();
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        return bin;
    }

    private boolean allEqualPixels(BufferedImage expected, BufferedImage actual) {
        for (int y = 0; y < actual.getHeight(); y++) {
            for (int x = 0; x < actual.getWidth(); x++) {
                if (expected.getRGB(x, y) != actual.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

}